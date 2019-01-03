package com.zhang.yong.demothree.vo;

import com.zhang.yong.demothree.constant.Codes;

import java.io.Serializable;

/**
 * created by zhang yong on 20181228
 * @param <T>
 */
public class JsonObject<T> implements Serializable {
    private final static String typeRefresh = "refresh";
    private final static String typeAlert = "alert";
    private final static String typeUpdate = "update";
    private final static String typeDialog = "dialog";
    private final static String typeData = "data";
    private final static String typeRedirect = "redirect";

    private String type;
    private T data;
    private boolean isSuccess = true;
    private String message;
    private int code;

    public static JsonObject refresh(String message) {
        return new JsonObject<>(typeRefresh, message);
    }

    public static JsonObject refresh() {
        return new JsonObject<>(typeRefresh, "");
    }

    public static JsonObject update(String html) {
        return new JsonObject<>(typeUpdate, html);
    }

    public static JsonObject dialog(JsonDialog dialog) {
        return new JsonObject<>(typeDialog, dialog);
    }

    public static JsonObject<String> success() {
        return new JsonObject<>(typeData, "", true, "",Codes.OK);
    }

    public static <T> JsonObject<T> success(T data) {
        return new JsonObject<>(typeData, data, true, "",Codes.OK);
    }

    public static JsonObject<String> error(String message) {
        return new JsonObject<>(typeDialog, message, false, message,Codes.fail);
    }

    public static JsonObject<String> redirect(String url){
        return new JsonObject<>(typeRedirect, url);
    }

    public static JsonObject alert(String message, WebMessageLevel level) {
        return new JsonObject<>(typeAlert, new JsonAlert(level.getText(), message, level));
    }


    public JsonObject(String type, T data) {
        this(type, data, true, "", Codes.OK);
    }

    public JsonObject(String type, T data, boolean success, String message, int code) {
        this.type = type;
        this.data = data;
        this.isSuccess = success;
        this.message = message;
        this.code = code;
    }

    public T getData() {
        return data;
    }

    public JsonObject setData(T data) {
        this.data = data;
        return this;
    }

    public String getType() {
        return type;
    }

    public JsonObject setType(String type) {
        this.type = type;
        return this;
    }

    public boolean isSuccess() {
        return isSuccess;
    }

    public JsonObject setSuccess(boolean isSuccess) {
        this.isSuccess = isSuccess;
        return this;
    }

    public String getMessage() {
        return message;
    }

    public JsonObject setMessage(String message) {
        this.message = message;
        return this;
    }

    public int getCode() {
        return code;
    }

    public JsonObject setCode(int code) {
        this.code = code;
        return this;
    }
}
