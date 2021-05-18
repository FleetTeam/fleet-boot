package org.fleet.modules.deve.autocode.generate.pojo.onetomany;

import java.util.List;
import java.util.Map;


public class MainTableVo {
    private String entityPackage;
    private String tableName;
    private String entityName;
    private String ftlDescription;
    private String primaryKeyPolicy;
    private String sequenceCode;
    private String ftl_mode = "A";

    List<SubTableVo> subTables;

    public Integer fieldRowNum;

    public Integer searchFieldNum;

    public Integer fieldRequiredNum;

    private Map<?, ?> extendParams;

    public List<SubTableVo> getSubTables() {
        return this.subTables;
    }

    public Integer getFieldRowNum() {
        return fieldRowNum;
    }

    public void setFieldRowNum(Integer fieldRowNum) {
        this.fieldRowNum = fieldRowNum;
    }

    public void setSubTables(List<SubTableVo> subTables) {
        this.subTables = subTables;
    }

    public String getEntityPackage() {
        return this.entityPackage;
    }

    public String getTableName() {
        return this.tableName;
    }

    public String getEntityName() {
        return this.entityName;
    }

    public String getFtlDescription() {
        return this.ftlDescription;
    }

    public void setEntityPackage(String entityPackage) {
        this.entityPackage = entityPackage;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public void setEntityName(String entityName) {
        this.entityName = entityName;
    }

    public void setFtlDescription(String ftlDescription) {
        this.ftlDescription = ftlDescription;
    }

    public String getFtl_mode() {
        return this.ftl_mode;
    }

    public void setFtl_mode(String ftl_mode) {
        this.ftl_mode = ftl_mode;
    }

    public String getPrimaryKeyPolicy() {
        return this.primaryKeyPolicy;
    }

    public String getSequenceCode() {
        return this.sequenceCode;
    }

    public void setPrimaryKeyPolicy(String primaryKeyPolicy) {
        this.primaryKeyPolicy = primaryKeyPolicy;
    }

    public void setSequenceCode(String sequenceCode) {
        this.sequenceCode = sequenceCode;
    }

    public Integer getSearchFieldNum() {
        return searchFieldNum;
    }

    public void setSearchFieldNum(Integer searchFieldNum) {
        this.searchFieldNum = searchFieldNum;
    }

    public Integer getFieldRequiredNum() {
        return fieldRequiredNum;
    }

    public void setFieldRequiredNum(Integer fieldRequiredNum) {
        this.fieldRequiredNum = fieldRequiredNum;
    }

    public Map<?, ?> getExtendParams() {
        return extendParams;
    }

    public void setExtendParams(Map<?, ?> extendParams) {
        this.extendParams = extendParams;
    }
}


