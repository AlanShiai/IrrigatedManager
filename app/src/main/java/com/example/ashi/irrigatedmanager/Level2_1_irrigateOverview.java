package com.example.ashi.irrigatedmanager;

import android.app.Dialog;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.annotation.DrawableRes;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewSwitcher;

import com.example.ashi.irrigatedmanager.util.AppManager;
import com.example.ashi.irrigatedmanager.util.Const;

import java.io.IOException;

public class Level2_1_irrigateOverview extends AppCompatActivity implements ViewSwitcher.ViewFactory, View.OnTouchListener {

    private ImageSwitcher mImageSwitcher;

    private int[] imgIds;

    private int currentPosition = 0;

    private  float downX;

    private LinearLayout linearLayout;

    private ImageView[] tips;

//    private  int Iindex=0;
    private  int DuringTime = 5000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level2_1_irrigate_overview);

        imgIds = new int[] {
//                R.drawable.bing1,
                R.drawable.bing2,
                R.drawable.bing3,
                R.drawable.bing4,
//                R.drawable.bing5,
                R.drawable.bing6,
                R.drawable.bing7,
                R.drawable.bing8,
                R.drawable.bing9,
                R.drawable.bing10,
        };

        mImageSwitcher = (ImageSwitcher) findViewById(R.id.image_switcher);
        mImageSwitcher.setFactory(this);
        mImageSwitcher.setOnTouchListener(this);
        mImageSwitcher.postDelayed(new Runnable() {
            public void run() {
                if(currentPosition == imgIds.length - 1){
                    currentPosition = 0;
                }else{
                    currentPosition++;
                }
                setImageBackground(currentPosition);
                mImageSwitcher.setImageResource(imgIds[currentPosition]);
                mImageSwitcher.postDelayed(this, DuringTime);
            }
        }, DuringTime);

        linearLayout = (LinearLayout) findViewById(R.id.viewGroup);

        tips = new ImageView[imgIds.length];
        for (int i = 0; i < imgIds.length; i++) {
            ImageView mImageView = new ImageView(this);
            tips[i] = mImageView;
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(new ViewGroup.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT));
            layoutParams.rightMargin = 3;
            layoutParams.leftMargin = 3;

            mImageView.setBackgroundResource(R.drawable.page_indicator_unfocused);
            linearLayout.addView(mImageView, layoutParams);
        }

        mImageSwitcher.setImageResource(imgIds[currentPosition]);
        setImageBackground(currentPosition);

        TextView content = (TextView) findViewById(R.id.level_2_1_content);
        content.setText(Const.LEVEL_2_CONTENT);
        content.setMovementMethod(ScrollingMovementMethod.getInstance());

        addListernerForBottomToolbar();

        AppManager.getAppManager().addActivity(this);
    }

    private void setImageBackground(int selectItems) {
        for (int i = 0; i < tips.length; i++ ) {
            if ( i == selectItems ) {
                tips[i].setBackgroundResource(R.drawable.page_indicator_focused);
            } else {
                tips[i].setBackgroundResource(R.drawable.page_indicator_unfocused);
            }
        }
    }

    public void onBackPressed() {
        final Dialog builder = new Dialog(this, R.style.update_dialog);
        View view = View.inflate(Level2_1_irrigateOverview.this, R.layout.dialog_info, null);

        final TextView info = (TextView) view.findViewById(R.id.info);
        info.setText("确认退出掌上灌区吗？");

        view.findViewById(R.id.ok).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppManager.getAppManager().AppExit(Level2_1_irrigateOverview.this);
                builder.dismiss();
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

    private void addListernerForBottomToolbar() {
//        findViewById(R.id.overview).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(Level2_1_irrigateOverview.this, Level2_2_projectInspection2.class);
//                startActivity(intent);
//                finish();
//            }
//        });
        findViewById(R.id.overview_inspect).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Level2_1_irrigateOverview.this, Level2_2_projectInspection2.class);
                startActivity(intent);
            }
        });
        findViewById(R.id.monitor).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Level2_1_irrigateOverview.this, Level2_4_realtimeMonitor2.class);
                startActivity(intent);
            }
        });
        findViewById(R.id.overview_appval).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Level2_1_irrigateOverview.this, Level2_5_appvalProcess.class);
                startActivity(intent);
            }
        });
        findViewById(R.id.project_info).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Level2_1_irrigateOverview.this, Logout.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN: {
                downX = event.getX();
                break;
            }
            case MotionEvent.ACTION_UP: {
                float lastX = event.getX();
                //抬起的时候的X坐标大于按下的时候就显示上一张图片
                if(lastX > downX){
                    if(currentPosition > 0){
                        //设置动画，这里的动画比较简单，不明白的去网上看看相关内容
//                        mImageSwitcher.setInAnimation(AnimationUtils.loadAnimation(getApplication(), R.anim.left_in));
//                        mImageSwitcher.setOutAnimation(AnimationUtils.loadAnimation(getApplication(), R.anim.right_out));
                        currentPosition --;
                        mImageSwitcher.setImageResource(imgIds[currentPosition % imgIds.length]);
                        setImageBackground(currentPosition);
                    }else{
                        Toast.makeText(getApplication(), "已经是第一张", Toast.LENGTH_SHORT).show();
                    }
                }

                if(lastX < downX){
                    if(currentPosition < imgIds.length - 1){
//                        mImageSwitcher.setInAnimation(AnimationUtils.loadAnimation(getApplication(), R.anim.right_in));
//                        mImageSwitcher.setOutAnimation(AnimationUtils.loadAnimation(getApplication(), R.anim.lift_out));
                        currentPosition ++ ;
                        mImageSwitcher.setImageResource(imgIds[currentPosition]);
                        setImageBackground(currentPosition);
                    }else{
                        Toast.makeText(getApplication(), "到了最后一张", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            break;
        }
        return true;
    }

    @Override
    public View makeView() {
        final ImageView i = new ImageView(this);
        i.setBackgroundColor(0xFF000000);
        i.setScaleType(ImageView.ScaleType.CENTER_CROP);
        i.setLayoutParams(new ImageSwitcher.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.FILL_PARENT));

        return i;
    }

    private String getResourcesUri(@DrawableRes int id) {
        Resources resources = getResources();
        Uri selectedVideoUri = Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE + "://" +
                resources.getResourcePackageName(id) + "/" +
                resources.getResourceTypeName(id) + "/" +
                resources.getResourceEntryName(id));
        String filePath;
        String[] filePathColumn = {MediaStore.MediaColumns.DATA};
        Bitmap bitmap = null;
        try {
            bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedVideoUri);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return "";
    }
}
