package org.fleet.modules.deve.autocode.generate.impl.base;

import freemarker.template.Configuration;
import freemarker.template.Template;
import org.fleet.modules.deve.autocode.config.AutoCodeConfigProperties;
import org.fleet.modules.deve.autocode.generate.config.CreateFileConfig;
import org.fleet.modules.deve.autocode.generate.util.FileHelper;
import org.fleet.modules.deve.autocode.generate.util.FreemarkerHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class BaseCodeGenerate {
    private static final Logger log = LoggerFactory.getLogger(BaseCodeGenerate.class);

    protected static String sourceEncoding = "UTF-8";

    protected List<String> results = new ArrayList<>();

    protected static String proceeForOutputFilepath(Map<String, Object> filePathModel, String templateFile,
                                                    CreateFileConfig createFileConfig) throws Exception {
        String outputFilePath = templateFile;

        int testExpressionIndex = -1;
        if ((testExpressionIndex = templateFile.indexOf('@')) != -1) {
            outputFilePath = templateFile.substring(0, testExpressionIndex);
            String testExpressionKey = templateFile.substring(testExpressionIndex + 1);
            Object expressionValue = filePathModel.get(testExpressionKey);
            if (expressionValue == null) {
                System.err.println("[not-generate] WARN: test expression is null by key:[" + testExpressionKey
                        + "] on template:[" + templateFile + "]");
                return null;
            }
            if (!"true".equals(String.valueOf(expressionValue))) {
                log.error("[not-generate]\t test expression '@" + testExpressionKey + "' is false,template:"
                        + templateFile);
                return null;
            }
        }
        Configuration conf = FreemarkerHelper.newFreeMarkerConfiguration(createFileConfig.getTemplateRootDirs(),
                sourceEncoding, "/");
        outputFilePath = FreemarkerHelper.processTemplateString(outputFilePath, filePathModel, conf);
        String stylePath = createFileConfig.getStylePath();
        if (stylePath != null && "".equals(stylePath)) {
            outputFilePath = outputFilePath.substring(stylePath.length() + 1);
        }
        String extName = outputFilePath.substring(outputFilePath.lastIndexOf("."));
        String fileName = outputFilePath.substring(0, outputFilePath.lastIndexOf(".")).replace(".", File.separator);
        outputFilePath = fileName + extName;
        return outputFilePath;
    }

    protected static boolean forceDelete(File f) {
        boolean result = false;
        int tryCount = 0;
        while (!result && tryCount++ < 10) {

            System.gc();
            result = f.delete();
        }
        return result;
    }

    protected static String trimSomeString(String sourceString, String someString) {
        boolean bool1 = true;
        boolean bool2 = true;
        int from;
        while (true) {
            boolean bool = (sourceString.indexOf(someString) == 0) ? true : false;
            if (bool == true) {
                from = 1;
            } else {
                from = 0;
            }
            int i = (sourceString.lastIndexOf(someString) + 1 == sourceString.length())
                    ? sourceString.lastIndexOf(someString)
                    : sourceString.length();
            sourceString = sourceString.substring(from, i);
            bool1 = (sourceString.indexOf(someString) == 0) ? true : false;
            bool2 = (sourceString.lastIndexOf(someString) + 1 == sourceString.length()) ? true : false;
            if (!bool1 && !bool2)
                return sourceString;
        }
    }

    protected void generateFileCommon(CreateFileConfig createFileConfig, String projectPath,
                                      Map<String, Object> templateData) throws Exception {
        log.debug("--------generate----projectPath--------" + projectPath);
        for (int i = 0; i < createFileConfig.getTemplateRootDirs().size(); i++) {
            File templateRootDir = createFileConfig.getTemplateRootDirs().get(i);
            scanTemplatesAndProcess(projectPath, templateRootDir, templateData, createFileConfig);
        }
    }

    protected void scanTemplatesAndProcess(String projectPath, File templateRootDir, Map<String, Object> templateData,
                                           CreateFileConfig createFileConfig) throws Exception {
        if (templateRootDir == null)
            throw new IllegalStateException("'templateRootDir' must be not null");
        log.info("-------------------load template from templateRootDir = '" + templateRootDir.getAbsolutePath()
                + "' outJavaRootDir:"
                + (new File(AutoCodeConfigProperties.sourceRootPackage.replace(".", File.separator))).getAbsolutePath()
                + "' outWebappRootDir:"
                + (new File(AutoCodeConfigProperties.webRootPackage.replace(".", File.separator))).getAbsolutePath());

        List<File> srcFiles = FileHelper.searchAllNotIgnoreFile(templateRootDir);
        log.info("----srcFiles----size-----------" + srcFiles.size());
        log.info("----srcFiles----list------------" + srcFiles.toString());
        for (int i = 0; i < srcFiles.size(); i++) {
            File srcFile = srcFiles.get(i);
            executeGenerate(projectPath, templateRootDir, templateData, srcFile, createFileConfig);
        }
    }

    protected void executeGenerate(String projectPath, File templateRootDir, Map<String, Object> templateData,
                                   File srcFile, CreateFileConfig createFileConfig) throws Exception {
        log.debug("-------templateRootDir--" + templateRootDir.getPath());
        log.debug("-------srcFile--" + srcFile.getPath());

        String templateFile = FileHelper.getRelativePath(templateRootDir, srcFile);
        try {
            log.debug("-------templateFile--" + templateFile);
            if (createFileConfig.getStylePath() != null && !"".equals(createFileConfig.getStylePath())
                    && !templateFile.replace(File.separator, ".").startsWith(createFileConfig.getStylePath())) {
                return;
            }
            String outputFilepath = proceeForOutputFilepath(templateData, templateFile, createFileConfig);
            log.debug("-------outputFilepath--" + outputFilepath);
            if (outputFilepath.startsWith("java")) {
                String path = projectPath + File.separator
                        + AutoCodeConfigProperties.sourceRootPackage.replace(".", File.separator);

                String soure = path;
                outputFilepath = outputFilepath.substring("java".length());
                outputFilepath = soure + outputFilepath;
                log.debug("-------java----outputFilepath--" + outputFilepath);
                generateNewFileOrInsertIntoFile(templateFile, outputFilepath, templateData, createFileConfig);
            } else if (outputFilepath.startsWith("webapp")) {
                String path = projectPath + File.separator
                        + AutoCodeConfigProperties.webRootPackage.replace(".", File.separator);

                String soure = path;
                outputFilepath = outputFilepath.substring("webapp".length());
                outputFilepath = soure + outputFilepath;
                log.debug("-------webapp---outputFilepath---" + outputFilepath);
                generateNewFileOrInsertIntoFile(templateFile, outputFilepath, templateData, createFileConfig);
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.toString());
        }
    }

    protected void generateNewFileOrInsertIntoFile(String templateFile, String outputFilepath,
                                                   Map<String, Object> templateModel, CreateFileConfig createFileConfig) throws Exception {
        if (outputFilepath.endsWith("i")) {
            outputFilepath = outputFilepath.substring(0, outputFilepath.length() - 1);
        }

        Template template = getFreeMarkerTemplate(templateFile, createFileConfig);
        template.setOutputEncoding(sourceEncoding);
        File absoluteOutputFilePath = FileHelper.parentMkdir(outputFilepath);
        log.info("[generate]\t template:" + templateFile + " ==> " + outputFilepath);
        FreemarkerHelper.processTemplate(template, templateModel, absoluteOutputFilePath, sourceEncoding);
        if (!isCutFile(absoluteOutputFilePath)) {
            results.add("生成成功：" + outputFilepath);
        } else {
            splitFile(absoluteOutputFilePath, "#segment#");
        }
    }

    protected Template getFreeMarkerTemplate(String templateName, CreateFileConfig createFileConfig)
            throws IOException {
        return FreemarkerHelper
                .newFreeMarkerConfiguration(createFileConfig.getTemplateRootDirs(), sourceEncoding, templateName)
                .getTemplate(templateName);
    }

    protected boolean isCutFile(File file) {
        if (file.getName().startsWith("[1-n]")) {
            return true;
        }
        return false;
    }

    protected void splitFile(File file, String splitStr) {
        InputStreamReader isr = null;
        BufferedReader br = null;
        List<OutputStreamWriter> fList = new ArrayList<>();
        try {
            isr = new InputStreamReader(new FileInputStream(file), "UTF-8");
            br = new BufferedReader(isr);
            boolean start = false;
            OutputStreamWriter targetFile = null;
            String row;
            while ((row = br.readLine()) != null) {
                if (row.trim().length() > 0 && row.startsWith(splitStr)) {
                    String fileName = row.substring(splitStr.length());
                    String parent = file.getParentFile().getAbsolutePath();
                    fileName = parent + File.separator + fileName;
                    log.info("[generate]\t split file:" + file.getAbsolutePath() + " ==> " + fileName);

                    targetFile = new OutputStreamWriter(new FileOutputStream(fileName), "UTF-8");
                    fList.add(targetFile);
                    results.add("生成成功：" + fileName);
                    start = true;
                    continue;
                }
                if (start) {
                    log.debug("row : " + row);
                    targetFile.append(row + "\n");
                }
            }
            for (int i = 0; i < fList.size(); i++) {
                ((Writer) fList.get(i)).close();
            }
            br.close();
            isr.close();

            log.info("[generate]\t delete file:" + file.getAbsolutePath());
            forceDelete(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (br != null) {
                    br.close();
                }
                if (isr != null) {
                    isr.close();
                }
                if (fList.size() > 0) {
                    for (int i = 0; i < fList.size(); i++) {
                        if (fList.get(i) != null) {
                            ((Writer) fList.get(i)).close();
                        }
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
