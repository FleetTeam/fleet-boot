package org.fleet.modules.deve.autocode.database;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.fleet.modules.deve.autocode.config.AutoCodeConfigProperties;
import org.fleet.modules.deve.autocode.database.util.AutoCodeStringUtils;
import org.fleet.modules.deve.autocode.generate.pojo.ColumnVo;
import org.fleet.modules.deve.autocode.generate.util.TableConvert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

public class DbReadTableUtil {
    private static final Logger log = LoggerFactory.getLogger(DbReadTableUtil.class);

    private static Connection conn;

    private static Statement stmt;

    public static void main(String[] args) throws SQLException {
        try {
            List<ColumnVo> cls = readTableColumn("demo");
            for (ColumnVo c : cls) {
                System.out.println(c.getFieldName());
            }
        } catch (Exception e) {

            e.printStackTrace();
        }
        new DbReadTableUtil();
        System.out.println(ArrayUtils.toString(readAllTableNames()));
    }

    public static List<String> readAllTableNames() throws SQLException {
        String sql = null;
        List<String> tableNames = new ArrayList<>(0);
        try {
            Class.forName(AutoCodeConfigProperties.diverName);
            conn = DriverManager.getConnection(AutoCodeConfigProperties.url, AutoCodeConfigProperties.username,
                    AutoCodeConfigProperties.password);
            stmt = conn.createStatement(1005, 1007);

            String databaseName = conn.getCatalog();
            log.info("connect databaseName :" + databaseName);
            if (AutoCodeConfigProperties.databaseType.equals("mysql")) {
                sql = MessageFormat.format(
                        "select distinct table_name from information_schema.columns where table_schema = {0}",
                        new Object[]{TableConvert.getV(AutoCodeConfigProperties.databaseName)});
            }

            if (AutoCodeConfigProperties.databaseType.equals("oracle")) {
                sql = " select distinct colstable.table_name as  table_name from user_tab_cols colstable order by colstable.table_name";
            }

            if (AutoCodeConfigProperties.databaseType.equals("postgresql")) {
                sql = "SELECT distinct c.relname AS  table_name FROM pg_class c";
            }

            if (AutoCodeConfigProperties.databaseType.equals("sqlserver")) {
                sql = "select distinct c.name as  table_name from sys.objects c where c.type = 'U' ";
            }
            log.debug("--------------sql-------------" + sql);
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                String tableName = rs.getString(1);
                tableNames.add(tableName);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (stmt != null) {
                    stmt.close();
                    stmt = null;
                    System.gc();
                }
                if (conn != null) {
                    conn.close();
                    conn = null;
                    System.gc();
                }
            } catch (SQLException e) {
                throw e;
            }
        }
        return tableNames;
    }

    public static List<ColumnVo> readTableColumn(String tableName) throws Exception {
        String sql = null;
        List<ColumnVo> columntList = new ArrayList<>();
        try {
            Class.forName(AutoCodeConfigProperties.diverName);
            conn = DriverManager.getConnection(AutoCodeConfigProperties.url, AutoCodeConfigProperties.username,
                    AutoCodeConfigProperties.password);
            stmt = conn.createStatement(1005, 1007);
            String databaseName = conn.getCatalog();

            log.info("connect databaseName :" + databaseName);

            if (AutoCodeConfigProperties.databaseType.equals("mysql")) {
                sql = MessageFormat.format(
                        "select column_name,data_type,column_comment,numeric_precision,numeric_scale,character_maximum_length,is_nullable nullable from information_schema.columns where table_name = {0} and table_schema = {1} order by ORDINAL_POSITION",
                        new Object[]{TableConvert.getV(tableName.toUpperCase()),
                                TableConvert.getV(AutoCodeConfigProperties.databaseName)});
            }

            if (AutoCodeConfigProperties.databaseType.equals("oracle")) {
                sql = MessageFormat.format(
                        " select colstable.column_name column_name, colstable.data_type data_type, commentstable.comments column_comment, colstable.Data_Precision column_precision, colstable.Data_Scale column_scale,colstable.Char_Length,colstable.nullable from user_tab_cols colstable  inner join user_col_comments commentstable  on colstable.column_name = commentstable.column_name  where colstable.table_name = commentstable.table_name  and colstable.table_name = {0}",
                        new Object[]{TableConvert.getV(tableName.toUpperCase())});
            }

            if (AutoCodeConfigProperties.databaseType.equals("postgresql")) {
                sql = MessageFormat.format(
                        "SELECT a.attname AS  field,t.typname AS type,col_description(a.attrelid,a.attnum) as comment,null as column_precision,null as column_scale,null as Char_Length,a.attnotnull  FROM pg_class c,pg_attribute  a,pg_type t  WHERE c.relname = {0} and a.attnum > 0  and a.attrelid = c.oid and a.atttypid = t.oid  ORDER BY a.attnum ",
                        new Object[]{TableConvert.getV(tableName.toLowerCase())});
            }

            if (AutoCodeConfigProperties.databaseType.equals("sqlserver")) {
                sql = MessageFormat.format(
                        "select distinct cast(a.name as varchar(50)) column_name,  cast(b.name as varchar(50)) data_type,  cast(e.value as varchar(200)) comment,  cast(ColumnProperty(a.object_id,a.Name,'''Precision''') as int) num_precision,  cast(ColumnProperty(a.object_id,a.Name,'''Scale''') as int) num_scale,  a.max_length,  (case when a.is_nullable=1 then '''y''' else '''n''' end) nullable,column_id   from sys.columns a left join sys.types b on a.user_type_id=b.user_type_id left join (select top 1 * from sys.objects where type = '''U''' and name ={0}  order by name) c on a.object_id=c.object_id left join sys.extended_properties e on e.major_id=c.object_id and e.minor_id=a.column_id and e.class=1 where c.name={0} order by a.column_id",
                        new Object[]{TableConvert.getV(tableName.toLowerCase())});
            }
            log.debug("--------------sql-------------" + sql);

            ResultSet rs = stmt.executeQuery(sql);
            rs.last();
            int fieldNum = rs.getRow();
            int n = fieldNum;

            if (n > 0) {
                ColumnVo columnt = new ColumnVo();

                if (AutoCodeConfigProperties.DB_FILED_CONVERT) {
                    columnt.setFieldName(formatField(rs.getString(1).toLowerCase()));
                } else {
                    columnt.setFieldName(rs.getString(1).toLowerCase());
                }

                columnt.setFieldDbName(rs.getString(1).toUpperCase());
                columnt.setFieldType(formatField(rs.getString(2).toLowerCase()));
                columnt.setFieldDbType(formatField(rs.getString(2).toLowerCase()));

                columnt.setPrecision(rs.getString(4));
                columnt.setScale(rs.getString(5));
                columnt.setCharmaxLength(rs.getString(6));
                columnt.setNullable(TableConvert.getNullAble(rs.getString(7)));

                formatFieldClassType(columnt);
                columnt.setFiledComment(
                        StringUtils.isBlank(rs.getString(3)) ? columnt.getFieldName() : rs.getString(3));

                log.debug("columnt.getFieldName() -------------" + columnt.getFieldName());

                String[] ui_filter_fields = new String[0];
                if (AutoCodeConfigProperties.PAGE_FILTER_FIELDS != null) {
                    ui_filter_fields = AutoCodeConfigProperties.PAGE_FILTER_FIELDS.toLowerCase().split(",");
                }

                if (!AutoCodeConfigProperties.DB_TABLE_ID.equals(columnt.getFieldName())
                        && !AutoCodeStringUtils.isIn(columnt.getFieldDbName().toLowerCase(), ui_filter_fields)) {

                    columntList.add(columnt);
                }
                while (rs.previous()) {
                    ColumnVo po = new ColumnVo();

                    if (AutoCodeConfigProperties.DB_FILED_CONVERT) {
                        po.setFieldName(formatField(rs.getString(1).toLowerCase()));
                    } else {
                        po.setFieldName(rs.getString(1).toLowerCase());
                    }

                    po.setFieldDbName(rs.getString(1).toUpperCase());
                    log.debug("columnt.getFieldName() -------------" + po.getFieldName());
                    if (AutoCodeConfigProperties.DB_TABLE_ID.equals(po.getFieldName())
                            || AutoCodeStringUtils.isIn(po.getFieldDbName().toLowerCase(), ui_filter_fields)) {
                        continue;
                    }
                    po.setFieldType(formatField(rs.getString(2).toLowerCase()));

                    po.setFieldDbType(formatField(rs.getString(2).toLowerCase()));
                    log.debug("-----po.setFieldType------------" + po.getFieldType());

                    po.setPrecision(rs.getString(4));
                    po.setScale(rs.getString(5));
                    po.setCharmaxLength(rs.getString(6));
                    po.setNullable(TableConvert.getNullAble(rs.getString(7)));

                    formatFieldClassType(po);
                    po.setFiledComment(StringUtils.isBlank(rs.getString(3)) ? po.getFieldName() : rs.getString(3));
                    columntList.add(po);
                }
            } else {
                throw new Exception("该表不存在或者表中没有字段");
            }

            log.debug("读取表成功");
        } catch (ClassNotFoundException e) {
            throw e;
        } catch (SQLException e) {
            throw e;
        } finally {
            try {
                if (stmt != null) {
                    stmt.close();
                    stmt = null;
                    System.gc();
                }
                if (conn != null) {
                    conn.close();
                    conn = null;
                    System.gc();
                }
            } catch (SQLException e) {
                throw e;
            }
        }

        List<ColumnVo> rsList = new ArrayList<>();
        for (int i = columntList.size() - 1; i >= 0; i--) {
            ColumnVo ch = columntList.get(i);
            rsList.add(ch);
        }
        return rsList;
    }

    public static List<ColumnVo> readOriginalTableColumn(String tableName) throws Exception {
        ResultSet rs = null;
        String sql = null;
        List<ColumnVo> columntList = new ArrayList<>();
        try {
            Class.forName(AutoCodeConfigProperties.diverName);
            conn = DriverManager.getConnection(AutoCodeConfigProperties.url, AutoCodeConfigProperties.username,
                    AutoCodeConfigProperties.password);
            stmt = conn.createStatement(1005, 1007);
            String databaseName = conn.getCatalog();

            log.info("connect databaseName :" + databaseName);
            if (AutoCodeConfigProperties.databaseType.equals("mysql")) {
                sql = MessageFormat.format(
                        "select column_name,data_type,column_comment,numeric_precision,numeric_scale,character_maximum_length,is_nullable nullable from information_schema.columns where table_name = {0} and table_schema = {1} order by ORDINAL_POSITION",
                        new Object[]{TableConvert.getV(tableName.toUpperCase()),
                                TableConvert.getV(AutoCodeConfigProperties.databaseName)});
            }

            if (AutoCodeConfigProperties.databaseType.equals("oracle")) {
                sql = MessageFormat.format(
                        " select colstable.column_name column_name, colstable.data_type data_type, commentstable.comments column_comment, colstable.Data_Precision column_precision, colstable.Data_Scale column_scale,colstable.Char_Length,colstable.nullable from user_tab_cols colstable  inner join user_col_comments commentstable  on colstable.column_name = commentstable.column_name  where colstable.table_name = commentstable.table_name  and colstable.table_name = {0}",
                        new Object[]{TableConvert.getV(tableName.toUpperCase())});
            }

            if (AutoCodeConfigProperties.databaseType.equals("postgresql")) {
                sql = MessageFormat.format(
                        "SELECT a.attname AS  field,t.typname AS type,col_description(a.attrelid,a.attnum) as comment,null as column_precision,null as column_scale,null as Char_Length,a.attnotnull  FROM pg_class c,pg_attribute  a,pg_type t  WHERE c.relname = {0} and a.attnum > 0  and a.attrelid = c.oid and a.atttypid = t.oid  ORDER BY a.attnum ",
                        new Object[]{TableConvert.getV(tableName.toLowerCase())});
            }
            if (AutoCodeConfigProperties.databaseType.equals("sqlserver")) {
                sql = MessageFormat.format(
                        "select distinct cast(a.name as varchar(50)) column_name,  cast(b.name as varchar(50)) data_type,  cast(e.value as varchar(200)) comment,  cast(ColumnProperty(a.object_id,a.Name,'''Precision''') as int) num_precision,  cast(ColumnProperty(a.object_id,a.Name,'''Scale''') as int) num_scale,  a.max_length,  (case when a.is_nullable=1 then '''y''' else '''n''' end) nullable,column_id   from sys.columns a left join sys.types b on a.user_type_id=b.user_type_id left join (select top 1 * from sys.objects where type = '''U''' and name ={0}  order by name) c on a.object_id=c.object_id left join sys.extended_properties e on e.major_id=c.object_id and e.minor_id=a.column_id and e.class=1 where c.name={0} order by a.column_id",
                        new Object[]{TableConvert.getV(tableName.toLowerCase())});
            }
            log.debug("--------------sql-------------" + sql);

            rs = stmt.executeQuery(sql);
            rs.last();
            int fieldNum = rs.getRow();
            int n = fieldNum;

            if (n > 0) {
                ColumnVo columnt = new ColumnVo();

                if (AutoCodeConfigProperties.DB_FILED_CONVERT) {
                    columnt.setFieldName(formatField(rs.getString(1).toLowerCase()));
                } else {
                    columnt.setFieldName(rs.getString(1).toLowerCase());
                }

                columnt.setFieldDbName(rs.getString(1).toUpperCase());

                columnt.setPrecision(TableConvert.getNullString(rs.getString(4)));
                columnt.setScale(TableConvert.getNullString(rs.getString(5)));
                columnt.setCharmaxLength(TableConvert.getNullString(rs.getString(6)));
                columnt.setNullable(TableConvert.getNullAble(rs.getString(7)));

                columnt.setFieldType(
                        formatDataType(rs.getString(2).toLowerCase(), columnt.getPrecision(), columnt.getScale()));

                columnt.setFieldDbType(formatField(rs.getString(2).toLowerCase()));

                formatFieldClassType(columnt);
                columnt.setFiledComment(
                        StringUtils.isBlank(rs.getString(3)) ? columnt.getFieldName() : rs.getString(3));

                log.debug("columnt.getFieldName() -------------" + columnt.getFieldName());
                columntList.add(columnt);
                while (rs.previous()) {
                    ColumnVo po = new ColumnVo();

                    if (AutoCodeConfigProperties.DB_FILED_CONVERT) {
                        po.setFieldName(formatField(rs.getString(1).toLowerCase()));
                    } else {
                        po.setFieldName(rs.getString(1).toLowerCase());
                    }

                    po.setFieldDbName(rs.getString(1).toUpperCase());

                    po.setPrecision(TableConvert.getNullString(rs.getString(4)));
                    po.setScale(TableConvert.getNullString(rs.getString(5)));
                    po.setCharmaxLength(TableConvert.getNullString(rs.getString(6)));
                    po.setNullable(TableConvert.getNullAble(rs.getString(7)));

                    po.setFieldType(formatDataType(rs.getString(2).toLowerCase(), po.getPrecision(), po.getScale()));

                    po.setFieldDbType(formatField(rs.getString(2).toLowerCase()));

                    formatFieldClassType(po);
                    po.setFiledComment(StringUtils.isBlank(rs.getString(3)) ? po.getFieldName() : rs.getString(3));
                    columntList.add(po);
                }
            } else {
                throw new Exception("该表不存在或者表中没有字段");
            }

            log.debug("读取表成功！");
        } catch (ClassNotFoundException e) {
            throw e;
        } catch (SQLException e) {
            throw e;
        } finally {
            try {
                if (stmt != null) {
                    stmt.close();
                    stmt = null;
                    System.gc();
                }
                if (conn != null) {
                    conn.close();
                    conn = null;
                    System.gc();
                }
            } catch (SQLException e) {
                throw e;
            }
        }

        List<ColumnVo> rsList = new ArrayList<>();
        for (int i = columntList.size() - 1; i >= 0; i--) {
            ColumnVo ch = columntList.get(i);
            rsList.add(ch);
        }
        return rsList;
    }

    public static boolean checkTableExist(String tableName) {
        String sql = null;
        try {
            log.info("数据库驱动：" + AutoCodeConfigProperties.diverName);
            Class.forName(AutoCodeConfigProperties.diverName);
            conn = DriverManager.getConnection(AutoCodeConfigProperties.url, AutoCodeConfigProperties.username,
                    AutoCodeConfigProperties.password);
            stmt = conn.createStatement(1005, 1007);
            String databaseName = conn.getCatalog();

            log.info("connect databaseName :" + databaseName);
            if (AutoCodeConfigProperties.databaseType.equals("mysql")) {
                sql = "select column_name,data_type,column_comment,0,0 from information_schema.columns where table_name = '"
                        + tableName.toUpperCase() + "' and table_schema = '" + AutoCodeConfigProperties.databaseName + "'";
            }

            if (AutoCodeConfigProperties.databaseType.equals("oracle")) {

                sql = "select colstable.column_name column_name, colstable.data_type data_type, commentstable.comments column_comment from user_tab_cols colstable  inner join user_col_comments commentstable  on colstable.column_name = commentstable.column_name  where colstable.table_name = commentstable.table_name  and colstable.table_name = '"
                        + tableName.toUpperCase() + "'";
            }

            if (AutoCodeConfigProperties.databaseType.equals("postgresql")) {
                sql = MessageFormat.format(
                        "SELECT a.attname AS  field,t.typname AS type,col_description(a.attrelid,a.attnum) as comment,null as column_precision,null as column_scale,null as Char_Length,a.attnotnull  FROM pg_class c,pg_attribute  a,pg_type t  WHERE c.relname = {0} and a.attnum > 0  and a.attrelid = c.oid and a.atttypid = t.oid  ORDER BY a.attnum ",
                        new Object[]{TableConvert.getV(tableName.toLowerCase())});
            }
            if (AutoCodeConfigProperties.databaseType.equals("sqlserver")) {
                sql = MessageFormat.format(
                        "select distinct cast(a.name as varchar(50)) column_name,  cast(b.name as varchar(50)) data_type,  cast(e.value as varchar(200)) comment,  cast(ColumnProperty(a.object_id,a.Name,'''Precision''') as int) num_precision,  cast(ColumnProperty(a.object_id,a.Name,'''Scale''') as int) num_scale,  a.max_length,  (case when a.is_nullable=1 then '''y''' else '''n''' end) nullable,column_id   from sys.columns a left join sys.types b on a.user_type_id=b.user_type_id left join (select top 1 * from sys.objects where type = '''U''' and name ={0}  order by name) c on a.object_id=c.object_id left join sys.extended_properties e on e.major_id=c.object_id and e.minor_id=a.column_id and e.class=1 where c.name={0} order by a.column_id",
                        new Object[]{TableConvert.getV(tableName.toLowerCase())});
            }
            log.debug("--------------sql-------------" + sql);

            ResultSet rs = stmt.executeQuery(sql);
            rs.last();
            int fieldNum = rs.getRow();
            if (fieldNum > 0) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return false;
    }

    private static String formatField(String field) {
        String[] strs = field.split("_");
        field = "";
        for (int m = 0, length = strs.length; m < length; m++) {
            if (m > 0) {
                String tempStr = strs[m].toLowerCase();

                tempStr = tempStr.substring(0, 1).toUpperCase() + tempStr.substring(1, tempStr.length());
                field = field + tempStr;
            } else {
                field = field + strs[m].toLowerCase();
            }
        }
        return field;
    }


    public static String formatFieldCapital(String field) {
        String[] strs = field.split("_");
        field = "";
        for (int m = 0, length = strs.length; m < length; m++) {
            if (m > 0) {
                String tempStr = strs[m].toLowerCase();

                tempStr = tempStr.substring(0, 1).toUpperCase() + tempStr.substring(1, tempStr.length());
                field = field + tempStr;
            } else {
                field = field + strs[m].toLowerCase();
            }
        }
        field = field.substring(0, 1).toUpperCase() + field.substring(1);
        return field;
    }

    private static void formatFieldClassType(ColumnVo columnt) {
        String fieldType = columnt.getFieldType();
        String scale = columnt.getScale();

        columnt.setClassType("inputxt");

        if ("N".equals(columnt.getNullable())) {
            columnt.setOptionType("*");
        }
        if ("datetime".equals(fieldType) || fieldType.contains("time")) {
            columnt.setClassType("easyui-datetimebox");
        } else if ("date".equals(fieldType)) {
            columnt.setClassType("easyui-datebox");
        } else if (fieldType.contains("int")) {
            columnt.setOptionType("n");
        } else if ("number".equals(fieldType)) {
            if (StringUtils.isNotBlank(scale) && Integer.parseInt(scale) > 0) {
                columnt.setOptionType("d");
            }
        } else if ("float".equals(fieldType) || "double".equals(fieldType) || "decimal".equals(fieldType)) {
            columnt.setOptionType("d");
        } else if ("numeric".equals(fieldType)) {
            columnt.setOptionType("d");
        }
    }

    private static String formatDataType(String dataType, String precision, String scale) {
        if (dataType.contains("char")) {
            dataType = "java.lang.String";
        } else if (dataType.contains("int")) {
            dataType = "java.lang.Integer";
        } else if (dataType.contains("float")) {
            dataType = "java.lang.Float";
        } else if (dataType.contains("double")) {
            dataType = "java.lang.Double";
        } else if (dataType.contains("number")) {
            if (StringUtils.isNotBlank(scale) && Integer.parseInt(scale) > 0) {
                dataType = "java.math.BigDecimal";
            } else if (StringUtils.isNotBlank(precision) && Integer.parseInt(precision) > 10) {
                dataType = "java.lang.Long";
            } else {
                dataType = "java.lang.Integer";
            }
        } else if (dataType.contains("decimal")) {
            dataType = "java.math.BigDecimal";
        } else if (dataType.contains("date")) {
            dataType = "java.util.Date";
        } else if (dataType.contains("time")) {

            dataType = "java.util.Date";
        } else if (dataType.contains("blob")) {
            dataType = "byte[]";
        } else if (dataType.contains("clob")) {
            dataType = "java.sql.Clob";
        } else if (dataType.contains("numeric")) {
            dataType = "java.math.BigDecimal";
        } else {
            dataType = "java.lang.Object";
        }
        return dataType;
    }
}
