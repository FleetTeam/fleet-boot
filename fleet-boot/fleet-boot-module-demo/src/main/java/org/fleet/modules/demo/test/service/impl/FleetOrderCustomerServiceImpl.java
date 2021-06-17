package org.fleet.modules.demo.test.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.fleet.modules.demo.test.entity.FleetOrderCustomer;
import org.fleet.modules.demo.test.mapper.FleetOrderCustomerMapper;
import org.fleet.modules.demo.test.service.IFleetOrderCustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Description: 订单客户
 * @Author: fleet-team
 * @Date: 2019-02-15
 * @Version: V1.0
 */
@Service
public class FleetOrderCustomerServiceImpl extends ServiceImpl<FleetOrderCustomerMapper, FleetOrderCustomer> implements IFleetOrderCustomerService {

    @Autowired
    private FleetOrderCustomerMapper fleetOrderCustomerMapper;

    @Override
    public List<FleetOrderCustomer> selectCustomersByMainId(String mainId) {
        return fleetOrderCustomerMapper.selectCustomersByMainId(mainId);
    }

}
