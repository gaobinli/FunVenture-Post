package com.nova.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.ext.BasePService;
import com.baomidou.mybatisplus.ext.IPService;
import com.baomidou.mybatisplus.ext.Pager;
import com.baomidou.mybatisplus.ext.QueryParamUtils;
import com.google.common.base.CaseFormat;
import com.nova.domain.dto.req.PwdReqDto;
import com.nova.domain.dto.res.UserResDto;
import com.nova.domain.model.LoginUserEntity;
import com.nova.mapper.UserMapper;
import com.nova.service.TokenService;
import com.nova.service.UserService;
import com.nova.common.convertor.UserReqDtoConvertor;
import com.nova.common.convertor.UserResDtoConvertor;
import com.nova.common.exception.AppException;
import com.nova.common.exception.ErrorCode;
import com.nova.common.util.ConditionUtil;
import com.nova.common.util.SecurityUtil;
import com.nova.domain.dto.req.UserReqDto;
import com.nova.domain.po.UserPo;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Data
@Slf4j
@Service("userAppService")
@Transactional(rollbackFor = Exception.class)
public class UserServiceImpl implements UserService {

    @Autowired
    private UserReqDtoConvertor userReqDtoConvertor;

    @Autowired
    private UserResDtoConvertor userResDtoConvertor;

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private IPService<UserPo> userPoService;

    @SuppressWarnings("SpringJavaAutowiringInspection")
    public UserServiceImpl(UserMapper userMapper) {
        this.userPoService = new BasePService(userMapper);
    }


    @Override
    public UserResDto save(UserReqDto reqDto) {
        if (StringUtils.isBlank(reqDto.getPassword())) {
            reqDto.setPassword(reqDto.getPhone());
        }
        reqDto.setPassword(SecurityUtil.encryptPassword(reqDto.getPassword()));

        UserPo po = userReqDtoConvertor.dto2Po(reqDto);
        userPoService.save(po);
        UserResDto resDto = userResDtoConvertor.po2Dto(po);
        return resDto;
    }

    @Override
    public List<UserResDto> getList() {
        List<UserPo> list = userPoService.list();
        return userResDtoConvertor.poList2DtoList(list);
    }

    @Override
    public boolean deleteByIds(String ids) {
        try {
        List<Long> idLongList =
        Arrays.asList(ids.split(",")).stream().map(s -> NumberUtils.createLong(s.trim())).collect(Collectors.toList());
        return userPoService.removeByIds(idLongList);
        } catch (Exception e) {
        throw new AppException("参数错误：" + ids, ErrorCode.sys("417"), e);
        }
     }

    @Override
    public Pager<UserResDto> doPager(Map<String, Object> params) {
        Pager<UserPo> poPager = userPoService.queryPage(params, UserPo.class);
        Pager<UserResDto> resDtoPager = userResDtoConvertor.convertPoPager2ResDtoPager(poPager);
        return resDtoPager;
    }

    @Override
    public UserResDto selectOne(Long id) {
        UserPo po = userPoService.getById(id);
        UserResDto resDto = userResDtoConvertor.po2Dto(po);
        return resDto;
    }

    @Override
    public UserResDto selectOne(Map<String, Object> params) {
        QueryWrapper queryWrapper = QueryParamUtils.queryWrapper4eq(UserPo::new, params);
        UserPo po = userPoService.getOne(queryWrapper);
        UserResDto resDto = userResDtoConvertor.po2Dto(po);
        return resDto;
    }

    @Override
    public boolean updateProps(Long id, Map<String, Object> params) {
        UpdateWrapper<UserPo> updateWrapper = QueryParamUtils.updateWrapper4Map(UserPo::new, id, params);
        return userPoService.update(new UserPo(), updateWrapper);
    }

    @Override
    public boolean updateProps(Long id, UserReqDto reqDto) {
        if (StringUtils.isNotBlank(reqDto.getPassword())) {
            reqDto.setPassword(SecurityUtil.encryptPassword(reqDto.getPassword()));
        }
        UserPo po = userReqDtoConvertor.dto2Po(reqDto);
        po.setId(id);
        return userPoService.updateById(po);
    }


    @Override
    public boolean updateAllProps(Long id, UserReqDto reqDto) {
        UpdateWrapper<UserPo> updateWrapper = Wrappers.update();
        updateWrapper.eq("id", id);
        Field[] fields = UserReqDto.class.getDeclaredFields();
        Arrays.stream(fields).filter(f -> !Modifier.isStatic(f.getModifiers())).forEach(field -> {
        try {
        PropertyDescriptor propertyDescriptor = new PropertyDescriptor(field.getName(), UserReqDto.class);
        Method getMethod = propertyDescriptor.getReadMethod();
        String fileNameCamel = getMethod.getName().substring(3);
        String fileNameUnderline = CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, fileNameCamel);
        updateWrapper.set(fileNameUnderline, getMethod.invoke(reqDto));
        } catch (Exception ex) {
        log.warn("属性不存在get方法："+field.getName(),ex);
        }
        });
        return userPoService.update(new UserPo(), updateWrapper);
    }

    @Override
    public boolean pwd(Long id, PwdReqDto pwdReqDto) {
        UserPo userPo = userPoService.getById(id);
        ConditionUtil.checkArgument(passwordEncoder.matches(pwdReqDto.getPwd(), userPo.getPassword()), "原密码输入错误");
        ConditionUtil.checkArgument(pwdReqDto.getNewPwd().equals(pwdReqDto.getReNewPwd()), "两次输入的新密码不一致");


        UserPo user = new UserPo();
        user.setId(id);
        user.setPassword(SecurityUtil.encryptPassword(pwdReqDto.getNewPwd()));
        userPoService.updateById(user);
        return true;
    }

    @Override
    public void register(UserReqDto userReqDto) {
        String phone = userReqDto.getPhone();
        UserPo userPo = userPoService.getOne(Wrappers.<UserPo>lambdaQuery()
                .eq(UserPo::getPhone, phone));
        ConditionUtil.checkArgument(userPo == null, "手机号为 " + phone + " 的用户已存在");
        userReqDto.setPassword(SecurityUtil.encryptPassword(userReqDto.getPassword()));
        UserPo po = userReqDtoConvertor.dto2Po(userReqDto);
        userPoService.save(po);
    }

    @Override
    public UserResDto getInfo() {
        LoginUserEntity loginUser = tokenService.getLoginUser(request);
        UserPo userPo = userPoService.getOne(Wrappers.<UserPo>lambdaQuery().eq(UserPo::getPhone, loginUser.getSysUser().getPhone()));
        return userResDtoConvertor.po2Dto(userPo);
    }
}