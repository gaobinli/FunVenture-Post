package com.nova.controller;

import com.nova.domain.dto.req.SysUserRoleReqDto;
import com.nova.domain.dto.res.SysUserRoleResDto;
import com.nova.service.SysUserRoleService;
import com.nova.domain.ResultDTO;
import io.swagger.v3.oas.annotations.tags.Tag;
import com.baomidou.mybatisplus.ext.Pager;
import io.swagger.annotations.*;
import lombok.Data;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;
import java.util.Map;

@Data
@RestController
@Tag(name = "sysUserRole")
@RequestMapping("/sysUserRole")
@SuppressWarnings("unchecked")
public class SysUserRoleController {

    private final SysUserRoleService sysUserRoleService;

    /**
     * 创建一个资源对象
     *
     * @return 返回新创建的资源对象
     */
    @ApiOperation(value = "创建一个资源对象（返回新创建的资源对象）", tags = {"sysUserRole"}, nickname = "addSysUserRole")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "000000:成功，否则失败")})
    @PostMapping(consumes = {"application/json"}, produces = {"application/json"})
    public ResultDTO<SysUserRoleResDto> addSysUserRole(
        @ApiParam(value = "要保存的对象", required = true) @Valid @RequestBody SysUserRoleReqDto reqDto) {
        SysUserRoleResDto resDto = sysUserRoleService.save(reqDto);
        return ResultDTO.ok(resDto);
    }

    /**
     * 删除单个或一系列资源对象
     *
     * @param ids 用英文逗号隔开
     * @return 返回一个空文档
     */
    @ApiOperation(value = "删除单个或一系列资源对象", tags = {"sysUserRole",}, nickname = "deleteSysUserRole")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "操作是否成功,000000:成功，否则失败")})
    @DeleteMapping(value = "/{ids}", produces = {"application/json"})
    public ResultDTO<Void> deleteSysUserRole(
        @ApiParam(value = "要删除的对象主键，多个对象主键可以用英文逗号隔开", required = true) @PathVariable("ids") String ids) {
        boolean bl = sysUserRoleService.deleteByIds(ids);
        return ResultDTO.ok();
    }

    /**
     * 查询一系列资源对象，也就是列表
     *
     * @param params 参数params
     * @return 返回一系列资源对象
     */
    @ApiOperation(value = "查询一系列资源对象，也就是列表(qp-参数支持的操作符号有: eq(=),ne(!=),gt(>),lt(<),ge(>=),le(<=),in,like,notLike,likeleft(左边LIKE '%xxx'),likeright(右边LIKE 'xx%'))", tags = {
            "sysUserRole",}, nickname = "getSysUserRoleList")
    @ApiImplicitParams({@ApiImplicitParam(name = "currentPage", value = "当前页数", paramType = "query", dataTypeClass = Map.class),
            @ApiImplicitParam(name = "pageSize", value = "每页记录数", paramType = "query", dataTypeClass = Map.class),
            @ApiImplicitParam(name = "sorter", value = "排序条件 desc-字段名或者asc-字段名", paramType = "query", dataTypeClass = Map.class),
            @ApiImplicitParam(name = "qp-userCode-eq", value = "用户编码", paramType = "query", dataTypeClass = Map.class),
            @ApiImplicitParam(name = "qp-roleCode-eq", value = "角色编码", paramType = "query", dataTypeClass = Map.class),
            @ApiImplicitParam(name = "qp-createUser-eq", value = "创建人", paramType = "query", dataTypeClass = Map.class),
            @ApiImplicitParam(name = "qp-createTime-eq", value = "创建时间", paramType = "query", dataTypeClass = Map.class),
            @ApiImplicitParam(name = "qp-modifyUser-eq", value = "修改人", paramType = "query", dataTypeClass = Map.class),
            @ApiImplicitParam(name = "qp-modifyTime-eq", value = "修改时间", paramType = "query", dataTypeClass = Map.class),

    })
    @ApiResponses(value = {@ApiResponse(code = 200, message = "操作是否成功,000000:成功，否则失败")})
    @GetMapping(produces = {"application/json"})
    public ResultDTO<Pager<SysUserRoleResDto>> getSysUserRoleList(@ApiIgnore @RequestParam Map<String, Object> params) {
        Pager<SysUserRoleResDto> resDtoPager = sysUserRoleService.doPager(params);
        return ResultDTO.ok(resDtoPager);
    }

    /**
     * 查询一个资源对象
     *
     * @return 返回单独的资源对象
     */
    @ApiOperation(value = "查询一个资源对象", tags = {"sysUserRole"}, nickname = "getOneSysUserRole")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "操作是否成功,000000:成功，否则失败")})
    @GetMapping(value = "/{id}", produces = {"application/json"})
    public ResultDTO<SysUserRoleResDto> getOneSysUserRole(@ApiParam(value = "要查询的对象主键", required = true) @PathVariable("id") Long id) {
        SysUserRoleResDto resDto = sysUserRoleService.selectOne(id);
        return ResultDTO.ok(resDto);
    }

    /**
     * 查询一个资源对象
     *
     * @param params 能确定唯一对象的条件
     * @return 返回单独的资源对象
     */
    @ApiOperation(value = "查询一个资源对象(若不唯一则随机返回符合条件的一个)", tags = {"sysUserRole"}, nickname = "getOneSysUserRoleByMap")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "操作是否成功,000000:成功，否则失败")})
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userCode", value = "用户编码", paramType = "query", dataTypeClass = Map.class),
            @ApiImplicitParam(name = "roleCode", value = "角色编码", paramType = "query", dataTypeClass = Map.class),

    })
    @GetMapping(value = "/one", produces = {"application/json"})
    public ResultDTO<SysUserRoleResDto> getOneSysUserRole(@ApiIgnore @RequestParam Map<String, Object> params) {
        SysUserRoleResDto resDto = sysUserRoleService.selectOne(params);
        return ResultDTO.ok(resDto);
    }

    /**
     * 更新一个资源对象(部分更新)
     *
     * @return 返回完整的资源对象
     */
    @ApiOperation(value = "更新一个资源对象(更新参数的全部属性)", tags = {"sysUserRole"}, nickname = "updateSysUserRoleByMap")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userCode", value = "用户编码", paramType = "query", dataTypeClass = Map.class),
            @ApiImplicitParam(name = "roleCode", value = "角色编码", paramType = "query", dataTypeClass = Map.class),
    })
    @ApiResponses(value = {@ApiResponse(code = 200, message = "操作是否成功,000000:成功，否则失败")})
    @PatchMapping(value = "/map/{id}", produces = {"application/json"})
    public ResultDTO<SysUserRoleResDto> updateSysUserRole(@ApiParam(value = "对象ID", required = true) @PathVariable("id") Long id,
    @ApiIgnore @RequestParam Map<String, Object> params) {
        sysUserRoleService.updateProps(id, params);
        SysUserRoleResDto resDto = sysUserRoleService.selectOne(id);
        return ResultDTO.ok(resDto);
    }

    /**
     * 更新一个资源对象(部分更新)
     *
     * @return 返回完整的资源对象
     */
    @ApiOperation(value = "更新一个资源对象(部分更新,更新对象里有值的属性,空值不更新)", tags = {"sysUserRole"}, nickname = "patchUpdateSysUserRole")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "操作是否成功,000000:成功，否则失败")})
    @PatchMapping(value = "/{id}", produces = {"application/json"})
    public ResultDTO<SysUserRoleResDto> patchUpdateSysUserRole(@ApiParam(value = "对象ID", required = true) @PathVariable("id") Long id,
        @ApiParam(value = "要修改的对象，对象属性有值的才更新,即null值不更新", required = true) @Valid @RequestBody SysUserRoleReqDto reqDto) {
        sysUserRoleService.updateProps(id, reqDto);
        SysUserRoleResDto resDto = sysUserRoleService.selectOne(id);
        return ResultDTO.ok(resDto);
    }

    /**
     * 更新一个资源对象（整个对象替换，属性的null值也会更新进去）
     *
     * @return 返回完整的资源对象
     */
    @ApiOperation(value = "更新一个资源对象（更新对象里全部属性，即整个对象替换）", tags = {"sysUserRole"}, nickname = "putUpdateSysUserRole")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "操作是否成功,000000:成功，否则失败")})
    @PutMapping(value = "/{id}", produces = {"application/json"})
    public ResultDTO<SysUserRoleResDto> putUpdateSysUserRole(@ApiParam(value = "对象ID", required = true) @PathVariable("id") Long id,
        @ApiParam(value = "要修改的对象,对象属性全部更新", required = true) @Valid @RequestBody SysUserRoleReqDto reqDto) {
        sysUserRoleService.updateAllProps(id, reqDto);
        SysUserRoleResDto resDto = sysUserRoleService.selectOne(id);
        return ResultDTO.ok(resDto);
    }
}
