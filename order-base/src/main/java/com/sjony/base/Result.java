package com.sjony.base;

import java.io.Serializable;

/**
 * @Description: 返回结果
 * @Create on: 2017/7/18 下午12:02
 *
 * @author shujiangcheng
 */
public class Result implements Serializable {

    private static final long serialVersionUID = -2433109748026208058L;
    private String success;
    private String sno;
    private Object data;
    private String msg;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public String getSno() {
        return sno;
    }

    public void setSno(String sno) {
        this.sno = sno;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
