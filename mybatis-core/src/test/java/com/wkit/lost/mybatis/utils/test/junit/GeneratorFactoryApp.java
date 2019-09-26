package com.wkit.lost.mybatis.utils.test.junit;

import com.wkit.lost.mybatis.filling.gen.DateGenerator;
import com.wkit.lost.mybatis.filling.proxy.GeneratorFactory;

public class GeneratorFactoryApp {

    public static void main( String[] args ) {
        Object value = GeneratorFactory.build( DateGenerator.class );
        System.out.println( value );
        Object ttt = 1;
    }
}
