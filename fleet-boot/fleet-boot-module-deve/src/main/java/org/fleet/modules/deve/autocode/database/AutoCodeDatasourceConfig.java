package org.fleet.modules.deve.autocode.database;

import org.fleet.modules.deve.autocode.config.AutoCodeConfigProperties;

public class AutoCodeDatasourceConfig {
    public static void initDbConfig(String diverName, String url, String username, String password) {
        AutoCodeConfigProperties.diverName = diverName;
        AutoCodeConfigProperties.url = url;
        AutoCodeConfigProperties.username = username;
        AutoCodeConfigProperties.password = password;
    }
}

