package com.gy.com.util;

import com.gy.com.model.FileInfo;
import org.apache.commons.collections.CollectionUtils;
import org.apache.groovy.parser.antlr4.Antlr4PluginFactory;
import org.codehaus.groovy.ast.AnnotationNode;
import org.codehaus.groovy.ast.MethodNode;
import org.codehaus.groovy.ast.stmt.BlockStatement;
import org.codehaus.groovy.ast.ModuleNode;
import org.codehaus.groovy.ast.stmt.ExpressionStatement;
import org.codehaus.groovy.control.CompilationUnit;
import org.codehaus.groovy.control.CompilerConfiguration;
import org.codehaus.groovy.control.Phases;
import org.codehaus.groovy.control.SourceUnit;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class GroovyParseUtil {
    public static Integer parseGroovy(FileInfo fileInfo) {

        try {
            CompilerConfiguration compilerConfiguration = new CompilerConfiguration(CompilerConfiguration.DEFAULT);
            Map<String, Boolean> map = new java.util.HashMap();
            map.put("groovydoc", true);
            compilerConfiguration.setOptimizationOptions(map);
            compilerConfiguration.setPluginFactory(new Antlr4PluginFactory());

            CompilationUnit compilationUnit = new CompilationUnit(compilerConfiguration);
            compilationUnit.compile(Phases.PARSING);
            SourceUnit sourceUnit = compilationUnit.addSource(fileInfo.getName(), fileInfo.getCodeText());
            sourceUnit.parse();
            sourceUnit.completePhase();
            sourceUnit.nextPhase();
            sourceUnit.convert();
            Integer count = getCount(sourceUnit.getAST());
            fileInfo.setUtCaseCount(count);
            return count;

        } catch (Exception e) {
            System.out.println(fileInfo.getClassName());
            return 0;
        }
    }

    public static Integer parseGroovy1(FileInfo fileInfo) {

        try {
            CompilationUnit compilationUnit = new CompilationUnit();
            compilationUnit.compile(Phases.CONVERSION);
            SourceUnit sourceUnit = compilationUnit.addSource(new File(fileInfo.getPath()));
            sourceUnit.parse();
            ModuleNode moduleNode = sourceUnit.getAST();


            sourceUnit.completePhase();
            sourceUnit.nextPhase();
            sourceUnit.convert();
            Integer count = getCount(sourceUnit.getAST());
            fileInfo.setUtCaseCount(count);
            return count;

        } catch (Exception e) {
            System.out.println(fileInfo.getClassName());
            return 0;
        }
    }

    private static Integer getCount(ModuleNode moudle) {
        return moudle.getClasses().get(0)
                .getMethods().stream()
                .filter(a -> isTestMethod(a))
                .map(a -> countStatement(a))
                .mapToInt(Integer::intValue).sum();
    }

    private static Integer countStatement(MethodNode methodNode) {
        if (hasUnroll(methodNode.getAnnotations())) {
            return countWhere(methodNode);
        } else {
            return 1;
        }
    }

    private static Integer countWhere(MethodNode methodNode) {

        BlockStatement statement = (BlockStatement) methodNode.getCode();
        // 最后一行代码行号
        int lastLineNumber = statement.getStatements().get(statement.getStatements().size() - 1).getLineNumber();
        int whereLine = statement.getStatements().stream().filter(Objects::nonNull)
                .filter(a -> !CollectionUtils.isEmpty(a.getStatementLabels()) &&
                        a.getStatementLabels().stream().anyMatch(b -> "where".equalsIgnoreCase(b)))
                .findFirst()
                .map(a -> (ExpressionStatement) a)
                .map(a -> a.getLineNumber()).orElse(lastLineNumber);
        return lastLineNumber - whereLine - 1;
    }


    /**
     * 判断是否是多用例测试方法
     *
     * @param annotationNodes
     * @return
     */
    private static boolean hasUnroll(List<AnnotationNode> annotationNodes) {
        return !CollectionUtils.isEmpty(annotationNodes) && annotationNodes.stream()
                .anyMatch(a -> "Unroll".equalsIgnoreCase(a.getClassNode().getName()));
    }

    /**
     * 判断是否是测试方法
     *
     * @param methodNode
     * @return
     */

    private static boolean isTestMethod(MethodNode methodNode) {
        BlockStatement statement = (BlockStatement) methodNode.getCode();
        return !CollectionUtils.isEmpty(statement.getStatements()) && statement.getStatements().stream()
                .filter(a -> !CollectionUtils.isEmpty(a.getStatementLabels()))
                .flatMap(a -> a.getStatementLabels().stream())
                .filter(Objects::nonNull)
                .anyMatch(a -> Arrays.asList("given", "and", "expect", "where", "then").contains(a));
    }
}
