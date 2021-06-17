package org.fleet.modules.demo.test.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.fleet.modules.demo.test.entity.FleetDemo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

/**
 * @Description: fleet 测试demo
 * @Author: fleet-team
 * @Date: 2018-12-29
 * @Version: V1.0
 */
public interface FleetDemoMapper extends BaseMapper<FleetDemo> {

    public List<FleetDemo> getDemoByName(@Param("name") String name);

    /**
     * 查询列表数据 直接传数据权限的sql进行数据过滤
     *
     * @param page
     * @param permissionSql
     * @return
     */
    public IPage<FleetDemo> queryListWithPermission(Page<FleetDemo> page, @Param("permissionSql") String permissionSql);

    /**
     * 根据前缀获取所有有效权限
     *
     * @param permsPrefix
     * @return
     */
    public List<String> queryAllAuth(@Param("permsPrefix") String permsPrefix);

    /**
     * 查询用户已授权字段
     *
     * @param userId
     * @param permsPrefix
     * @return
     */
    public List<String> queryUserAuth(@Param("userId") String userId, @Param("permsPrefix") String permsPrefix);

}
