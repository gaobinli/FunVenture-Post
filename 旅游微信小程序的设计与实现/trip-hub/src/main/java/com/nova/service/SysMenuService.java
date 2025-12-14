package com.nova.service;

import com.baomidou.mybatisplus.ext.Pager;
import com.nova.domain.dto.res.SysMenuResDto;
import com.nova.domain.dto.req.SysMenuReqDto;

import java.util.List;
import java.util.Map;

public interface SysMenuService {

    /**
     * 保存一个对象
     * @param reqDto
     * @return
     */
    SysMenuResDto save(SysMenuReqDto reqDto);

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
    Pager<SysMenuResDto> doPager(Map<String, Object> params);

    /**
     * 根据id查询一个对象
     * @param id
     * @return
     */
    SysMenuResDto selectOne(Long id);

    /**
     * 根据其它参数查询一个对象
     * @param params 查询参数
     */
    SysMenuResDto selectOne(Map<String, Object> params);

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
    boolean updateProps(Long id, SysMenuReqDto reqDto);

    /**
     * 更新一个资源对象（整个对象替换）
     * @param id
     * @param reqDto
     * @return
     */
    boolean updateAllProps(Long id, SysMenuReqDto reqDto);

    List<SysMenuResDto> getMenuTree();

    List<SysMenuResDto> getPermissonMenuTree();

    List<SysMenuResDto> getRoleMenus(String roleCode);

    List<SysMenuResDto> getParentList();
}