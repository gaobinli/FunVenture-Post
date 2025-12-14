package com.nova.service;

import com.baomidou.mybatisplus.ext.Pager;
import com.nova.domain.dto.res.SysRoleResDto;
import com.nova.domain.dto.req.SysRoleReqDto;

import java.util.List;
import java.util.Map;

public interface SysRoleService {

    /**
     * 保存一个对象
     * @param reqDto
     * @return
     */
    SysRoleResDto save(SysRoleReqDto reqDto);

    List<SysRoleResDto> selectAllRoleList();

    List<SysRoleResDto> getRoleList(Long userId);

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
    Pager<SysRoleResDto> doPager(Map<String, Object> params);

    /**
     * 根据id查询一个对象
     * @param id
     * @return
     */
    SysRoleResDto selectOne(Long id);

    /**
     * 根据其它参数查询一个对象
     * @param params 查询参数
     */
    SysRoleResDto selectOne(Map<String, Object> params);

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
    boolean updateProps(Long id, SysRoleReqDto reqDto);

    /**
     * 更新一个资源对象（整个对象替换）
     * @param id
     * @param reqDto
     * @return
     */
    boolean updateAllProps(Long id, SysRoleReqDto reqDto);

}