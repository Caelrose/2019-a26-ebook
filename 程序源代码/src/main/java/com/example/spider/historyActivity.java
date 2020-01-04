package com.example.spider;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.OrientationHelper;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class historyActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private Button button;
    private List<BookBean> mDatas=new ArrayList<>() ;
    private MyRecyclerAdapter recycleAdapter;

    @SuppressLint("WrongConstant")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.history_layout);
        initData();
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recycleAdapter= new MyRecyclerAdapter(historyActivity.this , mDatas);
        button=findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        //设置布局管理器
        recyclerView.setLayoutManager(layoutManager);
        //设置为垂直布局，这也是默认的
        layoutManager.setOrientation(OrientationHelper.VERTICAL);
        //设置Adapter
        recyclerView.setAdapter(recycleAdapter);
        recycleAdapter.MYResetOnitemClickLintener(new MyRecyclerAdapter.OnitemClick() {
            @Override
            public void onItemClick(int position) {
                Bundle bundle=new Bundle();
                bundle.putCharSequence("name","《"+mDatas.get(position).getBookName()+"》");
                bundle.putCharSequence("url",mDatas.get(position).getBookUrl());
                //当点击时获取对应的url并将该url传入Content，再跳转到Content界面
                Intent intent=new Intent(historyActivity.this, Chapter.class);
                intent.putExtras(bundle);
                startActivity(intent);

            }
        });
        //设置增加或删除条目的动画
        recyclerView.setItemAnimator(new DefaultItemAnimator());
    }

    @Override
    protected void onRestart() {
        mDatas.clear();
        initData();
        recycleAdapter= new MyRecyclerAdapter(historyActivity.this , mDatas);
        recyclerView.setAdapter(recycleAdapter);
        recycleAdapter.MYResetOnitemClickLintener(new MyRecyclerAdapter.OnitemClick() {
            @Override
            public void onItemClick(int position) {
                Bundle bundle=new Bundle();
                bundle.putCharSequence("name","《"+mDatas.get(position).getBookName()+"》");
                bundle.putCharSequence("url",mDatas.get(position).getBookUrl());
                //当点击时获取对应的url并将该url传入Content，再跳转到Content界面
                Intent intent=new Intent(historyActivity.this, Chapter.class);
                intent.putExtras(bundle);
                startActivity(intent);

            }
        });

        super.onRestart();
    }

    private void initData() {
        ListDataSave dataSave= new ListDataSave(getApplicationContext(), "data");

        List<BookBean> bookBeans=dataSave.getDataList("list");
        if (bookBeans!=null&&bookBeans.size()>0)
        {
            for (int i = bookBeans.size()-1; i >=0; i--) {
                mDatas.add(bookBeans.get(i));
            }
        }


    }

}

