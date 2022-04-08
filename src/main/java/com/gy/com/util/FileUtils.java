package com.gy.com.util;

import com.gy.com.model.FileInfo;
import com.intellij.lang.Language;
import com.intellij.openapi.actionSystem.DataContext;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.fileEditor.FileDocumentManager;
import com.intellij.openapi.vfs.VirtualFile;
import org.codehaus.groovy.ast.GroovyClassVisitor;
import org.codehaus.groovy.tools.groovydoc.GroovyDocTool;


import java.io.File;
import java.io.IOException;
import java.net.JarURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class FileUtils {


    /**
     * 递归读取文件
     *
     * @return
     */
    public static void recursionFileTree(VirtualFile virtualFile, DataContext dataContext, List<FileInfo> fileInfoArr) {
        if (virtualFile.isDirectory()) {
            VirtualFile[] dicFiles = virtualFile.getChildren();
            for (int i = 0; i < dicFiles.length; i++) {
                VirtualFile fileTree = dicFiles[i];
                recursionFileTree(fileTree, dataContext, fileInfoArr);
            }
        } else {
            String fileName = virtualFile.getName();
            if (!virtualFile.getPath().contains("src/test") || !(fileName.endsWith(".java") || fileName.endsWith(".groovy"))) {
                return;
            }
            Document document = FileDocumentManager.getInstance().getDocument(virtualFile);
            if (document == null) {
                System.out.println("file not exist:" + virtualFile.getPath());
                return;
            }
            long size = document.getTextLength();
            String path = virtualFile.getPath();
            long lineCount = document.getLineCount();
            FileInfo fileInfo = new FileInfo();
            fileInfo.setName(fileName);
            fileInfo.setSize(size);
            fileInfo.setLanguage(getLanguage(fileName));
            fileInfo.setLineCount(lineCount);
            fileInfo.setPath(path);
            fileInfo.setClassName(fileName.substring(0, fileName.indexOf(".")));
            fileInfo.setCodeText(document.getText());
            fileInfoArr.add(fileInfo);
        }
    }

    private static String getLanguage(String fileName) {
        return fileName.endsWith(".java") ? "java" : fileName.endsWith(".groovy") ? "groovy" : "";
    }


}
