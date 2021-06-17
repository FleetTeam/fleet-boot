package org.fleet.modules.deve.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.jeecgframework.poi.excel.annotation.Excel;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * @Description: 数据字典
 * @Author: fleet-team
 * @Date: 2021-05-25
 * @Version: V1.0
 */
@Data
@TableName("dev_data_dict")
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value = "dev_data_dict对象", description = "数据字典")
public class DevDataDict {

    /**
     * ID
     */
    @TableId(type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "ID")
    private java.lang.String id;
    /**
     * ver
     */
    @Excel(name = "ver", width = 15)
    @ApiModelProperty(value = "ver")
    private java.lang.Integer ver;
    /**
     * 系统标识
     */
    @Excel(name = "系统标识", width = 15)
    @ApiModelProperty(value = "系统标识")
    private java.lang.String sysId;
    /**
     * 元数据字典代码
     */
    @Excel(name = "元数据字典代码", width = 15)
    @ApiModelProperty(value = "元数据字典代码")
    private java.lang.String unitDataCode;
    /**
     * 元数据字典描述
     */
    @Excel(name = "元数据字典描述", width = 15)
    @ApiModelProperty(value = "元数据字典描述")
    private java.lang.String unitDataDesc;
    /**
     * 参考字典
     */
    @Excel(name = "参考字典", width = 15)
    @ApiModelProperty(value = "参考字典")
    private java.lang.String refDict;
    /**
     * 状态
     */
    @Excel(name = "状态", width = 15)
    @ApiModelProperty(value = "状态")
    private java.lang.String status;
    /**
     * 选项代码
     */
    @Excel(name = "选项代码", width = 15)
    @ApiModelProperty(value = "选项代码")
    private java.lang.String optionCode;
    /**
     * 选项组别
     */
    @Excel(name = "选项组别", width = 15)
    @ApiModelProperty(value = "选项组别")
    private java.lang.String optionGroup;
    /**
     * 选项版本
     */
    @Excel(name = "选项版本", width = 15)
    @ApiModelProperty(value = "选项版本")
    private java.lang.String optionVer;
    /**
     * 控制状态
     */
    @Excel(name = "控制状态", width = 15)
    @ApiModelProperty(value = "控制状态")
    private java.lang.String ctrlStatus;
    /**
     * verNo
     */
    @Excel(name = "verNo", width = 15)
    @ApiModelProperty(value = "verNo")
    private java.lang.Integer verNo;
    /**
     * 版本状态
     */
    @Excel(name = "版本状态", width = 15)
    @ApiModelProperty(value = "版本状态")
    private java.lang.String verStatus;
    /**
     * 检出用户
     */
    @Excel(name = "检出用户", width = 15)
    @ApiModelProperty(value = "检出用户")
    private java.lang.String checkUser;
    /**
     * 备注
     */
    @Excel(name = "备注", width = 15)
    @ApiModelProperty(value = "备注")
    private java.lang.String remark;
    /**
     * 创建人
     */
    @Excel(name = "创建人", width = 15)
    @ApiModelProperty(value = "创建人")
    private java.lang.String createBy;
    /**
     * 创建日期
     */
    @Excel(name = "创建日期", width = 20, format = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "创建日期")
    private java.util.Date createTime;
    /**
     * 更新人
     */
    @Excel(name = "更新人", width = 15)
    @ApiModelProperty(value = "更新人")
    private java.lang.String updateBy;
    /**
     * 更新日期
     */
    @Excel(name = "更新日期", width = 20, format = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "更新日期")
    private java.util.Date updateTime;
}
