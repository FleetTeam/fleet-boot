package org.fleet.modules.system.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.fleet.modules.system.entity.SysDepartRole;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * @Description: 部门角色
 * @Author: fleet-team
 * @Date: 2021-04-19
 * @Version: V1.0
 */
public interface SysDepartRoleMapper extends BaseMapper<SysDepartRole> {
    /**
     * 根据用户id，部门id查询可授权所有部门角色
     *
     * @param orgCode
     * @param userId
     * @return
     */
    public List<SysDepartRole> queryDeptRoleByDeptAndUser(@Param("orgCode") String orgCode, @Param("userId") String userId);
}
