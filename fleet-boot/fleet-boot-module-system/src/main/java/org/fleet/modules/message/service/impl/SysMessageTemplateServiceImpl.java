package org.fleet.modules.message.service.impl;

import org.fleet.common.system.base.service.impl.FleetServiceImpl;
import org.fleet.modules.message.entity.SysMessageTemplate;
import org.fleet.modules.message.mapper.SysMessageTemplateMapper;
import org.fleet.modules.message.service.ISysMessageTemplateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

/**
 * @Description: 消息模板
 * @Author: fleet-team
 * @Date:  2021-04-19
 * @Version: V1.0
 */
@Service
public class SysMessageTemplateServiceImpl extends FleetServiceImpl<SysMessageTemplateMapper, SysMessageTemplate> implements ISysMessageTemplateService {

    @Autowired
    private SysMessageTemplateMapper sysMessageTemplateMapper;


    @Override
    public List<SysMessageTemplate> selectByCode(String code) {
        return sysMessageTemplateMapper.selectByCode(code);
    }
}
