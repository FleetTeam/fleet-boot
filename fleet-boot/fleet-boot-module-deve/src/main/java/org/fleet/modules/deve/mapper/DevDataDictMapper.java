package org.fleet.modules.deve.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import org.fleet.modules.deve.entity.DevDataDict;
import org.fleet.modules.deve.vo.DevDataRefDictVo;

/**
 * @Description: 数据字典
 * @Author: fleet-team
 * @Date: 2021-05-25
 * @Version: V1.0
 */
public interface DevDataDictMapper extends BaseMapper<DevDataDict> {
    /**
     * 查询数据字典及其参考字典的属性信息列表
     *
     * @param page
     * @param wrapper
     * @return
     */
    IPage<DevDataRefDictVo> getDataDictAttr(Page page, @Param(Constants.WRAPPER) QueryWrapper<DevDataRefDictVo> wrapper);

}
