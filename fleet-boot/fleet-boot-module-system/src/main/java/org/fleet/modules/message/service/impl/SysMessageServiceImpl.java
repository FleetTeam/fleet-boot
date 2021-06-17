package org.fleet.modules.message.service.impl;

import org.fleet.common.system.base.service.impl.FleetServiceImpl;
import org.fleet.modules.message.entity.SysMessage;
import org.fleet.modules.message.mapper.SysMessageMapper;
import org.fleet.modules.message.service.ISysMessageService;
import org.springframework.stereotype.Service;

/**
 * @Description: 消息
 * @Author: fleet-team
 * @Date: 2021-04-19
 * @Version: V1.0
 */
@Service
public class SysMessageServiceImpl extends FleetServiceImpl<SysMessageMapper, SysMessage> implements ISysMessageService {

}
