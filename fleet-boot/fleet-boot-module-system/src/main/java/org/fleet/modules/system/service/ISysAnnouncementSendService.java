package org.fleet.modules.system.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import org.fleet.modules.system.entity.SysAnnouncementSend;
import org.fleet.modules.system.model.AnnouncementSendModel;

import java.util.List;

/**
 * @Description: 用户通告阅读标记表
 * @Author: fleet-team
 * @Date: 2021-04-21
 * @Version: V1.0
 */
public interface ISysAnnouncementSendService extends IService<SysAnnouncementSend> {

    public List<String> queryByUserId(String userId);

    /**
     * @param announcementSendModel
     * @return
     * @功能：获取我的消息
     */
    public Page<AnnouncementSendModel> getMyAnnouncementSendPage(Page<AnnouncementSendModel> page, AnnouncementSendModel announcementSendModel);

}
