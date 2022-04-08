package com.gy.com.action;

import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.gy.com.build.FileInfoBuild;
import com.gy.com.model.FileInfo;
import com.gy.com.util.FileUtils;
import com.gy.com.util.GroovyParseUtil;
import com.gy.com.util.ParseUtil;
import com.intellij.notification.*;
import com.intellij.openapi.actionSystem.*;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.module.Module;
import com.intellij.openapi.module.ModuleUtil;
import com.intellij.openapi.module.ModuleUtilCore;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.roots.ModuleRootManager;
import com.intellij.openapi.ui.Messages;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.*;
import com.intellij.psi.util.PsiTreeUtil;
import com.intellij.psi.util.PsiUtilBase;
import com.intellij.util.MathUtil;
import org.apache.commons.lang.StringUtils;
import org.apache.velocity.runtime.parser.node.MathUtils;
import org.apache.velocity.util.ArrayListWrapper;
import org.codehaus.groovy.runtime.typehandling.BigDecimalMath;
import org.jetbrains.annotations.Nullable;

import javax.swing.tree.DefaultMutableTreeNode;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class Action extends AnAction {

    @Override
    public void actionPerformed(AnActionEvent event) {
        // TODO: insert action logic here

        Project project = event.getData(PlatformDataKeys.PROJECT);

        VirtualFile projectFile = event.getData(PlatformDataKeys.PROJECT_FILE_DIRECTORY);
        List<FileInfo> utFileInfos = FileInfoBuild.getUtFileInfo(projectFile.getChildren(), event.getDataContext());

        List<FileInfo> javaUtFileInfos = FileInfoBuild.getJavaFile(utFileInfos);
        Integer javaUtCount = javaUtFileInfos.stream().mapToInt(a -> ParseUtil.parse(a)).sum();

        List<FileInfo> groovyUtFileInfos = FileInfoBuild.getSpockFile(utFileInfos);
        Integer spockUtCount = groovyUtFileInfos.stream().mapToInt(a -> GroovyParseUtil.parseGroovy(a)).sum();
        String result = String.format("Spock UT Cases:%d,Total UT Cases:%d，Percentage: %s", spockUtCount, javaUtCount + spockUtCount,
                formatPercent(spockUtCount, javaUtCount + spockUtCount));
        Messages.showMessageDialog(project, result, "UT统计", Messages.getInformationIcon());
//        NotificationGroup group = new NotificationGroup("com.gy.spock.count", NotificationDisplayType.STICKY_BALLOON, true);
//        Notification notification = group.createNotification(
//                "统计结果",
//                result,
//                NotificationType.INFORMATION,
//                new NotificationListener.UrlOpeningListener(false)
//        );
//
//        Notifications.Bus.notify(notification, project);
    }

    public static String formatPercent(int num1, int num2) {
        NumberFormat numberFormat = NumberFormat.getInstance();
        numberFormat.setMaximumFractionDigits(2);
        String result = numberFormat.format((float) num1 / (float) num2 * 100) + "%";
        return result;
    }

}
