package org.fleet.modules.deve.autocode.generate.pojo;

import java.util.Map;


public class TableVo {
    private String tableName;
    private String ftlDescription;
    private String primaryKeyPolicy;
    private String sequenceCode;
    private String entityPackage;
    private String entityName;

    public Integer fieldRowNum;

    public Integer searchFieldNum;

    public Integer fieldRequiredNum;

    private Map<?, ?> extendParams;

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

    public Integer getFieldRowNum() {
        return this.fieldRowNum;
    }

    public void setFieldRowNum(int fieldRowNum) {
        this.fieldRowNum = fieldRowNum;
    }

    public Integer getSearchFieldNum() {
        return this.searchFieldNum;
    }

    public void setSearchFieldNum(int searchFieldNum) {
        this.searchFieldNum = searchFieldNum;
    }

    public Integer getFieldRequiredNum() {
        return this.fieldRequiredNum;
    }

    public void setFieldRequiredNum(int fieldRequiredNum) {
        this.fieldRequiredNum = fieldRequiredNum;
    }

    public Map<?, ?> getExtendParams() {
        return this.extendParams;
    }

    public void setExtendParams(Map<?, ?> extendParams) {
        this.extendParams = extendParams;
    }

    public String toString() {
        return "TableVo [tableName=" + this.tableName + ", ftlDescription=" + this.ftlDescription + ", primaryKeyPolicy=" + this.primaryKeyPolicy + ", sequenceCode=" + this.sequenceCode + ", entityPackage=" + this.entityPackage + ", entityName=" + this.entityName + ", foreignKeys=" + ", fieldRowNum=" + this.fieldRowNum + ", searchFieldNum=" + this.searchFieldNum + ", fieldRequiredNum=" + this.fieldRequiredNum + "]";
    }
}


