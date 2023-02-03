package com.dbrud1032.alarmapp;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;

public class MainActivity extends AppCompatActivity {

    ImageView imgAlarm;
    TextView txtTimer;
    EditText editTimer;
    Button btnCancel;
    Button btnStart;

    String strTime;

    CountDownTimer timer;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imgAlarm = findViewById(R.id.imgAlarm);
        txtTimer = findViewById(R.id.txtTimer);
        editTimer = findViewById(R.id.editTimer);
        btnCancel = findViewById(R.id.btnCancel);
        btnStart = findViewById(R.id.btnStart);


        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 1. 에디트텍스트에 적힌 시간을 가져온다.
                // 1-1. 아무것도 안 적혀있을 때 처리
                strTime  = editTimer.getText().toString().trim();

                if (strTime.isEmpty()){
                    Toast.makeText(getApplicationContext(), "타이머 시간을 먼저 셋팅하세요.", Toast.LENGTH_SHORT).show();
                    return;
                }

                // 1-2. 문자열로 되어있는 초를 Long 로 바꿔야 한다.
                long time = Long.valueOf(strTime).longValue();
                // 60 입력했다면, => 60000
                time = time * 1000;


                // 2. 적힌 시간에 맞는 타이머 만들기
                timer = new CountDownTimer(time, 1000) {
                    @Override
                    public void onTick(long l) {
                        // 화면에 표시
                        long remain = l/1000;
                        txtTimer.setText(remain + "");
                    }

                    @Override
                    public void onFinish() {

                        // 1. 애니메이션 효과를 준다.
                        YoYo.with(Techniques.Shake).duration(400)
                                .repeat(3).playOn(imgAlarm);

                        // 2. 알람음 처리한다.
                        MediaPlayer mp = MediaPlayer.create(MainActivity.this, R.raw.alarm);
                        mp.start();

                    }
                };

                // 3. 타이머를 시작한다.
                timer.start();

            }
        });

        // 취소 버튼 눌렀을 때
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 0. 타이머가 null 인지 확인
                if (timer == null){
                    return;
                }

                // 1. 타이머 취소
                timer.cancel();

                // 2. 남은 초 화면을 유저가
                // 에디트텍스트에 셋팅한 값으로 보여준다.
                if (strTime.isEmpty()) {
                    txtTimer.setText("남은 시간");
                } else {
                    txtTimer.setText(strTime);

                }


            }
        });



    }
}