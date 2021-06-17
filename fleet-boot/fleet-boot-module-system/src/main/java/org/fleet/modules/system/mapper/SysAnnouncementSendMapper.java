package org.fleet.modules.system.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.fleet.modules.system.entity.SysAnnouncementSend;
import org.fleet.modules.system.model.AnnouncementSendModel;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

/**
 * @Description: 用户通告阅读标记表
 * @Author: fleet-team
 * @Date: 2021-04-19
 * @Version: V1.0
 */
public interface SysAnnouncementSendMapper extends BaseMapper<SysAnnouncementSend> {

    public List<String> queryByUserId(@Param("userId") String userId);

    /**
     * @param announcementSendModel
     * @param page
     * @param announcementSendModel
     * @return
     * @功能：获取我的消息
     */
    public List<AnnouncementSendModel> getMyAnnouncementSendList(Page<AnnouncementSendModel> page, @Param("announcementSendModel") AnnouncementSendModel announcementSendModel);

}
