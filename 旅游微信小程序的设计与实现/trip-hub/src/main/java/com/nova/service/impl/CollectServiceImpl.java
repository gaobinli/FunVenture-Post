package com.nova.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.ext.BasePService;
import com.baomidou.mybatisplus.ext.IPService;
import com.baomidou.mybatisplus.ext.Pager;
import com.baomidou.mybatisplus.ext.QueryParamUtils;
import com.google.common.base.CaseFormat;
import com.nova.domain.dto.res.CollectResDto;
import com.nova.mapper.CollectMapper;
import com.nova.mapper.FoodMapper;
import com.nova.mapper.HotelMapper;
import com.nova.mapper.ScenicSpotMapper;
import com.nova.service.CollectService;
import com.nova.service.TokenService;
import com.nova.common.convertor.CollectReqDtoConvertor;
import com.nova.common.convertor.CollectResDtoConvertor;
import com.nova.common.exception.AppException;
import com.nova.common.exception.ErrorCode;
import com.nova.common.util.ConditionUtil;
import com.nova.domain.dto.req.CollectReqDto;
import com.nova.domain.po.CollectPo;
import com.nova.domain.po.FoodPo;
import com.nova.domain.po.HotelPo;
import com.nova.domain.po.ScenicSpotPo;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
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
@Service("CollectAppService")
@Transactional(rollbackFor = Exception.class)
public class CollectServiceImpl implements CollectService {

    @Autowired
    private CollectReqDtoConvertor collectReqDtoConvertor;

    @Autowired
    private CollectResDtoConvertor collectResDtoConvertor;

    @Autowired
    private ScenicSpotMapper scenicSpotMapper;

    @Autowired
    private FoodMapper foodMapper;

    @Autowired
    private HotelMapper hotelMapper;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private HttpServletRequest httpServletRequest;

    @Autowired
    private CollectMapper collectMapper;

    private IPService<CollectPo> collectPoService;

    @SuppressWarnings("SpringJavaAutowiringInspection")
    public CollectServiceImpl(CollectMapper collectMapper) {
        this.collectPoService = new BasePService(collectMapper);
    }


    @Override
    public CollectResDto save(CollectReqDto reqDto) {
        Integer count = collectMapper.selectCount(Wrappers.<CollectPo>lambdaQuery()
                .eq(CollectPo::getRelationId, reqDto.getRelationId())
                .eq(CollectPo::getType, reqDto.getType()));
        ConditionUtil.checkArgument(count < 1, "亲，你已经收藏过了");
        CollectPo po = collectReqDtoConvertor.dto2Po(reqDto);
        Integer userId = tokenService.getLoginUser(httpServletRequest).getSysUser().getId();
        po.setUserId(Long.valueOf(userId));
        collectPoService.save(po);
        CollectResDto resDto = collectResDtoConvertor.po2Dto(po);
        return resDto;
    }

    @Override
    public List<CollectResDto> getList() {
        List<CollectPo> list = collectPoService.list();
        return collectResDtoConvertor.poList2DtoList(list);
    }

    @Override
    public boolean deleteByIds(String ids) {
        try {
        List<Long> idLongList =
        Arrays.asList(ids.split(",")).stream().map(s -> NumberUtils.createLong(s.trim())).collect(Collectors.toList());
        return collectPoService.removeByIds(idLongList);
        } catch (Exception e) {
        throw new AppException("参数错误：" + ids, ErrorCode.sys("417"), e);
        }
     }

