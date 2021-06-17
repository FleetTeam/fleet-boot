package org.fleet.modules.demo.test.service.impl;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.fleet.modules.demo.test.entity.FleetOrderCustomer;
import org.fleet.modules.demo.test.entity.FleetOrderMain;
import org.fleet.modules.demo.test.entity.FleetOrderTicket;
import org.fleet.modules.demo.test.mapper.FleetOrderCustomerMapper;
import org.fleet.modules.demo.test.mapper.FleetOrderMainMapper;
import org.fleet.modules.demo.test.mapper.FleetOrderTicketMapper;
import org.fleet.modules.demo.test.service.IFleetOrderMainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

/**
 * @Description: 订单
 * @Author: Fleet-boot
 * @Date: 2019-02-15
 * @Version: V1.0
 */
@Service
public class FleetOrderMainServiceImpl extends ServiceImpl<FleetOrderMainMapper, FleetOrderMain> implements IFleetOrderMainService {

    @Autowired
    private FleetOrderMainMapper fleetOrderMainMapper;
    @Autowired
    private FleetOrderCustomerMapper fleetOrderCustomerMapper;
    @Autowired
    private FleetOrderTicketMapper fleetOrderTicketMapper;

    @Override
    @Transactional
    public void saveMain(FleetOrderMain fleetOrderMain, List<FleetOrderCustomer> fleetOrderCustomerList, List<FleetOrderTicket> fleetOrderTicketList) {
        fleetOrderMainMapper.insert(fleetOrderMain);
        if (fleetOrderCustomerList != null) {
            for (FleetOrderCustomer entity : fleetOrderCustomerList) {
                entity.setOrderId(fleetOrderMain.getId());
                fleetOrderCustomerMapper.insert(entity);
            }
        }
        if (fleetOrderTicketList != null) {
            for (FleetOrderTicket entity : fleetOrderTicketList) {
                entity.setOrderId(fleetOrderMain.getId());
                fleetOrderTicketMapper.insert(entity);
            }
        }
    }

    @Override
    @Transactional
    public void updateMain(FleetOrderMain fleetOrderMain, List<FleetOrderCustomer> fleetOrderCustomerList, List<FleetOrderTicket> fleetOrderTicketList) {
        fleetOrderMainMapper.updateById(fleetOrderMain);

        // 1.先删除子表数据
        fleetOrderTicketMapper.deleteTicketsByMainId(fleetOrderMain.getId());
        fleetOrderCustomerMapper.deleteCustomersByMainId(fleetOrderMain.getId());

        // 2.子表数据重新插入
        if (fleetOrderCustomerList != null) {
            for (FleetOrderCustomer entity : fleetOrderCustomerList) {
                entity.setOrderId(fleetOrderMain.getId());
                fleetOrderCustomerMapper.insert(entity);
            }
        }
        if (fleetOrderTicketList != null) {
            for (FleetOrderTicket entity : fleetOrderTicketList) {
                entity.setOrderId(fleetOrderMain.getId());
                fleetOrderTicketMapper.insert(entity);
            }
        }
    }

    /**
     * 一对多维护逻辑改造  LOWCOD-315
     *
     * @param fleetOrderMain
     * @param fleetOrderCustomerList
     * @param fleetOrderTicketList
     */
    @Override
    @Transactional
    public void updateCopyMain(FleetOrderMain fleetOrderMain, List<FleetOrderCustomer> fleetOrderCustomerList, List<FleetOrderTicket> fleetOrderTicketList) {
        fleetOrderMainMapper.updateById(fleetOrderMain);

        // 循环前台传过来的数据
        for (FleetOrderTicket ticket : fleetOrderTicketList) {
            // 先查询子表数据库
            FleetOrderTicket orderTicket = fleetOrderTicketMapper.selectById(ticket.getId());
            if (orderTicket == null) {
                // 当传过来的id数据库不存在时，说明数据库没有，走新增逻辑
                ticket.setOrderId(fleetOrderMain.getId());
                fleetOrderTicketMapper.insert(ticket);
                break;
            }
            if (orderTicket.getId().equals(ticket.getId())) {
                // 传过来的id和数据库id一至时，说明数据库存在该数据，走更新逻辑
                fleetOrderTicketMapper.updateById(ticket);
            }
        }
        for (FleetOrderCustomer customer : fleetOrderCustomerList) {
            // 先查询子表数据库
            FleetOrderCustomer customers = fleetOrderCustomerMapper.selectById(customer.getId());
            if (customers == null) {
                // 当传过来的id数据库不存在时，说明数据库没有，走新增逻辑
                customer.setOrderId(fleetOrderMain.getId());
                fleetOrderCustomerMapper.insert(customer);
                break;
            }
            if (customers.getId().equals(customer.getId())) {
                // TODO 传过来的id和数据库id一至时，说明数据库存在该数据，走更新逻辑
                fleetOrderCustomerMapper.updateById(customer);
            }
        }
        // 当跟新和删除之后取差集， 当传过来的id不存在，而数据库存在时，说明已删除，走删除逻辑
        List<FleetOrderTicket> fleetOrderTickets = fleetOrderTicketMapper.selectTicketsByMainId(fleetOrderMain.getId());
        List<FleetOrderTicket> collect = fleetOrderTickets.stream()
                .filter(item -> !fleetOrderTicketList.stream()
                        .map(e -> e.getId())
                        .collect(Collectors.toList())
                        .contains(item.getId()))
                .collect(Collectors.toList());
        // for循环删除id
        for (FleetOrderTicket ticket : collect) {
            fleetOrderTicketMapper.deleteById(ticket.getId());
        }

        List<FleetOrderCustomer> fleetOrderCustomers = fleetOrderCustomerMapper.selectCustomersByMainId(fleetOrderMain.getId());
        List<FleetOrderCustomer> customersCollect = fleetOrderCustomers.stream()
                .filter(item -> !fleetOrderCustomerList.stream()
                        .map(e -> e.getId())
                        .collect(Collectors.toList())
                        .contains(item.getId()))
                .collect(Collectors.toList());
        // TODO for循环删除id
        for (FleetOrderCustomer c : customersCollect) {
            fleetOrderCustomerMapper.deleteById(c.getId());
        }
    }

    @Override
    @Transactional
    public void delMain(String id) {
        fleetOrderMainMapper.deleteById(id);
        fleetOrderTicketMapper.deleteTicketsByMainId(id);
        fleetOrderCustomerMapper.deleteCustomersByMainId(id);
    }

    @Override
    @Transactional
    public void delBatchMain(Collection<? extends Serializable> idList) {
        for (Serializable id : idList) {
            fleetOrderMainMapper.deleteById(id);
            fleetOrderTicketMapper.deleteTicketsByMainId(id.toString());
            fleetOrderCustomerMapper.deleteCustomersByMainId(id.toString());
        }
    }

}
