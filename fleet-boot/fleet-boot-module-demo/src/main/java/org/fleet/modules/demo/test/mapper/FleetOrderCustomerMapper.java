package org.fleet.modules.demo.test.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Select;
import org.fleet.modules.demo.test.entity.FleetOrderCustomer;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * @Description: 订单客户
 * @Author: fleet-team
 * @Date:  2019-02-15
 * @Version: V1.0
 */
public interface FleetOrderCustomerMapper extends BaseMapper<FleetOrderCustomer> {
	
	/**
	 *  通过主表外键批量删除客户
	 * @param mainId
	 * @return
	 */
    @Delete("DELETE FROM JEECG_ORDER_CUSTOMER WHERE ORDER_ID = #{mainId}")
	public boolean deleteCustomersByMainId(String mainId);
    
    @Select("SELECT * FROM JEECG_ORDER_CUSTOMER WHERE ORDER_ID = #{mainId}")
	public List<FleetOrderCustomer> selectCustomersByMainId(String mainId);
}
