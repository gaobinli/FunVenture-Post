package com.nova.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.ext.BasePService;
import com.baomidou.mybatisplus.ext.IPService;
import com.baomidou.mybatisplus.ext.Pager;
import com.baomidou.mybatisplus.ext.QueryParamUtils;
import com.google.common.base.CaseFormat;
import com.nova.domain.dto.res.OrderResDto;
import com.nova.domain.po.*;
import com.nova.mapper.*;
import com.nova.service.OrderService;
import com.nova.service.TokenService;
import com.nova.common.convertor.OrderReqDtoConvertor;
import com.nova.common.convertor.OrderResDtoConvertor;
import com.nova.common.exception.AppException;
import com.nova.common.exception.ErrorCode;
import com.nova.domain.dto.req.OrderReqDto;
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
@Service("orderAppService")
@Transactional(rollbackFor = Exception.class)
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderReqDtoConvertor orderReqDtoConvertor;

    @Autowired
    private OrderResDtoConvertor orderResDtoConvertor;

    @Autowired
    private ScenicSpotMapper scenicSpotMapper;

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private FoodMapper foodMapper;

    @Autowired
    private HotelMapper hotelMapper;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private HttpServletRequest httpServletRequest;

    private IPService<OrderPo> orderPoService;

    @SuppressWarnings("SpringJavaAutowiringInspection")
    public OrderServiceImpl(OrderMapper orderMapper) {
        this.orderPoService = new BasePService(orderMapper);
    }


    @Override
    public OrderResDto save(OrderReqDto reqDto) {
        Integer userId = tokenService.getLoginUser(httpServletRequest).getSysUser().getId();
        UserPo userPo = userMapper.selectById(userId);

        if (1 == reqDto.getType()) {
            ScenicSpotPo scenicSpotPo = scenicSpotMapper.selectById(reqDto.getRelationId());
            if (null == scenicSpotPo || null == scenicSpotPo.getInventory() || scenicSpotPo.getInventory() < 1) {
                throw new AppException("库存不足");
            }
            scenicSpotPo.setInventory(scenicSpotPo.getInventory() - 1);
            scenicSpotMapper.updateById(scenicSpotPo);
            userPo.setBalance(userPo.getBalance() - scenicSpotPo.getPrice());
        } else if (2 == reqDto.getType()) {
            FoodPo foodPo = foodMapper.selectById(reqDto.getRelationId());
            if (null == foodPo || null == foodPo.getInventory() || foodPo.getInventory() < 1) {
                throw new AppException("库存不足");
            }
            foodPo.setInventory(foodPo.getInventory() - 1);
            foodMapper.updateById(foodPo);
            userPo.setBalance(userPo.getBalance() - foodPo.getPrice());
        } else if (3 == reqDto.getType()) {
            HotelPo hotelPo = hotelMapper.selectById(reqDto.getRelationId());
            if (null == hotelPo || null == hotelPo.getInventory() || hotelPo.getInventory() < 1) {
                throw new AppException("库存不足");
            }
            hotelPo.setInventory(hotelPo.getInventory() - 1);
            hotelMapper.updateById(hotelPo);
            userPo.setBalance(userPo.getBalance() - hotelPo.getPrice());
        }
        userMapper.updateById(userPo);

        OrderPo po = orderReqDtoConvertor.dto2Po(reqDto);
        po.setUserId(Long.valueOf(userId));
        po.setStatus(1);
        orderPoService.save(po);
        OrderResDto resDto = orderResDtoConvertor.po2Dto(po);
        return resDto;
    }

    @Override
    public List<OrderResDto> getList() {
        List<OrderPo> list = orderPoService.list();
        return orderResDtoConvertor.poList2DtoList(list);
    }

    @Override
    public boolean deleteByIds(String ids) {
        try {
        List<Long> idLongList =
        Arrays.asList(ids.split(",")).stream().map(s -> NumberUtils.createLong(s.trim())).collect(Collectors.toList());
        return orderPoService.removeByIds(idLongList);
        } catch (Exception e) {
        throw new AppException("参数错误：" + ids, ErrorCode.sys("417"), e);
        }
     }

    @Override
    public Pager<OrderResDto> doPager(Map<String, Object> params) {
        if (params.containsKey("dataLimit") && null != params.get("dataLimit") && "1".equals(params.get("dataLimit"))) {
            params.remove("dataLimit");
            params.put("qp-userId-eq", tokenService.getLoginUser(httpServletRequest).getSysUser().getId());
        }
        Pager<OrderPo> poPager = orderPoService.queryPage(params, OrderPo.class);
        Pager<OrderResDto> resDtoPager = orderResDtoConvertor.convertPoPager2ResDtoPager(poPager);
        ScenicSpotPo scenicSpotPo;
        FoodPo foodPo;
        HotelPo hotelPo;
        for (OrderResDto orderResDto : resDtoPager.getList()) {
            if (orderResDto.getType().equals(1)) {
                scenicSpotPo = scenicSpotMapper.selectById(orderResDto.getRelationId());
                if (scenicSpotPo == null) {
                    continue;
                }
                orderResDto.setName(scenicSpotPo.getName());
                orderResDto.setUrl(scenicSpotPo.getUrl());
                orderResDto.setAddress(scenicSpotPo.getAddress());
                orderResDto.setIntroduce(scenicSpotPo.getIntroduce());
                orderResDto.setPrice(scenicSpotPo.getPrice());
                orderResDto.setShortRecommend(scenicSpotPo.getShortRecommend());
                orderResDto.setCoverUrl(scenicSpotPo.getCoverUrl());
            }
            if (orderResDto.getType().equals(2)) {
                foodPo = foodMapper.selectById(orderResDto.getRelationId());
                if (foodPo == null) {
                    continue;
                }
                orderResDto.setName(foodPo.getName());
                orderResDto.setUrl(foodPo.getUrl());
                orderResDto.setAddress(foodPo.getAddress());
                orderResDto.setIntroduce(foodPo.getShortRecommend());
                orderResDto.setPrice(foodPo.getPrice());
                orderResDto.setShortRecommend(foodPo.getShortRecommend());
                orderResDto.setCoverUrl(foodPo.getUrl());
            }
            if (orderResDto.getType().equals(3)) {
                hotelPo = hotelMapper.selectById(orderResDto.getRelationId());
                if (hotelPo == null) {
                    continue;
                }
                orderResDto.setName(hotelPo.getName());
                orderResDto.setUrl(hotelPo.getUrl());
                orderResDto.setAddress(hotelPo.getAddress());
                orderResDto.setIntroduce(hotelPo.getShortRecommend());
                orderResDto.setPrice(hotelPo.getPrice());
                orderResDto.setShortRecommend(hotelPo.getShortRecommend());
                orderResDto.setCoverUrl(hotelPo.getUrl());
            }
        }
        return resDtoPager;
    }

    @Override
    public OrderResDto selectOne(Long id) {
        OrderPo po = orderPoService.getById(id);
        OrderResDto resDto = orderResDtoConvertor.po2Dto(po);
        return resDto;
    }

    @Override
    public OrderResDto selectOne(Map<String, Object> params) {
        QueryWrapper queryWrapper = QueryParamUtils.queryWrapper4eq(OrderPo::new, params);
        OrderPo po = orderPoService.getOne(queryWrapper);
        OrderResDto resDto = orderResDtoConvertor.po2Dto(po);
        return resDto;
    }

    @Override
    public boolean updateProps(Long id, Map<String, Object> params) {
        UpdateWrapper<OrderPo> updateWrapper = QueryParamUtils.updateWrapper4Map(OrderPo::new, id, params);
        return orderPoService.update(new OrderPo(), updateWrapper);
        }

    @Override
    public boolean updateProps(Long id, OrderReqDto reqDto) {
        OrderPo orderPo = orderMapper.selectById(id);
        UserPo userPo = userMapper.selectById(orderPo.getUserId());

        if (Integer.valueOf(5).equals(reqDto.getStatus())) {
            if (1 == orderPo.getType()) {
                ScenicSpotPo scenicSpotPo = scenicSpotMapper.selectById(orderPo.getRelationId());
                if (null != scenicSpotPo && null != scenicSpotPo.getPrice() && null != scenicSpotPo.getInventory()) {
                    scenicSpotPo.setInventory(scenicSpotPo.getInventory() + 1);
                    scenicSpotMapper.updateById(scenicSpotPo);
                    userPo.setBalance(userPo.getBalance() + scenicSpotPo.getPrice());
                }
            } else if (2 == orderPo.getType()) {
                FoodPo foodPo = foodMapper.selectById(orderPo.getRelationId());
                if (null != foodPo && null != foodPo.getPrice() && null != foodPo.getInventory()) {
                    foodPo.setInventory(foodPo.getInventory() + 1);
                    foodMapper.updateById(foodPo);
                    userPo.setBalance(userPo.getBalance() + foodPo.getPrice());
                }
            } else if (3 == orderPo.getType()) {
                HotelPo hotelPo = hotelMapper.selectById(orderPo.getRelationId());
                if (null != hotelPo && null != hotelPo.getPrice() && null != hotelPo.getInventory()) {
                    hotelPo.setInventory(hotelPo.getInventory() + 1);
                    hotelMapper.updateById(hotelPo);
                    userPo.setBalance(userPo.getBalance() + hotelPo.getPrice());
                }
            }
        }
        userMapper.updateById(userPo);
        orderPo.setId(id);
        orderPo.setStatus(reqDto.getStatus());
        return orderPoService.updateById(orderPo);
    }


    @Override
    public boolean updateAllProps(Long id, OrderReqDto reqDto) {
        UpdateWrapper<OrderPo> updateWrapper = Wrappers.update();
        updateWrapper.eq("id", id);
        Field[] fields = OrderReqDto.class.getDeclaredFields();
        Arrays.stream(fields).filter(f -> !Modifier.isStatic(f.getModifiers())).forEach(field -> {
        try {
        PropertyDescriptor propertyDescriptor = new PropertyDescriptor(field.getName(), OrderReqDto.class);
        Method getMethod = propertyDescriptor.getReadMethod();
        String fileNameCamel = getMethod.getName().substring(3);
        String fileNameUnderline = CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, fileNameCamel);
        updateWrapper.set(fileNameUnderline, getMethod.invoke(reqDto));
        } catch (Exception ex) {
        log.warn("属性不存在get方法："+field.getName(),ex);
        }
        });
        return orderPoService.update(new OrderPo(), updateWrapper);
    }

}