package org.fleet.modules.monitor.service;

import com.alibaba.fastjson.JSONArray;
import org.fleet.modules.monitor.domain.RedisInfo;
import org.fleet.modules.monitor.exception.RedisConnectException;

import java.util.List;
import java.util.Map;

public interface RedisService {

    /**
     * 获取 redis 的详细信息
     *
     * @return List
     */
    List<RedisInfo> getRedisInfo() throws RedisConnectException;

    /**
     * 获取 redis key 数量
     *
     * @return Map
     */
    Map<String, Object> getKeysSize() throws RedisConnectException;

    /**
     * 获取 redis 内存信息
     *
     * @return Map
     */
    Map<String, Object> getMemoryInfo() throws RedisConnectException;

    /**
     * 获取 报表需要个redis信息
     *
     * @return Map
     */
    Map<String, JSONArray> getMapForReport(String type) throws RedisConnectException;
}
