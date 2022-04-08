import com.gy.com.model.FileInfo;
import com.gy.com.util.GroovyParseUtil;
import com.gy.com.util.ParseUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
public class ParseUtilTest {

    private final static String code = "package com.ctrip.flight.hotel.common.platform\n" +
            "\n" +
            "import com.ctrip.flight.hotel.model.dto.PlatformInfoDTO\n" +
            "import com.ctrip.flight.mobile.fx.context.WorkContext\n" +
            "import com.ctrip.flight.mobile.fx.context.WorkContextFactory\n" +
            "import spock.lang.*\n" +
            "\n" +
            "\n" +
            "class DefaultPlatformStrategySpockTest extends Specification {\n" +
            "    def testObj = new DefaultPlatformStrategy()\n" +
            "    def workContext = Mock(WorkContext)\n" +
            "\n" +
            "\n" +
            "    @Unroll\n" +
            "    def \"isH5\"() {\n" +
            "        given: \"\"\n" +
            "        workContext.getItems() >> items\n" +
            "        WorkContextFactory.INSTANCE.setCurrent(workContext)\n" +
            "        when:\n" +
            "        def expectedResult = testObj.isH5()\n" +
            "\n" +
            "        then: \"验证返回结果里属性值是否符合预期\"\n" +
            "        with(expectedResult) {\n" +
            "            expectedResult == result\n" +
            "        }\n" +
            "        where: \"表格方式验证多种分支调用场景\"\n" +
            "        items                         | result | _\n" +
            "        new HashMap<Object, Object>() | false  | _\n" +
            "        new HashMap<String, String>() {\n" +
            "            {\n" +
            "                put(\"platform_key\", new PlatformInfoDTO(source: \"H5\"))\n" +
            "            }\n" +
            "        }                             | true   | _\n" +
            "    }\n" +
            "}";

    @Test
    public void test() {
        FileInfo fileInfo = new FileInfo();
        fileInfo.setPath("/Users/yong.gao/Desktop/job/git-data/flight-hotel/common/src/test/groovy/com/ctrip/flight/hotel/common/platform/DefaultPlatformStrategySpockTest.groovy");
        fileInfo.setClassName("DefaultPlatformStrategySpockTest.groovy");
        GroovyParseUtil.parseGroovy1(fileInfo);
    }

    @Test
    public void test2() {
        FileInfo fileInfo = new FileInfo();
        fileInfo.setPath("/Users/yong.gao/Desktop/job/git-data/flight-hotel/common/src/test/groovy/com/ctrip/flight/hotel/common/platform/DefaultPlatformStrategySpockTest.groovy");
        fileInfo.setClassName("DefaultPlatformStrategySpockTest.groovy");

        fileInfo.setCodeText(code);
        GroovyParseUtil.parseGroovy(fileInfo);

    }
}
