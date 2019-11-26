package com.example.i18n;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private EditText user;
    private EditText pass;
    private Button ok;
    private Button cancle;
    private TextView count;
    private int num_count=5;

    private Handler mHandler;
    private TextView btn;
    private int num=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        btn=findViewById(R.id.txv);
        user=findViewById(R.id.user);
        pass=findViewById(R.id.pass);
        ok=findViewById(R.id.ok);
        cancle=findViewById(R.id.cancle);
        count=findViewById(R.id.count);

        cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.exit(0);
            }
        });
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String password=pass.getText().toString();
                System.out.println(password);
                String username=user.getText().toString();
                System.out.println(username);
                if(password.equals("123456")&&username.equals("hello")){
                    Toast.makeText(MainActivity.this,getResources().getString(R.string.success),Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(MainActivity.this,getResources().getString(R.string.fail),Toast.LENGTH_SHORT).show();
                    num_count--;
                    count.setText(String.valueOf(num_count));
                    if(num_count==0){
                        ok.setEnabled(false);
                        mHandler=new Handler(){
                            @Override
                            public void handleMessage(@NonNull Message msg) {
                                super.handleMessage(msg);
                                if(msg.what==0x111&&num<10) {
                                    num++;
                                    btn.setVisibility(View.VISIBLE);
                                    btn.setText(getResources().getString(R.string.wait) + "  " + String.valueOf(10 - num) + "s");
                                }
                                else{

                                    num=0;num_count=5;
                                    ok.setEnabled(true);
                                    btn.setVisibility(View.INVISIBLE);
                                    count.setText(String.valueOf(num_count));
                                }
                            }
                        };
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                while(true){
                                    try {
                                        Thread.sleep(1000);
                                    } catch (InterruptedException e) {
                                        e.printStackTrace();

                                    }
                                    Message m=new Message();
                                    m.what=0x111;

                                    mHandler.sendMessage(m);
                                    if(num==10)break;
                                }
                            }
                        }).start();
                    }
                }
            }
        });


    }
}
