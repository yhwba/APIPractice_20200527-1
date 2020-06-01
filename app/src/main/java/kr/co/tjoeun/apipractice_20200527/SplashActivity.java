package kr.co.tjoeun.apipractice_20200527;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingService;

import kr.co.tjoeun.apipractice_20200527.utils.ContextUtil;

public class SplashActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        setupEvents();
        setValues();
    }

    @Override
    public void setupEvents() {

    }

    @Override
    public void setValues() {

//        저장토큰이 있나? AND 자동로그인이 체크됬나? => MainActivity로 이동
//        둘중 하나라도 틀리면 => LoginActivity로 이동

//        이 행동을 2초 후에 실행

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                try {
                    Log.d("저장된 디바이스토큰", FirebaseInstanceId.getInstance().getToken());
                }
                catch (Exception e) {
                    e.printStackTrace();
                }

                if (!ContextUtil.getLoginUserToken(mContext).equals("") && ContextUtil.isAutoLogin(mContext)) {

                    Intent myIntent = new Intent(mContext, MainActivity.class);
                    startActivity(myIntent);

                }
                else {

                    Intent myIntent = new Intent(mContext, LoginActivity.class);
                    startActivity(myIntent);

                }

                finish();
            }
        }, 2000);



    }
}









