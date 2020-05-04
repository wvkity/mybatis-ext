package com.wvkity.mybatis.utils.test.junit;

import com.wvkity.mybatis.core.conditional.utils.Formatter;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

public class FormatterTest {

    @Test
    public void test() {
        String template1 = "我是{}，今年{}岁, {}...\\{}这是转义，无需替换";
        System.out.println(Formatter.format(template1, "张三", 22));
        String template2 = "我是${userName}，今年${age}岁, ${year}这是否能${ts}替换...\\${}这是转义，无需替换";
        Map<String, Object> params = new HashMap<>();
        params.put("userName", "张婷");
        params.put("year", 2020);
        params.put("age", 25);
        System.out.println(Formatter.format(template2, params));
    }
}
