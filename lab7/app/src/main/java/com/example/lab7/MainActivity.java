package com.example.lab7;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.Toast;

import java.nio.channels.SeekableByteChannel;

public class MainActivity extends AppCompatActivity {

    private int progessRabbit = 0;
    private int progessTurtle = 0;

    private Button btn_start;
    private SeekBar sb_rabbit, sb_turtle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn_start = findViewById(R.id.btn_start);
        sb_rabbit = findViewById(R.id.sb_rabbit);
        sb_turtle = findViewById(R.id.sb_turtle);

        btn_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btn_start.setEnabled(false);
                progessRabbit = 0;
                progessTurtle = 0;
                sb_rabbit.setProgress(0);
                sb_turtle.setProgress(0);
                runRabbit();
                runTurtle();
            }
        });
    }

    private final Handler handler = new Handler(Looper.myLooper(), new Handler.Callback() {
        @Override
        public boolean handleMessage(@NonNull Message message) {
            if(message.what == 1){
                sb_rabbit.setProgress(progessRabbit);
            }else if(message.what == 2){
                sb_turtle.setProgress(progessTurtle);
            }

            if(progessRabbit >= 100 && progessTurtle < 100){
                Toast.makeText(MainActivity.this, "兔子勝利", Toast.LENGTH_SHORT).show();
                btn_start.setEnabled(true);
            }else if(progessTurtle >= 100 && progessRabbit < 100){
                Toast.makeText(MainActivity.this, "烏龜勝利", Toast.LENGTH_SHORT).show();
                btn_start.setEnabled(true);
            }

            return false;
        }
    });

    private void runRabbit(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                boolean[] sleepProbability = {true, true, false};

                while(progessRabbit <= 100 & progessTurtle < 100){
                    try{
                        Thread.sleep(100);
                        if(sleepProbability[(int)(Math.random()*3)]){
                            Thread.sleep(300);
                        }
                    }catch(InterruptedException e){
                        e.printStackTrace();
                    }
                    progessRabbit+=3;
                    Message msg = new Message();
                    msg.what = 1;
                    handler.sendMessage(msg);
                }
            }
        }).start();
    }

    private void runTurtle(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                while(progessTurtle <= 100 & progessRabbit < 100){
                    try{
                        Thread.sleep(100);
                    }catch(InterruptedException e){
                        e.printStackTrace();
                    }
                    progessTurtle+=1;
                    Message msg = new Message();
                    msg.what = 2;
                    handler.sendMessage(msg);
                }
            }
        }).start();
    }
}