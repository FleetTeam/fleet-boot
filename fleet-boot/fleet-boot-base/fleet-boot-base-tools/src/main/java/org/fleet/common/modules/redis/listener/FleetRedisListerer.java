package org.fleet.common.modules.redis.listener;

import org.fleet.common.base.BaseMap;

/**
 * 自定义消息监听
 */
public interface FleetRedisListerer {

    void onMessage(BaseMap message);

}
