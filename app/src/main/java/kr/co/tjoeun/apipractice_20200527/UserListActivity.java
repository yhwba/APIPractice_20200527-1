package kr.co.tjoeun.apipractice_20200527;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class UserListActivity extends BaseActivity {

//    /user - GET 으로 접근해서, 사용자목록을 리스트뷰로 출력
//    닉네임 (이메일주소) => 이 양식으로 표현.

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_list);
        setupEvents();
        setValues();
    }

    @Override
    public void setupEvents() {

    }

    @Override
    public void setValues() {

    }
}
