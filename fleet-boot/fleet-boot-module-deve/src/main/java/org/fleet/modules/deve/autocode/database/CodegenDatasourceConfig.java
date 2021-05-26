package org.fleet.modules.deve.autocode.database;

import org.fleet.modules.deve.autocode.config.CodeConfigProperties;

public class CodegenDatasourceConfig {
    public static void initDbConfig(String diverName, String url, String username, String password) {
        CodeConfigProperties.diverName = diverName;
        CodeConfigProperties.url = url;
        CodeConfigProperties.username = username;
        CodeConfigProperties.password = password;
    }
}

