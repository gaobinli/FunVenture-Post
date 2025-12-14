package com.nova.controller;

import com.nova.domain.dto.res.SysMenuResDto;
import com.nova.service.SysMenuService;
import com.nova.domain.ResultDTO;
import io.swagger.v3.oas.annotations.tags.Tag;
import com.baomidou.mybatisplus.ext.Pager;
import com.nova.domain.dto.req.SysMenuReqDto;
import io.swagger.annotations.*;
import lombok.Data;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@Data
@RestController
@Tag(name = "sysMenu")
@RequestMapping("/sysMenu")
@SuppressWarnings("unchecked")
public class SysMenuController {

    private final SysMenuService sysMenuService;

    /**
     * 创建一个资源对象
     *
     * @return 返回新创建的资源对象
     */
    @ApiOperation(value = "创建一个资源对象（返回新创建的资源对象）", tags = {"sysMenu"}, nickname = "addSysMenu")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "000000:成功，否则失败")})
    @PostMapping(consumes = {"application/json"}, produces = {"application/json"})
    public ResultDTO<SysMenuResDto> addSysMenu(
        @ApiParam(value = "要保存的对象", required = true) @Valid @RequestBody SysMenuReqDto reqDto) {
        SysMenuResDto resDto = sysMenuService.save(reqDto);
        return ResultDTO.ok(resDto);
    }

    /**
     * 删除单个或一系列资源对象
     *
     * @param ids 用英文逗号隔开
     * @return 返回一个空文档
     */
    @ApiOperation(value = "删除单个或一系列资源对象", tags = {"sysMenu",}, nickname = "deleteSysMenu")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "操作是否成功,000000:成功，否则失败")})
    @DeleteMapping(value = "/{ids}", produces = {"application/json"})
    public ResultDTO<Void> deleteSysMenu(
        @ApiParam(value = "要删除的对象主键，多个对象主键可以用英文逗号隔开", required = true) @PathVariable("ids") String ids) {
        boolean bl = sysMenuService.deleteByIds(ids);
        return ResultDTO.ok();
    }

    /**
     * 查询一系列资源对象，也就是列表
     *
     * @param params 参数params
     * @return 返回一系列资源对象
     */
    @ApiOperation(value = "查询一系列资源对象，也就是列表(qp-参数支持的操作符号有: eq(=),ne(!=),gt(>),lt(<),ge(>=),le(<=),in,like,notLike,likeleft(左边LIKE '%xxx'),likeright(右边LIKE 'xx%'))", tags = {
            "sysMenu",}, nickname = "getSysMenuList")
    @ApiImplicitParams({@ApiImplicitParam(name = "currentPage", value = "当前页数", paramType = "query", dataTypeClass = Map.class),
            @ApiImplicitParam(name = "pageSize", value = "每页记录数", paramType = "query", dataTypeClass = Map.class),
            @ApiImplicitParam(name = "sorter", value = "排序条件 desc-字段名或者asc-字段名", paramType = "query", dataTypeClass = Map.class),
            @ApiImplicitParam(name = "qp-code-eq", value = "菜单编码", paramType = "query", dataTypeClass = Map.class),
            @ApiImplicitParam(name = "qp-name-eq", value = "菜单名称", paramType = "query", dataTypeClass = Map.class),
            @ApiImplicitParam(name = "qp-parentCode-eq", value = "上级菜单", paramType = "query", dataTypeClass = Map.class),
            @ApiImplicitParam(name = "qp-path-eq", value = "菜单路径", paramType = "query", dataTypeClass = Map.class),
            @ApiImplicitParam(name = "qp-createUser-eq", value = "创建人", paramType = "query", dataTypeClass = Map.class),
            @ApiImplicitParam(name = "qp-createTime-eq", value = "创建时间", paramType = "query", dataTypeClass = Map.class),
            @ApiImplicitParam(name = "qp-modifyUser-eq", value = "修改人", paramType = "query", dataTypeClass = Map.class),
            @ApiImplicitParam(name = "qp-modifyTime-eq", value = "修改时间", paramType = "query", dataTypeClass = Map.class),

    })
    @ApiResponses(value = {@ApiResponse(code = 200, message = "操作是否成功,000000:成功，否则失败")})
    @GetMapping(produces = {"application/json"})
    public ResultDTO<Pager<SysMenuResDto>> getSysMenuList(@ApiIgnore @RequestParam Map<String, Object> params) {
        Pager<SysMenuResDto> resDtoPager = sysMenuService.doPager(params);
        return ResultDTO.ok(resDtoPager);
    }

    /**
     * 查询一个资源对象
     *
     * @return 返回单独的资源对象
     */
    @ApiOperation(value = "查询一个资源对象", tags = {"sysMenu"}, nickname = "getOneSysMenu")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "操作是否成功,000000:成功，否则失败")})
    @GetMapping(value = "/{id}", produces = {"application/json"})
    public ResultDTO<SysMenuResDto> getOneSysMenu(@ApiParam(value = "要查询的对象主键", required = true) @PathVariable("id") Long id) {
        SysMenuResDto resDto = sysMenuService.selectOne(id);
        return ResultDTO.ok(resDto);
    }

    /**
     * 查询一个资源对象
     *
     * @param params 能确定唯一对象的条件
     * @return 返回单独的资源对象
     */
    @ApiOperation(value = "查询一个资源对象(若不唯一则随机返回符合条件的一个)", tags = {"sysMenu"}, nickname = "getOneSysMenuByMap")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "操作是否成功,000000:成功，否则失败")})
    @ApiImplicitParams({
            @ApiImplicitParam(name = "code", value = "菜单编码", paramType = "query", dataTypeClass = Map.class),
            @ApiImplicitParam(name = "name", value = "菜单名称", paramType = "query", dataTypeClass = Map.class),
            @ApiImplicitParam(name = "parentCode", value = "上级菜单", paramType = "query", dataTypeClass = Map.class),
            @ApiImplicitParam(name = "path", value = "菜单路径", paramType = "query", dataTypeClass = Map.class),

    })
    @GetMapping(value = "/one", produces = {"application/json"})
    public ResultDTO<SysMenuResDto> getOneSysMenu(@ApiIgnore @RequestParam Map<String, Object> params) {
        SysMenuResDto resDto = sysMenuService.selectOne(params);
        return ResultDTO.ok(resDto);
    }

    /**
     * 更新一个资源对象(部分更新)
     *
     * @return 返回完整的资源对象
     */
    @ApiOperation(value = "更新一个资源对象(更新参数的全部属性)", tags = {"sysMenu"}, nickname = "updateSysMenuByMap")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "code", value = "菜单编码", paramType = "query", dataTypeClass = Map.class),
            @ApiImplicitParam(name = "name", value = "菜单名称", paramType = "query", dataTypeClass = Map.class),
            @ApiImplicitParam(name = "parentCode", value = "上级菜单", paramType = "query", dataTypeClass = Map.class),
            @ApiImplicitParam(name = "path", value = "菜单路径", paramType = "query", dataTypeClass = Map.class),
    })
    @ApiResponses(value = {@ApiResponse(code = 200, message = "操作是否成功,000000:成功，否则失败")})
    @PatchMapping(value = "/map/{id}", produces = {"application/json"})
    public ResultDTO<SysMenuResDto> updateSysMenu(@ApiParam(value = "对象ID", required = true) @PathVariable("id") Long id,
    @ApiIgnore @RequestParam Map<String, Object> params) {
        sysMenuService.updateProps(id, params);
        SysMenuResDto resDto = sysMenuService.selectOne(id);
        return ResultDTO.ok(resDto);
    }

    /**
     * 更新一个资源对象(部分更新)
     *
     * @return 返回完整的资源对象
     */
    @ApiOperation(value = "更新一个资源对象(部分更新,更新对象里有值的属性,空值不更新)", tags = {"sysMenu"}, nickname = "patchUpdateSysMenu")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "操作是否成功,000000:成功，否则失败")})
    @PatchMapping(value = "/{id}", produces = {"application/json"})
    public ResultDTO<SysMenuResDto> patchUpdateSysMenu(@ApiParam(value = "对象ID", required = true) @PathVariable("id") Long id,
        @ApiParam(value = "要修改的对象，对象属性有值的才更新,即null值不更新", required = true) @Valid @RequestBody SysMenuReqDto reqDto) {
        sysMenuService.updateProps(id, reqDto);
        SysMenuResDto resDto = sysMenuService.selectOne(id);
        return ResultDTO.ok(resDto);
    }

    /**
     * 更新一个资源对象（整个对象替换，属性的null值也会更新进去）
     *
     * @return 返回完整的资源对象
     */
    @ApiOperation(value = "更新一个资源对象（更新对象里全部属性，即整个对象替换）", tags = {"sysMenu"}, nickname = "putUpdateSysMenu")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "操作是否成功,000000:成功，否则失败")})
    @PutMapping(value = "/{id}", produces = {"application/json"})
    public ResultDTO<SysMenuResDto> putUpdateSysMenu(@ApiParam(value = "对象ID", required = true) @PathVariable("id") Long id,
        @ApiParam(value = "要修改的对象,对象属性全部更新", required = true) @Valid @RequestBody SysMenuReqDto reqDto) {
        sysMenuService.updateAllProps(id, reqDto);
        SysMenuResDto resDto = sysMenuService.selectOne(id);
        return ResultDTO.ok(resDto);
    }

    @ApiOperation(value = "菜单树", tags = {
            "sysMenu",}, nickname = "getSysMenuTree")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "操作是否成功,000000:成功，否则失败")})
    @GetMapping(value = "/menuTree", produces = {"application/json"})
    public ResultDTO<List<SysMenuResDto>> getSysMenuTree() {
        List<SysMenuResDto> menuTree = sysMenuService.getMenuTree();
        return ResultDTO.ok(menuTree);
    }

    @ApiOperation(value = "权限菜单树", tags = {
            "sysMenu",}, nickname = "getPermissionSysMenuTree")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "操作是否成功,000000:成功，否则失败")})
    @GetMapping(value = "/permisson/menuTree", produces = {"application/json"})
    public ResultDTO<List<SysMenuResDto>> getPermissionSysMenuTree() {
        List<SysMenuResDto> menuTree = sysMenuService.getPermissonMenuTree();
        return ResultDTO.ok(menuTree);
    }

    @ApiOperation(value = "查询角色编码对应的菜单编码", tags = {"sysMenu"}, nickname = "getSysRoleMenuList")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "000000:成功，否则失败")})
    @GetMapping(value = "/getSysRoleMenuList", produces = {"application/json"})
    public ResultDTO<SysMenuResDto> getSysRoleMenuList(@RequestParam String roleCode) {
        return ResultDTO.ok(sysMenuService.getRoleMenus(roleCode));
    }

    @ApiOperation(value = "查询所有父级菜单", tags = {"sysMenu"}, nickname = "getParentList")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "000000:成功，否则失败")})
    @GetMapping(value = "/getParentList", produces = {"application/json"})
    public ResultDTO<SysMenuResDto> getParentList() {
        return ResultDTO.ok(sysMenuService.getParentList());
    }
}
