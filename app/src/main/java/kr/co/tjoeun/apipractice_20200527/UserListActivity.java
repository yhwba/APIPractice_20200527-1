package kr.co.tjoeun.apipractice_20200527;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;
import android.util.Log;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import kr.co.tjoeun.apipractice_20200527.databinding.ActivityUserListBinding;
import kr.co.tjoeun.apipractice_20200527.datas.User;
import kr.co.tjoeun.apipractice_20200527.utils.ServerUtil;

public class UserListActivity extends BaseActivity {

//    /user - GET 으로 접근해서, 사용자목록을 리스트뷰로 출력
//    닉네임 (이메일주소) => 이 양식으로 표현.

    ActivityUserListBinding binding;

    List<User> users = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_user_list);
        setupEvents();
        setValues();
    }

    @Override
    public void setupEvents() {

    }

    @Override
    public void setValues() {

    }

    @Override
    protected void onResume() {
        super.onResume();
        getUsersFromServer();
    }

    void getUsersFromServer() {

        ServerUtil.getRequestUserList(mContext, new ServerUtil.JsonResponseHandler() {
            @Override
            public void onResponse(JSONObject json) {
                Log.d("사용자목록확인", json.toString());
            }
        });

    }

}
