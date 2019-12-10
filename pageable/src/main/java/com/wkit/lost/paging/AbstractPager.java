package com.wkit.lost.paging;

import java.util.regex.Pattern;

/**
 * 抽象分页类
 * @author wvkity
 */
@SuppressWarnings( "serial" )
public abstract class AbstractPager implements Pageable {

    /**
     * 正整数正则表达式
     */
    private static final Pattern PATTERN = Pattern.compile( "^\\+?(0|[1-9]+\\d?)$" );

    /**
     * 零
     */
    public static final long ZERO = 0;

    /**
     * 第一页
     */
    public static final long FIRST_PAGE = 1;

    /**
     * 每页显示数目
     */
    public static final long PAGE_SIZE = 20;

    /**
     * 每页最大记录数
     */
    public static final long MAX_PAGE_SIZE = 2000;

    /**
     * 显示页码数目
     */
    public static final long VISIBLE_SIZE = 8;

    /**
     * 最大显示页码数目
     */
    public static final long MAX_VISIBLE_SIZE = 20;

    /**
     * 页数
     */
    protected long page;

    /**
     * 每页数目
     */
    protected long size;

    /**
     * 总记录数
     */
    protected long record;

    /**
     * 页码显示数目
     */
    protected long visible;

    /**
     * 总页数
     */
    protected long total;

    /**
     * 页码起始位置
     */
    protected long start;

    /**
     * 页码结束位置
     */
    protected long end;

    /**
     * 构造方法
     */
    public AbstractPager() {
        this( FIRST_PAGE, PAGE_SIZE );
    }

    /**
     * 构造方法
     * @param page 当前页
     */
    public AbstractPager( String page ) {
        this( toLongValue( page ) );
    }

    /**
     * 构造方法
     * @param page 当前页
     */
    public AbstractPager( long page ) {
        this( page, PAGE_SIZE );
    }

    /**
     * 构造方法
     * @param page 当前页
     * @param size 每页显示数目
     */
    public AbstractPager( String page, String size ) {
        this( toLongValue( page ), toLongValue( size ) );
    }

    /**
     * 构造方法
     * @param page 当前页
     * @param size 每页显示数目
     */
    public AbstractPager( long page, long size ) {
        this.setPage( page );
        this.setSize( size );
    }

    /**
     * 计算总页数
     */
    protected void calculateTotal() {
        long count = record / size;
        if ( record % size != 0 ) {
            count += 1;
        }
        if ( this.page > count ) {
            this.page = count;
        }
        this.total = count;
        this.calculatePageStartAndEnd();
    }

    /**
     * 计算页码起始位置和结束位置
     */
    protected void calculatePageStartAndEnd() {
        long tempStart = 1;
        if ( this.visible < 1 ) {
            this.visible = VISIBLE_SIZE;
        } else if ( this.visible > MAX_VISIBLE_SIZE ) {
            this.visible = MAX_VISIBLE_SIZE;
        }
        long tempPage = this.getPage();
        long tempTotal = this.getTotal();
        double temp = Math.floor( this.visible / 2.0 );
        long number = ( long ) temp + 1;
        long tempEnd;
        if ( tempPage <= number ) {
            tempEnd = this.visible;
        } else {
            tempStart = tempPage - number + 1;
            tempEnd = tempPage + number - 2;
            if ( ( tempPage - tempStart + 1 ) == number || ( tempPage + 2 ) == tempTotal ) {
                tempStart -= 1;
                tempEnd -= 1;
            }
        }
        if ( tempEnd > tempTotal ) {
            tempEnd = tempTotal;
        }
        long diff = tempEnd - tempStart + 1;
        if ( diff != this.visible ) {
            tempStart = tempStart - ( tempEnd - diff );
        }
        if ( tempStart <= 0 ) {
            tempStart = 1;
        }
        this.start = tempStart;
        this.end = tempEnd;
    }

    /**
     * 检测是否为有效正整数
     * @param value 待校验值
     * @return boolean
     */
    private static boolean isPositiveInteger( final String value ) {
        if ( value == null || value.isEmpty() ) {
            return false;
        }
        return PATTERN.matcher( value ).matches();
    }

    /**
     * 字符串整数转数值
     * @param value 值
     * @return long
     */
    private static long toLongValue( final String value ) {
        return isPositiveInteger( value ) ? Long.parseLong( value ) : 0;
    }

    @Override
    public long getPage() {
        this.page = Math.max( this.page, FIRST_PAGE );
        return this.page;
    }

    @Override
    public void setPage( long page ) {
        this.page = page < 1 ? FIRST_PAGE : page;
    }

    @Override
    public long getSize() {
        return this.size;
    }

    @Override
    public void setSize( long size ) {
        this.size = size < ZERO ? PAGE_SIZE : Math.min( size, MAX_PAGE_SIZE );
    }

    @Override
    public long getRecord() {
        return this.record;
    }

    @Override
    public void setRecord( long record ) {
        this.record = Math.max( ZERO, record );
        this.calculateTotal();
    }

    @Override
    public long getTotal() {
        return this.total;
    }

    @Override
    public long getVisible() {
        return this.visible;
    }

    @Override
    public void setVisible( long visible ) {
        this.visible = visible < 1 ? VISIBLE_SIZE : visible;
    }

    @Override
    public long offset() {
        long limit = ( this.getPage() - 1 ) * size;
        return Math.max( limit, ZERO );
    }

    @Override
    public long getStart() {
        return this.start;
    }

    @Override
    public long getEnd() {
        return this.end;
    }

    @Override
    public boolean hasNext() {
        if ( record > 0 ) {
            return record > ( getPage() - 1 ) * getSize();
        }
        return false;
    }
}
