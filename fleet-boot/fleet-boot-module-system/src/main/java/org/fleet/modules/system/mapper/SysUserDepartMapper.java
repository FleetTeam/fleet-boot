package org.fleet.modules.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.fleet.modules.system.entity.SysUserDepart;

import java.util.List;

public interface SysUserDepartMapper extends BaseMapper<SysUserDepart> {

    List<SysUserDepart> getUserDepartByUid(@Param("userId") String userId);
}
