package com.nova.service;

import com.baomidou.mybatisplus.ext.Pager;
import com.nova.domain.dto.req.PwdReqDto;
import com.nova.domain.dto.res.UserResDto;
import com.nova.domain.dto.req.UserReqDto;

import java.util.List;
import java.util.Map;

public interface UserService {

    /**
     * 保存一个对象
     * @param reqDto
     * @return
     */
    UserResDto save(UserReqDto reqDto);

    /**
     * 查询所有数据
     * @return
     */
    List<UserResDto> getList();

    /**
     * 删除（支持批量）
     * @param ids
     * @return
     */
    boolean deleteByIds(String ids);

    /**
     * 分页查询
     * @param params
     * @return
     */
    Pager<UserResDto> doPager(Map<String, Object> params);

    /**
     * 根据id查询一个对象
     * @param id
     * @return
     */
    UserResDto selectOne(Long id);

    /**
     * 根据其它参数查询一个对象
     * @param params 查询参数
     */
    UserResDto selectOne(Map<String, Object> params);

    /**
     * 更新一个资源对象(更新参数的全部属性)
     * @param id
     * @param params
     * @return
     */
    boolean updateProps(Long id, Map<String, Object> params);

    /**
     * 更新一个资源对象(部分更新)
     * @param id
     * @param reqDto
     * @return
     */
    boolean updateProps(Long id, UserReqDto reqDto);

    /**
     * 更新一个资源对象（整个对象替换）
     * @param id
     * @param reqDto
     * @return
     */
    boolean updateAllProps(Long id, UserReqDto reqDto);

    boolean pwd(Long id, PwdReqDto pwdReqDto);

    void register(UserReqDto userReqDto);

    UserResDto getInfo();
}