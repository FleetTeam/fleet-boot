package org.fleet.modules.deve.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.fleet.modules.deve.entity.DevDataDict;
import org.fleet.modules.deve.mapper.DevDataDictMapper;
import org.fleet.modules.deve.service.IDevDataDictService;
import org.fleet.modules.deve.vo.DevDataRefDictVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

/**
 * @Description: 数据字典
 * @Author: fleet-team
 * @Date: 2021-05-25
 * @Version: V1.0
 */
@Service
public class DevDataDictServiceImpl extends ServiceImpl<DevDataDictMapper, DevDataDict> implements IDevDataDictService {
    @Autowired
    private DevDataDictMapper devDataDictMapper;

    @Override
    public IPage<DevDataRefDictVo> queryDataDictRefAttr(Page<DevDataRefDictVo> page, QueryWrapper<DevDataRefDictVo> queryWrapper) {
        return devDataDictMapper.getDataDictAttr(page, queryWrapper);
    }
}
