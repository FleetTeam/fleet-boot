package org.fleet.modules.demo.test.service;

import java.util.List;

import org.fleet.modules.demo.test.entity.FleetOrderTicket;

import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @Description: 订单机票
 * @Author: fleet-team
 * @Date:  2019-02-15
 * @Version: V1.0
 */
public interface IFleetOrderTicketService extends IService<FleetOrderTicket> {
	
	public List<FleetOrderTicket> selectTicketsByMainId(String mainId);
}
