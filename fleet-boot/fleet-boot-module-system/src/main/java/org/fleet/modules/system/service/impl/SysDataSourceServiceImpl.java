package org.fleet.modules.system.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.fleet.modules.system.entity.SysDataSource;
import org.fleet.modules.system.mapper.SysDataSourceMapper;
import org.fleet.modules.system.service.ISysDataSourceService;
import org.springframework.stereotype.Service;

/**
 * @Description: 多数据源管理
 * @Author: fleet-team
 * @Date: 2021-04-19
 * @Version: V1.0
 */
@Service
public class SysDataSourceServiceImpl extends ServiceImpl<SysDataSourceMapper, SysDataSource> implements ISysDataSourceService {

}
