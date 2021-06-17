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
 * @Description: 词汇定义
 * @Author: fleet-team
 * @Date: 2021-05-25
 * @Version: V1.0
 */
@Data
@TableName("dev_word")
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value = "dev_word对象", description = "词汇定义")
public class DevWord {

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
     * 词汇代码
     */
    @Excel(name = "词汇代码", width = 15)
    @ApiModelProperty(value = "词汇代码")
    private java.lang.String wordCode;
    /**
     * 词汇描述
     */
    @Excel(name = "词汇描述", width = 15)
    @ApiModelProperty(value = "词汇描述")
    private java.lang.String wordDesc;
    /**
     * 英文描述
     */
    @Excel(name = "英文描述", width = 15)
    @ApiModelProperty(value = "英文描述")
    private java.lang.String englishDesc;
    /**
     * 词汇缩写
     */
    @Excel(name = "词汇缩写", width = 15)
    @ApiModelProperty(value = "词汇缩写")
    private java.lang.String abbreviate;
    /**
     * 状态
     */
    @Excel(name = "状态", width = 15)
    @ApiModelProperty(value = "状态")
    private java.lang.String status;
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
