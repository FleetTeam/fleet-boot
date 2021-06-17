package org.fleet.modules.deve.autocode.generate.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CreateFileConfig {
    private static final Logger log = LoggerFactory.getLogger(CreateFileConfig.class);

    private List<File> templateRootDirs = new ArrayList<>();

    private String templatePath;

    private String stylePath;

    public CreateFileConfig(String templatePath) {
        log.debug("----templatePath-----------------" + templatePath);
        log.debug("----stylePath-----------------" + this.stylePath);
        this.templatePath = templatePath;
    }

    private void setTemplateRootDir(File templateRootDir) {
        setTemplateRootDirs(new File[]{templateRootDir});
    }

    public String getStylePath() {
        return this.stylePath;
    }

    public void setStylePath(String stylePath) {
        this.stylePath = stylePath;
    }

    public List<File> getTemplateRootDirs() {
        String classpath = getClass().getResource(templatePath).getFile();
        classpath = classpath.replaceAll("%20", " ");
        log.debug("-------classpath-------" + classpath);
        if (classpath.indexOf("/BOOT-INF/classes!") != -1 || classpath.indexOf("/BOOT-INF/lib/") != -1) {
            classpath = System.getProperty("user.dir") + File.separator + "config/fleet/code-template-online/".replace("/", File.separator);
            log.debug("---JAR--config--classpath-------" + classpath);
        }

        setTemplateRootDir(new File(classpath));
        return this.templateRootDirs;
    }

    private void setTemplateRootDirs(File... templateRootDirs) {
        this.templateRootDirs = Arrays.asList(templateRootDirs);
    }

    public void setTemplateRootDirs(List<File> templateRootDirs) {
        this.templateRootDirs = templateRootDirs;
    }

    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("{\"templateRootDirs\":\"");
        builder.append(this.templateRootDirs);
        builder.append("\",\"stylePath\":\"");
        builder.append(this.stylePath);
        builder.append("\"} ");
        return builder.toString();
    }
}
