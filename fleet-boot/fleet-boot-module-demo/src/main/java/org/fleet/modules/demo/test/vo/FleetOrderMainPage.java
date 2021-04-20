package org.fleet.modules.demo.test.vo;

import java.util.List;

import org.fleet.modules.demo.test.entity.FleetOrderCustomer;
import org.fleet.modules.demo.test.entity.FleetOrderTicket;
import org.jeecgframework.poi.excel.annotation.Excel;
import org.jeecgframework.poi.excel.annotation.ExcelCollection;

import lombok.Data;

@Data
public class FleetOrderMainPage {
	
	/**主键*/
	private java.lang.String id;
	/**订单号*/
	@Excel(name="订单号",width=15)
	private java.lang.String orderCode;
	/**订单类型*/
	private java.lang.String ctype;
	/**订单日期*/
	@Excel(name="订单日期",width=15,format = "yyyy-MM-dd")
	private java.util.Date orderDate;
	/**订单金额*/
	@Excel(name="订单金额",width=15)
	private java.lang.Double orderMoney;
	/**订单备注*/
	private java.lang.String content;
	/**创建人*/
	private java.lang.String createBy;
	/**创建时间*/
	private java.util.Date createTime;
	/**修改人*/
	private java.lang.String updateBy;
	/**修改时间*/
	private java.util.Date updateTime;
	
	@ExcelCollection(name="客户")
	private List<FleetOrderCustomer> fleetOrderCustomerList;
	@ExcelCollection(name="机票")
	private List<FleetOrderTicket> fleetOrderTicketList;
	
}
