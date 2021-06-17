package org.fleet.modules.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.fleet.modules.system.entity.SysPermissionDataRule;

import java.util.List;

/**
 * <p>
 * 菜单权限规则 服务类
 * </p>
 *
 * @Author fleet-team
 * @since 2021-04-19
 */
public interface ISysPermissionDataRuleService extends IService<SysPermissionDataRule> {

    /**
     * 根据菜单id查询其对应的权限数据
     *
     * @param permissionId
     */
    List<SysPermissionDataRule> getPermRuleListByPermId(String permissionId);

    /**
     * 根据页面传递的参数查询菜单权限数据
     *
     * @return
     */
    List<SysPermissionDataRule> queryPermissionRule(SysPermissionDataRule permRule);

    /**
     * 根据菜单ID和用户名查找数据权限配置信息
     *
     * @param permissionId
     * @param username
     * @return
     */
    List<SysPermissionDataRule> queryPermissionDataRules(String username, String permissionId);

    /**
     * 新增菜单权限配置 修改菜单rule_flag
     *
     * @param sysPermissionDataRule
     */
    public void savePermissionDataRule(SysPermissionDataRule sysPermissionDataRule);

    /**
     * 删除菜单权限配置 判断菜单还有无权限
     *
     * @param dataRuleId
     */
    public void deletePermissionDataRule(String dataRuleId);

}
