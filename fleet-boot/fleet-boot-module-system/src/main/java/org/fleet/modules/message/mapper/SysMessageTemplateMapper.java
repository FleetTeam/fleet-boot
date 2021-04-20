package org.fleet.modules.message.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;
import org.fleet.modules.message.entity.SysMessageTemplate;

import java.util.List;

/**
 * @Description: 消息模板
 * @Author: fleet-team
 * @Date:  2021-04-19
 * @Version: V1.0
 */
public interface SysMessageTemplateMapper extends BaseMapper<SysMessageTemplate> {
    @Select("SELECT * FROM SYS_SMS_TEMPLATE WHERE TEMPLATE_CODE = #{code}")
    List<SysMessageTemplate> selectByCode(String code);
}
