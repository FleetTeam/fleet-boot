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
import org.fleet.modules.deve.entity.DevRefDict;
import org.fleet.modules.deve.service.IDevRefDictService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;

/**
 * @Description: 参考字典
 * @Author: fleet-team
 * @Date: 2021-05-25
 * @Version: V1.0
 */
@Slf4j
@Api(tags = "参考字典")
@RestController
@RequestMapping("/deve/devRefDict")
public class DevRefDictController extends FleetController<DevRefDict, IDevRefDictService> {
    @Autowired
    private IDevRefDictService devRefDictService;

    /**
     * 分页列表查询
     *
     * @param devRefDict
     * @param pageNo
     * @param pageSize
     * @param req
     * @return
     */
    @AutoLog(value = "参考字典-分页列表查询")
    @ApiOperation(value = "参考字典-分页列表查询", notes = "参考字典-分页列表查询")
    @GetMapping(value = "/list")
    public Result<?> queryPageList(DevRefDict devRefDict,
                                   @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
                                   @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize,
                                   HttpServletRequest req) {
        QueryWrapper<DevRefDict> queryWrapper = QueryGenerator.initQueryWrapper(devRefDict, req.getParameterMap());
        Page<DevRefDict> page = new Page<DevRefDict>(pageNo, pageSize);
        IPage<DevRefDict> pageList = devRefDictService.page(page, queryWrapper);
        return Result.OK(pageList);
    }

    /**
     * 添加
     *
     * @param devRefDict
     * @return
     */
    @AutoLog(value = "参考字典-添加")
    @ApiOperation(value = "参考字典-添加", notes = "参考字典-添加")
    @PostMapping(value = "/add")
    public Result<?> add(@RequestBody DevRefDict devRefDict) {
        devRefDictService.save(devRefDict);
        return Result.OK("添加成功！");
    }

    /**
     * 编辑
     *
     * @param devRefDict
     * @return
     */
    @AutoLog(value = "参考字典-编辑")
    @ApiOperation(value = "参考字典-编辑", notes = "参考字典-编辑")
    @PutMapping(value = "/edit")
    public Result<?> edit(@RequestBody DevRefDict devRefDict) {
        devRefDictService.updateById(devRefDict);
        return Result.OK("编辑成功!");
    }

    /**
     * 通过id删除
     *
     * @param id
     * @return
     */
    @AutoLog(value = "参考字典-通过id删除")
    @ApiOperation(value = "参考字典-通过id删除", notes = "参考字典-通过id删除")
    @DeleteMapping(value = "/delete")
    public Result<?> delete(@RequestParam(name = "id", required = true) String id) {
        devRefDictService.removeById(id);
        return Result.OK("删除成功!");
    }

    /**
     * 批量删除
     *
     * @param ids
     * @return
     */
    @AutoLog(value = "参考字典-批量删除")
    @ApiOperation(value = "参考字典-批量删除", notes = "参考字典-批量删除")
    @DeleteMapping(value = "/deleteBatch")
    public Result<?> deleteBatch(@RequestParam(name = "ids", required = true) String ids) {
        this.devRefDictService.removeByIds(Arrays.asList(ids.split(",")));
        return Result.OK("批量删除成功！");
    }

    /**
     * 通过id查询
     *
     * @param id
     * @return
     */
    @AutoLog(value = "参考字典-通过id查询")
    @ApiOperation(value = "参考字典-通过id查询", notes = "参考字典-通过id查询")
    @GetMapping(value = "/queryById")
    public Result<?> queryById(@RequestParam(name = "id", required = true) String id) {
        DevRefDict devRefDict = devRefDictService.getById(id);
        return Result.OK(devRefDict);
    }

    /**
     * 导出excel
     *
     * @param request
     * @param devRefDict
     */
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, DevRefDict devRefDict) {
        return super.exportXls(request, devRefDict, DevRefDict.class, "参考字典");
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
        return super.importExcel(request, response, DevRefDict.class);
    }

}
