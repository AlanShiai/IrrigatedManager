package com.example.ashi.irrigatedmanager.gson;

/**
 * Created by ashi on 8/27/2018.
 */

public class HttpResult {
    public String code;
    public String msg;

    public boolean isSuccess() {
        boolean isSuccess = false;
        if (null != code && "200".equals(code)) {
            isSuccess = true;
        }
        return isSuccess;
    }
}
