package org.fleet.modules.demo.test.service;

import org.fleet.common.system.base.service.FleetService;
import org.fleet.modules.demo.test.entity.FleetDemo;

import com.baomidou.mybatisplus.core.metadata.IPage;

/**
 * @Description: fleet 测试demo
 * @Author: fleet-team
 * @Date: 2018-12-29
 * @Version: V1.0
 */
public interface IFleetDemoService extends FleetService<FleetDemo> {

    public void testTran();

    public FleetDemo getByIdCacheable(String id);

    /**
     * 查询列表数据 在service中获取数据权限sql信息
     *
     * @param pageSize
     * @param pageNo
     * @return
     */
    IPage<FleetDemo> queryListWithPermission(int pageSize, int pageNo);

    /**
     * 根据用户权限获取导出字段
     *
     * @return
     */
    String getExportFields();
}
