package com.nova.controller;

import com.nova.domain.dto.res.SysUserResDto;
import com.nova.service.SysUserService;
import com.nova.domain.ResultDTO;
import com.nova.domain.dto.req.SysUserReqDto;
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
@Tag(name = "sysUser")
@RequestMapping("/sysUser")
@SuppressWarnings("unchecked")
public class SysUserController {

    private final SysUserService sysUserService;

    /**
     * 创建一个资源对象
     *
     * @return 返回新创建的资源对象
     */
    @ApiOperation(value = "创建一个资源对象（返回新创建的资源对象）", tags = {"sysUser"}, nickname = "addSysUser")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "000000:成功，否则失败")})
    @PostMapping(consumes = {"application/json"}, produces = {"application/json"})
    public ResultDTO<SysUserResDto> addSysUser(
        @ApiParam(value = "要保存的对象", required = true) @Valid @RequestBody SysUserReqDto reqDto) {
        SysUserResDto resDto = sysUserService.save(reqDto);
        return ResultDTO.ok(resDto);
    }

    /**
     * 删除单个或一系列资源对象
     *
     * @param ids 用英文逗号隔开
     * @return 返回一个空文档
     */
    @ApiOperation(value = "删除单个或一系列资源对象", tags = {"sysUser",}, nickname = "deleteSysUser")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "操作是否成功,000000:成功，否则失败")})
    @DeleteMapping(value = "/{ids}", produces = {"application/json"})
    public ResultDTO<Void> deleteSysUser(
        @ApiParam(value = "要删除的对象主键，多个对象主键可以用英文逗号隔开", required = true) @PathVariable("ids") String ids) {
        boolean bl = sysUserService.deleteByIds(ids);
        return ResultDTO.ok();
    }

    /**
     * 查询一系列资源对象，也就是列表
     *
     * @param params 参数params
     * @return 返回一系列资源对象
     */
    @ApiOperation(value = "查询一系列资源对象，也就是列表(qp-参数支持的操作符号有: eq(=),ne(!=),gt(>),lt(<),ge(>=),le(<=),in,like,notLike,likeleft(左边LIKE '%xxx'),likeright(右边LIKE 'xx%'))", tags = {
            "sysUser",}, nickname = "getSysUserList")
    @ApiImplicitParams({@ApiImplicitParam(name = "currentPage", value = "当前页数", paramType = "query", dataTypeClass = Map.class),
            @ApiImplicitParam(name = "pageSize", value = "每页记录数", paramType = "query", dataTypeClass = Map.class),
            @ApiImplicitParam(name = "sorter", value = "排序条件 desc-字段名或者asc-字段名", paramType = "query", dataTypeClass = Map.class),
            @ApiImplicitParam(name = "qp-code-eq", value = "用户编码", paramType = "query", dataTypeClass = Map.class),
            @ApiImplicitParam(name = "qp-password-eq", value = "密码", paramType = "query", dataTypeClass = Map.class),
            @ApiImplicitParam(name = "qp-name-eq", value = "姓名", paramType = "query", dataTypeClass = Map.class),
            @ApiImplicitParam(name = "qp-phone-eq", value = "手机号", paramType = "query", dataTypeClass = Map.class),
            @ApiImplicitParam(name = "qp-email-eq", value = "邮箱", paramType = "query", dataTypeClass = Map.class),
            @ApiImplicitParam(name = "qp-enable-eq", value = "账号是否启用，1 启用", paramType = "query", dataTypeClass = Map.class),
            @ApiImplicitParam(name = "qp-createUser-eq", value = "创建人", paramType = "query", dataTypeClass = Map.class),
            @ApiImplicitParam(name = "qp-createTime-eq", value = "创建时间", paramType = "query", dataTypeClass = Map.class),
            @ApiImplicitParam(name = "qp-modifyUser-eq", value = "修改人", paramType = "query", dataTypeClass = Map.class),
            @ApiImplicitParam(name = "qp-modifyTime-eq", value = "修改时间", paramType = "query", dataTypeClass = Map.class),

    })
    @ApiResponses(value = {@ApiResponse(code = 200, message = "操作是否成功,000000:成功，否则失败")})
    @GetMapping(produces = {"application/json"})
    public ResultDTO<Pager<SysUserResDto>> getSysUserList(@ApiIgnore @RequestParam Map<String, Object> params) {
        Pager<SysUserResDto> resDtoPager = sysUserService.doPager(params);
        return ResultDTO.ok(resDtoPager);
    }

    /**
     * 查询一个资源对象
     *
     * @return 返回单独的资源对象
     */
    @ApiOperation(value = "查询一个资源对象", tags = {"sysUser"}, nickname = "getOneSysUser")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "操作是否成功,000000:成功，否则失败")})
    @GetMapping(value = "/{id}", produces = {"application/json"})
    public ResultDTO<SysUserResDto> getOneSysUser(@ApiParam(value = "要查询的对象主键", required = true) @PathVariable("id") Long id) {
        SysUserResDto resDto = sysUserService.selectOne(id);
        return ResultDTO.ok(resDto);
    }

    @ApiOperation(value = "根据code查询管理员信息", tags = {"sysUser"}, nickname = "getInfo")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "操作是否成功,000000:成功，否则失败")})
    @GetMapping(value = "/getInfo", produces = {"application/json"})
    public ResultDTO<SysUserResDto> getInfo(@ApiParam(value = "要查询的对象主键", required = true) @RequestParam String code) {
        SysUserResDto resDto = sysUserService.getInfo(code);
        return ResultDTO.ok(resDto);
    }

    /**
     * 查询一个资源对象
     *
     * @param params 能确定唯一对象的条件
     * @return 返回单独的资源对象
     */
    @ApiOperation(value = "查询一个资源对象(若不唯一则随机返回符合条件的一个)", tags = {"sysUser"}, nickname = "getOneSysUserByMap")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "操作是否成功,000000:成功，否则失败")})
    @ApiImplicitParams({
            @ApiImplicitParam(name = "code", value = "用户编码", paramType = "query", dataTypeClass = Map.class),
            @ApiImplicitParam(name = "password", value = "密码", paramType = "query", dataTypeClass = Map.class),
            @ApiImplicitParam(name = "name", value = "姓名", paramType = "query", dataTypeClass = Map.class),
            @ApiImplicitParam(name = "phone", value = "手机号", paramType = "query", dataTypeClass = Map.class),
            @ApiImplicitParam(name = "email", value = "邮箱", paramType = "query", dataTypeClass = Map.class),
            @ApiImplicitParam(name = "enable", value = "账号是否启用，1 启用", paramType = "query", dataTypeClass = Map.class),

    })
    @GetMapping(value = "/one", produces = {"application/json"})
    public ResultDTO<SysUserResDto> getOneSysUser(@ApiIgnore @RequestParam Map<String, Object> params) {
        SysUserResDto resDto = sysUserService.selectOne(params);
        return ResultDTO.ok(resDto);
    }

    /**
     * 更新一个资源对象(部分更新)
     *
     * @return 返回完整的资源对象
     */
    @ApiOperation(value = "更新一个资源对象(更新参数的全部属性)", tags = {"sysUser"}, nickname = "updateSysUserByMap")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "code", value = "用户编码", paramType = "query", dataTypeClass = Map.class),
            @ApiImplicitParam(name = "password", value = "密码", paramType = "query", dataTypeClass = Map.class),
            @ApiImplicitParam(name = "name", value = "姓名", paramType = "query", dataTypeClass = Map.class),
            @ApiImplicitParam(name = "phone", value = "手机号", paramType = "query", dataTypeClass = Map.class),
            @ApiImplicitParam(name = "email", value = "邮箱", paramType = "query", dataTypeClass = Map.class),
            @ApiImplicitParam(name = "enable", value = "账号是否启用，1 启用", paramType = "query", dataTypeClass = Map.class),
    })
    @ApiResponses(value = {@ApiResponse(code = 200, message = "操作是否成功,000000:成功，否则失败")})
    @PatchMapping(value = "/map/{id}", produces = {"application/json"})
    public ResultDTO<SysUserResDto> updateSysUser(@ApiParam(value = "对象ID", required = true) @PathVariable("id") Long id,
    @ApiIgnore @RequestParam Map<String, Object> params) {
        sysUserService.updateProps(id, params);
        SysUserResDto resDto = sysUserService.selectOne(id);
        return ResultDTO.ok(resDto);
    }

    /**
     * 更新一个资源对象(部分更新)
     *
     * @return 返回完整的资源对象
     */
    @ApiOperation(value = "更新一个资源对象(部分更新,更新对象里有值的属性,空值不更新)", tags = {"sysUser"}, nickname = "patchUpdateSysUser")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "操作是否成功,000000:成功，否则失败")})
    @PatchMapping(value = "/{id}", produces = {"application/json"})
    public ResultDTO<SysUserResDto> patchUpdateSysUser(@ApiParam(value = "对象ID", required = true) @PathVariable("id") Long id,
        @ApiParam(value = "要修改的对象，对象属性有值的才更新,即null值不更新", required = true) @Valid @RequestBody SysUserReqDto reqDto) {
        sysUserService.updateProps(id, reqDto);
        SysUserResDto resDto = sysUserService.selectOne(id);
        return ResultDTO.ok(resDto);
    }

    /**
     * 更新一个资源对象（整个对象替换，属性的null值也会更新进去）
     *
     * @return 返回完整的资源对象
     */
    @ApiOperation(value = "更新一个资源对象（更新对象里全部属性，即整个对象替换）", tags = {"sysUser"}, nickname = "putUpdateSysUser")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "操作是否成功,000000:成功，否则失败")})
    @PutMapping(value = "/{id}", produces = {"application/json"})
    public ResultDTO<SysUserResDto> putUpdateSysUser(@ApiParam(value = "对象ID", required = true) @PathVariable("id") Long id,
        @ApiParam(value = "要修改的对象,对象属性全部更新", required = true) @Valid @RequestBody SysUserReqDto reqDto) {
        sysUserService.updateAllProps(id, reqDto);
        SysUserResDto resDto = sysUserService.selectOne(id);
        return ResultDTO.ok(resDto);
    }

    @ApiOperation(value = "根据code查询管理员信息", tags = {"sysUser"}, nickname = "getInfo")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "操作是否成功,000000:成功，否则失败")})
    @GetMapping(value = "/info", produces = {"application/json"})
    public ResultDTO<SysUserResDto> info() {
        SysUserResDto resDto = sysUserService.info();
        return ResultDTO.ok(resDto);
    }

    /**
     * 更新一个资源对象(部分更新)
     *
     * @return 返回完整的资源对象
     */
    @ApiOperation(value = "更新一个资源对象(部分更新,更新对象里有值的属性,空值不更新)", tags = {"sysUser"}, nickname = "patchUpdateSysUser")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "操作是否成功,000000:成功，否则失败")})
    @PatchMapping(value = "/updatePasssword/{id}", produces = {"application/json"})
    public ResultDTO<SysUserResDto> patchUpdatePasssword(@PathVariable("id") Long id, @RequestBody SysUserReqDto reqDto) {
        sysUserService.updatePassword(id, reqDto);
        return ResultDTO.ok();
    }
}
