package com.example.spider;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class Information extends AppCompatActivity {
    private static final int IMAGE = 1;
    private Context context;
    private TextView change;
    private ImageView back;
    private ImageView info_touxiang;
    private EditText info_sign;
    private EditText info_name;
    private EditText info_sex;
    private EditText info_birthday;
    private EditText info_favourite;
    private EditText info_job;
    private EditText info_company;
    private EditText info_school;
    private EditText info_location;
    private EditText info_beizhu;

    private ImageView head;
    private TextView head_name;
    private TextView head_sign;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_information);

        StatusBarUtil.setStatusBarLayoutStyle(this,true);//状态栏透色

        change=findViewById(R.id.change_button);
        change.setClickable(true);
        back=findViewById(R.id.back);
        info_touxiang=findViewById(R.id.info_touxiang);
        info_name=findViewById(R.id.info_name);
        info_sign=findViewById(R.id.info_sign);
        info_sex=findViewById(R.id.info_sex);
        info_birthday=findViewById(R.id.info_birthday);

        info_favourite=findViewById(R.id.info_favourite);
        info_job=findViewById(R.id.info_job);
        info_company=findViewById(R.id.info_company);
        info_school=findViewById(R.id.info_school);
        info_location=findViewById(R.id.info_location);
        info_beizhu=findViewById(R.id.info_beizhu);

        info_name.setEnabled(false);
        info_sign.setEnabled(false);
        info_sex.setEnabled(false);
        info_birthday.setEnabled(false);
        info_favourite.setEnabled(false);
        info_job.setEnabled(false);
        info_company.setEnabled(false);
        info_school.setEnabled(false);
        info_location.setEnabled(false);
        info_beizhu.setEnabled(false);

        //获取本地个人信息
        SharedPreferences getinfo= getSharedPreferences("data",Context.MODE_PRIVATE);
        info_name.setText(getinfo.getString("name",""));
        info_sign.setText(getinfo.getString("sign",""));
        info_sex.setText(getinfo.getString("sex",""));
        info_birthday.setText(getinfo.getString("birthday",""));
        info_favourite.setText(getinfo.getString("favourite",""));
        info_job.setText(getinfo.getString("job",""));
        info_company.setText(getinfo.getString("company",""));
        info_school.setText(getinfo.getString("school",""));
        info_location.setText(getinfo.getString("location",""));
        info_beizhu.setText(getinfo.getString("beizhu",""));

        //将储存在SharedPreferences中的头像显示出来
        String headpic=getinfo.getString("HeadPortrait","");
        Bitmap bitmap=null;
        if (headpic!="")
        {
            byte[] bytes = Base64.decode(headpic.getBytes(), 1);
            //  byte[] bytes =headPic.getBytes();
            bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
            ((ImageView)findViewById(R.id.info_touxiang)).setImageBitmap(bitmap);
        }



        back.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public  void onClick(View v)
            {
                change.performClick();
                finish();
            }
        });
        info_touxiang.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public  void onClick(View v)
            {
                Intent intent = new Intent(Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, IMAGE);

            }
        });
        change.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(change.getText().toString()=="更改")
                        {
                            change.setText("保存");
                            info_name.setEnabled(true);
                            info_sign.setEnabled(true);
                            info_sex.setEnabled(true);
                            info_birthday.setEnabled(true);
                            info_favourite.setEnabled(true);
                            info_job.setEnabled(true);
                            info_company.setEnabled(true);
                            info_school.setEnabled(true);
                            info_location.setEnabled(true);
                            info_beizhu.setEnabled(true);
                        }
                        else
                        {
                            //保存个人信息到本地
                            SharedPreferences info= getSharedPreferences("data",Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor=info.edit();
                            editor.putString("name",info_name.getText().toString());
                            editor.putString("sign",info_sign.getText().toString());
                            editor.putString("sex",info_sex.getText().toString());
                            editor.putString("birthday",info_birthday.getText().toString());
                            editor.putString("favourite",info_favourite.getText().toString());
                            editor.putString("job",info_job.getText().toString());
                            editor.putString("company",info_company.getText().toString());
                            editor.putString("school",info_school.getText().toString());
                            editor.putString("location",info_location.getText().toString());
                            editor.putString("beizhu",info_beizhu.getText().toString());
                            editor.commit();
                            change.setText("更改");
                            info_name.setEnabled(false);
                            info_sign.setEnabled(false);
                            info_sex.setEnabled(false);
                            info_birthday.setEnabled(false);
                            info_favourite.setEnabled(false);
                            info_job.setEnabled(false);
                            info_company.setEnabled(false);
                            info_school.setEnabled(false);
                            info_location.setEnabled(false);
                            info_beizhu.setEnabled(false);
                        }

                    }
                }
        );

    }

    @Override
    protected void onDestroy()
    {


        super.onDestroy();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //获取图片路径
        if (requestCode == IMAGE && resultCode == Activity.RESULT_OK && data != null) {
            Uri selectedImage = data.getData();
            String[] filePathColumns = {MediaStore.Images.Media.DATA};
            Cursor c = getContentResolver().query(selectedImage, filePathColumns, null, null, null);
            c.moveToFirst();
            int columnIndex = c.getColumnIndex(filePathColumns[0]);
            String imagePath = c.getString(columnIndex);
            SaveImage2SP(imagePath);
            c.close();
        }
    }

    private void SaveImage2SP(String imagePath)
    {
        Bitmap bm = BitmapFactory.decodeFile(imagePath);

        SharedPreferences info= getSharedPreferences("data",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=info.edit();
        ByteArrayOutputStream byteArrayOutputStream=new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.PNG, 50, byteArrayOutputStream);
        String headPicBase64=new String(Base64.encodeToString(byteArrayOutputStream.toByteArray(),Base64.DEFAULT));
        editor.putString("HeadPortrait",headPicBase64);
        editor.commit();

        ((ImageView)findViewById(R.id.info_touxiang)).setImageBitmap(bm);
    }

    public static boolean saveImageToGallery(Context context, Bitmap bitmap, String fileName) {
        // 保存图片至指定路径
        String storePath = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "qrcode";
        File appDir = new File(storePath);
        if (!appDir.exists()) {
            appDir.mkdir();
        }
        File file = new File(appDir, fileName);
        try {
            FileOutputStream fos = new FileOutputStream(file);
            //通过io流的方式来压缩保存图片(80代表压缩20%)
            boolean isSuccess = bitmap.compress(Bitmap.CompressFormat.JPEG, 80, fos);
            fos.flush();
            fos.close();

            //发送广播通知系统图库刷新数据
            Uri uri = Uri.fromFile(file);
            context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, uri));
            if (isSuccess) {
                return true;
            } else {
                return false;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

}
