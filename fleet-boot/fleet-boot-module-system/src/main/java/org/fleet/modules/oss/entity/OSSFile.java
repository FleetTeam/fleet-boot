package org.fleet.modules.oss.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.fleet.common.system.base.entity.FleetEntity;
import org.jeecgframework.poi.excel.annotation.Excel;

@Data
@TableName("oss_file")
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class OSSFile extends FleetEntity {

	private static final long serialVersionUID = 1L;

	@Excel(name = "文件名称")
	private String fileName;

	@Excel(name = "文件地址")
	private String url;

}
