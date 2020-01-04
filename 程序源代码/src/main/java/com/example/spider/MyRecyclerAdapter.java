package com.example.spider;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MyRecyclerAdapter extends RecyclerView.Adapter<MyRecyclerAdapter.MyViewHolder>  {

    private List<BookBean> mDatas;
    private Context mContext;
    private LayoutInflater inflater;
    public MyRecyclerAdapter(Context context, List<BookBean> datas){
        this. mContext=context;
        this. mDatas=datas;
        inflater=LayoutInflater.from(mContext);
    }
    @NonNull
    private OnitemClick onitemClick;   //定义点击事件接口

    public void MYResetOnitemClickLintener (OnitemClick onitemClick) {
        this.onitemClick = onitemClick;
    }

    //回调接口
    public interface OnitemClick {
        void onItemClick(int position);
    }


    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_view,parent, false);
        MyViewHolder holder= new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {
        holder.bn.setText(mDatas.get(position).getBookName());
        holder.pb.setMax(mDatas.get(position).getTotal_chapter());//mDatas.get(position).getTotal_chapter()
       holder.pb.setProgress(mDatas.get(position).getChapter().size());
        holder.progress.setText("进度："+mDatas.get(position).getChapter().size()+"/"+mDatas.get(position).getTotal_chapter());
        holder.chaters.setText("已读到:第"+mDatas.get(position).getChapter().size()+"章");
        if (onitemClick != null) {
            holder.myurl.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //在TextView的地方进行监听点击事件，并且实现接口
                    onitemClick.onItemClick(position);
                }
            });
        }



    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView bn;
        ProgressBar pb;
        TextView progress;
        ImageView myurl;
        TextView chaters;


        public MyViewHolder(View view) {
            super(view);
            bn=view.findViewById(R.id.textView6);
            progress=view.findViewById(R.id.textView7);
            pb=view.findViewById(R.id.progressBar);
            chaters=view.findViewById(R.id.textView15);
            myurl=view.findViewById(R.id.jump);


            TextView testitem=view.findViewById(R.id.textView6);
            Typeface typeface=Typeface.createFromAsset(mContext.getAssets(),"fonts/writeen.ttf");
            testitem.setTypeface(typeface);

        }

        }


    }



