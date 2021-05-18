package org.fleet.modules.deve.autocode.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;


public class CodeConfigProperties {
    private static final Logger log = LoggerFactory.getLogger(CodeConfigProperties.class);


    private static final String databaseConfigPath = "fleet/fleet_database";

    private static final String configPath = "fleet/fleet_config";
    public static String databaseType = "mysql";
    public static String diverName = "com.mysql.cj.jdbc.Driver";
    public static String url = "jdbc:mysql://localhost:3306/fleet-boot?useUnicode=true&characterEncoding=UTF-8";
    public static String username = "root";
    public static String password = "root";
    public static String databaseName = "fleet-boot";
    public static String projectPath = "c:/workspace/fleet";
    public static String bussiPackage = "org.fleet";
    public static String sourceRootPackage = "src";
    public static String webRootPackage = "WebRoot";
    public static String templatePath = "/fleet/code-template/";
    public static boolean DB_FILED_CONVERT = true;
    public static String DB_TABLE_ID;
    public static String PAGE_FIELD_REQUIRED_NUM = "4";
    public static String PAGE_FIELD_SEARCH_NUM = "3";
    public static String PAGE_FILTER_FIELDS;
    public static String FIELD_ROW_NUM = "1";
    private static ResourceBundle database_bundle = getResourceBundle(databaseConfigPath);
    private static ResourceBundle config_bundle = getResourceBundle(configPath);

    static {
        if (database_bundle == null) {
            log.debug("通过class目录加载配置文件 fleet/fleet_database");
            database_bundle = ResourceBundle.getBundle("fleet/fleet_database");
        }
    }

    static {
        if (config_bundle == null) {
            log.debug("通过class目录加载配置文件 fleet/fleet_config");
            config_bundle = ResourceBundle.getBundle("fleet/fleet_config");
        }
    }

    static {
        diverName = getDIVER_NAME();
        url = getURL();
        username = getUSERNAME();
        password = getPASSWORD();
        databaseName = getDATABASE_NAME();

        sourceRootPackage = getSourceRootPackage();
        webRootPackage = getWebRootPackage();
        bussiPackage = getBussiPackage();
        templatePath = getTemplatePath();
        projectPath = getProject_path();

        DB_TABLE_ID = getFleet_generate_table_id();

        DB_FILED_CONVERT = getDB_FILED_CONVERT();

        PAGE_FILTER_FIELDS = getFleet_generate_ui_filter_fields();

        PAGE_FIELD_SEARCH_NUM = getFleet_ui_search_filed_num();

        if (url.indexOf("mysql") >= 0 || url.indexOf("MYSQL") >= 0) {
            databaseType = "mysql";
        } else if (url.indexOf("oracle") >= 0 || url.indexOf("ORACLE") >= 0) {
            databaseType = "oracle";
        } else if (url.indexOf("postgresql") >= 0 || url.indexOf("POSTGRESQL") >= 0) {
            databaseType = "postgresql";
        } else if (url.indexOf("sqlserver") >= 0 || url.indexOf("sqlserver") >= 0) {
            databaseType = "sqlserver";
        }

        sourceRootPackage = sourceRootPackage.replace(".", "/");
        webRootPackage = webRootPackage.replace(".", "/");
    }

    private static ResourceBundle getResourceBundle(String configFilePath) {
        PropertyResourceBundle propertyResourceBundle = null;
        BufferedInputStream bufferedInputStream = null;
        String str = System.getProperty("user.dir") + File.separator + "config" + File.separator + configFilePath + ".properties";
        try {
            bufferedInputStream = new BufferedInputStream(new FileInputStream(str));
            propertyResourceBundle = new PropertyResourceBundle(bufferedInputStream);
            bufferedInputStream.close();
            if (propertyResourceBundle != null) {
                log.info(" JAR方式部署，通过config目录读取配置：" + str);
            }
        } catch (IOException iOException) {

        } finally {
            if (bufferedInputStream != null) {
                try {
                    bufferedInputStream.close();
                } catch (IOException iOException) {
                    iOException.printStackTrace();
                }
            }
        }
        return propertyResourceBundle;
    }

    public static final String getDIVER_NAME() {
        return database_bundle.getString("diver_name");
    }

    public static final String getURL() {
        return database_bundle.getString("url");
    }

    public static final String getUSERNAME() {
        return database_bundle.getString("username");
    }

    public static final String getPASSWORD() {
        return database_bundle.getString("password");
    }

    public static final String getDATABASE_NAME() {
        return database_bundle.getString("database_name");
    }

    public static final boolean getDB_FILED_CONVERT() {
        String s = config_bundle.getString("db_filed_convert");
        return !s.equals("false");
    }

    private static String getBussiPackage() {
        return config_bundle.getString("bussi_package");
    }

    private static String getTemplatePath() {
        return config_bundle.getString("templatepath");
    }

    public static final String getSourceRootPackage() {
        return config_bundle.getString("source_root_package");
    }

    public static final String getWebRootPackage() {
        return config_bundle.getString("webroot_package");
    }

    public static final String getFleet_generate_table_id() {
        return config_bundle.getString("db_table_id");
    }

    public static final String getFleet_generate_ui_filter_fields() {
        return config_bundle.getString("page_filter_fields");
    }

    public static final String getFleet_ui_search_filed_num() {
        return config_bundle.getString("page_search_filed_num");
    }

    public static final String getFleet_ui_field_required_num() {
        return config_bundle.getString("page_field_required_num");
    }

    public static String getProject_path() {
        String projp = config_bundle.getString("project_path");
        if (projp != null && !"".equals(projp)) {
            projectPath = projp;
        }
        return projectPath;
    }

    public static void setProjectPath(String projectPath) {
        CodeConfigProperties.projectPath = projectPath;
    }

    public static void setTemplatePath(String templatePath) {
        CodeConfigProperties.templatePath = templatePath;
    }
}
