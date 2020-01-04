package com.example.spider;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.example.spider.NewTextView.fzktjtView;
import com.example.spider.NewTextView.hksnztView;
import com.example.spider.NewTextView.kaitiView;
import com.example.spider.NewTextView.yaheiView;
import com.github.ybq.android.spinkit.sprite.Sprite;
import com.github.ybq.android.spinkit.style.DoubleBounce;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class Content extends AppCompatActivity {
private String url;
private String Chapter_title;
private String bookname;
private String bookurl;
private int total_chapter;

    private ProgressBar progressBar;
    private Handler mHandler;
    private String findall;
    private TextView textView;
    private ScrollView page;
    private FloatingActionButton bt;
private EditText current;
private TextView max;
private Button jump;

    private int total_length;
    private int current_length=0;






    private int colors[]={R.color.background1,R.color.background2,R.color.background3,R.color.background4};
    private String text_Style[]={"fonts/hksnzt.ttf","fonts/kaiti.ttf","fonts/fzktjt.ttf","fonts/yahei.ttf"};

    private SeekBar seekBar1;

    private ImageView background_1;
    private ImageView background_2;
    private ImageView background_3;
    private ImageView background_4;



    private hksnztView text_style1;
    private kaitiView text_style2;
    private fzktjtView text_style3;
    private yaheiView text_style4;

private int click=0;
    private long downTime=0;

    private SharedPreferences sp;
    private Context mContext;
    private ListDataSave dataSave;




    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content);

        StatusBarUtil.setStatusBarLayoutStyle(this,true);//状态栏透色

        mContext = getApplicationContext();
        dataSave = new ListDataSave(mContext, "data");

        textView=findViewById(R.id.show);

        page=findViewById(R.id.page);

bt=findViewById(R.id.bt);

        seekBar1=findViewById(R.id.seekbar1);
        background_1=findViewById(R.id.background_1);
        background_2=findViewById(R.id.background_2);
        background_3=findViewById(R.id.background_3);
        background_4=findViewById(R.id.background_4);

        background_1.setOnClickListener(background_color);
        background_2.setOnClickListener(background_color);
        background_3.setOnClickListener(background_color);
        background_4.setOnClickListener(background_color);

        text_style1=findViewById(R.id.text_style1);
        text_style2=findViewById(R.id.text_style2);
        text_style3=findViewById(R.id.text_style3);
        text_style4=findViewById(R.id.text_style4);


        text_style1.setOnClickListener(TextStyle);
        text_style2.setOnClickListener(TextStyle);
        text_style3.setOnClickListener(TextStyle);
        text_style4.setOnClickListener(TextStyle);

        current=findViewById(R.id.current);
        max=findViewById(R.id.max);
        jump=findViewById(R.id.jump);


        //从asset 读取字体
        AssetManager mgr = getAssets();
        //根据路径得到Typeface
        Typeface tf = Typeface.createFromAsset(mgr, "fonts/hksnzt.ttf");//华康少女字体

        //底端的窗口
        final BottomSheetBehavior bottomSheetBehavior=BottomSheetBehavior.from(findViewById(R.id.design_bottom_sheet1));
        //设置默认先隐藏
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);










page.setOnScrollChangeListener(new View.OnScrollChangeListener() {                          //获取当前进度
    @Override
    public void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
        if (bottomSheetBehavior.getState() != BottomSheetBehavior.STATE_HIDDEN) {
            bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
        }

        System.out.println("scrollX    "+scrollX+"    scrollY    "+scrollY+"   oldScrollX    "+oldScrollX+"   oldScrollY   "+oldScrollY);

        current_length=scrollY/textView.getLineHeight();
        System.out.println("current_length"+current_length);
        System.out.println("total_length"+total_length);
        System.out.println("page.getHeight()/textView.getLineHeight()"+page.getHeight()/textView.getLineHeight());




    }
});


        sp=getSharedPreferences("data",MODE_PRIVATE);

//阅读背景
        int color=sp.getInt("background",-1);
        if(color==-1) {
            textView.setBackgroundColor(getResources().getColor(R.color.background1));

        }
        else{
            textView.setBackgroundColor(getResources().getColor(colors[color]));

        }
        int text_style=sp.getInt("text_style",-1);
        if(text_style==-1){
            tf = Typeface.createFromAsset(mgr, "fonts/kaiti.ttf");//楷体
            textView.setTypeface(tf);
        }else{
            tf = Typeface.createFromAsset(mgr, text_Style[text_style]);//华康少女字体
            textView.setTypeface(tf);
        }

//字体大小
        int size=sp.getInt( "text_Size",-1);
        if(color==-1) {
            seekBar1.setProgress(0);
            textView.setTextSize(20);
        }
        else{

            seekBar1.setProgress(size-20);
            textView.setTextSize(size);
        }



