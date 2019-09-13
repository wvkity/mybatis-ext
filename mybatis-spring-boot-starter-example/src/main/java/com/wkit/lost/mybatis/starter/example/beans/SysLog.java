package com.wkit.lost.mybatis.starter.example.beans;

import com.wkit.lost.mybatis.annotation.GeneratedValue;

import java.io.Serializable;
import java.time.OffsetDateTime;

public class SysLog implements Serializable {
    private static final long serialVersionUID = 1688978164655658786L;
    
    @GeneratedValue( generator = "UUID")
    private Long id;
    
    private String type;
    
    private String desc;
    
    private String createUserId;
    
    private OffsetDateTime gmtCreate;
    
    private String deleted;
    
    //@Transient
    private int state;

    public SysLog() {
    }

    public Long getId() {
        return id;
    }

    public void setId( Long id ) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType( String type ) {
        this.type = type;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc( String desc ) {
        this.desc = desc;
    }

    public String getCreateUserId() {
        return createUserId;
    }

    public void setCreateUserId( String createUserId ) {
        this.createUserId = createUserId;
    }

    public OffsetDateTime getGmtCreate() {
        return gmtCreate;
    }

    public void setGmtCreate( OffsetDateTime gmtCreate ) {
        this.gmtCreate = gmtCreate;
    }

    public String getDeleted() {
        return deleted;
    }

    public void setDeleted( String deleted ) {
        this.deleted = deleted;
    }

    public int getState() {
        return state;
    }

    public void setState( int state ) {
        this.state = state;
    }
}
