package org.fleet.modules.demo.test.service;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

import org.fleet.modules.demo.test.entity.FleetOrderCustomer;
import org.fleet.modules.demo.test.entity.FleetOrderMain;
import org.fleet.modules.demo.test.entity.FleetOrderTicket;

import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @Description: 订单
 * @Author: fleet-team
 * @Date: 2019-02-15
 * @Version: V1.0
 */
public interface IFleetOrderMainService extends IService<FleetOrderMain> {

    /**
     * 添加一对多
     */
    public void saveMain(FleetOrderMain fleetOrderMain, List<FleetOrderCustomer> fleetOrderCustomerList, List<FleetOrderTicket> fleetOrderTicketList);

    /**
     * 修改一对多
     */
    public void updateMain(FleetOrderMain fleetOrderMain, List<FleetOrderCustomer> fleetOrderCustomerList, List<FleetOrderTicket> fleetOrderTicketList);

    /**
     * 删除一对多
     *
     * @param id
     */
    public void delMain(String id);

    /**
     * 批量删除一对多
     *
     * @param idList
     */
    public void delBatchMain(Collection<? extends Serializable> idList);

    public void updateCopyMain(FleetOrderMain fleetOrderMain, List<FleetOrderCustomer> fleetOrderCustomerList, List<FleetOrderTicket> fleetOrderTicketList);
}
