package org.fleet.modules.system.model;

import lombok.Data;

/**
 * 包含 SysUser 和 SysDepart 的 Model
 *
 * @author fleet-team
 */
@Data
public class SysUserSysDepartModel {

    private String id;
    private String realname;
    private String workNo;
    private String post;
    private String telephone;
    private String email;
    private String phone;
    private String departId;
    private String departName;
    private String avatar;

}
