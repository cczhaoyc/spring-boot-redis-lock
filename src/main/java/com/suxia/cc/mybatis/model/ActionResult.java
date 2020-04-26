package com.suxia.cc.mybatis.model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * 类ActionResult.java的实现描述：接口请求封装类
 */
public class ActionResult implements Serializable {

    private static final long serialVersionUID = 1110507991796070037L;

    public static String SUCCESS = "success";
    public static String WARN = "warn";
    public static String ERROR = "error";

    private Map<String, Object> contents = new HashMap<String, Object>();
    private String type = SUCCESS;
    private String title;
    private int errorNo;
    private Object data;

    public ActionResult() {

    }

    public ActionResult(String title) {
        this.title = title;
    }

    public ActionResult(String title, String type) {
        this.type = type;
        this.title = title;
    }

    public ActionResult addContent(String key, Object obj) {
        contents.put(key, obj);
        return this;
    }

    public static ActionResult create(Object data) {
        ActionResult msg = new ActionResult();
        msg.setData(data);
        return msg;
    }

    public static ActionResult success() {
        return new ActionResult();
    }

    public int getErrorNo() {
        return errorNo;
    }

    public void setErrorNo(int errorNo) {
        this.errorNo = errorNo;
    }

    /**
     * 提示内容
     */
    public Map<String, Object> getContents() {
        return contents;
    }

    /**
     * 提示内容
     */
    public void setContents(Map<String, Object> contents) {
        this.contents = contents;
    }

    /**
     * 获取内容
     *
     * @param key
     * @return <p>
     * author: <a href="mailto:shenw@hundsun.com">shenw</a><br>
     * create at: 2014年4月16日上午12:34:54
     */
    public Object getContent(String key) {
        return contents.get(key);
    }

    /**
     * 提示类型
     */
    public String getType() {
        return type;
    }

    /**
     * 提示类型
     */
    public void setType(String type) {
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "ActionResult [contents=" + contents + ", type=" + type + ", title=" + title + ", errorNo=" + errorNo
                + ", data=" + data + "]";
    }

}
