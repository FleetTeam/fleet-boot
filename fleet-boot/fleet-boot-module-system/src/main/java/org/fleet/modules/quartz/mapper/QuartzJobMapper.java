package org.fleet.modules.quartz.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.fleet.modules.quartz.entity.QuartzJob;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * @Description: 定时任务在线管理
 * @Author: fleet-team
 * @Date:  2021-04-19
 * @Version: V1.0
 */
public interface QuartzJobMapper extends BaseMapper<QuartzJob> {

	public List<QuartzJob> findByJobClassName(@Param("jobClassName") String jobClassName);

}