//加载进度条
        progressBar = (ProgressBar)findViewById(R.id.spin_kit);
        Sprite doubleBounce= new DoubleBounce();
        progressBar.setIndeterminateDrawable(doubleBounce);
        new Thread(new Runnable(){
            @Override
            public void run() {
                while(true){
                    Intent intent=getIntent();
                    Bundle bundle=intent.getExtras();
                    bookname=bundle.getString("book_title");
                    bookurl=bundle.getString("book_url");
                    total_chapter=bundle.getInt("total_chapter");
                    url=bundle.getString("url");
                    Chapter_title=bundle.getString("chapter_title");
                    System.out.println("url  "+url);
                    System.out.println("chapter_title"+Chapter_title);
                    //通过Chapter传入的url进行对该网页的进行爬虫处理
                    findall=getArticleListFromUrl(url);
                    Message msg=new Message();

                    if(findall!=null&&findall.length()!=0){//findall!=null&&findall.length()!=0
                        msg.what=0x111;
                        mHandler.sendMessage(msg);
                        break;
                    }
                }
            }
        }).start();






        mHandler=new Handler(){                                         //加载内容
            @Override
            public void handleMessage(@NonNull Message msg) {
                super.handleMessage(msg);
                if(msg.what==0x111){
                    textView.setText(findall);
                    System.out.println("内容:"+findall);
                    progressBar.setVisibility(View.GONE);
                    //开始时获取总行数
                    new Handler().postDelayed(new Runnable() {//显示总页数
                        @Override
                        public void run() {
                            System.out.println("总行数"+textView.getLineCount());
                            total_length=textView.getLineCount();
                            //bar_progress.setMax(total_length);
                            max.setText("/"+total_length);
                            //确定当前行数位置
                            if (current_length + page.getHeight() / textView.getLineHeight()+3>total_length) {
                                current_length=total_length;
                            }
                            current.setText(current_length+"");
                            //current.setFocusable(false);
                            //bar_progress.setProgress(current_length);


                        }
                    },200);



                }
            }
        };









//设置与保存字体大小
seekBar1.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                //获取编辑器
                SharedPreferences.Editor editor=sp.edit();
                //保存字体大小
                textView.setTextSize(20+i);
                editor.putInt("text_Size",20+i);
                editor.commit();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

bt.setOnTouchListener(new View.OnTouchListener() {
            int lastX, lastY; // 记录移动的最后的位置


            public boolean onTouch(View v, MotionEvent event) {
                // 获取Action
                int ea = event.getAction();

                int screenWidth = findViewById(R.id.parent).getWidth();
                int  screenHeight = findViewById(R.id.parent).getHeight();




                switch (ea) {
                    case MotionEvent.ACTION_DOWN: // 按下
                        lastX = (int) event.getRawX();
                        lastY = (int) event.getRawY();
                        downTime=System.currentTimeMillis();
                        break;
                    case MotionEvent.ACTION_MOVE: // 移动
                        // 移动中动态设置位置
                        int dx = (int) event.getRawX() - lastX;
                        int dy = (int) event.getRawY() - lastY;
                        int left = v.getLeft() + dx;
                        int top = v.getTop() + dy;
                        int right = v.getRight() + dx;
                        int bottom = v.getBottom() + dy;
                        if (left < 0) {
                            left = 0;
                            right = left + v.getWidth();
                        }
                        if (right > screenWidth) {
                            right = screenWidth;
                            left = right - v.getWidth();
                        }

                        if (top < 0) {
                            top = 0;
                            bottom = top + v.getHeight();
                        }
                        if (bottomSheetBehavior.getState() == BottomSheetBehavior.STATE_HIDDEN) {
                            if (bottom > screenHeight) {
                                bottom = screenHeight;
                                top = bottom - v.getHeight();
                            }
                        }else{
                            int height=findViewById(R.id.parent).getHeight()-findViewById(R.id.design_bottom_sheet1).getHeight();
                            if (bottom > height) {
                                bottom = height;
                                top = bottom - v.getHeight();
                            }
                        }

                        v.layout(left, top, right, bottom);

                        // 将当前的位置再次设置
                        lastX = (int) event.getRawX();
                        lastY = (int) event.getRawY();
                        break;
                    case MotionEvent.ACTION_UP: // 抬起
                         //向四周吸附

                        //判断是否为点击
                        if(System.currentTimeMillis()-downTime<1000){

                            click=1;
                        }

					int dx1 = (int) event.getRawX() - lastX;
					int dy1 = (int) event.getRawY() - lastY;
					int left1 = v.getLeft() + dx1;
					int top1 = v.getTop() + dy1;

					int bottom1 = v.getBottom() + dy1;
					if (left1 < (screenWidth / 2)) {
                        v.layout(0, top1, 0+v.getWidth(), bottom1);
					} else{
                        v.layout(screenWidth-v.getWidth(), top1, screenWidth, bottom1);
                    }


					break;
                }
                return false;
            }
        });
