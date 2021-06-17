package org.fleet.modules.system.service;

import java.util.List;

import org.fleet.modules.system.entity.SysUser;
import org.fleet.modules.system.entity.SysUserDepart;
import org.fleet.modules.system.model.DepartIdModel;

import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * SysUserDpeart用户组织机构service
 * </p>
 *
 * @Author fleet-team
 */
public interface ISysUserDepartService extends IService<SysUserDepart> {

    /**
     * 根据指定用户id查询部门信息
     *
     * @param userId
     * @return
     */
    List<DepartIdModel> queryDepartIdsOfUser(String userId);

    /**
     * 根据部门id查询用户信息
     *
     * @param depId
     * @return
     */
    List<SysUser> queryUserByDepId(String depId);

    /**
     * 根据部门code，查询当前部门和下级部门的用户信息
     */
    public List<SysUser> queryUserByDepCode(String depCode, String realname);
}
