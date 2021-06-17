package org.fleet.common.system.api.factory;

import feign.hystrix.FallbackFactory;
import org.fleet.common.system.api.ISysBaseAPI;
import org.fleet.common.system.api.fallback.SysBaseAPIFallback;
import org.springframework.stereotype.Component;

@Component
public class SysBaseAPIFallbackFactory implements FallbackFactory<ISysBaseAPI> {

    @Override
    public ISysBaseAPI create(Throwable throwable) {
        SysBaseAPIFallback fallback = new SysBaseAPIFallback();
        fallback.setCause(throwable);
        return fallback;
    }
}
