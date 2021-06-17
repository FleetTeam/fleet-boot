package org.fleet.modules.deve.autocode.generate.util;

import freemarker.cache.FileTemplateLoader;
import freemarker.cache.MultiTemplateLoader;
import freemarker.cache.TemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

public class FreemarkerHelper {
    private static final Logger log = LoggerFactory.getLogger(FreemarkerHelper.class);

    public static Configuration newFreeMarkerConfiguration(List<File> templateRootDirs, String defaultEncoding,
                                                           String templateName) throws IOException {

        Configuration conf = new Configuration();
        log.debug(" FileTemplateLoader[] size " + templateRootDirs.size());
        log.debug(" templateRootDirs templateName " + templateName);
        FileTemplateLoader[] templateLoaders = new FileTemplateLoader[templateRootDirs.size()];
        for (int i = 0; i < templateRootDirs.size(); i++) {
            File file = templateRootDirs.get(i);
            log.debug(" FileTemplateLoader " + file.getAbsolutePath());
            templateLoaders[i] = new FileTemplateLoader(file);
        }
        MultiTemplateLoader multiTemplateLoader = new MultiTemplateLoader((TemplateLoader[]) templateLoaders);

        conf.setTemplateLoader((TemplateLoader) multiTemplateLoader);
        conf.setNumberFormat("###############");
        conf.setBooleanFormat("true,false");
        conf.setDefaultEncoding(defaultEncoding);

        return conf;
    }

    public static List<String> getParentPaths(String templateName, String suffix) {
        String[] array = tokenizeToStringArray(templateName, "\\/");
        List<String> list = new ArrayList<>();
        list.add(suffix);
        list.add(File.separator + suffix);
        String path = "";
        for (int i = 0; i < array.length; i++) {
            path = path + File.separator + array[i];
            list.add(path + File.separator + suffix);
        }
        return list;
    }

    public static String[] tokenizeToStringArray(String str, String seperators) {
        if (str == null)
            return new String[0];
        StringTokenizer tokenlizer = new StringTokenizer(str, seperators);
        List<Object> result = new ArrayList<Object>();

        while (tokenlizer.hasMoreElements()) {
            Object s = tokenlizer.nextElement();
            result.add(s);
        }
        return result.<String>toArray(new String[result.size()]);
    }

    public static String processTemplateString(String templateString, Map<?, ?> model, Configuration conf) {
        StringWriter out = new StringWriter();
        try {
            Template template = new Template("templateString...", new StringReader(templateString), conf);
            template.process(model, out);
            return out.toString();
        } catch (Exception e) {
            throw new IllegalStateException("cannot process templateString:" + templateString + " cause:" + e, e);
        }
    }

    public static void processTemplate(Template template, Map<String, Object> model, File outputFile, String encoding)
            throws IOException, TemplateException {
        BufferedWriter out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(outputFile), encoding));
        model.put("Format", new SimpleFormat());
        template.process(model, out);
        out.close();
    }
}
