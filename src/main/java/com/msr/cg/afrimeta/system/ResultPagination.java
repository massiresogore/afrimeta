package com.msr.cg.afrimeta.system;

import java.util.List;
import java.util.Map;

public class ResultPagination {
    private boolean flag;
    private int code;
    private String message;
    private Map<String, Object> data;


    public ResultPagination(boolean flag, int code, String message) {
        this.flag = flag;
        this.code = code;
        this.message = message;
    }

    public ResultPagination(boolean flag, int code, String message, Map<String, Object> data) {
        this(flag, code, message);
        this.data = data;
    }

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Map<String, Object> data) {
        this.data = data;
    }
}
