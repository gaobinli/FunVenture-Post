package com.nova.service.impl;


import com.nova.domain.dto.res.HotelResDto;
import com.nova.mapper.HotelMapper;
import com.nova.service.HotelService;
import com.nova.common.convertor.HotelReqDtoConvertor;
import com.nova.common.convertor.HotelResDtoConvertor;
import com.nova.domain.dto.req.HotelReqDto;
import com.nova.domain.po.HotelPo;
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
@Service("hotelAppService")
@Transactional(rollbackFor = Exception.class)
public class HotelServiceImpl implements HotelService {

    @Autowired
    private HotelReqDtoConvertor hotelReqDtoConvertor;

    @Autowired
    private HotelResDtoConvertor hotelResDtoConvertor;

    private IPService<HotelPo> hotelPoService;

    @SuppressWarnings("SpringJavaAutowiringInspection")
    public HotelServiceImpl(HotelMapper hotelMapper) {
        this.hotelPoService = new BasePService(hotelMapper);
    }


    @Override
    public HotelResDto save(HotelReqDto reqDto) {
        HotelPo po = hotelReqDtoConvertor.dto2Po(reqDto);
        hotelPoService.save(po);
        HotelResDto resDto = hotelResDtoConvertor.po2Dto(po);
        return resDto;
    }

    @Override
    public List<HotelResDto> getList() {
        List<HotelPo> list = hotelPoService.list();
        return hotelResDtoConvertor.poList2DtoList(list);
    }

    @Override
    public boolean deleteByIds(String ids) {
        try {
        List<Long> idLongList =
        Arrays.asList(ids.split(",")).stream().map(s -> NumberUtils.createLong(s.trim())).collect(Collectors.toList());
        return hotelPoService.removeByIds(idLongList);
        } catch (Exception e) {
        throw new AppException("参数错误：" + ids, ErrorCode.sys("417"), e);
        }
     }

    @Override
    public Pager<HotelResDto> doPager(Map<String, Object> params) {
        Pager<HotelPo> poPager = hotelPoService.queryPage(params, HotelPo.class);
        Pager<HotelResDto> resDtoPager = hotelResDtoConvertor.convertPoPager2ResDtoPager(poPager);
        return resDtoPager;
    }

    @Override
    public HotelResDto selectOne(Long id) {
        HotelPo po = hotelPoService.getById(id);
        HotelResDto resDto = hotelResDtoConvertor.po2Dto(po);
        return resDto;
    }

    @Override
    public HotelResDto selectOne(Map<String, Object> params) {
        QueryWrapper queryWrapper = QueryParamUtils.queryWrapper4eq(HotelPo::new, params);
        HotelPo po = hotelPoService.getOne(queryWrapper);
        HotelResDto resDto = hotelResDtoConvertor.po2Dto(po);
        return resDto;
    }

    @Override
    public boolean updateProps(Long id, Map<String, Object> params) {
        UpdateWrapper<HotelPo> updateWrapper = QueryParamUtils.updateWrapper4Map(HotelPo::new, id, params);
        return hotelPoService.update(new HotelPo(), updateWrapper);
        }

    @Override
    public boolean updateProps(Long id, HotelReqDto reqDto) {
        HotelPo po = hotelReqDtoConvertor.dto2Po(reqDto);
        po.setId(id);
        return hotelPoService.updateById(po);
    }


    @Override
    public boolean updateAllProps(Long id, HotelReqDto reqDto) {
        UpdateWrapper<HotelPo> updateWrapper = Wrappers.update();
        updateWrapper.eq("id", id);
        Field[] fields = HotelReqDto.class.getDeclaredFields();
        Arrays.stream(fields).filter(f -> !Modifier.isStatic(f.getModifiers())).forEach(field -> {
        try {
        PropertyDescriptor propertyDescriptor = new PropertyDescriptor(field.getName(), HotelReqDto.class);
        Method getMethod = propertyDescriptor.getReadMethod();
        String fileNameCamel = getMethod.getName().substring(3);
        String fileNameUnderline = CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, fileNameCamel);
        updateWrapper.set(fileNameUnderline, getMethod.invoke(reqDto));
        } catch (Exception ex) {
        log.warn("属性不存在get方法："+field.getName(),ex);
        }
        });
        return hotelPoService.update(new HotelPo(), updateWrapper);
    }

}