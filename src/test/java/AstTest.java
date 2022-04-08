import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.body.BodyDeclaration;
import com.github.javaparser.ast.expr.Expression;
import com.github.javaparser.printer.DotPrinter;
import org.junit.Test;

import java.nio.file.Files;
import java.nio.file.Path;

public class AstTest {


    @Test
    public void test() throws Exception {
        Path fileName = Path.of("/Users/yong.gao/Desktop/job/git-data/Spock-Count/src/test/java/Calculate.java");
        String sourceCode = Files.readString(fileName);
        com.github.javaparser.ast.CompilationUnit cu = StaticJavaParser.parse(sourceCode);
        System.out.println(new DotPrinter(true).output(cu));
    }

    @Test
    public void testExpression() throws Exception {

        Expression expression = StaticJavaParser.parseExpression("c=a+b");
        // dot打印(可以通过Graphiz dot命令，将输出生成为图片格式，例如 dot -Tpng ast.dot > ast.png)
        System.out.println(new DotPrinter(true).output(expression));
    }
    @Test
    public void testMethod() throws Exception {

        BodyDeclaration expression = StaticJavaParser.parseBodyDeclaration(" public int sum(int a, int b) {\n" +
                "        int c = a + b;\n" +
                "        return c;\n" +
                "    }");
        // dot打印(可以通过Graphiz dot命令，将输出生成为图片格式，例如 dot -Tpng ast.dot > ast.png)
        System.out.println(new DotPrinter(true).output(expression));
    }
}
