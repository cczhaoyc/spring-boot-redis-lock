package com.suxia.cc.mybatis.base.result;

import java.io.Serializable;
import java.util.Date;


/**
 * @author cczhaoyc@163.com
 * @version v_1.0.0
 * @description 统一返回格式
 * @date 2020/4/22 10:35
 */
public class ActionResult implements Serializable {

    private static final long serialVersionUID = 5371348467034672309L;

    public static String SUCCESS = "success";
    public static String WARN = "warn";
    public static String ERROR = "error";
    public static int SUCCESS_CODE = 200;
    public static int SERVICE_ERROR_CODE = 9999;
    public static int NO_LOGIN_ERROR_CODE = 10000;

    private String type = SUCCESS;
    private String title;
    private int errorNo;
    private Object data;
    private String url;
    private Date time = new Date();

    public ActionResult() {

    }

    public ActionResult(String title) {
        this.title = title;
    }

    public ActionResult(int errorNo) {
        this.errorNo = errorNo;
    }

    public ActionResult(String title, String type) {
        this.type = type;
        this.title = title;
    }

    public static ActionResult success(Object data) {
        ActionResult msg = new ActionResult();
        msg.setType(SUCCESS);
        msg.setData(data);
        msg.setErrorNo(SUCCESS_CODE);
        msg.setTitle(SUCCESS);
        return msg;
    }

    public static ActionResult success(Object data, int errorNo, String title) {
        ActionResult msg = new ActionResult();
        msg.setType(SUCCESS);
        msg.setData(data);
        msg.setErrorNo(errorNo);
        msg.setTitle(title);
        return msg;
    }

    public static ActionResult success(Object data, int errorNo, String title, String url) {
        ActionResult msg = new ActionResult();
        msg.setType(SUCCESS);
        msg.setData(data);
        msg.setErrorNo(errorNo);
        msg.setTitle(title);
        msg.setUrl(url);
        return msg;
    }

    public static ActionResult error(Object data) {
        ActionResult msg = new ActionResult();
        msg.setType(ERROR);
        msg.setData(data);
        msg.setErrorNo(SERVICE_ERROR_CODE);
        msg.setTitle(ERROR);
        return msg;
    }

    public static ActionResult error(Object data, int errorNo, String title, String url) {
        ActionResult msg = new ActionResult();
        msg.setType(ERROR);
        msg.setData(data);
        msg.setErrorNo(errorNo);
        msg.setTitle(title);
        msg.setUrl(url);
        return msg;
    }

    public static ActionResult success() {
        return ActionResult.success(null, SUCCESS_CODE, SUCCESS, null);
    }

    public int getErrorNo() {
        return errorNo;
    }

    public void setErrorNo(int errorNo) {
        this.errorNo = errorNo;
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

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return "ActionResult{" +
                "type='" + type + '\'' +
                ", title='" + title + '\'' +
                ", errorNo=" + errorNo +
                ", data=" + data +
                ", url='" + url + '\'' +
                ", time=" + time +
                '}';
    }
}
