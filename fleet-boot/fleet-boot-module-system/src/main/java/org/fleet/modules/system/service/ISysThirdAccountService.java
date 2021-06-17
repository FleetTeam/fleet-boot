package org.fleet.modules.system.service;

import org.fleet.modules.system.entity.SysThirdAccount;
import com.baomidou.mybatisplus.extension.service.IService;
import org.fleet.modules.system.entity.SysUser;

/**
 * @Description: 第三方登录账号表
 * @Author: fleet-team
 * @Date: 2021-04-19
 * @Version: V1.0
 */
public interface ISysThirdAccountService extends IService<SysThirdAccount> {
    /**
     * 更新第三方账户信息
     */
    void updateThirdUserId(SysUser sysUser, String thirdUserUuid);

    /**
     * 创建第三方用户
     */
    SysUser createUser(String phone, String thirdUserUuid);

}