    @Override
    public Pager<CollectResDto> doPager(Map<String, Object> params) {
        if (params.containsKey("dataLimit") && null != params.get("dataLimit") && "1".equals(params.get("dataLimit"))) {
            params.remove("dataLimit");
            params.put("qp-userId-eq", tokenService.getLoginUser(httpServletRequest).getSysUser().getId());
        }
        Pager<CollectPo> poPager = collectPoService.queryPage(params, CollectPo.class);
        Pager<CollectResDto> resDtoPager = collectResDtoConvertor.convertPoPager2ResDtoPager(poPager);
        ScenicSpotPo scenicSpotPo;
        FoodPo foodPo;
        HotelPo hotelPo;
        for (CollectResDto CollectResDto : resDtoPager.getList()) {
            if (CollectResDto.getType().equals(1)) {
                scenicSpotPo = scenicSpotMapper.selectById(CollectResDto.getRelationId());
                if (scenicSpotPo == null) {
                    continue;
                }
                CollectResDto.setName(scenicSpotPo.getName());
                CollectResDto.setUrl(scenicSpotPo.getUrl());
                CollectResDto.setAddress(scenicSpotPo.getAddress());
                CollectResDto.setIntroduce(scenicSpotPo.getIntroduce());
                CollectResDto.setPrice(scenicSpotPo.getPrice());
                CollectResDto.setShortRecommend(scenicSpotPo.getShortRecommend());
                CollectResDto.setCoverUrl(scenicSpotPo.getCoverUrl());
            }
            if (CollectResDto.getType().equals(2)) {
                foodPo = foodMapper.selectById(CollectResDto.getRelationId());
                if (foodPo == null) {
                    continue;
                }
                CollectResDto.setName(foodPo.getName());
                CollectResDto.setUrl(foodPo.getUrl());
                CollectResDto.setAddress(foodPo.getAddress());
                CollectResDto.setIntroduce(foodPo.getShortRecommend());
                CollectResDto.setPrice(foodPo.getPrice());
                CollectResDto.setShortRecommend(foodPo.getShortRecommend());
                CollectResDto.setCoverUrl(foodPo.getUrl());
            }
            if (CollectResDto.getType().equals(3)) {
                hotelPo = hotelMapper.selectById(CollectResDto.getRelationId());
                if (hotelPo == null) {
                    continue;
                }
                CollectResDto.setName(hotelPo.getName());
                CollectResDto.setUrl(hotelPo.getUrl());
                CollectResDto.setAddress(hotelPo.getAddress());
                CollectResDto.setIntroduce(hotelPo.getShortRecommend());
                CollectResDto.setPrice(hotelPo.getPrice());
                CollectResDto.setShortRecommend(hotelPo.getShortRecommend());
                CollectResDto.setCoverUrl(hotelPo.getUrl());
            }
        }
        return resDtoPager;
    }

    @Override
    public CollectResDto selectOne(Long id) {
        CollectPo po = collectPoService.getById(id);
        CollectResDto resDto = collectResDtoConvertor.po2Dto(po);
        return resDto;
    }

    @Override
    public CollectResDto selectOne(Map<String, Object> params) {
        QueryWrapper queryWrapper = QueryParamUtils.queryWrapper4eq(CollectPo::new, params);
        CollectPo po = collectPoService.getOne(queryWrapper);
        CollectResDto resDto = collectResDtoConvertor.po2Dto(po);
        return resDto;
    }

    @Override
    public boolean updateProps(Long id, Map<String, Object> params) {
        UpdateWrapper<CollectPo> updateWrapper = QueryParamUtils.updateWrapper4Map(CollectPo::new, id, params);
        return collectPoService.update(new CollectPo(), updateWrapper);
        }

    @Override
    public boolean updateProps(Long id, CollectReqDto reqDto) {
        CollectPo po = collectReqDtoConvertor.dto2Po(reqDto);
        po.setId(id);
        return collectPoService.updateById(po);
    }


    @Override
    public boolean updateAllProps(Long id, CollectReqDto reqDto) {
        UpdateWrapper<CollectPo> updateWrapper = Wrappers.update();
        updateWrapper.eq("id", id);
        Field[] fields = CollectReqDto.class.getDeclaredFields();
        Arrays.stream(fields).filter(f -> !Modifier.isStatic(f.getModifiers())).forEach(field -> {
        try {
        PropertyDescriptor propertyDescriptor = new PropertyDescriptor(field.getName(), CollectReqDto.class);
        Method getMethod = propertyDescriptor.getReadMethod();
        String fileNameCamel = getMethod.getName().substring(3);
        String fileNameUnderline = CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, fileNameCamel);
        updateWrapper.set(fileNameUnderline, getMethod.invoke(reqDto));
        } catch (Exception ex) {
        log.warn("属性不存在get方法："+field.getName(),ex);
        }
        });
        return collectPoService.update(new CollectPo(), updateWrapper);
    }

}