package org.fleet.modules.demo.test.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.fleet.common.api.vo.Result;
import org.fleet.common.system.base.controller.FleetController;
import org.fleet.common.system.query.QueryGenerator;
import org.fleet.common.system.vo.LoginUser;
import org.fleet.modules.demo.test.entity.FleetOrderCustomer;
import org.fleet.modules.demo.test.entity.FleetOrderMain;
import org.fleet.modules.demo.test.entity.FleetOrderTicket;
import org.fleet.modules.demo.test.service.IFleetOrderCustomerService;
import org.fleet.modules.demo.test.service.IFleetOrderMainService;
import org.fleet.modules.demo.test.service.IFleetOrderTicketService;
import org.fleet.modules.demo.test.vo.FleetOrderMainPage;
import org.jeecgframework.poi.excel.ExcelImportUtil;
import org.jeecgframework.poi.excel.def.NormalExcelConstants;
import org.jeecgframework.poi.excel.entity.ExportParams;
import org.jeecgframework.poi.excel.entity.ImportParams;
import org.jeecgframework.poi.excel.view.JeecgEntityExcelView;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * @Description: 一对多示例（JEditableTable行编辑）
 * @Author: fleet-boot
 * @Date:2019-02-15
 * @Version: V2.0
 */
@RestController
@RequestMapping("/test/jeecgOrderMain")
@Slf4j
public class FleetOrderMainController extends FleetController<FleetOrderMain, IFleetOrderMainService> {

    @Autowired
    private IFleetOrderMainService fleetOrderMainService;
    @Autowired
    private IFleetOrderCustomerService fleetOrderCustomerService;
    @Autowired
    private IFleetOrderTicketService fleetOrderTicketService;

    /**
     * 分页列表查询
     *
     * @param fleetOrderMain
     * @param pageNo
     * @param pageSize
     * @param req
     * @return
     */
    @GetMapping(value = "/list")
    public Result<?> queryPageList(FleetOrderMain fleetOrderMain, @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo, @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize, HttpServletRequest req) {
        QueryWrapper<FleetOrderMain> queryWrapper = QueryGenerator.initQueryWrapper(fleetOrderMain, req.getParameterMap());
        Page<FleetOrderMain> page = new Page<FleetOrderMain>(pageNo, pageSize);
        IPage<FleetOrderMain> pageList = fleetOrderMainService.page(page, queryWrapper);
        return Result.ok(pageList);
    }

    /**
     * 添加
     *
     * @param fleetOrderMainPage
     * @return
     */
    @PostMapping(value = "/add")
    public Result<?> add(@RequestBody FleetOrderMainPage fleetOrderMainPage) {
        FleetOrderMain fleetOrderMain = new FleetOrderMain();
        BeanUtils.copyProperties(fleetOrderMainPage, fleetOrderMain);
        fleetOrderMainService.saveMain(fleetOrderMain, fleetOrderMainPage.getFleetOrderCustomerList(), fleetOrderMainPage.getFleetOrderTicketList());
        return Result.ok("添加成功！");
    }

    /**
     * 编辑
     *
     * @param fleetOrderMainPage
     * @return
     */
    @PutMapping(value = "/edit")
    public Result<?> eidt(@RequestBody FleetOrderMainPage fleetOrderMainPage) {
        FleetOrderMain fleetOrderMain = new FleetOrderMain();
        BeanUtils.copyProperties(fleetOrderMainPage, fleetOrderMain);
        fleetOrderMainService.updateCopyMain(fleetOrderMain, fleetOrderMainPage.getFleetOrderCustomerList(), fleetOrderMainPage.getFleetOrderTicketList());
        return Result.ok("编辑成功！");
    }

    /**
     * 通过id删除
     *
     * @param id
     * @return
     */
    @DeleteMapping(value = "/delete")
    public Result<?> delete(@RequestParam(name = "id", required = true) String id) {
        fleetOrderMainService.delMain(id);
        return Result.ok("删除成功!");
    }

    /**
     * 批量删除
     *
     * @param ids
     * @return
     */
    @DeleteMapping(value = "/deleteBatch")
    public Result<?> deleteBatch(@RequestParam(name = "ids", required = true) String ids) {
        this.fleetOrderMainService.delBatchMain(Arrays.asList(ids.split(",")));
        return Result.ok("批量删除成功!");
    }

