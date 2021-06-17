package org.fleet.common.system.base.service.impl;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.fleet.common.system.base.entity.FleetEntity;
import org.fleet.common.system.base.service.FleetService;

/**
 * @Description: ServiceImpl基类
 * @Author: fleet-team
 * @Date: 2021-04-21
 * @Version: 1.0
 */
@Slf4j
public class FleetServiceImpl<M extends BaseMapper<T>, T extends FleetEntity> extends ServiceImpl<M, T> implements FleetService<T> {

}
