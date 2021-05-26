package org.fleet.modules.deve.autocode.generate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface IGenerate {
    Map<String, Object> initData() throws Exception;

    List<String> generateCodeFile(String stylePath) throws Exception;

    List<String> generateCodeFile(String projectPath, String templatePath, String stylePath) throws Exception;
}


