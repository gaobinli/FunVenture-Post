package com.nova.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.ext.BasePService;
import com.baomidou.mybatisplus.ext.IPService;
import com.baomidou.mybatisplus.ext.Pager;
import com.baomidou.mybatisplus.ext.QueryParamUtils;
import com.nova.domain.dto.res.SysUserResDto;
import com.nova.domain.model.SysUserEntity;
import com.nova.mapper.SysRoleMapper;
import com.nova.mapper.SysUserMapper;
import com.nova.mapper.SysUserRoleMapper;
import com.nova.service.SysUserService;
import com.nova.common.exception.AppException;
import com.nova.common.exception.ErrorCode;
import com.nova.common.util.ConditionUtil;
import com.nova.common.util.MapUtil;
import com.nova.common.util.SecurityUtil;
import com.google.common.base.CaseFormat;
import com.nova.common.convertor.SysUserReqDtoConvertor;
import com.nova.common.convertor.SysUserResDtoConvertor;
import com.nova.domain.dto.req.SysUserReqDto;
import com.nova.domain.po.SysRolePo;
import com.nova.domain.po.SysUserPo;
import com.nova.domain.po.SysUserRolePo;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Data
@Slf4j
@Service("sysUserAppService")
@Transactional(rollbackFor = Exception.class)
public class SysUserServiceImpl implements SysUserService {

    @Autowired
    private SysUserReqDtoConvertor sysUserReqDtoConvertor;

    @Autowired
    private SysUserResDtoConvertor sysUserResDtoConvertor;
    @Autowired
    private SysUserRoleMapper sysUserRoleMapper;
    @Autowired
    private SysRoleMapper sysRoleMapper;

    private IPService<SysUserPo> sysUserPoService;

    @SuppressWarnings("SpringJavaAutowiringInspection")
    public SysUserServiceImpl(SysUserMapper sysUserMapper) {
        this.sysUserPoService = new BasePService(sysUserMapper);
    }


    @Override
    public SysUserResDto save(SysUserReqDto reqDto) {
        String userCode = reqDto.getCode();
        checkCodeRepeat(userCode);

        List<String> roleCodes = reqDto.getRoleCodes();
        if (!CollectionUtils.isEmpty(roleCodes)) {
            List<SysUserRolePo> sysUserRolePos = new ArrayList<>();
            roleCodes.stream().forEach(roleCode -> {
                SysUserRolePo sysUserRolePo = new SysUserRolePo();
                sysUserRolePo.setRoleCode(roleCode);
                sysUserRolePo.setUserCode(userCode);
                sysUserRolePos.add(sysUserRolePo);
            });
            if (!CollectionUtils.isEmpty(sysUserRolePos)) {
                for (SysUserRolePo sysUserRolePo : sysUserRolePos) {
                    sysUserRoleMapper.insert(sysUserRolePo);
                }
            }
        }

        if (StringUtils.isBlank(reqDto.getPassword())) {
            reqDto.setPassword(SecurityUtil.encryptPassword("123456"));
        } else {
            reqDto.setPassword(SecurityUtil.encryptPassword(reqDto.getPassword()));
        }

        SysUserPo po = sysUserReqDtoConvertor.dto2Po(reqDto);
        sysUserPoService.save(po);
        SysUserResDto resDto = sysUserResDtoConvertor.po2Dto(po);
        return resDto;
    }

    public void checkCodeRepeat(String code) {
        List<SysUserPo> list = sysUserPoService.getList(new MapUtil().put("qp-code-eq", code), SysUserPo.class);
        ConditionUtil.validCollectionIsEmpty(list, "账号不可重复");
    }

    @Override
    public SysUserResDto info() {
        SysUserEntity sysUser = SecurityUtil.getLoginUser().getSysUser();
        return getInfo(sysUser.getCode());
    }

    @Override
    public boolean updatePassword(Long id, SysUserReqDto reqDto) {
        SysUserPo sysUserPo = sysUserPoService.getById(id);
        if (!SecurityUtil.matches(reqDto.getPassword(), sysUserPo.getPassword())) {
            throw new AppException("请输入正确的原密码");
        }
        reqDto.setPassword(SecurityUtil.encryptPassword(reqDto.getNewPassword()));
        SysUserPo po = sysUserReqDtoConvertor.dto2Po(reqDto);
        po.setId(id);
        return sysUserPoService.updateById(po);
    }

    @Override
    public boolean deleteByIds(String ids) {
        try {
        List<Long> idLongList =
        Arrays.asList(ids.split(",")).stream().map(s -> NumberUtils.createLong(s.trim())).collect(Collectors.toList());
        return sysUserPoService.removeByIds(idLongList);
        } catch (Exception e) {
        throw new AppException("参数错误：" + ids, ErrorCode.sys("417"), e);
        }
     }

    @Override
    public Pager<SysUserResDto> doPager(Map<String, Object> params) {
        params.put("qp-id-ne", 1);
        Pager<SysUserPo> poPager = sysUserPoService.queryPage(params, SysUserPo.class);
        Pager<SysUserResDto> resDtoPager = sysUserResDtoConvertor.convertPoPager2ResDtoPager(poPager);
        dealUserRoleInfo(resDtoPager.getList());
        return resDtoPager;
    }

