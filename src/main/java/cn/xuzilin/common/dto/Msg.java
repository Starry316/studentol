package cn.xuzilin.common.dto;

import java.io.Serializable;

/**
 * Created by imaxct on 17-7-5.
 * isdu-new
 */
public class Msg<T> implements Serializable{
    /**
     * code 请求状态码 0表示无错误, 小于0代表错误, 大于0代表非错误性提示
     */
    private int code = -1;

    /**
     * msg 请求返回信息 有错误或有提示时显示, 尽量可直接显示给用户
     */
    private String msg = null;

    /**
     * obj 附加返回对象 携带附加信息
     */
    private T obj = null;
    public Msg(){}

    public Msg(int code, String msg, T obj) {
        this.code = code;
        this.msg = msg;
        this.obj = obj;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getObj() {
        return obj;
    }

    public void setObj(T obj) {
        this.obj = obj;
    }

    public static Msg ok(String msg){
        return new Msg<>(0, msg, null);
    }

    public static Msg ok(String msg, Object obj){
        return new Msg<>(0, msg, obj);
    }

    public static Msg err(String msg){
        return new Msg<>(-1, msg, null);
    }

    public static Msg err(String msg, Object obj){
        return new Msg<>(-1, msg, obj);
    }

    public static Msg err(int code, String msg, Object obj) {
        return new Msg<>(code, msg, obj);
    }
}
