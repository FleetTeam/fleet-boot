package org.fleet.modules.quartz.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.fleet.modules.quartz.entity.QuartzJob;

import java.util.List;

/**
 * @Description: 定时任务在线管理
 * @Author: fleet-team
 * @Date: 2021-04-19
 * @Version: V1.0
 */
public interface QuartzJobMapper extends BaseMapper<QuartzJob> {

    public List<QuartzJob> findByJobClassName(@Param("jobClassName") String jobClassName);

}
