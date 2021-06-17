package org.fleet.modules.system.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.fleet.modules.system.entity.SysCategory;
import org.fleet.modules.system.model.TreeSelectModel;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * @Description: 分类字典
 * @Author: fleet-team
 * @Date: 2021-04-19
 * @Version: V1.0
 */
public interface SysCategoryMapper extends BaseMapper<SysCategory> {

    /**
     * 根据父级ID查询树节点数据
     *
     * @param pid
     * @return
     */
    public List<TreeSelectModel> queryListByPid(@Param("pid") String pid, @Param("query") Map<String, String> query);

    @Select("SELECT ID FROM sys_category WHERE CODE = #{code,jdbcType=VARCHAR}")
    public String queryIdByCode(@Param("code") String code);

}
