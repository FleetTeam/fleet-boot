package org.fleet.common.modules.redis.receiver;

import cn.hutool.core.util.ObjectUtil;
import lombok.Data;
import org.fleet.common.base.BaseMap;
import org.fleet.common.constant.GlobalConstants;
import org.fleet.common.modules.redis.listener.FleetRedisListerer;
import org.fleet.common.util.SpringContextHolder;
import org.springframework.stereotype.Component;

/**
 * @author fleet-team
 */
@Component
@Data
public class RedisReceiver {

    /**
     * 接受消息并调用业务逻辑处理器
     *
     * @param params
     */
    public void onMessage(BaseMap params) {
        Object handlerName = params.get(GlobalConstants.HANDLER_NAME);
        FleetRedisListerer messageListener = SpringContextHolder.getHandler(handlerName.toString(), FleetRedisListerer.class);
        if (ObjectUtil.isNotEmpty(messageListener)) {
            messageListener.onMessage(params);
        }
    }

}
