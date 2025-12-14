package com.nova.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.ext.BasePService;
import com.baomidou.mybatisplus.ext.IPService;
import com.baomidou.mybatisplus.ext.Pager;
import com.baomidou.mybatisplus.ext.QueryParamUtils;
import com.google.common.base.CaseFormat;
import com.nova.domain.dto.req.CarouselReqDto;
import com.nova.domain.dto.res.CarouselResDto;
import com.nova.mapper.CarouselMapper;
import com.nova.service.CarouselService;
import com.nova.common.convertor.CarouselReqDtoConvertor;
import com.nova.common.convertor.CarouselResDtoConvertor;
import com.nova.common.exception.AppException;
import com.nova.common.exception.ErrorCode;
import com.nova.domain.po.CarouselPo;
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
@Service("carouselAppService")
@Transactional(rollbackFor = Exception.class)
public class CarouselServiceImpl implements CarouselService {

    @Autowired
    private CarouselReqDtoConvertor carouselReqDtoConvertor;

    @Autowired
    private CarouselResDtoConvertor carouselResDtoConvertor;

    private IPService<CarouselPo> carouselPoService;

    @SuppressWarnings("SpringJavaAutowiringInspection")
    public CarouselServiceImpl(CarouselMapper carouselMapper) {
        this.carouselPoService = new BasePService(carouselMapper);
    }


    @Override
    public CarouselResDto save(CarouselReqDto reqDto) {
        CarouselPo po = carouselReqDtoConvertor.dto2Po(reqDto);
        carouselPoService.save(po);
        CarouselResDto resDto = carouselResDtoConvertor.po2Dto(po);
        return resDto;
    }

    @Override
    public List<CarouselResDto> getList() {
        List<CarouselPo> list = carouselPoService.list(Wrappers.<CarouselPo>lambdaQuery()
                .orderByDesc(CarouselPo::getSort));
        return carouselResDtoConvertor.poList2DtoList(list);
    }

    @Override
    public boolean deleteByIds(String ids) {
        try {
        List<Long> idLongList =
        Arrays.asList(ids.split(",")).stream().map(s -> NumberUtils.createLong(s.trim())).collect(Collectors.toList());
        return carouselPoService.removeByIds(idLongList);
        } catch (Exception e) {
        throw new AppException("参数错误：" + ids, ErrorCode.sys("417"), e);
        }
     }

    @Override
    public Pager<CarouselResDto> doPager(Map<String, Object> params) {
        Pager<CarouselPo> poPager = carouselPoService.queryPage(params, CarouselPo.class);
        Pager<CarouselResDto> resDtoPager = carouselResDtoConvertor.convertPoPager2ResDtoPager(poPager);
        return resDtoPager;
    }

    @Override
    public CarouselResDto selectOne(Long id) {
        CarouselPo po = carouselPoService.getById(id);
        CarouselResDto resDto = carouselResDtoConvertor.po2Dto(po);
        return resDto;
    }

    @Override
    public CarouselResDto selectOne(Map<String, Object> params) {
        QueryWrapper queryWrapper = QueryParamUtils.queryWrapper4eq(CarouselPo::new, params);
        CarouselPo po = carouselPoService.getOne(queryWrapper);
        CarouselResDto resDto = carouselResDtoConvertor.po2Dto(po);
        return resDto;
    }

    @Override
    public boolean updateProps(Long id, Map<String, Object> params) {
        UpdateWrapper<CarouselPo> updateWrapper = QueryParamUtils.updateWrapper4Map(CarouselPo::new, id, params);
        return carouselPoService.update(new CarouselPo(), updateWrapper);
        }

    @Override
    public boolean updateProps(Long id, CarouselReqDto reqDto) {
        CarouselPo po = carouselReqDtoConvertor.dto2Po(reqDto);
        po.setId(id);
        return carouselPoService.updateById(po);
    }


    @Override
    public boolean updateAllProps(Long id, CarouselReqDto reqDto) {
        UpdateWrapper<CarouselPo> updateWrapper = Wrappers.update();
        updateWrapper.eq("id", id);
        Field[] fields = CarouselReqDto.class.getDeclaredFields();
        Arrays.stream(fields).filter(f -> !Modifier.isStatic(f.getModifiers())).forEach(field -> {
        try {
        PropertyDescriptor propertyDescriptor = new PropertyDescriptor(field.getName(), CarouselReqDto.class);
        Method getMethod = propertyDescriptor.getReadMethod();
        String fileNameCamel = getMethod.getName().substring(3);
        String fileNameUnderline = CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, fileNameCamel);
        updateWrapper.set(fileNameUnderline, getMethod.invoke(reqDto));
        } catch (Exception ex) {
        log.warn("属性不存在get方法："+field.getName(),ex);
        }
        });
        return carouselPoService.update(new CarouselPo(), updateWrapper);
    }

}