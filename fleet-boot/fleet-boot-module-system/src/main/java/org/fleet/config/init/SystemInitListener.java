package org.fleet.config.init;

import lombok.extern.slf4j.Slf4j;
import org.fleet.common.constant.CacheConstant;
import org.fleet.config.FleetCloudCondition;
import org.fleet.modules.system.service.ISysGatewayRouteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Conditional;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;

/**
 * @desc: 启动程序，初始化路由配置
 * @author: fleet-team
 */
@Slf4j
@Component
@Conditional(FleetCloudCondition.class)
public class SystemInitListener implements ApplicationListener<ApplicationReadyEvent>, Ordered {

    @Autowired
    private ISysGatewayRouteService sysGatewayRouteService;

    @Override
    public void onApplicationEvent(ApplicationReadyEvent applicationReadyEvent) {

        log.info(" 服务已启动，初始化路由配置 ###################");

        final String displayName = "AnnotationConfigServletWebServerApplicationContext";
        if (applicationReadyEvent.getApplicationContext().getDisplayName().contains(displayName)) {
            sysGatewayRouteService.addRoute2Redis(CacheConstant.GATEWAY_ROUTES);
        }

    }

    @Override
    public int getOrder() {
        return 1;
    }
}
