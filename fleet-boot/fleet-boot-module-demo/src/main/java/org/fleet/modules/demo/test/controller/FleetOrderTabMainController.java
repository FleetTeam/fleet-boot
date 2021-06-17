package org.fleet.modules.demo.test.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.fleet.common.api.vo.Result;
import org.fleet.common.system.query.QueryGenerator;
import org.fleet.modules.demo.test.entity.FleetOrderCustomer;
import org.fleet.modules.demo.test.entity.FleetOrderMain;
import org.fleet.modules.demo.test.entity.FleetOrderTicket;
import org.fleet.modules.demo.test.service.IFleetOrderCustomerService;
import org.fleet.modules.demo.test.service.IFleetOrderMainService;
import org.fleet.modules.demo.test.service.IFleetOrderTicketService;
import org.fleet.modules.demo.test.vo.FleetOrderMainPage;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

/**
 * @Description: 一对多示例（ERP TAB风格）
 * @Author: ZhiLin
 * @Date: 2019-02-20
 * @Version: v2.0
 */
@Slf4j
@RestController
@RequestMapping("/test/order")
public class FleetOrderTabMainController {

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
    @GetMapping(value = "/orderList")
    public Result<?> respondePagedData(FleetOrderMain fleetOrderMain,
                                       @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
                                       @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize,
                                       HttpServletRequest req) {
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
        fleetOrderMainService.save(fleetOrderMain);
        return Result.ok("添加成功!");
    }

    /**
     * 编辑
     *
     * @param fleetOrderMainPage
     * @return
     */
    @PutMapping("/edit")
    public Result<?> edit(@RequestBody FleetOrderMainPage fleetOrderMainPage) {
        FleetOrderMain fleetOrderMain = new FleetOrderMain();
        BeanUtils.copyProperties(fleetOrderMainPage, fleetOrderMain);
        fleetOrderMainService.updateById(fleetOrderMain);
        return Result.ok("编辑成功!");
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
        this.fleetOrderMainService.removeByIds(Arrays.asList(ids.split(",")));
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
     * @param fleetOrderCustomer
     * @return
     */
    @GetMapping(value = "/listOrderCustomerByMainId")
    public Result<?> queryOrderCustomerListByMainId(FleetOrderCustomer fleetOrderCustomer,
                                                    @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
                                                    @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize,
                                                    HttpServletRequest req) {
        QueryWrapper<FleetOrderCustomer> queryWrapper = QueryGenerator.initQueryWrapper(fleetOrderCustomer, req.getParameterMap());
        Page<FleetOrderCustomer> page = new Page<FleetOrderCustomer>(pageNo, pageSize);
        IPage<FleetOrderCustomer> pageList = fleetOrderCustomerService.page(page, queryWrapper);
        return Result.ok(pageList);
    }

    /**
     * 通过id查询
     *
     * @param fleetOrderTicket
     * @return
     */
    @GetMapping(value = "/listOrderTicketByMainId")
    public Result<?> queryOrderTicketListByMainId(FleetOrderTicket fleetOrderTicket,
                                                  @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
                                                  @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize,
                                                  HttpServletRequest req) {
        QueryWrapper<FleetOrderTicket> queryWrapper = QueryGenerator.initQueryWrapper(fleetOrderTicket, req.getParameterMap());
        Page<FleetOrderTicket> page = new Page<FleetOrderTicket>(pageNo, pageSize);
        IPage<FleetOrderTicket> pageList = fleetOrderTicketService.page(page, queryWrapper);
        return Result.ok(pageList);
    }

    /**
     * 添加
     *
     * @param fleetOrderCustomer
     * @return
     */
    @PostMapping(value = "/addCustomer")
    public Result<?> addCustomer(@RequestBody FleetOrderCustomer fleetOrderCustomer) {
        fleetOrderCustomerService.save(fleetOrderCustomer);
        return Result.ok("添加成功!");
    }

    /**
     * 编辑
     *
     * @param fleetOrderCustomer
     * @return
     */
    @PutMapping("/editCustomer")
    public Result<?> editCustomer(@RequestBody FleetOrderCustomer fleetOrderCustomer) {
        fleetOrderCustomerService.updateById(fleetOrderCustomer);
        return Result.ok("添加成功!");
    }

    /**
     * 通过id删除
     *
     * @param id
     * @return
     */
    @DeleteMapping(value = "/deleteCustomer")
    public Result<?> deleteCustomer(@RequestParam(name = "id", required = true) String id) {
        fleetOrderCustomerService.removeById(id);
        return Result.ok("删除成功!");
    }

    /**
     * 批量删除
     *
     * @param ids
     * @return
     */
    @DeleteMapping(value = "/deleteBatchCustomer")
    public Result<?> deleteBatchCustomer(@RequestParam(name = "ids", required = true) String ids) {
        this.fleetOrderCustomerService.removeByIds(Arrays.asList(ids.split(",")));
        return Result.ok("批量删除成功!");
    }

    /**
     * 添加
     *
     * @param fleetOrderTicket
     * @return
     */
    @PostMapping(value = "/addTicket")
    public Result<?> addTicket(@RequestBody FleetOrderTicket fleetOrderTicket) {
        fleetOrderTicketService.save(fleetOrderTicket);
        return Result.ok("添加成功!");
    }

    /**
     * 编辑
     *
     * @param fleetOrderTicket
     * @return
     */
    @PutMapping("/editTicket")
    public Result<?> editTicket(@RequestBody FleetOrderTicket fleetOrderTicket) {
        fleetOrderTicketService.updateById(fleetOrderTicket);
        return Result.ok("编辑成功!");
    }

    /**
     * 通过id删除
     *
     * @param id
     * @return
     */
    @DeleteMapping(value = "/deleteTicket")
    public Result<?> deleteTicket(@RequestParam(name = "id", required = true) String id) {
        fleetOrderTicketService.removeById(id);
        return Result.ok("删除成功!");
    }

    /**
     * 批量删除
     *
     * @param ids
     * @return
     */
    @DeleteMapping(value = "/deleteBatchTicket")
    public Result<?> deleteBatchTicket(@RequestParam(name = "ids", required = true) String ids) {
        this.fleetOrderTicketService.removeByIds(Arrays.asList(ids.split(",")));
        return Result.ok("批量删除成功!");
    }

}
