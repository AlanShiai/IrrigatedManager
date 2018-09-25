package com.example.ashi.irrigatedmanager;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.provider.Settings;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.ashi.irrigatedmanager.util.Global;

public class Logout extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logout);

        TextView loginName = (TextView) findViewById(R.id.login_name);
        loginName.setText(Global.user.name);

        TextView officeName = (TextView) findViewById(R.id.officeName);
        officeName.setText(Global.user.officeName);

        findViewById(R.id.action_to_project_info).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Logout.this, Level2_3_projectInfo.class);
                startActivity(intent);
                finish();
            }
        });

        addListernerForBottomToolbar();

        findViewById(R.id.logout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog builder = new Dialog(Logout.this, R.style.update_dialog);
                View view = View.inflate(Logout.this, R.layout.dialog_info, null);

                final TextView info = (TextView) view.findViewById(R.id.info);
                info.setText("确认退出登录吗？");

                view.findViewById(R.id.ok).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        builder.dismiss();
                        Intent intent = new Intent(Logout.this, LoginActivity.class);
                        startActivity(intent);
                        finish();
                    }
                });
                view.findViewById(R.id.cancel).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        builder.dismiss();
                    }
                });

                builder.setContentView(view);
                builder.show();
            }
        });
    }

    private void addListernerForBottomToolbar() {
        findViewById(R.id.overview).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Logout.this, Level2_1_irrigateOverview.class);
                startActivity(intent);
                finish();
            }
        });
        findViewById(R.id.overview_inspect).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Logout.this, Level2_2_projectInspection2.class);
                startActivity(intent);
                finish();
            }
        });
        findViewById(R.id.monitor).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Logout.this, Level2_4_realtimeMonitor2.class);
                startActivity(intent);
                finish();
            }
        });
        findViewById(R.id.overview_appval).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Logout.this, Level2_5_appvalProcess.class);
                startActivity(intent);
                finish();
            }
        });
//        findViewById(R.id.project_info).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(Level2_5_appvalProcess.this, Level2_3_projectInfo.class);
//                startActivity(intent);
//                finish();
//            }
//        });
    }

}
