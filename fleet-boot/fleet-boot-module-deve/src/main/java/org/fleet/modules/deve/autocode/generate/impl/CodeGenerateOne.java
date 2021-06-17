package org.fleet.modules.deve.autocode.generate.impl;

import org.apache.commons.lang.StringUtils;
import org.fleet.modules.deve.autocode.config.AutoCodeConfigProperties;
import org.fleet.modules.deve.autocode.database.DbReadTableUtil;
import org.fleet.modules.deve.autocode.generate.IGenerate;
import org.fleet.modules.deve.autocode.generate.config.CreateFileConfig;
import org.fleet.modules.deve.autocode.generate.impl.base.BaseCodeGenerate;
import org.fleet.modules.deve.autocode.generate.pojo.ColumnVo;
import org.fleet.modules.deve.autocode.generate.pojo.TableVo;
import org.fleet.modules.deve.autocode.generate.util.NonceUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CodeGenerateOne
        extends BaseCodeGenerate
        implements IGenerate {
    private static final Logger log = LoggerFactory.getLogger(CodeGenerateOne.class);

    private TableVo tableVo;

    private List<ColumnVo> columns;

    private List<ColumnVo> originalColumns;

    public CodeGenerateOne(TableVo tableVo) {
        this.tableVo = tableVo;
    }

    public CodeGenerateOne(TableVo tableVo, List<ColumnVo> columns, List<ColumnVo> originalColumns) {
        this.tableVo = tableVo;
        this.columns = columns;
        this.originalColumns = originalColumns;
    }

    public static void main(String[] args) {
        System.out.println("----fleet--------- Code------------- Generation -----[单表模型]------- 生成中。。。");
        TableVo table = new TableVo();
        table.setTableName("demo");
        table.setPrimaryKeyPolicy("uuid");
        table.setEntityPackage("test");
        table.setEntityName("JeecgDemo");
        table.setFtlDescription("fleet 测试demo");
        try {
            (new CodeGenerateOne(table)).generateCodeFile((String) null);

        } catch (Exception exception) {
            exception.printStackTrace();
        }

        System.out.println("----fleet--------- Code------------- Generation -----[单表模型]------- 生成完成。。。");
    }

    public Map<String, Object> initData() {

        Map<String, Object> data = new HashMap<>();

        data.put("bussiPackage", AutoCodeConfigProperties.bussiPackage);

        data.put("entityPackage", this.tableVo.getEntityPackage());

        data.put("entityName", this.tableVo.getEntityName());

        data.put("tableName", this.tableVo.getTableName());

        data.put("primaryKeyField", AutoCodeConfigProperties.DB_TABLE_ID);

        if (tableVo.getFieldRequiredNum() == null) {
            tableVo.setFieldRequiredNum(Integer.valueOf(StringUtils.isNotEmpty(AutoCodeConfigProperties.PAGE_FIELD_REQUIRED_NUM) ? Integer.parseInt(AutoCodeConfigProperties.PAGE_FIELD_REQUIRED_NUM) : -1));
        }
        if (tableVo.getSearchFieldNum() == null) {
            this.tableVo.setSearchFieldNum(StringUtils.isNotEmpty(AutoCodeConfigProperties.PAGE_FIELD_SEARCH_NUM) ? Integer.parseInt(AutoCodeConfigProperties.PAGE_FIELD_SEARCH_NUM) : -1);
        }
        if (tableVo.getFieldRowNum() == null) {
            this.tableVo.setSearchFieldNum(Integer.parseInt(AutoCodeConfigProperties.FIELD_ROW_NUM));
        }

        data.put("tableVo", this.tableVo);

        try {
            if (columns == null || columns.size() == 0) {
                columns = DbReadTableUtil.readTableColumn(this.tableVo.getTableName());
            }
            data.put("columns", columns);

            if (originalColumns == null || originalColumns.size() == 0) {
                originalColumns = DbReadTableUtil.readOriginalTableColumn(this.tableVo.getTableName());

            }
            data.put("originalColumns", originalColumns);

            for (ColumnVo c : originalColumns) {
                if (c.getFieldName().toLowerCase().equals(AutoCodeConfigProperties.DB_TABLE_ID.toLowerCase())) {
                    data.put("primary_key_type", c.getFieldType());
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            System.exit(-1);
        }

        long serialVersionUID = NonceUtils.randomLong() + NonceUtils.currentMills();
        data.put("serialVersionUID", String.valueOf(serialVersionUID));
        log.info("code template data: " + data.toString());
        return data;
    }

    @Override
    public List<String> generateCodeFile(String stylePath) {
        log.info("----fleet---Code----Generation----[单表模型:" + this.tableVo.getTableName() + "]------- 生成中。。。");

        String projectPath = AutoCodeConfigProperties.projectPath;

        Map<String, Object> templateData = initData();

        String templatePath = AutoCodeConfigProperties.templatePath;

        if (trimSomeString(templatePath, "/").equals("fleet/code-template")) {
            templatePath = "/" + trimSomeString(templatePath, "/") + "/one";
            AutoCodeConfigProperties.setTemplatePath(templatePath);
        }

        CreateFileConfig createFileConfig = new CreateFileConfig(templatePath);
        createFileConfig.setStylePath(stylePath);

        try {
            generateFileCommon(createFileConfig, projectPath, templateData);
        } catch (Exception e) {
            e.printStackTrace();
        }
        log.info("----fleet----Code----Generation-----[单表模型：" + this.tableVo.getTableName() + "]------ 生成完成。。。");
        return results;
    }

    @Override
    public List<String> generateCodeFile(String projectPath, String templatePath, String stylePath) throws Exception {
        if (projectPath != null && !"".equals(projectPath))
            AutoCodeConfigProperties.setProjectPath(projectPath);
        if (templatePath != null && !"".equals(templatePath))
            AutoCodeConfigProperties.setTemplatePath(templatePath);

        generateCodeFile(stylePath);

        return results;
    }

}
