package org.fleet.modules.deve.autocode.window;

import org.fleet.modules.deve.autocode.generate.impl.CodeGenerateOne;
import org.fleet.modules.deve.autocode.generate.pojo.TableVo;

public class FleetOneUtil {
    public static void main(String[] args) {
        TableVo tableVo = new TableVo();
        tableVo.setEntityName("demo");
        tableVo.setEntityPackage("test");
        tableVo.setFtlDescription("测试表");
        tableVo.setTableName("demo");

        try {
            String projectPath = "/Users/wangrongpu/src";
            String templatePath = "/fleet/code-template";
            (new CodeGenerateOne(tableVo)).generateCodeFile(projectPath, templatePath, null);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }
}