package org.fleet.modules.demo.test.service.impl;

import java.util.List;

import org.fleet.modules.demo.test.entity.FleetOrderTicket;
import org.fleet.modules.demo.test.mapper.FleetOrderTicketMapper;
import org.fleet.modules.demo.test.service.IFleetOrderTicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

/**
 * @Description: 订单机票
 * @Author: fleet-team
 * @Date: 2019-02-15
 * @Version: V1.0
 */
@Service
public class FleetOrderTicketServiceImpl extends ServiceImpl<FleetOrderTicketMapper, FleetOrderTicket> implements IFleetOrderTicketService {
    @Autowired
    private FleetOrderTicketMapper fleetOrderTicketMapper;

    @Override
    public List<FleetOrderTicket> selectTicketsByMainId(String mainId) {
        return fleetOrderTicketMapper.selectTicketsByMainId(mainId);
    }

}
