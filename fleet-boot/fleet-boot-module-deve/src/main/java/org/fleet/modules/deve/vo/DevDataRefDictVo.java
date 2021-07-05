package org.fleet.modules.deve.vo;

import lombok.Data;
import org.fleet.modules.deve.entity.DevRefDict;

import java.util.Date;

/**
 * @Description: 数据字典增加参数字典属性
 * @Author: fleet-team
 * @Date: 2021-06-24
 * @Version: V1.0
 */
@Data
public class DevDataRefDictVo {

    /**
     * ID
     */
    private String id;
    /**
     * ver
     */
    private Integer ver;
    /**
     * 系统标识
     */
    private String sysId;
    /**
     * 元数据字典代码
     */
    private String unitDataCode;
    /**
     * 元数据字典描述
     */
    private String unitDataDesc;
    /**
     * 参考字典
     */
    private String refDict;
    /**
     * 状态
     */
    private String status;
    /**
     * 选项代码
     */
    private String optionCode;
    /**
     * 选项组别
     */
    private String optionGroup;
    /**
     * 选项版本
     */
    private String optionVer;
    /**
     * 控制状态
     */
    private String ctrlStatus;
    /**
     * verNo
     */
    private Integer verNo;
    /**
     * 版本状态
     */
    private String verStatus;
    /**
     * 检出用户
     */
    private String checkUser;
    /**
     * 备注
     */
    private String remark;
    /**
     * 创建人
     */
    private String createBy;
    /**
     * 创建日期
     */

    private Date createTime;
    /**
     * 更新人
     */
    private String updateBy;
    /**
     * 更新日期
     */
    private Date updateTime;
    /**
     * 参考字典属性
     */
    /**
     * 参考字典的数据类型
     */
    private String type;
    /**
     * 参考字典的长度
     */
    private java.lang.Integer length;
    /**
     * 参考字典小数位数
     */
    private java.lang.Integer point;

}
