package com.example.spider;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.navigation.NavigationView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;


public class KeepActivity extends AppCompatActivity  {

    private WaveView waveView3;
    private NavigationView nav;
    private SharedPreferences sp;
    private TextView text11;
    private TextView text12;
    private Button button5;
    private TextView textView;
    private TextView textView5;
    private int breakcard;
    private int experience;
    private int exMax;
    private int level;

    protected SharedPreferences getinfo;
    private TextView head_name;
    private TextView head_sign;
    private ImageView head;

    private CircularProgressView circularProgressView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.keep_layout);
        StatusBarUtil.setStatusBarLayoutStyle(this, true);//状态栏透色
        button5=findViewById(R.id.button5);
        textView=findViewById(R.id.textView);
        textView5=findViewById(R.id.textView5);
        textView5.setOnClickListener(l);
        textView.setOnClickListener(l);
        button5.setOnClickListener(l);

        TextView test1 = findViewById(R.id.textView8);
        TextView test2 = findViewById(R.id.textView14);

        findViewById(R.id.imageView3).setOnClickListener(l);
        findViewById(R.id.imageView6).setOnClickListener(l);

        text11 =findViewById(R.id.textView11);
        text12=findViewById(R.id.textView12);
        Typeface typeface = Typeface.createFromAsset(getAssets(), "fonts/writeen.ttf");
        test1.setTypeface(typeface);
        test2.setTypeface(typeface);
        waveView3=findViewById(R.id.wave_view);



        //将储存在SharedPreferences中的信息显示出来
        getinfo= getSharedPreferences("data",Context.MODE_PRIVATE);
        String headpic=getinfo.getString("HeadPortrait","");
        Bitmap bitmap=null;
        LayoutInflater inflater = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        LinearLayout view= (LinearLayout) inflater.inflate(R.layout.head,null);
        head= (ImageView)view.findViewById(R.id.person);
        System.out.print(headpic);
        if (headpic!="" & headpic!=null)
        {
            Log.v("headpic","not kong and not null");
            byte[] bytes = Base64.decode(headpic.getBytes(), 1);
            //  byte[] bytes =headPic.getBytes();
            bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
            head.setImageBitmap(bitmap);
        }

        head_name=(TextView)view.findViewById(R.id.head_name);
        head_sign=(TextView)view.findViewById(R.id.head_sign);
        head_name.setText(getinfo.getString("name",""));
        head_sign.setText(getinfo.getString("sign",""));





        ListDataSave dataSave= new ListDataSave(getApplicationContext(), "data");
        List<BookBean> bookBeans=dataSave.getDataList("list");
        nav=findViewById(R.id.nav);
        nav.setItemIconTintList(null);//显示图标
        if(bookBeans!=null){
            experience=bookBeans.size();//经验值
        }else{
            experience=0;
        }


        sp=getSharedPreferences("data",MODE_PRIVATE);
        Calendar c = Calendar.getInstance(TimeZone.getTimeZone("GMT+08:00"));
        SimpleDateFormat s=new SimpleDateFormat("yyyy-MM-dd");

        String Now = s.format(c.getTime());
        SharedPreferences.Editor editor=sp.edit();
        //格式化时间格式
        System.out.println(Now);

        //点击事件
        String dateMark=sp.getString("date","f");
        breakcard=sp.getInt("break",-1);

        exMax=sp.getInt("exMax",-1);
        System.out.println(exMax);
        level=sp.getInt("level",-1);
        if(level==-1)
        {
            editor.putInt("level",1);
            editor.commit();
            level=1;
        }
        if(exMax==-1)
        {
            editor.putInt("exMax",2);
            editor.commit();
            exMax=2;
        }
        if(exMax<=experience)
        {
            exMax=exMax*2;
            editor.putInt("exMax",exMax);
            editor.commit();
            level++;
            editor.putInt("level",level);
            editor.commit();
        }
        TextView textView_LV=findViewById(R.id.textView2);
        textView_LV.setText("Lv"+" "+level);
        TextView textView_Read=findViewById(R.id.textView10);
        textView_Read.setText(""+experience);


        if(dateMark.equals("f"))
        {
            editor.putString("date",Now);
            editor.commit();
            breakcard=1;
            editor.putInt("break",breakcard);
            editor.commit();
        }
        else
        {
            if(!dateMark.equals(Now))
            {
                breakcard++;
                editor.putInt("break",breakcard);
                editor.commit();
                editor.putString("date",Now);

                editor.commit();
            }
        }
        text11.setText(breakcard+"");


        nav.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId())
                {
                    case R.id.menu_pi:
                        Intent pi=new Intent(KeepActivity.this,Information.class);
                        startActivity(pi);
                        break;
                        default:
                            break;
                }
                return false;
            }
        });
        findViewById(R.id.button6).setOnClickListener(l);
        circularProgressView=findViewById(R.id.circularProgressView);
        final FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(-2,-2);
        lp.gravity = Gravity.BOTTOM|Gravity.CENTER;
        waveView3.setOnWaveAnimationListener(new WaveView.OnWaveAnimationListener() {
            @Override
            public void OnWaveAnimation(float y) {
                lp.setMargins(0,0,0,(int)y+2);

            }
        });
        /*ListDataSave dataSave= new ListDataSave(getApplicationContext(), "data");
        List<BookBean> bookBeans=dataSave.getDataList("list");*/
        if (bookBeans!=null&&bookBeans.size()>0)
        {
            BookBean bookBean=bookBeans.get(bookBeans.size()-1);
            TextView bn=findViewById(R.id.textView8);
            bn.setText(bookBean.getBookName());
            TextView keep_pro=findViewById(R.id.textView9);
            keep_pro.setText("进度:"+bookBean.getChapter().size()+"/"+bookBean.getTotal_chapter());
           text12.setText(bookBeans.size()+"");

            ProgressBar progressBar=findViewById(R.id.progress_bar_healthy);
            progressBar.setMax(bookBean.getTotal_chapter());
            progressBar.setProgress(bookBean.getChapter().size());
        }
        circularProgressView.setProgress((experience*100/exMax));
        System.out.println(exMax);
        System.out.println((experience/exMax)*100);


    }

    @Override
    protected void onRestart() {
        ListDataSave dataSave= new ListDataSave(getApplicationContext(), "data");
        List<BookBean> bookBeans=dataSave.getDataList("list");
        SharedPreferences.Editor editor=sp.edit();
        if(bookBeans!=null)
        experience=bookBeans.size();//经验值
        else
            experience=0;//经验值
        exMax=sp.getInt("exMax",-1);
        System.out.println(exMax);
        level=sp.getInt("level",-1);
        if(level==-1)
        {
            editor.putInt("level",1);
            editor.commit();
            level=1;
        }
        if(exMax==-1)
        {
            editor.putInt("exMax",1);
            editor.commit();
            exMax=1;
        }
        if(exMax<=experience)
        {
            exMax=exMax*2;
            editor.putInt("exMax",exMax);
            editor.commit();
            level++;
            editor.putInt("level",level);
            editor.commit();
        }
        TextView textView_LV=findViewById(R.id.textView2);
        textView_LV.setText("Lv"+" "+level);
        TextView textView_Read=findViewById(R.id.textView10);
        textView_Read.setText(""+experience);
        circularProgressView.setProgress((experience*100/exMax));
        System.out.println(exMax);
        System.out.println(experience);
        if (bookBeans!=null&&bookBeans.size()>0)
        {
            BookBean bookBean=bookBeans.get(bookBeans.size()-1);
            TextView bn=findViewById(R.id.textView8);
            bn.setText(bookBean.getBookName());
            TextView keep_pro=findViewById(R.id.textView9);
            keep_pro.setText("进度:"+bookBean.getChapter().size()+"/"+bookBean.getTotal_chapter());
            text12.setText(bookBeans.size()+"");

            ProgressBar progressBar=findViewById(R.id.progress_bar_healthy);
            progressBar.setMax(bookBean.getTotal_chapter());
            progressBar.setProgress(bookBean.getChapter().size());
        }

        //将储存在SharedPreferences中的头像显示出来
        SharedPreferences getinfo= getSharedPreferences("data",Context.MODE_PRIVATE);
        String headpic=getinfo.getString("HeadPortrait","");
        Bitmap bitmap=null;
        //LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        //LinearLayout view= (LinearLayout) inflater.inflate(R.layout.head,null);
        head= (ImageView)findViewById(R.id.person);
        if (headpic!="")
        {
            byte[] bytes = Base64.decode(headpic.getBytes(), 1);
            //  byte[] bytes =headPic.getBytes();
            bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
            head.setImageBitmap(bitmap);
        }

        head_name=(TextView)findViewById(R.id.head_name);
        head_sign=(TextView)findViewById(R.id.head_sign);
        head_name.setText(getinfo.getString("name",""));
        head_sign.setText(getinfo.getString("sign",""));


        super.onRestart();
    }

    private  View.OnClickListener l=new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        switch(v.getId())
        {
            case R.id.button5:
            case R.id.textView5:
            case R.id.imageView6:
                Intent intent2=new Intent(KeepActivity.this,historyActivity.class);
                startActivity(intent2);
                break;
            case R.id.button6:
                Intent intent1=new Intent(KeepActivity.this,MainActivity.class);
                startActivity(intent1);
                break;

            case R.id.textView:
            case R.id.imageView3:
                ListDataSave dataSave= new ListDataSave(getApplicationContext(), "data");
                List<BookBean> bookBeans=dataSave.getDataList("list");
                if (bookBeans!=null&&bookBeans.size()>0)
                {
                    BookBean bookBean=bookBeans.get(bookBeans.size()-1);
                    Bundle bundle=new Bundle();
                    bundle.putCharSequence("name","《"+bookBean.getBookName()+"》");
                    bundle.putCharSequence("url",bookBean.getBookUrl());
                    //当点击时获取对应的url并将该url传入Content，再跳转到Content界面
                    Intent intent3=new Intent(KeepActivity.this, Chapter.class);
                    intent3.putExtras(bundle);
                    startActivity(intent3);

                }else{
                Toast.makeText(KeepActivity.this,"无阅读记录",Toast.LENGTH_SHORT).show();
            }

                break;


        }
    }
};



}
