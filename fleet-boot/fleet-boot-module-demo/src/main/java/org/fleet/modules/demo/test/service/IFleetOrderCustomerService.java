package org.fleet.modules.demo.test.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.fleet.modules.demo.test.entity.FleetOrderCustomer;

import java.util.List;

/**
 * @Description: 订单客户
 * @Author: fleet-team
 * @Date: 2019-02-15
 * @Version: V1.0
 */
public interface IFleetOrderCustomerService extends IService<FleetOrderCustomer> {

    public List<FleetOrderCustomer> selectCustomersByMainId(String mainId);
}
