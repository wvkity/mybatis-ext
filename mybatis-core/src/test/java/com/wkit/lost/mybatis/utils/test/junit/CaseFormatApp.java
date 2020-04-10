package com.wkit.lost.mybatis.utils.test.junit;

import com.wkit.lost.mybatis.utils.CaseFormat;
import com.wkit.lost.mybatis.utils.StringUtil;
import org.junit.jupiter.api.Test;

import java.util.regex.Pattern;

public class CaseFormatApp {

    @Test
    public void test() {
        System.out.println(CaseFormat.LOWER_HYPHEN.to(CaseFormat.LOWER_CAMEL, "test-data"));
        System.out.println(CaseFormat.LOWER_HYPHEN.to(CaseFormat.NORMAL_LOWER_CAMEL, "test-data"));
        System.out.println(CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, "test_data"));
        System.out.println(CaseFormat.UPPER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, "TEST_DATA"));
        System.out.println(CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, "testData"));
        System.out.println(CaseFormat.LOWER_CAMEL.to(CaseFormat.UPPER_UNDERSCORE, "testData"));
        System.out.println(CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_HYPHEN, "testData"));
        System.out.println(CaseFormat.LOWER_CAMEL.to(CaseFormat.UPPER_CAMEL, "testData"));
        System.out.println(CaseFormat.UPPER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, "testData"));
        System.out.println(CaseFormat.UPPER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, "TEST_DATA"));
        System.out.println(CaseFormat.UPPER_UNDERSCORE.to(CaseFormat.LOWER_UNDERSCORE, "TEST_DATA"));
        System.out.println(CaseFormat.UPPER_UNDERSCORE.to(CaseFormat.NORMAL_LOWER_CAMEL, "TEST_DATA"));
        System.out.println(CaseFormat.UPPER_UNDERSCORE.to(CaseFormat.NORMAL_LOWER_CAMEL, "testData"));
        System.out.println(CaseFormat.UPPER_UNDERSCORE.to(CaseFormat.NORMAL_LOWER_CAMEL, "TESTDATA"));
        System.out.println(CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, "testData"));
        System.out.println(CaseFormat.UPPER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL_UNDERSCORE, "TEST_DATA"));
        System.out.println(StringUtil.camelHumpToUnderline("testData"));
    }

    @Test
    public void patternTest() {
        Pattern pattern = Pattern.compile("[A-Z0-9_\\-]+");
        System.out.println(pattern.matcher("AAABB3").matches());
        System.out.println(pattern.matcher("aaabb3").matches());
        System.out.println(pattern.matcher("AAabB3").matches());
        System.out.println(pattern.matcher("abB3").matches());
        System.out.println(pattern.matcher("abB").matches());
        System.out.println(pattern.matcher("abbabb").matches());
        System.out.println(pattern.matcher("ABBABB").matches());
        System.out.println(pattern.matcher("test-data").matches());
        System.out.println(pattern.matcher("TEST-DATA").matches());
        System.out.println(pattern.matcher("TEST_DATA").matches());
        System.out.println(pattern.matcher("DATA").matches());
        System.out.println(pattern.matcher("tDATA").matches());
        System.out.println(!pattern.matcher("ADATA").matches());
        System.out.println(!pattern.matcher("t_DATA").matches());
    }

    @Test
    public void test3() {
        System.out.println(CaseFormat.UPPER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, (CaseFormat.LOWER_CAMEL.to(CaseFormat.UPPER_UNDERSCORE, "zhangSan") + "_AVG")));
        System.out.println(CaseFormat.UPPER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, (CaseFormat.LOWER_CAMEL.to(CaseFormat.UPPER_UNDERSCORE, "userName") + "_AVG")));
        System.out.println(CaseFormat.UPPER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, (CaseFormat.LOWER_CAMEL.to(CaseFormat.UPPER_UNDERSCORE, "userAAName") + "_AVG")));
    }
}
