package org.fleet.modules.deve.autocode.window;

import org.fleet.modules.deve.autocode.generate.impl.CodeGenerateOneToMany;
import org.fleet.modules.deve.autocode.generate.pojo.onetomany.MainTableVo;
import org.fleet.modules.deve.autocode.generate.pojo.onetomany.SubTableVo;

import java.util.ArrayList;

public class FleetOneToMainUtil {
    public static void main(String[] args) {
        MainTableVo mainTableVo = new MainTableVo();
        mainTableVo.setTableName("jeecg_order_main");
        mainTableVo.setEntityName("TestOrderMain");
        mainTableVo.setEntityPackage("test2");
        mainTableVo.setFtlDescription("订单");

        ArrayList<SubTableVo> arrayList = new ArrayList();

        SubTableVo subTableVo1 = new SubTableVo();
        subTableVo1.setTableName("jeecg_order_customer");
        subTableVo1.setEntityName("TestOrderCustom");
        subTableVo1.setEntityPackage("test2");
        subTableVo1.setFtlDescription("客户明细");

        subTableVo1.setForeignKeys(new String[]{"order_id"});
        arrayList.add(subTableVo1);

        SubTableVo subTableVo2 = new SubTableVo();
        subTableVo2.setTableName("jeecg_order_ticket");
        subTableVo2.setEntityName("TestOrderTicket");
        subTableVo2.setEntityPackage("test2");
        subTableVo2.setFtlDescription("产品明细");

        subTableVo2.setForeignKeys(new String[]{"order_id"});
        arrayList.add(subTableVo2);
        mainTableVo.setSubTables(arrayList);

        try {
            String projectPath = "/Users/wangrongpu/src";
            String templatePath = "/fleet/code-template";
            (new CodeGenerateOneToMany(mainTableVo, arrayList)).generateCodeFile(projectPath, templatePath, null);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }
}