    public void dealUserRoleInfo(List<SysUserResDto> sysUserResDtos) {
        if (!CollectionUtils.isEmpty(sysUserResDtos)) {
            List<String> userCodes = sysUserResDtos.stream()
                    .map(SysUserResDto::getCode)
                    .collect(Collectors.toList());
            if (CollectionUtils.isEmpty(userCodes)) {
                return;
            }
            List<SysUserRolePo> userRoleList = sysUserRoleMapper.selectList(Wrappers.<SysUserRolePo>lambdaQuery()
                    .in(SysUserRolePo::getUserCode, userCodes));
            if (CollectionUtils.isEmpty(userRoleList)) {
                return;
            }
            List<String> roleCodes = userRoleList.stream()
                    .map(SysUserRolePo::getRoleCode)
                    .collect(Collectors.toList());
            List<SysRolePo> roleList = sysRoleMapper.selectList(Wrappers.<SysRolePo>lambdaQuery()
                    .in(SysRolePo::getCode, roleCodes));

            Map<String, List<SysUserRolePo>> userRoleMap = userRoleList.stream()
                    .collect(Collectors.groupingBy(SysUserRolePo::getUserCode));
            Map<String, SysRolePo> roleMap = roleList.stream()
                    .collect(Collectors.toMap(SysRolePo::getCode, o -> o));
            sysUserResDtos.stream().forEach(res -> {
                List<SysUserRolePo> userRolePoList = userRoleMap.get(res.getCode());
                if (CollectionUtils.isEmpty(userRolePoList)) {
                    return;
                }
                List<String> roleNames = new ArrayList<>();
                userRolePoList.stream().forEach(userRole -> {
                    SysRolePo sysRole = roleMap.get(userRole.getRoleCode());
                    if (sysRole == null) {
                        return;
                    }
                    roleNames.add(sysRole.getName());
                });
                if (CollectionUtils.isEmpty(roleNames)) {
                    return;
                }
                String roleNameStr = roleNames.stream().collect(Collectors.joining(","));
                res.setRoleNames(roleNameStr);
            });
        }
    }

    @Override
    public SysUserResDto getInfo(String code) {
        SysUserPo sysUserPo = sysUserPoService.getOne(Wrappers.<SysUserPo>lambdaQuery().eq(SysUserPo::getCode, code));
        return sysUserResDtoConvertor.po2Dto(sysUserPo);
    }

    @Override
    public SysUserResDto selectOne(Long id) {
        SysUserPo po = sysUserPoService.getById(id);
        SysUserResDto resDto = sysUserResDtoConvertor.po2Dto(po);
        return resDto;
    }

    @Override
    public SysUserResDto selectOne(Map<String, Object> params) {
        QueryWrapper queryWrapper = QueryParamUtils.queryWrapper4eq(SysUserPo::new, params);
        SysUserPo po = sysUserPoService.getOne(queryWrapper);
        SysUserResDto resDto = sysUserResDtoConvertor.po2Dto(po);
        return resDto;
    }

    @Override
    public boolean updateProps(Long id, Map<String, Object> params) {
        UpdateWrapper<SysUserPo> updateWrapper = QueryParamUtils.updateWrapper4Map(SysUserPo::new, id, params);
        return sysUserPoService.update(new SysUserPo(), updateWrapper);
        }

    @Override
    public boolean updateProps(Long id, SysUserReqDto reqDto) {
        String userCode = reqDto.getCode();
        List<String> roleCodes = reqDto.getRoleCodes();
        List<SysUserRolePo> sysUserRolePos = new ArrayList<>();
        if (!CollectionUtils.isEmpty(roleCodes)) {
            roleCodes.stream().forEach(roleCode -> {
                SysUserRolePo sysUserRolePo = new SysUserRolePo();
                sysUserRolePo.setRoleCode(roleCode);
                sysUserRolePo.setUserCode(userCode);
                sysUserRolePos.add(sysUserRolePo);
            });
            if (!CollectionUtils.isEmpty(sysUserRolePos)) {
                sysUserRoleMapper.delete(Wrappers.<SysUserRolePo>lambdaQuery().eq(SysUserRolePo::getUserCode, userCode));
                for (SysUserRolePo sysUserRolePo : sysUserRolePos) {
                    sysUserRoleMapper.insert(sysUserRolePo);
                }
            }
        } else {
            sysUserRoleMapper.delete(Wrappers.<SysUserRolePo>lambdaQuery().eq(SysUserRolePo::getUserCode, userCode));
        }

        if (StringUtils.isNotBlank(reqDto.getPassword())) {
            reqDto.setPassword(SecurityUtil.encryptPassword(reqDto.getPassword()));
        } else {
            reqDto.setPassword(null);
        }

        SysUserPo po = sysUserReqDtoConvertor.dto2Po(reqDto);
        po.setId(id);
        return sysUserPoService.updateById(po);
    }


    @Override
    public boolean updateAllProps(Long id, SysUserReqDto reqDto) {
        UpdateWrapper<SysUserPo> updateWrapper = Wrappers.update();
        updateWrapper.eq("id", id);
        Field[] fields = SysUserReqDto.class.getDeclaredFields();
        Arrays.stream(fields).filter(f -> !Modifier.isStatic(f.getModifiers())).forEach(field -> {
        try {
        PropertyDescriptor propertyDescriptor = new PropertyDescriptor(field.getName(), SysUserReqDto.class);
        Method getMethod = propertyDescriptor.getReadMethod();
        String fileNameCamel = getMethod.getName().substring(3);
        String fileNameUnderline = CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, fileNameCamel);
        updateWrapper.set(fileNameUnderline, getMethod.invoke(reqDto));
        } catch (Exception ex) {
        log.warn("属性不存在get方法："+field.getName(),ex);
        }
        });
        return sysUserPoService.update(new SysUserPo(), updateWrapper);
    }

}