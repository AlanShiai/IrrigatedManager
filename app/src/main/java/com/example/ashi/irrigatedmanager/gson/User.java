package com.example.ashi.irrigatedmanager.gson;

/**
 * Created by ashi on 7/11/2018.
 */

public class User {
    // http://www.boze-tech.com/zfh_manager/a/app/login/loginCheck?loginName=admin&passwd=123
    // {"id":"1","officeId":"8eff2c16d5cf45fca84ac984190b0890","phone":"8675","officeName":
    // "漳滏河管理处","name":"系统管理员","code":"200","msg":"登陆成功","loginName":"admin"}

    public String id;
    public String officeId;
    public String phone;
    public String officeName;
    public String name;
    public String code;
    public String msg;
    public String loginName;

    public User (String id) {
        this.id = id;
    }

    public boolean isLoginSuccess() {
        boolean isSuccess = false;
        if (null != code && "200".equals(code)) {
            isSuccess = true;
        }
        return isSuccess;
    }

}
