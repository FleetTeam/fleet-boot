package org.fleet.modules.deve.autocode.generate.impl;

import org.apache.commons.lang.StringUtils;
import org.fleet.modules.deve.autocode.config.AutoCodeConfigProperties;
import org.fleet.modules.deve.autocode.database.DbReadTableUtil;
import org.fleet.modules.deve.autocode.generate.IGenerate;
import org.fleet.modules.deve.autocode.generate.config.CreateFileConfig;
import org.fleet.modules.deve.autocode.generate.impl.base.BaseCodeGenerate;
import org.fleet.modules.deve.autocode.generate.pojo.ColumnVo;
import org.fleet.modules.deve.autocode.generate.pojo.onetomany.MainTableVo;
import org.fleet.modules.deve.autocode.generate.pojo.onetomany.SubTableVo;
import org.fleet.modules.deve.autocode.generate.util.NonceUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CodeGenerateOneToMany extends BaseCodeGenerate
        implements IGenerate {
    private static final Logger log = LoggerFactory.getLogger(CodeGenerateOneToMany.class);


    private static String f;

    public static String a = "A";

    public static String b = "B";

    private MainTableVo mainTableVo;

    private List<ColumnVo> mainColumns;

    private List<ColumnVo> originaMainColumns;

    private List<SubTableVo> subTables;

    public CodeGenerateOneToMany(MainTableVo mainTableVo, List<SubTableVo> subTables) {
        this.mainTableVo = mainTableVo;
        this.subTables = subTables;
    }

    public CodeGenerateOneToMany(MainTableVo mainTableVo, List<ColumnVo> mainColums, List<ColumnVo> originalMainColumns, List<SubTableVo> subTables) {
        this.mainTableVo = mainTableVo;
        this.subTables = subTables;
        this.mainColumns = mainColums;
        this.originaMainColumns = originalMainColumns;
    }


    @Override
    public HashMap<String, Object> initData() throws Exception {
        HashMap<String, Object> data = new HashMap<>();

        data.put("bussiPackage", AutoCodeConfigProperties.bussiPackage);

        data.put("entityPackage", this.mainTableVo.getEntityPackage());

        data.put("entityName", this.mainTableVo.getEntityName());

        data.put("tableName", this.mainTableVo.getTableName());

        data.put("primaryKeyField", AutoCodeConfigProperties.DB_TABLE_ID);

        if (mainTableVo.getFieldRequiredNum() == null) {
            mainTableVo.setFieldRequiredNum(Integer.valueOf(StringUtils.isNotEmpty(AutoCodeConfigProperties.PAGE_FIELD_REQUIRED_NUM) ? Integer.parseInt(AutoCodeConfigProperties.PAGE_FIELD_REQUIRED_NUM) : -1));
        }
        if (mainTableVo.getSearchFieldNum() == null) {
            this.mainTableVo.setSearchFieldNum(StringUtils.isNotEmpty(AutoCodeConfigProperties.PAGE_FIELD_SEARCH_NUM) ? Integer.parseInt(AutoCodeConfigProperties.PAGE_FIELD_SEARCH_NUM) : -1);
        }
        if (mainTableVo.getFieldRowNum() == null) {
            this.mainTableVo.setSearchFieldNum(Integer.parseInt(AutoCodeConfigProperties.FIELD_ROW_NUM));
        }

        data.put("tableVo", this.mainTableVo);

        try {
            if (this.mainColumns == null || mainColumns.size() == 0) {
                mainColumns = DbReadTableUtil.readTableColumn(this.mainTableVo.getTableName());
            }
            data.put("columns", mainColumns);

            if (originaMainColumns == null || originaMainColumns.size() == 0) {
                originaMainColumns = DbReadTableUtil.readOriginalTableColumn(this.mainTableVo.getTableName());

            }

            data.put("originalColumns", originaMainColumns);


            for (ColumnVo c : originaMainColumns) {
                if (c.getFieldName().toLowerCase().equals(AutoCodeConfigProperties.DB_TABLE_ID.toLowerCase())) {
                    data.put("primary_key_type", c.getFieldType());
                }
            }

            for (SubTableVo subTableVo : this.subTables) {
                if (subTableVo.getColums() == null || subTableVo.getColums().size() == 0) {
                    List list = DbReadTableUtil.readTableColumn(subTableVo.getTableName());
                    subTableVo.setColums(list);
                }
                if (subTableVo.getOriginalColumns() == null || subTableVo.getOriginalColumns().size() == 0) {
                    List list = DbReadTableUtil.readOriginalTableColumn(subTableVo.getTableName());
                    subTableVo.setOriginalColumns(list);
                }

                String[] arrayOfString = subTableVo.getForeignKeys();
                ArrayList<String> arrayList = new ArrayList();
                for (String str : arrayOfString)
                    arrayList.add(DbReadTableUtil.formatFieldCapital(str));
                subTableVo.setForeignKeys(arrayList.<String>toArray(new String[0]));
                subTableVo.setOriginalForeignKeys(arrayOfString);
            }
            data.put("subTables", this.subTables);

        } catch (Exception e) {
            e.printStackTrace();
            System.exit(-1);
        }

        long serialVersionUID = NonceUtils.randomLong() + NonceUtils.currentMills();
        data.put("serialVersionUID", String.valueOf(serialVersionUID));
        return data;

    }

    @Override
    public List<String> generateCodeFile(String stylePath) throws Exception {

        log.info("----fleet---Code----Generation----[单表模型:" + this.mainTableVo.getTableName() + "]------- 生成中。。。");

        String projectPath = AutoCodeConfigProperties.projectPath;

        Map<String, Object> templateData = initData();

        String templatePath = AutoCodeConfigProperties.templatePath;

        if (trimSomeString(templatePath, "/").equals("fleet/code-template")) {
            templatePath = "/" + trimSomeString(templatePath, "/") + "/onetomany";
            AutoCodeConfigProperties.setTemplatePath(templatePath);
        }

        CreateFileConfig createFileConfig = new CreateFileConfig(templatePath);
        createFileConfig.setStylePath(stylePath);

        try {
            generateFileCommon(createFileConfig, projectPath, templateData);
        } catch (Exception e) {
            e.printStackTrace();
        }
        log.info("----fleet----Code----Generation-----[单表模型：" + this.mainTableVo.getTableName() + "]------ 生成完成。。。");
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
