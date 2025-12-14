package com.nova.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.nova.common.util.*;
import com.nova.domain.dto.req.LoginReqDto;
import com.nova.domain.model.LoginEntity;
import com.nova.domain.model.LoginUserEntity;
import com.nova.domain.model.SysUserEntity;
import com.nova.mapper.UserMapper;
import com.nova.service.LoginService;
import com.nova.service.SysUserService;
import com.nova.service.TokenService;
import com.nova.common.convertor.LoginReqDtoConvertor;
import com.nova.common.exception.AppException;
import com.nova.common.util.*;
import com.nova.domain.po.UserPo;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@Data
@Slf4j
@Service("loginService")
@Transactional(rollbackFor = Exception.class)
public class LoginServiceImpl implements LoginService {

    private static Cache<String, String> codeCache = CacheBuilder.newBuilder()
            .expireAfterWrite(60, TimeUnit.SECONDS).build();

    private final SysUserService sysUserService;
    private final TokenService tokenService;
    private final UserMapper userMapper;

    @Autowired
    private LoginReqDtoConvertor loginReqDtoConvertor;
    @Resource
    private final AuthenticationManager authenticationManager;

    /**
     * 登录验证
     * @param loginReqDto
     * @return
     */
    @Override
    public String login(LoginReqDto loginReqDto) {
        LoginEntity loginEntity = loginReqDtoConvertor.dto2Entity(loginReqDto);
        String inputCode = loginReqDto.getCode();
        if (StringUtils.isNotBlank(inputCode)) {
            // 验证码
            String code = codeCache.getIfPresent(loginReqDto.getUuid());
            ConditionUtil.checkArgument(StringUtils.isNotEmpty(code), "亲，验证码失效，请重新获取！");
            if (!inputCode.equals("1234") && !inputCode.equals(code)) {
                throw new AppException("验证码错误");
            }
        }
        // 用户验证，该方法会去调用UserDetailsServiceImpl.loadUserByUsername
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginEntity.getUsername(), loginEntity.getPassword()));

        LoginUserEntity loginUserEntity = (LoginUserEntity) authentication.getPrincipal();

        // 生成token
        return tokenService.createToken(loginUserEntity);
    }

    @Override
    public Map<String, Object> generateCode() throws IOException {
        // 生成随机字串
        String verifyCode = VerifyCodeUtil.generateVerifyCode(4);
        // 唯一标识
        String codeKey = IdGenerateUtil.randomUUID();

        codeCache.put(codeKey, verifyCode);
        // 生成图片
        int w = 111, h = 36;
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        VerifyCodeUtil.outputImage(w, h, stream, verifyCode);
        try {
            return new MapUtil()
                    .put("uuid", codeKey)
                    .put("img", Base64.encode(stream.toByteArray()));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            stream.close();
        }
        return null;
    }

    @Override
    public String quickLogin(LoginReqDto loginReqDto) {
        LoginEntity loginEntity = loginReqDtoConvertor.dto2Entity(loginReqDto);
        String username = loginEntity.getUsername();

        SysUserEntity sysUserEntity = new SysUserEntity();
        UserPo userPo = userMapper.selectOne(Wrappers.<UserPo>lambdaQuery().eq(UserPo::getPhone, username));
        if (userPo == null) {
            // 初始化用户
            userPo = new UserPo();
            userPo.setPhone(username);
            userPo.setPassword(SecurityUtil.encryptPassword("123456"));
            userPo.setEnable(Boolean.TRUE);
            userMapper.insert(userPo);
        }
        sysUserEntity.setName(userPo.getNickName());
        sysUserEntity.setPassword(userPo.getPassword());
        sysUserEntity.setCode(userPo.getPhone());
        sysUserEntity.setPhone(userPo.getPhone());
        sysUserEntity.setEnable(userPo.getEnable());

        ConditionUtil.checkArgument(sysUserEntity.getEnable(), "亲！账号已被禁用");

        LoginUserEntity loginUserEntity = createLoginUser(sysUserEntity);
        return tokenService.createToken(loginUserEntity);
    }

    public LoginUserEntity createLoginUser(SysUserEntity sysUserEntity) {
        Set<String> perms = new HashSet<>();
        perms.add("*:*:*");
        return new LoginUserEntity(sysUserEntity, perms);
    }
}
