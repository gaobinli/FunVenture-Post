package com.nova.controller;

import com.nova.domain.dto.req.FoodReqDto;
import com.nova.domain.dto.res.FoodResDto;
import com.nova.service.FoodService;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "food")
@RequestMapping("/food")
@SuppressWarnings("unchecked")
public class FoodController {

    private final FoodService foodService;

    /**
     * 创建一个资源对象
     *
     * @return 返回新创建的资源对象
     */
    @ApiOperation(value = "创建一个资源对象（返回新创建的资源对象）", tags = {"food"}, nickname = "addFood")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "000000:成功，否则失败")})
    @PostMapping(consumes = {"application/json"}, produces = {"application/json"})
    public ResultDTO<FoodResDto> addFood(
        @ApiParam(value = "要保存的对象", required = true) @Valid @RequestBody FoodReqDto reqDto) {
        FoodResDto resDto = foodService.save(reqDto);
        return ResultDTO.ok(resDto);
    }

    /**
     * 查询所有数据
     *
     * @return 返回新创建的资源对象
     */
    @ApiOperation(value = "查询所有数据", tags = {"food"}, nickname = "getList")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "操作是否成功,000000:成功，否则失败")})
    @GetMapping(value = "/list", produces = {"application/json"})
    public ResultDTO<List<FoodResDto>> getList() {
        List<FoodResDto> resDtos = foodService.getList();
        return ResultDTO.ok(resDtos);
    }

    /**
     * 删除单个或一系列资源对象
     *
     * @param ids 用英文逗号隔开
     * @return 返回一个空文档
     */
    @ApiOperation(value = "删除单个或一系列资源对象", tags = {"food",}, nickname = "deleteFood")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "操作是否成功,000000:成功，否则失败")})
    @DeleteMapping(value = "/{ids}", produces = {"application/json"})
    public ResultDTO<Void> deleteFood(
        @ApiParam(value = "要删除的对象主键，多个对象主键可以用英文逗号隔开", required = true) @PathVariable("ids") String ids) {
        boolean bl = foodService.deleteByIds(ids);
        return ResultDTO.ok();
    }

    /**
     * 查询一系列资源对象，也就是列表
     *
     * @param params 参数params
     * @return 返回一系列资源对象
     */
    @ApiOperation(value = "查询一系列资源对象，也就是列表(qp-参数支持的操作符号有: eq(=),ne(!=),gt(>),lt(<),ge(>=),le(<=),in,like,notLike,likeleft(左边LIKE '%xxx'),likeright(右边LIKE 'xx%'))", tags = {
            "food",}, nickname = "getFoodList")
    @ApiImplicitParams({@ApiImplicitParam(name = "currentPage", value = "当前页数", paramType = "query", dataTypeClass = Map.class),
            @ApiImplicitParam(name = "pageSize", value = "每页记录数", paramType = "query", dataTypeClass = Map.class),
            @ApiImplicitParam(name = "sorter", value = "排序条件 desc-字段名或者asc-字段名", paramType = "query", dataTypeClass = Map.class),
            @ApiImplicitParam(name = "qp-name-eq", value = "食物名", paramType = "query", dataTypeClass = Map.class),
            @ApiImplicitParam(name = "qp-price-eq", value = "价格", paramType = "query", dataTypeClass = Map.class),
            @ApiImplicitParam(name = "qp-pic-eq", value = "食物照片", paramType = "query", dataTypeClass = Map.class),
            @ApiImplicitParam(name = "qp-createdTime-eq", value = "创建时间", paramType = "query", dataTypeClass = Map.class),
            @ApiImplicitParam(name = "qp-updatedTime-eq", value = "修改时间", paramType = "query", dataTypeClass = Map.class),
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
    public ResultDTO<Pager<FoodResDto>> getFoodList(@ApiIgnore @RequestParam Map<String, Object> params) {
        Pager<FoodResDto> resDtoPager = foodService.doPager(params);
        return ResultDTO.ok(resDtoPager);
    }

    /**
     * 查询一个资源对象
     *
     * @return 返回单独的资源对象
     */
    @ApiOperation(value = "查询一个资源对象", tags = {"food"}, nickname = "getOneFood")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "操作是否成功,000000:成功，否则失败")})
    @GetMapping(value = "/{id}", produces = {"application/json"})
    public ResultDTO<FoodResDto> getOneFood(@ApiParam(value = "要查询的对象主键", required = true) @PathVariable("id") Long id) {
        FoodResDto resDto = foodService.selectOne(id);
        return ResultDTO.ok(resDto);
    }

    /**
     * 查询一个资源对象
     *
     * @param params 能确定唯一对象的条件
     * @return 返回单独的资源对象
     */
    @ApiOperation(value = "查询一个资源对象(若不唯一则随机返回符合条件的一个)", tags = {"food"}, nickname = "getOneFoodByMap")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "操作是否成功,000000:成功，否则失败")})
    @ApiImplicitParams({
            @ApiImplicitParam(name = "name", value = "食物名", paramType = "query", dataTypeClass = Map.class),
            @ApiImplicitParam(name = "price", value = "价格", paramType = "query", dataTypeClass = Map.class),
            @ApiImplicitParam(name = "pic", value = "食物照片", paramType = "query", dataTypeClass = Map.class),
            @ApiImplicitParam(name = "createdTime", value = "创建时间", paramType = "query", dataTypeClass = Map.class),
            @ApiImplicitParam(name = "updatedTime", value = "修改时间", paramType = "query", dataTypeClass = Map.class),
            @ApiImplicitParam(name = "url", value = "封面", paramType = "query", dataTypeClass = Map.class),
            @ApiImplicitParam(name = "name", value = "名称", paramType = "query", dataTypeClass = Map.class),
            @ApiImplicitParam(name = "price", value = "价格", paramType = "query", dataTypeClass = Map.class),
            @ApiImplicitParam(name = "content", value = "详情内容", paramType = "query", dataTypeClass = Map.class),

    })
    @GetMapping(value = "/one", produces = {"application/json"})
    public ResultDTO<FoodResDto> getOneFood(@ApiIgnore @RequestParam Map<String, Object> params) {
        FoodResDto resDto = foodService.selectOne(params);
        return ResultDTO.ok(resDto);
    }

    /**
     * 更新一个资源对象(部分更新)
     *
     * @return 返回完整的资源对象
     */
    @ApiOperation(value = "更新一个资源对象(更新参数的全部属性)", tags = {"food"}, nickname = "updateFoodByMap")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "name", value = "食物名", paramType = "query", dataTypeClass = Map.class),
            @ApiImplicitParam(name = "price", value = "价格", paramType = "query", dataTypeClass = Map.class),
            @ApiImplicitParam(name = "pic", value = "食物照片", paramType = "query", dataTypeClass = Map.class),
            @ApiImplicitParam(name = "createdTime", value = "创建时间", paramType = "query", dataTypeClass = Map.class),
            @ApiImplicitParam(name = "updatedTime", value = "修改时间", paramType = "query", dataTypeClass = Map.class),
            @ApiImplicitParam(name = "url", value = "封面", paramType = "query", dataTypeClass = Map.class),
            @ApiImplicitParam(name = "name", value = "名称", paramType = "query", dataTypeClass = Map.class),
            @ApiImplicitParam(name = "price", value = "价格", paramType = "query", dataTypeClass = Map.class),
            @ApiImplicitParam(name = "content", value = "详情内容", paramType = "query", dataTypeClass = Map.class),
    })
    @ApiResponses(value = {@ApiResponse(code = 200, message = "操作是否成功,000000:成功，否则失败")})
    @PatchMapping(value = "/map/{id}", produces = {"application/json"})
    public ResultDTO<FoodResDto> updateFood(@ApiParam(value = "对象ID", required = true) @PathVariable("id") Long id,
    @ApiIgnore @RequestParam Map<String, Object> params) {
        foodService.updateProps(id, params);
        FoodResDto resDto = foodService.selectOne(id);
        return ResultDTO.ok(resDto);
    }

    /**
     * 更新一个资源对象(部分更新)
     *
     * @return 返回完整的资源对象
     */
    @ApiOperation(value = "更新一个资源对象(部分更新,更新对象里有值的属性,空值不更新)", tags = {"food"}, nickname = "patchUpdateFood")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "操作是否成功,000000:成功，否则失败")})
    @PatchMapping(value = "/{id}", produces = {"application/json"})
    public ResultDTO<FoodResDto> patchUpdateFood(@ApiParam(value = "对象ID", required = true) @PathVariable("id") Long id,
        @ApiParam(value = "要修改的对象，对象属性有值的才更新,即null值不更新", required = true) @Valid @RequestBody FoodReqDto reqDto) {
        foodService.updateProps(id, reqDto);
        FoodResDto resDto = foodService.selectOne(id);
        return ResultDTO.ok(resDto);
    }

    /**
     * 更新一个资源对象（整个对象替换，属性的null值也会更新进去）
     *
     * @return 返回完整的资源对象
     */
    @ApiOperation(value = "更新一个资源对象（更新对象里全部属性，即整个对象替换）", tags = {"food"}, nickname = "putUpdateFood")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "操作是否成功,000000:成功，否则失败")})
    @PutMapping(value = "/{id}", produces = {"application/json"})
    public ResultDTO<FoodResDto> putUpdateFood(@ApiParam(value = "对象ID", required = true) @PathVariable("id") Long id,
        @ApiParam(value = "要修改的对象,对象属性全部更新", required = true) @Valid @RequestBody FoodReqDto reqDto) {
        foodService.updateAllProps(id, reqDto);
        FoodResDto resDto = foodService.selectOne(id);
        return ResultDTO.ok(resDto);
    }
}
