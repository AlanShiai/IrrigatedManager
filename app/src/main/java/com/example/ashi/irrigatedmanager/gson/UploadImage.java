package com.example.ashi.irrigatedmanager.gson;

import java.util.List;

/**
 * Created by ashi on 9/11/2018.
 */

public class UploadImage {
    // {"status":"1","paths":[{"path":"/home/zfh/uploads/zfh_manager/userfiles/patrolImage/output_image.jpg"},]}
    public String status = "0";
    public List<UploadImagePath> paths;
}
