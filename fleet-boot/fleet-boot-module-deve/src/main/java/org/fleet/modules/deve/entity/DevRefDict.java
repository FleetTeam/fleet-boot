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
 * @Description: 参考字典
 * @Author: fleet-team
 * @Date: 2021-05-25
 * @Version: V1.0
 */
@Data
@TableName("dev_ref_dict")
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value = "dev_ref_dict对象", description = "参考字典")
public class DevRefDict {

    /**
     * ID
     */
    @TableId(type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "ID")
    private java.lang.String id;
    /**
     * 版本
     */
    @Excel(name = "版本", width = 15)
    @ApiModelProperty(value = "版本")
    private java.lang.Integer ver;
    /**
     * 系统标识
     */
    @Excel(name = "系统标识", width = 15)
    @ApiModelProperty(value = "系统标识")
    private java.lang.String sysId;
    /**
     * 字典代码
     */
    @Excel(name = "字典代码", width = 15)
    @ApiModelProperty(value = "字典代码")
    private java.lang.String dictCode;
    /**
     * 字典描述
     */
    @Excel(name = "字典描述", width = 15)
    @ApiModelProperty(value = "字典描述")
    private java.lang.String dictDesc;
    /**
     * 类型
     */
    @Excel(name = "类型", width = 15)
    @ApiModelProperty(value = "类型")
    private java.lang.String type;
    /**
     * 长度
     */
    @Excel(name = "长度", width = 15)
    @ApiModelProperty(value = "长度")
    private java.lang.Integer length;
    /**
     * 小数
     */
    @Excel(name = "小数", width = 15)
    @ApiModelProperty(value = "小数")
    private java.lang.Integer point;
    /**
     * 字典缩写
     */
    @Excel(name = "字典缩写", width = 15)
    @ApiModelProperty(value = "字典缩写")
    private java.lang.String dictAbbr;
    /**
     * 状态
     */
    @Excel(name = "状态", width = 15)
    @ApiModelProperty(value = "状态")
    private java.lang.String status;
    /**
     * 版本号
     */
    @Excel(name = "版本号", width = 15)
    @ApiModelProperty(value = "版本号")
    private java.lang.String verNo;
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