jump.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        String str=current.getText().toString();
        Pattern pattern = Pattern.compile("[0-9]*");
        if(pattern.matcher(str).matches()){
            int a=Integer.parseInt(str);
            page.scrollTo(0,a*textView.getLineHeight());
            hintKbOne();
        }else{
            Toast.makeText(Content.this,"请输入正整数",Toast.LENGTH_SHORT).show();
        }



    }
});

//每次低端的窗口时，设置总行数
        findViewById(R.id.bt).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(click==1){
                //根据状态不同显示隐藏
                if (bottomSheetBehavior.getState() == BottomSheetBehavior.STATE_HIDDEN) {
                    bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);//显示


                    new Handler().postDelayed(new Runnable() {//显示总页数
                        @Override
                        public void run() {
                            System.out.println("总行数"+textView.getLineCount());
                             total_length=textView.getLineCount();
                            max.setText("/"+total_length);
current.setText(current_length+"");

                        }
                    },200);

                }else {
                    bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
                }
                int height=findViewById(R.id.parent).getHeight()-findViewById(R.id.design_bottom_sheet1).getHeight();
                    System.out.println("height"+height);
                    System.out.println("findViewById(R.id.design_bottom_sheet1).getHeight()"+findViewById(R.id.design_bottom_sheet1).getHeight());
                    System.out.println("view.getBottom()"+view.getBottom());
                 if(height<view.getBottom()){
                     view.layout(view.getLeft(),height-view.getHeight(),view.getRight(),height);
                 }

                click=0;
                }
            }
        });
        //设置监听事件
        bottomSheetBehavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                //拖动
            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {
                //状态变化
            }
        });



    }
    //通过Chapter传入的url进行对该网页爬虫处理 返回内容
    public static String getArticleListFromUrl(String url) {


        Document doc = null;


            try {

                Connection conn = Jsoup.connect(url).timeout(50000);
                conn.header("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
                conn.header("Accept-Encoding", "gzip, deflate, sdch");
                conn.header("Accept-Language", "zh-CN,zh;q=0.8");
                conn.header("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/55.0.2883.87 Safari/537.36");
                doc = conn.get();
                Thread.sleep(1000);

            } catch (Exception e) {
                e.printStackTrace();
            }

        if(doc!=null){
            return dealTagLink(doc.getElementsByTag("div")); //找到所有a标签
        }else{
            System.out.println("doc==null");
            return null;
        }


    }
    //此方法，如果显示则隐藏，如果隐藏则显示
    private void hintKbOne() {
        InputMethodManager imm = (InputMethodManager)getApplicationContext().getSystemService(Context.INPUT_METHOD_SERVICE);
// 得到InputMethodManager的实例
        if (imm.isActive()) {
            // 如果开启
            imm.toggleSoftInput(InputMethodManager.SHOW_IMPLICIT,
                    InputMethodManager.HIDE_NOT_ALWAYS);

        }
    }
    private static String dealTagLink(Elements elements) {

        ArrayList<String > result=new ArrayList<>();
        for (Element element : elements) {
            String S_element=element.toString();
            String id = element.attr("id");

            if (id.equals("Content")) {
                S_element=S_element.replace("<p>","");
                S_element=S_element.replace("</p>","");
                S_element=S_element.replace("</div>","");
                S_element=S_element.replace("&nbsp;","");


                while (S_element.contains(">")) {
                    int start = S_element.indexOf(">");
                    int end=S_element.indexOf("<");
                    if (end-1<0)
                    {
                        S_element=S_element.substring(start+1,S_element.length()-1);
                    }
                    else if(start+1<S_element.length()-1)
                    {
                        S_element=S_element.substring(0,end-1)+ S_element.substring(start+1,S_element.length()-1);
                    }else if(start==S_element.length()-1)
                    {
                        S_element=S_element.substring(0,end-1);
                    }


                }
                S_element=S_element.replace("p{text-indent:2em;","");
                //System.out.println(S_element);
                return S_element;

            }
        }
        return null;
    }
    public View.OnClickListener background_color=new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            //获取编辑器
            SharedPreferences.Editor editor=sp.edit();
            switch (v.getId()){
                case R.id.background_1:
                    System.out.println("background1");
                    textView.setBackgroundColor(getResources().getColor(R.color.background1));
                    editor.putInt("background",0);
                    editor.commit();
                    break;
                case R.id.background_2:
                    textView.setBackgroundColor(getResources().getColor(R.color.background2));
                    System.out.println("background2");
                    editor.putInt("background",1);
                    editor.commit();
                    break;
                case R.id.background_3:
                    textView.setBackgroundColor(getResources().getColor(R.color.background3));
                    System.out.println("background3");
                    editor.putInt("background",2);
                    editor.commit();
                    break;
                case R.id.background_4:
                    textView.setBackgroundColor(getResources().getColor(R.color.background4));
                    System.out.println("background4");
                    editor.putInt("background",3);

                    editor.commit();
                    break;
            }
        }
    };

    public View.OnClickListener TextStyle=new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            //从asset 读取字体
            AssetManager mgr = getAssets();
            Typeface tf;

            SharedPreferences.Editor editor=sp.edit();
            switch (v.getId()){
                case R.id.text_style1:

                     tf= Typeface.createFromAsset(mgr, text_Style[0]);//华康少女字体
                    textView.setTypeface(tf);
                    editor.putInt("text_style",0);
                    editor.commit();

                    break;
                case R.id.text_style2:

                    tf = Typeface.createFromAsset(mgr, text_Style[1]);//隶书字体
                    textView.setTypeface(tf);
                    editor.putInt("text_style",1);
                    editor.commit();
                    break;
                case R.id.text_style3:

                    tf = Typeface.createFromAsset(mgr, text_Style[2]);//方正卡通
                    textView.setTypeface(tf);
                    editor.putInt("text_style",2);
                    editor.commit();
                    break;
                case R.id.text_style4:

                    tf = Typeface.createFromAsset(mgr, text_Style[3]);//方正流行
                    textView.setTypeface(tf);
                    editor.putInt("text_style",3);
                    editor.commit();
                    break;
            }
        }
    };

    @Override
    protected void onDestroy() {
        int current;
        if(current_length+page.getHeight() / textView.getLineHeight()+2<total_length){
            current=current_length+page.getHeight() / textView.getLineHeight()+2;

        }else{
            current=total_length;
        }
        System.out.println("bookname "+bookname+"  bookurl  "+bookurl+" total chapter  "+total_chapter);
        System.out.println("chapter title "+Chapter_title+" current length "+current_length+"  total length  "+total_length);


        List<BookBean>bookBeans=dataSave.getDataList("list");                             //书籍列表

        if(bookBeans==null){                                                                    //历史记录为空
            bookBeans=new ArrayList<BookBean>();

            ChapterBean temp_chapter=new ChapterBean(Chapter_title,url,current,total_length);
            List<ChapterBean> list=new ArrayList<>();                                           //章节列表
            list.add(temp_chapter);

            BookBean temp_book=new BookBean(bookname,bookurl,list,total_chapter);               //书籍信息
            bookBeans.add(temp_book);
        }else {
            int a=-1;
            for(int i=0;i<bookBeans.size();i++){                                                //寻找是否有书籍
                if(bookBeans.get(i).getBookName().equals(bookname)){
                    a=i;
                    System.out.println("有对应的书籍");
                    break;
                }
            }
            if(a==-1){                                                                       //历史记录中无对应的书籍
                System.out.println("无对应的书籍");
                ChapterBean temp_chapter=new ChapterBean(Chapter_title,url,current,total_length);
                List<ChapterBean> list=new ArrayList<>();
                list.add(temp_chapter);

                BookBean temp_book=new BookBean(bookname,bookurl,list,total_chapter);
                bookBeans.add(temp_book);
            }else{                                                                          //历史记录中有对应的书籍
                int b=-1;
                List<ChapterBean> chapterBeans=bookBeans.get(a).getChapter();
                for(int i=0;i<chapterBeans.size();i++){
                    if(chapterBeans.get(i).getChapterName().equals(Chapter_title)){
                        b=i;
                        System.out.println("有对应的章节");
                        break;
                    }
                }
                if(b==-1){                                                                          //历史记录虽然有书籍记录但无对应的章节信息
                    System.out.println("无对应的章节");
                    ChapterBean temp_chapter=new ChapterBean(Chapter_title,url,current,total_length);
                    chapterBeans.add(temp_chapter);                                                 //追加现在的章节信息
                    bookBeans.remove(a);
                    BookBean temp_book=new BookBean(bookname,bookurl,chapterBeans,total_chapter);
                    bookBeans.add(temp_book);
                }else{                                                                               //历史记录虽然有书籍记录也有对应的章节信息
                                                                                                     //修改阅读的进度
                    ChapterBean temp_chapter=new ChapterBean(Chapter_title,url,current,total_length);
                    chapterBeans.remove(b);                                                         //删除之前的章节信息
                    chapterBeans.add(temp_chapter);                                                 //追加现在的章节信息
                    bookBeans.remove(a);                                                            //删除之前的书籍信息
                    BookBean temp_book=new BookBean(bookname,bookurl,chapterBeans,total_chapter);
                    bookBeans.add(temp_book);
                }

            }
        }

        dataSave.setDataList("list",bookBeans);


        System.out.println(dataSave.getDataList("list"));
        super.onDestroy();
    }
}
