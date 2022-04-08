package com.gy.com.build;

import com.gy.com.model.FileInfo;
import com.gy.com.util.FileUtils;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.DataContext;
import com.intellij.openapi.vfs.VirtualFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class FileInfoBuild {
    public static List<FileInfo> getUtFileInfo(VirtualFile[] files, DataContext dataContext) {
        List<FileInfo> fileInfos = new ArrayList();
        for (int i = 0; i < files.length; i++) {
            VirtualFile file = files[i];
            FileUtils.recursionFileTree(file, dataContext, fileInfos);
        }
        return fileInfos;
    }


    public static List<FileInfo> getSpockFile(List<FileInfo> fileInfos) {
        return fileInfos.stream().filter(Objects::nonNull)
                .filter(a -> "groovy".equalsIgnoreCase(a.getLanguage()))
                .collect(Collectors.toList());
    }

    public static List<FileInfo> getJavaFile(List<FileInfo> fileInfos) {
        return fileInfos.stream().filter(Objects::nonNull).filter(a -> "java".equalsIgnoreCase(a.getLanguage()))
                .collect(Collectors.toList());
    }
}
