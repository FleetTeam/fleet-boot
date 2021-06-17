package org.fleet.modules.deve.autocode.generate.util;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FileHelper {
    private static final Logger log = LoggerFactory.getLogger(FileHelper.class);
    public static ArrayList<String> ignoreList = new ArrayList<String>();
    public static List<String> ignoreEndWithList = new ArrayList<>();

    static {
        ignoreList.add(".svn");
        ignoreList.add("CVS");
        ignoreList.add(".cvsignore");
        ignoreList.add(".copyarea.db");
        ignoreList.add("SCCS");
        ignoreList.add("vssver.scc");
        ignoreList.add(".DS_Store");
        ignoreList.add(".git");
        ignoreList.add(".gitignore");
        ignoreEndWithList.add(".ftl");
    }

    public static List<File> searchAllNotIgnoreFile(File dir) throws IOException {
        ArrayList<File> arrayList = new ArrayList<File>();
        searchAllNotIgnoreFile(dir, arrayList);
        Collections.sort(arrayList, new Comparator<File>() {
            public int compare(File o1, File o2) {
                return o1.getAbsolutePath().compareTo(o2.getAbsolutePath());
            }
        });
        return arrayList;
    }

    public static void searchAllNotIgnoreFile(File dir, List<File> collector) throws IOException {
        log.debug("---------dir------------path: " + dir.getPath() + " -- isHidden --: " + dir.isHidden()
                + " -- isDirectory --: " + dir.isDirectory());
        if (!dir.isHidden() && dir.isDirectory() && !isIgnoreFile(dir)) {
            File[] subFiles = dir.listFiles();
            for (int i = 0; i < subFiles.length; i++) {
                searchAllNotIgnoreFile(subFiles[i], collector);
            }
        } else if (!isIgnoreEndWithFile(dir) && !isIgnoreFile(dir)) {
            collector.add(dir);
        }
    }

    public static String getRelativePath(File baseDir, File file) {
        if (baseDir.equals(file))
            return "";
        if (baseDir.getParentFile() == null)
            return file.getAbsolutePath().substring(baseDir.getAbsolutePath().length());
        return file.getAbsolutePath().substring(baseDir.getAbsolutePath().length() + 1);
    }

    public static boolean isBinaryFile(File file) {
        if (file.isDirectory())
            return false;
        return isBinaryFile(file.getName());
    }

    public static boolean isBinaryFile(String filename) {
        if (StringUtils.isBlank(getExtension(filename)))
            return false;
        return true;
    }

    public static String getExtension(String filename) {
        if (filename == null) {
            return null;
        }
        int index = filename.indexOf(".");
        if (index == -1) {
            return "";
        }
        return filename.substring(index + 1);
    }

    public static File parentMkdir(String file) {
        if (file == null)
            throw new IllegalArgumentException("file must be not null");
        File result = new File(file);
        parnetMkdir(result);
        return result;
    }

    public static void parnetMkdir(File outputFile) {
        if (outputFile.getParentFile() != null) {
            outputFile.getParentFile().mkdirs();
        }
    }

    private static boolean isIgnoreFile(File file) {
        for (int i = 0; i < ignoreList.size(); i++) {
            if (file.getName().equals(ignoreList.get(i))) {
                return true;
            }
        }
        return false;
    }

    private static boolean isIgnoreEndWithFile(File file) {
        for (int i = 0; i < ignoreEndWithList.size(); i++) {
            if (file.getName().endsWith(ignoreEndWithList.get(i))) {
                return true;
            }
        }
        return false;
    }
}
