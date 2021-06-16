package org.fleet.modules.deve.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.fleet.common.api.vo.Result;
import org.fleet.common.system.query.QueryGenerator;
import org.fleet.common.aspect.annotation.AutoLog;
import org.fleet.common.util.oConvertUtils;
import org.fleet.modules.deve.entity.DevWord;
import org.fleet.modules.deve.service.IDevWordService;

import java.util.Date;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.fleet.common.system.base.controller.FleetController;
import org.jeecgframework.poi.excel.ExcelImportUtil;
import org.jeecgframework.poi.excel.def.NormalExcelConstants;
import org.jeecgframework.poi.excel.entity.ExportParams;
import org.jeecgframework.poi.excel.entity.ImportParams;
import org.jeecgframework.poi.excel.view.JeecgEntityExcelView;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;
import com.alibaba.fastjson.JSON;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * @Description: 词汇定义
 * @Author: fleet-team
 * @Date: 2021-05-25
 * @Version: V1.0
 */
@Slf4j
@Api(tags = "词汇定义")
@RestController
@RequestMapping("/deve/devWord")
public class DevWordController extends FleetController<DevWord, IDevWordService> {
    @Autowired
    private IDevWordService devWordService;

    /**
     * 分页列表查询
     *
     * @param devWord
     * @param pageNo
     * @param pageSize
     * @param req
     * @return
     */
    @AutoLog(value = "词汇定义-分页列表查询")
    @ApiOperation(value = "词汇定义-分页列表查询", notes = "词汇定义-分页列表查询")
    @GetMapping(value = "/list")
    public Result<?> queryPageList(DevWord devWord,
                                   @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
                                   @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize,
                                   HttpServletRequest req) {
        QueryWrapper<DevWord> queryWrapper = QueryGenerator.initQueryWrapper(devWord, req.getParameterMap());
        Page<DevWord> page = new Page<DevWord>(pageNo, pageSize);
        IPage<DevWord> pageList = devWordService.page(page, queryWrapper);
        return Result.OK(pageList);
    }

    /**
     * 添加
     *
     * @param devWord
     * @return
     */
    @AutoLog(value = "词汇定义-添加")
    @ApiOperation(value = "词汇定义-添加", notes = "词汇定义-添加")
    @PostMapping(value = "/add")
    public Result<?> add(@RequestBody DevWord devWord) {
        devWordService.save(devWord);
        return Result.OK("添加成功！");
    }

    /**
     * 编辑
     *
     * @param devWord
     * @return
     */
    @AutoLog(value = "词汇定义-编辑")
    @ApiOperation(value = "词汇定义-编辑", notes = "词汇定义-编辑")
    @PutMapping(value = "/edit")
    public Result<?> edit(@RequestBody DevWord devWord) {
        devWordService.updateById(devWord);
        return Result.OK("编辑成功!");
    }

    /**
     * 通过id删除
     *
     * @param id
     * @return
     */
    @AutoLog(value = "词汇定义-通过id删除")
    @ApiOperation(value = "词汇定义-通过id删除", notes = "词汇定义-通过id删除")
    @DeleteMapping(value = "/delete")
    public Result<?> delete(@RequestParam(name = "id", required = true) String id) {
        devWordService.removeById(id);
        return Result.OK("删除成功!");
    }

    /**
     * 批量删除
     *
     * @param ids
     * @return
     */
    @AutoLog(value = "词汇定义-批量删除")
    @ApiOperation(value = "词汇定义-批量删除", notes = "词汇定义-批量删除")
    @DeleteMapping(value = "/deleteBatch")
    public Result<?> deleteBatch(@RequestParam(name = "ids", required = true) String ids) {
        this.devWordService.removeByIds(Arrays.asList(ids.split(",")));
        return Result.OK("批量删除成功！");
    }

    /**
     * 通过id查询
     *
     * @param id
     * @return
     */
    @AutoLog(value = "词汇定义-通过id查询")
    @ApiOperation(value = "词汇定义-通过id查询", notes = "词汇定义-通过id查询")
    @GetMapping(value = "/queryById")
    public Result<?> queryById(@RequestParam(name = "id", required = true) String id) {
        DevWord devWord = devWordService.getById(id);
        return Result.OK(devWord);
    }

    /**
     * 导出excel
     *
     * @param request
     * @param devWord
     */
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, DevWord devWord) {
        return super.exportXls(request, devWord, DevWord.class, "词汇定义");
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
        return super.importExcel(request, response, DevWord.class);
    }

}
