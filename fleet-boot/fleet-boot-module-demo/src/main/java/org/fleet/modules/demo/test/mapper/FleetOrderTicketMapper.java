package org.fleet.modules.demo.test.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Select;
import org.fleet.modules.demo.test.entity.FleetOrderTicket;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * @Description: 订单机票
 * @Author: fleet-team
 * @Date: 2019-02-15
 * @Version: V1.0
 */
public interface FleetOrderTicketMapper extends BaseMapper<FleetOrderTicket> {

    /**
     * 通过主表外键批量删除客户
     *
     * @param mainId
     * @return
     */
    @Delete("DELETE FROM JEECG_ORDER_TICKET WHERE ORDER_ID = #{mainId}")
    public boolean deleteTicketsByMainId(String mainId);

    @Select("SELECT * FROM JEECG_ORDER_TICKET WHERE ORDER_ID = #{mainId}")
    public List<FleetOrderTicket> selectTicketsByMainId(String mainId);
}