    /**
     * 通过id查询
     *
     * @param id
     * @return
     */
    @GetMapping(value = "/queryById")
    public Result<?> queryById(@RequestParam(name = "id", required = true) String id) {
        FleetOrderMain fleetOrderMain = fleetOrderMainService.getById(id);
        return Result.ok(fleetOrderMain);
    }

    /**
     * 通过id查询
     *
     * @param id
     * @return
     */
    @GetMapping(value = "/queryOrderCustomerListByMainId")
    public Result<?> queryOrderCustomerListByMainId(@RequestParam(name = "id", required = true) String id) {
        List<FleetOrderCustomer> fleetOrderCustomerList = fleetOrderCustomerService.selectCustomersByMainId(id);
        return Result.ok(fleetOrderCustomerList);
    }

    /**
     * 通过id查询
     *
     * @param id
     * @return
     */
    @GetMapping(value = "/queryOrderTicketListByMainId")
    public Result<?> queryOrderTicketListByMainId(@RequestParam(name = "id", required = true) String id) {
        List<FleetOrderTicket> fleetOrderTicketList = fleetOrderTicketService.selectTicketsByMainId(id);
        return Result.ok(fleetOrderTicketList);
    }

    /**
     * 导出excel
     *
     * @param request
     */
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, FleetOrderMain fleetOrderMain) {
        // Step.1 组装查询条件
        QueryWrapper<FleetOrderMain> queryWrapper = QueryGenerator.initQueryWrapper(fleetOrderMain, request.getParameterMap());
        // Step.2 AutoPoi 导出Excel
        ModelAndView mv = new ModelAndView(new JeecgEntityExcelView());
        // 获取当前用户
        LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();

        List<FleetOrderMainPage> pageList = new ArrayList<FleetOrderMainPage>();

        List<FleetOrderMain> fleetOrderMainList = fleetOrderMainService.list(queryWrapper);
        for (FleetOrderMain orderMain : fleetOrderMainList) {
            FleetOrderMainPage vo = new FleetOrderMainPage();
            BeanUtils.copyProperties(orderMain, vo);
            // 查询机票
            List<FleetOrderTicket> fleetOrderTicketList = fleetOrderTicketService.selectTicketsByMainId(orderMain.getId());
            vo.setFleetOrderTicketList(fleetOrderTicketList);
            // 查询客户
            List<FleetOrderCustomer> fleetOrderCustomerList = fleetOrderCustomerService.selectCustomersByMainId(orderMain.getId());
            vo.setFleetOrderCustomerList(fleetOrderCustomerList);
            pageList.add(vo);
        }

        // 导出文件名称
        mv.addObject(NormalExcelConstants.FILE_NAME, "一对多订单示例");
        // 注解对象Class
        mv.addObject(NormalExcelConstants.CLASS, FleetOrderMainPage.class);
        // 自定义表格参数
        mv.addObject(NormalExcelConstants.PARAMS, new ExportParams("自定义导出Excel内容标题", "导出人:" + sysUser.getRealname(), "自定义Sheet名字"));
        // 导出数据列表
        mv.addObject(NormalExcelConstants.DATA_LIST, pageList);
        return mv;
    }

    /**
     * 通过excel导入数据
     *
     * @param request
     * @param
     * @return
     */
    @RequestMapping(value = "/importExcel", method = RequestMethod.POST)
    public Result<?> importExcel(HttpServletRequest request, HttpServletResponse response) {
        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        Map<String, MultipartFile> fileMap = multipartRequest.getFileMap();
        for (Map.Entry<String, MultipartFile> entity : fileMap.entrySet()) {
            MultipartFile file = entity.getValue(); // 获取上传文件对象
            ImportParams params = new ImportParams();
            params.setTitleRows(2);
            params.setHeadRows(2);
            params.setNeedSave(true);
            try {
                List<FleetOrderMainPage> list = ExcelImportUtil.importExcel(file.getInputStream(), FleetOrderMainPage.class, params);
                for (FleetOrderMainPage page : list) {
                    FleetOrderMain po = new FleetOrderMain();
                    BeanUtils.copyProperties(page, po);
                    fleetOrderMainService.saveMain(po, page.getFleetOrderCustomerList(), page.getFleetOrderTicketList());
                }
                return Result.ok("文件导入成功！");
            } catch (Exception e) {
                log.error(e.getMessage(), e);
                return Result.error("文件导入失败：" + e.getMessage());
            } finally {
                try {
                    file.getInputStream().close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return Result.error("文件导入失败！");
    }

}
