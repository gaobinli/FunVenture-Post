package com.nova.service.impl;


import com.nova.domain.dto.req.FoodReqDto;
import com.nova.domain.dto.res.FoodResDto;
import com.nova.mapper.FoodMapper;
import com.nova.service.FoodService;
import com.nova.common.convertor.FoodReqDtoConvertor;
import com.nova.common.convertor.FoodResDtoConvertor;
import com.nova.domain.po.FoodPo;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.google.common.base.CaseFormat;
import com.nova.common.exception.AppException;
import com.nova.common.exception.ErrorCode;
import com.baomidou.mybatisplus.ext.BasePService;
import com.baomidou.mybatisplus.ext.IPService;
import com.baomidou.mybatisplus.ext.Pager;
import com.baomidou.mybatisplus.ext.QueryParamUtils;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
@Service("foodAppService")
@Transactional(rollbackFor = Exception.class)
public class FoodServiceImpl implements FoodService {

    @Autowired
    private FoodReqDtoConvertor foodReqDtoConvertor;

    @Autowired
    private FoodResDtoConvertor foodResDtoConvertor;

    private IPService<FoodPo> foodPoService;

    @SuppressWarnings("SpringJavaAutowiringInspection")
    public FoodServiceImpl(FoodMapper foodMapper) {
        this.foodPoService = new BasePService(foodMapper);
    }


    @Override
    public FoodResDto save(FoodReqDto reqDto) {
        FoodPo po = foodReqDtoConvertor.dto2Po(reqDto);
        foodPoService.save(po);
        FoodResDto resDto = foodResDtoConvertor.po2Dto(po);
        return resDto;
    }

    @Override
    public List<FoodResDto> getList() {
        List<FoodPo> list = foodPoService.list();
        return foodResDtoConvertor.poList2DtoList(list);
    }

    @Override
    public boolean deleteByIds(String ids) {
        try {
        List<Long> idLongList =
        Arrays.asList(ids.split(",")).stream().map(s -> NumberUtils.createLong(s.trim())).collect(Collectors.toList());
        return foodPoService.removeByIds(idLongList);
        } catch (Exception e) {
        throw new AppException("参数错误：" + ids, ErrorCode.sys("417"), e);
        }
     }

    @Override
    public Pager<FoodResDto> doPager(Map<String, Object> params) {
        Pager<FoodPo> poPager = foodPoService.queryPage(params, FoodPo.class);
        Pager<FoodResDto> resDtoPager = foodResDtoConvertor.convertPoPager2ResDtoPager(poPager);
        return resDtoPager;
    }

    @Override
    public FoodResDto selectOne(Long id) {
        FoodPo po = foodPoService.getById(id);
        FoodResDto resDto = foodResDtoConvertor.po2Dto(po);
        return resDto;
    }

    @Override
    public FoodResDto selectOne(Map<String, Object> params) {
        QueryWrapper queryWrapper = QueryParamUtils.queryWrapper4eq(FoodPo::new, params);
        FoodPo po = foodPoService.getOne(queryWrapper);
        FoodResDto resDto = foodResDtoConvertor.po2Dto(po);
        return resDto;
    }

    @Override
    public boolean updateProps(Long id, Map<String, Object> params) {
        UpdateWrapper<FoodPo> updateWrapper = QueryParamUtils.updateWrapper4Map(FoodPo::new, id, params);
        return foodPoService.update(new FoodPo(), updateWrapper);
        }

    @Override
    public boolean updateProps(Long id, FoodReqDto reqDto) {
        FoodPo po = foodReqDtoConvertor.dto2Po(reqDto);
        po.setId(id);
        return foodPoService.updateById(po);
    }


    @Override
    public boolean updateAllProps(Long id, FoodReqDto reqDto) {
        UpdateWrapper<FoodPo> updateWrapper = Wrappers.update();
        updateWrapper.eq("id", id);
        Field[] fields = FoodReqDto.class.getDeclaredFields();
        Arrays.stream(fields).filter(f -> !Modifier.isStatic(f.getModifiers())).forEach(field -> {
        try {
        PropertyDescriptor propertyDescriptor = new PropertyDescriptor(field.getName(), FoodReqDto.class);
        Method getMethod = propertyDescriptor.getReadMethod();
        String fileNameCamel = getMethod.getName().substring(3);
        String fileNameUnderline = CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, fileNameCamel);
        updateWrapper.set(fileNameUnderline, getMethod.invoke(reqDto));
        } catch (Exception ex) {
        log.warn("属性不存在get方法："+field.getName(),ex);
        }
        });
        return foodPoService.update(new FoodPo(), updateWrapper);
    }

}