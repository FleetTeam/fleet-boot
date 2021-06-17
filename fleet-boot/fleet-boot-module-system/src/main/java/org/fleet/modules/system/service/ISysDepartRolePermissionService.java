package org.fleet.modules.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.fleet.modules.system.entity.SysDepartRolePermission;

/**
 * @Description: 部门角色权限
 * @Author: fleet-team
 * @Date: 2021-04-19
 * @Version: V1.0
 */
public interface ISysDepartRolePermissionService extends IService<SysDepartRolePermission> {
    /**
     * 保存授权 将上次的权限和这次作比较 差异处理提高效率
     *
     * @param roleId
     * @param permissionIds
     * @param lastPermissionIds
     */
    public void saveDeptRolePermission(String roleId, String permissionIds, String lastPermissionIds);
}
