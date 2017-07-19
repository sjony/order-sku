package com.sjony.vo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by sjony on 2017/7/15.
 */
public class SkuQtyVO implements Serializable {


    private static final long serialVersionUID = -6953828248794760129L;


    private long id;
    private String skuCode;
    private BigDecimal skuQty;
    private Date createTime;
    private Date updateTime;
    private String createUser;
    private String updateUser;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getSkuCode() {
        return skuCode;
    }

    public void setSkuCode(String skuCode) {
        this.skuCode = skuCode;
    }

    public BigDecimal getSkuQty() {
        return skuQty;
    }

    public void setSkuQty(BigDecimal skuQty) {
        this.skuQty = skuQty;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    public String getUpdateUser() {
        return updateUser;
    }

    public void setUpdateUser(String updateUser) {
        this.updateUser = updateUser;
    }

}
