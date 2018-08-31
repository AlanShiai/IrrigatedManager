package com.example.ashi.irrigatedmanager.gson;

/**
 * Created by ashi on 8/31/2018.
 */

public class PatrolNote {
    // {"id":"30136878e084460cad141af485addea2","result":"异常","goalId":"4339bf3b0da04f92a16b688cd02f2a14",
    // "goalName":"滏阳河磁县段","type":"渠道","updateDate":"2018-05-18 10:46:43 "}
    public String id;
    public String result = "正常";
    public String goalId = "4339bf3b0da04f92a16b688cd02f2a14";
    public String goalName = "滏阳河磁县段";
    public String type;
    public String updateDate = "2018-05-18 10:46:43 ";

    public PatrolNote() {

    }
    public PatrolNote(String result) {
        this.result = result;
    }
}
