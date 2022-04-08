package com.gy.com.util;

import com.github.javaparser.StaticJavaParser;

import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.gy.com.model.FileInfo;
import com.intellij.util.containers.hash.HashMap;
import groovy.lang.Binding;
import groovy.lang.GroovyClassLoader;
import groovy.lang.GroovyObject;
import groovy.lang.GroovyShell;
import groovy.util.GroovyScriptEngine;
import groovyjarjarantlr4.v4.runtime.CharStream;
import groovyjarjarantlr4.v4.runtime.CharStreams;
import groovyjarjarantlr4.v4.runtime.CommonTokenStream;
import org.apache.commons.collections.CollectionUtils;
import org.apache.groovy.parser.antlr4.AbstractParser;
import org.apache.groovy.parser.antlr4.Antlr4PluginFactory;
import org.apache.groovy.parser.antlr4.GroovyLangLexer;
import org.apache.groovy.parser.antlr4.GroovyLangParser;
import org.codehaus.groovy.ast.AnnotationNode;
import org.codehaus.groovy.ast.CompileUnit;
import org.codehaus.groovy.ast.MethodNode;
import org.codehaus.groovy.ast.ModuleNode;
//import org.codehaus.groovy.control.CompilationUnit;

import org.codehaus.groovy.control.*;
import org.codehaus.groovy.tools.groovydoc.GroovyDocTool;
import org.jf.util.StringUtils;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

import static org.apache.groovy.parser.antlr4.GroovydocManager.DOC_COMMENT;

public class ParseUtil {

    public static Integer parse(FileInfo fileInfo) {

        try {
            Path fileName = Path.of(fileInfo.getPath());
            String sourceCode = Files.readString(fileName);
            com.github.javaparser.ast.CompilationUnit cu = StaticJavaParser.parse(sourceCode);
            Integer count = cu.getClassByName(fileInfo.getClassName()).map(ClassOrInterfaceDeclaration::getMethods)
                    .map(m -> getTestCount(m))
                    .map(Long::intValue)
                    .orElse(0);
            fileInfo.setUtCaseCount(count);
            return count;
        } catch (IOException e) {
            System.out.println(fileInfo.getClassName());
            return 0;
        }
    }


    private static long getTestCount(List<MethodDeclaration> methodDeclarations) {
        return methodDeclarations.stream().filter(Objects::nonNull)
                .filter(a -> !CollectionUtils.isEmpty(a.getAnnotations()) && a.getAnnotations()
                        .stream().anyMatch(exr -> "Test".equalsIgnoreCase(exr.getName().asString())))
                .count();
    }
}
