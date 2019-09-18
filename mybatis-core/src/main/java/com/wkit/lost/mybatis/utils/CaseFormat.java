package com.wkit.lost.mybatis.utils;

import java.util.function.Predicate;
import java.util.regex.Pattern;

/**
 * 字符串格式化
 * @author DT
 */
public enum CaseFormat {

    /**
     * 连字符连接变量命名规则
     * <p>如: lower-hyphen</p>
     */
    LOWER_HYPHEN( c -> c == '-', "-" ) {
        @Override
        String normalizeWord( String word ) {
            return Ascii.toLowerCase( word );
        }

        @Override
        String convert( CaseFormat format, String source ) {
            if ( format == LOWER_UNDERSCORE ) {
                return source.replace( '-', '_' );
            }
            if ( format == UPPER_UNDERSCORE ) {
                return Ascii.toUpperCase( source.replace( '-', '_' ) );
            }
            return super.convert( format, source );
        }
    },

    /**
     * C++变量命名规则
     * <p>如: lower_underscore</p>
     */
    LOWER_UNDERSCORE( c -> '_' == c, "_" ) {
        @Override
        String normalizeWord( String word ) {
            return Ascii.toLowerCase( word );
        }

        @Override
        String convert( CaseFormat format, String source ) {
            if ( format == LOWER_HYPHEN ) {
                return source.replace( '_', '-' );
            }
            if ( format == UPPER_UNDERSCORE ) {
                return Ascii.toUpperCase( source );
            }
            return super.convert( format, source );
        }
    },

    /**
     * Java变量命名规则
     * <p>如: lowerCamel</p>
     */
    LOWER_CAMEL( Ascii::isUpperCase, "" ) {
        @Override
        String normalizeWord( String word ) {
            return firstCharOnlyToUpper( word );
        }

        @Override
        String normalizeFirstWord( String word ) {
            return Ascii.toLowerCase( word );
        }
    },

    /**
     * 驼峰下划线变量命名规则
     * <p>如: lower_Camel</p>
     */
    LOWER_CAMEL_UNDERSCORE( Ascii::isUpperCase, "_" ) {
        @Override
        String normalizeWord( String word ) {
            return firstCharOnlyToUpper( word );
        }

        @Override
        String normalizeFirstWord( String word ) {
            return Ascii.toLowerCase( word );
        }
    },

    /**
     * Java变量命名规则
     * <p>如: lowerCamel</p>
     * <p>注: 当字符串为lowerCamel规则时返回原字符串</p>
     */
    NORMAL_LOWER_CAMEL(Ascii::isUpperCase, "" ) {
        @Override
        String normalizeWord( String word ) {
            return firstCharOnlyToUpper( word );
        }

        @Override
        String normalizeFirstWord( String word ) {
            return Ascii.toLowerCase( word );
        }
    },

    /**
     * Java和C++类名命名规则
     * <p>如: UpperCamel</p>
     */
    UPPER_CAMEL( Ascii::isUpperCase, "" ) {
        @Override
        String normalizeWord( String word ) {
            return firstCharOnlyToUpper( word );
        }
    },

    /**
     * Java和C++常量命名规则
     * <p>如: UPPER_UNDERSCORE</p>
     */
    UPPER_UNDERSCORE( c -> '_' == c, "_" ) {
        @Override
        String normalizeWord( String word ) {
            return Ascii.toUpperCase( word );
        }

        @Override
        String convert( CaseFormat format, String source ) {
            if ( format == LOWER_HYPHEN ) {
                return Ascii.toLowerCase( source.replace( '_', '-' ) );
            }
            if ( format == LOWER_UNDERSCORE ) {
                return Ascii.toLowerCase( source );
            }
            return super.convert( format, source );
        }
    };

    private final Predicate<Character> charMatcher;
    private final String wordSeparator;
    private static final Pattern CHECK_FOR_NORMAL = Pattern.compile( "[A-Z]+\\d*" );

    CaseFormat( Predicate<Character> charMatcher, String wordSeparator ) {
        this.charMatcher = charMatcher;
        this.wordSeparator = wordSeparator;
    }

    public String to( CaseFormat format, String source ) {
        return ( format == null || Ascii.isNullOrEmpty( source ) || format == this ) ? source : convert( format, source );
    }

    String convert( CaseFormat format, String source ) {
        // normal lower camel特殊处理
        if ( format == NORMAL_LOWER_CAMEL ) {
            if (!source.contains( "_" ) && !source.contains( "-" ) && !CHECK_FOR_NORMAL.matcher( source ).matches() ) {
                return Ascii.toLowerCase( source.charAt( 0 ) ) + source.substring( 1 );
            }
        }
        StringBuilder out = null;
        int i = 0;
        int j = -1;
        while ( ( j = indexIn( charMatcher, source, ++j ) ) != -1 ) {
            if ( i == 0 ) {
                out = new StringBuilder( source.length() + 4 * wordSeparator.length() );
                out.append( format.normalizeFirstWord( source.substring( i, j ) ) );
            } else {
                out.append( format.normalizeWord( source.substring( i, j ) ) );
            }
            out.append( format.wordSeparator );
            i = j + wordSeparator.length();
        }
        return i == 0 ? format.normalizeFirstWord( source ) : out.append( format.normalizeWord( source.substring( i ) ) ).toString();
    }

    abstract String normalizeWord( String word );

    int indexIn( Predicate<Character> charMatcher, CharSequence chars, int index ) {
        int size = chars.length();
        positionIndex( index, size );
        for ( int i = index; i < size; i++ ) {
            if ( charMatcher.test( chars.charAt( i ) ) ) {
                return i;
            }
        }
        return -1;
    }

    void positionIndex( int index, int size ) {
        if ( index < 0 ) {
            throw new IndexOutOfBoundsException( "index (" + index + ") must not be negative" );
        } else if ( size < 0 ) {
            throw new IllegalArgumentException( "negative size: " + size );
        } else if ( index > size ) {
            throw new IndexOutOfBoundsException( "index (" + index + ") must not be greater than size (" + size + ")" );
        }
    }

    String normalizeFirstWord( String word ) {
        return normalizeWord( word );
    }

    /**
     * 字符串首字母转大写
     * @param word 待转换字符串
     * @return 字符串转换后的副本
     */
    private static String firstCharOnlyToUpper( String word ) {
        return Ascii.isNullOrEmpty( word ) ? word : Ascii.toUpperCase( word.charAt( 0 ) ) + Ascii.toLowerCase( word.substring( 1 ) );
    }
}
