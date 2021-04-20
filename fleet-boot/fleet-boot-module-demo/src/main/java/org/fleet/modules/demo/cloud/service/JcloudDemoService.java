package org.fleet.modules.demo.cloud.service;

import org.fleet.common.api.vo.Result;

public interface JcloudDemoService {
    Result<String> getMessage(String name);
}
