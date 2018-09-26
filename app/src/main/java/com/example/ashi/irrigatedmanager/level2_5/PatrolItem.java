package com.example.ashi.irrigatedmanager.level2_5;

import com.example.ashi.irrigatedmanager.util.CreateDate;

/**
 * Created by ashi on 8/29/2018.
 */

public class PatrolItem {
    // {"id":"bd764e5557844e0fb31dfc14fdb92196","sort":2,"contents":"基金会接受","remarks":"没有什么可描述的就开始倒海翻江",
    // "subPosition":"英国","createDate":{"nanos":0,"time":1528683993000,"minutes":26,"seconds":33,"hours":10,"month":5,
    // "year":118,"timezoneOffset":-480,"day":1,"date":11},"type":"channel","mainPosition":"美国","delFlag":"0"}
    public String id;
    public String sort;
    public String contents;
    public String remarks;
    public String subPosition;
    public CreateDate createDate;
    public String type;
    public String mainPosition;
    public String delFlag;

    public PatrolItem(String contents) {
        this.contents = contents;
    }
}
