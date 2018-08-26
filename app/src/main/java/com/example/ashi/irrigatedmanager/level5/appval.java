package com.example.ashi.irrigatedmanager.level5;

/**
 * Created by AShi on 7/22/2018.
 */

public class Appval {

    // {"taskId":"fda14f44ec3f46b3b1f29ada19fcae15","title":"民有三干渠大名段","processInstanceId":"93acd047627d45edad2102a7cf00cc0e",
    // "status":"todo","businessKey":"pro_patrol_result_deal:1ec1a25d583a42568e09b18351cc41f2","taskDefKey":"audit",
    // "dealUser":"xizha","executionId":"93acd047627d45edad2102a7cf00cc0e","current":"所长查看","dealDate":"2018-07-05 10:24:22",
    // "taskName":"巡检异常处理","procDefId":"patrol_result_deal_new:4:a1e5c2f5962a4db1820ca13aea26f57a"},

    public String taskId;
    public String title;
    public String processInstanceId;
    public String status;
    public String businessKey;
    public String taskDefKey;
    public String dealUser;
    public String executionId;
    public String current;
    public String dealDate;
    public String taskName;
    public String procDefId;

    public Appval(String name) {
        this.title = name;
    }

}
