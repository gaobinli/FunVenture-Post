package com.nova.controller;

import com.nova.domain.dto.res.HotelResDto;
import io.swagger.v3.oas.annotations.tags.Tag;
import com.nova.domain.dto.req.HotelReqDto;
import com.nova.service.HotelService;
import com.baomidou.mybatisplus.ext.Pager;
import com.nova.domain.ResultDTO;
import io.swagger.annotations.*;
import lombok.Data;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;
import java.util.Map;
import java.util.List;

@Data
@RestController
@Tag(name = "hotel")
@RequestMapping("/hotel")
@SuppressWarnings("unchecked")
public class HotelController {

    private final HotelService hotelService;

    /**
     * 创建一个资源对象
     *
     * @return 返回新创建的资源对象
     */
    @ApiOperation(value = "创建一个资源对象（返回新创建的资源对象）", tags = {"hotel"}, nickname = "addHotel")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "000000:成功，否则失败")})
    @PostMapping(consumes = {"application/json"}, produces = {"application/json"})
    public ResultDTO<HotelResDto> addHotel(
        @ApiParam(value = "要保存的对象", required = true) @Valid @RequestBody HotelReqDto reqDto) {
        HotelResDto resDto = hotelService.save(reqDto);
        return ResultDTO.ok(resDto);
    }

    /**
     * 查询所有数据
     *
     * @return 返回新创建的资源对象
     */
    @ApiOperation(value = "查询所有数据", tags = {"hotel"}, nickname = "getList")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "操作是否成功,000000:成功，否则失败")})
    @GetMapping(value = "/list", produces = {"application/json"})
    public ResultDTO<List<HotelResDto>> getList() {
        List<HotelResDto> resDtos = hotelService.getList();
        return ResultDTO.ok(resDtos);
    }

    /**
     * 删除单个或一系列资源对象
     *
     * @param ids 用英文逗号隔开
     * @return 返回一个空文档
     */
    @ApiOperation(value = "删除单个或一系列资源对象", tags = {"hotel",}, nickname = "deleteHotel")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "操作是否成功,000000:成功，否则失败")})
    @DeleteMapping(value = "/{ids}", produces = {"application/json"})
    public ResultDTO<Void> deleteHotel(
        @ApiParam(value = "要删除的对象主键，多个对象主键可以用英文逗号隔开", required = true) @PathVariable("ids") String ids) {
        boolean bl = hotelService.deleteByIds(ids);
        return ResultDTO.ok();
    }

    /**
     * 查询一系列资源对象，也就是列表
     *
     * @param params 参数params
     * @return 返回一系列资源对象
     */
    @ApiOperation(value = "查询一系列资源对象，也就是列表(qp-参数支持的操作符号有: eq(=),ne(!=),gt(>),lt(<),ge(>=),le(<=),in,like,notLike,likeleft(左边LIKE '%xxx'),likeright(右边LIKE 'xx%'))", tags = {
            "hotel",}, nickname = "getHotelList")
    @ApiImplicitParams({@ApiImplicitParam(name = "currentPage", value = "当前页数", paramType = "query", dataTypeClass = Map.class),
            @ApiImplicitParam(name = "pageSize", value = "每页记录数", paramType = "query", dataTypeClass = Map.class),
            @ApiImplicitParam(name = "sorter", value = "排序条件 desc-字段名或者asc-字段名", paramType = "query", dataTypeClass = Map.class),
            @ApiImplicitParam(name = "qp-url-eq", value = "封面", paramType = "query", dataTypeClass = Map.class),
            @ApiImplicitParam(name = "qp-name-eq", value = "名称", paramType = "query", dataTypeClass = Map.class),
            @ApiImplicitParam(name = "qp-price-eq", value = "价格", paramType = "query", dataTypeClass = Map.class),
            @ApiImplicitParam(name = "qp-content-eq", value = "详情内容", paramType = "query", dataTypeClass = Map.class),
            @ApiImplicitParam(name = "qp-createUser-eq", value = "创建人", paramType = "query", dataTypeClass = Map.class),
            @ApiImplicitParam(name = "qp-createTime-eq", value = "创建时间", paramType = "query", dataTypeClass = Map.class),
            @ApiImplicitParam(name = "qp-modifyUser-eq", value = "修改人", paramType = "query", dataTypeClass = Map.class),
            @ApiImplicitParam(name = "qp-modifyTime-eq", value = "修改时间", paramType = "query", dataTypeClass = Map.class),

    })
    @ApiResponses(value = {@ApiResponse(code = 200, message = "操作是否成功,000000:成功，否则失败")})
    @GetMapping(produces = {"application/json"})
    public ResultDTO<Pager<HotelResDto>> getHotelList(@ApiIgnore @RequestParam Map<String, Object> params) {
        Pager<HotelResDto> resDtoPager = hotelService.doPager(params);
        return ResultDTO.ok(resDtoPager);
    }

    /**
     * 查询一个资源对象
     *
     * @return 返回单独的资源对象
     */
    @ApiOperation(value = "查询一个资源对象", tags = {"hotel"}, nickname = "getOneHotel")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "操作是否成功,000000:成功，否则失败")})
    @GetMapping(value = "/{id}", produces = {"application/json"})
    public ResultDTO<HotelResDto> getOneHotel(@ApiParam(value = "要查询的对象主键", required = true) @PathVariable("id") Long id) {
        HotelResDto resDto = hotelService.selectOne(id);
        return ResultDTO.ok(resDto);
    }

    /**
     * 查询一个资源对象
     *
     * @param params 能确定唯一对象的条件
     * @return 返回单独的资源对象
     */
    @ApiOperation(value = "查询一个资源对象(若不唯一则随机返回符合条件的一个)", tags = {"hotel"}, nickname = "getOneHotelByMap")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "操作是否成功,000000:成功，否则失败")})
    @ApiImplicitParams({
            @ApiImplicitParam(name = "url", value = "封面", paramType = "query", dataTypeClass = Map.class),
            @ApiImplicitParam(name = "name", value = "名称", paramType = "query", dataTypeClass = Map.class),
            @ApiImplicitParam(name = "price", value = "价格", paramType = "query", dataTypeClass = Map.class),
            @ApiImplicitParam(name = "content", value = "详情内容", paramType = "query", dataTypeClass = Map.class),

    })
    @GetMapping(value = "/one", produces = {"application/json"})
    public ResultDTO<HotelResDto> getOneHotel(@ApiIgnore @RequestParam Map<String, Object> params) {
        HotelResDto resDto = hotelService.selectOne(params);
        return ResultDTO.ok(resDto);
    }

    /**
     * 更新一个资源对象(部分更新)
     *
     * @return 返回完整的资源对象
     */
    @ApiOperation(value = "更新一个资源对象(更新参数的全部属性)", tags = {"hotel"}, nickname = "updateHotelByMap")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "url", value = "封面", paramType = "query", dataTypeClass = Map.class),
            @ApiImplicitParam(name = "name", value = "名称", paramType = "query", dataTypeClass = Map.class),
            @ApiImplicitParam(name = "price", value = "价格", paramType = "query", dataTypeClass = Map.class),
            @ApiImplicitParam(name = "content", value = "详情内容", paramType = "query", dataTypeClass = Map.class),
    })
    @ApiResponses(value = {@ApiResponse(code = 200, message = "操作是否成功,000000:成功，否则失败")})
    @PatchMapping(value = "/map/{id}", produces = {"application/json"})
    public ResultDTO<HotelResDto> updateHotel(@ApiParam(value = "对象ID", required = true) @PathVariable("id") Long id,
    @ApiIgnore @RequestParam Map<String, Object> params) {
        hotelService.updateProps(id, params);
        HotelResDto resDto = hotelService.selectOne(id);
        return ResultDTO.ok(resDto);
    }

    /**
     * 更新一个资源对象(部分更新)
     *
     * @return 返回完整的资源对象
     */
    @ApiOperation(value = "更新一个资源对象(部分更新,更新对象里有值的属性,空值不更新)", tags = {"hotel"}, nickname = "patchUpdateHotel")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "操作是否成功,000000:成功，否则失败")})
    @PatchMapping(value = "/{id}", produces = {"application/json"})
    public ResultDTO<HotelResDto> patchUpdateHotel(@ApiParam(value = "对象ID", required = true) @PathVariable("id") Long id,
        @ApiParam(value = "要修改的对象，对象属性有值的才更新,即null值不更新", required = true) @Valid @RequestBody HotelReqDto reqDto) {
        hotelService.updateProps(id, reqDto);
        HotelResDto resDto = hotelService.selectOne(id);
        return ResultDTO.ok(resDto);
    }

    /**
     * 更新一个资源对象（整个对象替换，属性的null值也会更新进去）
     *
     * @return 返回完整的资源对象
     */
    @ApiOperation(value = "更新一个资源对象（更新对象里全部属性，即整个对象替换）", tags = {"hotel"}, nickname = "putUpdateHotel")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "操作是否成功,000000:成功，否则失败")})
    @PutMapping(value = "/{id}", produces = {"application/json"})
    public ResultDTO<HotelResDto> putUpdateHotel(@ApiParam(value = "对象ID", required = true) @PathVariable("id") Long id,
        @ApiParam(value = "要修改的对象,对象属性全部更新", required = true) @Valid @RequestBody HotelReqDto reqDto) {
        hotelService.updateAllProps(id, reqDto);
        HotelResDto resDto = hotelService.selectOne(id);
        return ResultDTO.ok(resDto);
    }
}
