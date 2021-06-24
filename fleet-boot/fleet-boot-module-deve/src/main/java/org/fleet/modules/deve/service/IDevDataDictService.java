package org.fleet.modules.deve.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.fleet.modules.deve.entity.DevDataDict;
import com.baomidou.mybatisplus.extension.service.IService;
import org.fleet.modules.deve.vo.DevDataRefDictVo;

/**
 * @Description: 数据字典
 * @Author: fleet-team
 * @Date: 2021-05-25
 * @Version: V1.0
 */
public interface IDevDataDictService extends IService<DevDataDict> {

    public IPage<DevDataRefDictVo> queryDataDictRefAttr(Page<DevDataRefDictVo> page, QueryWrapper<DevDataRefDictVo> queryWrapper);

}
