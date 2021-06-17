package org.fleet.modules.deve.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.fleet.common.api.vo.Result;
import org.fleet.common.aspect.annotation.AutoLog;
import org.fleet.common.system.base.controller.FleetController;
import org.fleet.common.system.query.QueryGenerator;
import org.fleet.modules.deve.entity.DevDataDict;
import org.fleet.modules.deve.service.IDevDataDictService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;

/**
 * @Description: 数据字典
 * @Author: fleet-team
 * @Date: 2021-05-25
 * @Version: V1.0
 */
@Slf4j
@Api(tags = "数据字典")
@RestController
@RequestMapping("/deve/devDataDict")
public class DevDataDictController extends FleetController<DevDataDict, IDevDataDictService> {
    @Autowired
    private IDevDataDictService devDataDictService;

    /**
     * 分页列表查询
     *
     * @param devDataDict
     * @param pageNo
     * @param pageSize
     * @param req
     * @return
     */
    @AutoLog(value = "数据字典-分页列表查询")
    @ApiOperation(value = "数据字典-分页列表查询", notes = "数据字典-分页列表查询")
    @GetMapping(value = "/list")
    public Result<?> queryPageList(DevDataDict devDataDict,
                                   @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
                                   @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize,
                                   HttpServletRequest req) {
        QueryWrapper<DevDataDict> queryWrapper = QueryGenerator.initQueryWrapper(devDataDict, req.getParameterMap());
        Page<DevDataDict> page = new Page<DevDataDict>(pageNo, pageSize);
        IPage<DevDataDict> pageList = devDataDictService.page(page, queryWrapper);
        return Result.OK(pageList);
    }

    /**
     * 添加
     *
     * @param devDataDict
     * @return
     */
    @AutoLog(value = "数据字典-添加")
    @ApiOperation(value = "数据字典-添加", notes = "数据字典-添加")
    @PostMapping(value = "/add")
    public Result<?> add(@RequestBody DevDataDict devDataDict) {
        devDataDictService.save(devDataDict);
        return Result.OK("添加成功！");
    }

    /**
     * 编辑
     *
     * @param devDataDict
     * @return
     */
    @AutoLog(value = "数据字典-编辑")
    @ApiOperation(value = "数据字典-编辑", notes = "数据字典-编辑")
    @PutMapping(value = "/edit")
    public Result<?> edit(@RequestBody DevDataDict devDataDict) {
        devDataDictService.updateById(devDataDict);
        return Result.OK("编辑成功!");
    }

    /**
     * 通过id删除
     *
     * @param id
     * @return
     */
    @AutoLog(value = "数据字典-通过id删除")
    @ApiOperation(value = "数据字典-通过id删除", notes = "数据字典-通过id删除")
    @DeleteMapping(value = "/delete")
    public Result<?> delete(@RequestParam(name = "id", required = true) String id) {
        devDataDictService.removeById(id);
        return Result.OK("删除成功!");
    }

    /**
     * 批量删除
     *
     * @param ids
     * @return
     */
    @AutoLog(value = "数据字典-批量删除")
    @ApiOperation(value = "数据字典-批量删除", notes = "数据字典-批量删除")
    @DeleteMapping(value = "/deleteBatch")
    public Result<?> deleteBatch(@RequestParam(name = "ids", required = true) String ids) {
        this.devDataDictService.removeByIds(Arrays.asList(ids.split(",")));
        return Result.OK("批量删除成功！");
    }

    /**
     * 通过id查询
     *
     * @param id
     * @return
     */
    @AutoLog(value = "数据字典-通过id查询")
    @ApiOperation(value = "数据字典-通过id查询", notes = "数据字典-通过id查询")
    @GetMapping(value = "/queryById")
    public Result<?> queryById(@RequestParam(name = "id", required = true) String id) {
        DevDataDict devDataDict = devDataDictService.getById(id);
        return Result.OK(devDataDict);
    }

    /**
     * 导出excel
     *
     * @param request
     * @param devDataDict
     */
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, DevDataDict devDataDict) {
        return super.exportXls(request, devDataDict, DevDataDict.class, "数据字典");
    }

    /**
     * 通过excel导入数据
     *
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/importExcel", method = RequestMethod.POST)
    public Result<?> importExcel(HttpServletRequest request, HttpServletResponse response) {
        return super.importExcel(request, response, DevDataDict.class);
    }

}
