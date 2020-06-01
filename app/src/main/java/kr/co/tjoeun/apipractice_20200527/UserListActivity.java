package kr.co.tjoeun.apipractice_20200527;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import kr.co.tjoeun.apipractice_20200527.adapters.UserAdapter;
import kr.co.tjoeun.apipractice_20200527.databinding.ActivityUserListBinding;
import kr.co.tjoeun.apipractice_20200527.datas.User;
import kr.co.tjoeun.apipractice_20200527.utils.ServerUtil;

public class UserListActivity extends BaseActivity {

//    /user - GET 으로 접근해서, 사용자목록을 리스트뷰로 출력
//    닉네임 (이메일주소) => 이 양식으로 표현.

    ActivityUserListBinding binding;

    List<User> users = new ArrayList<>();
    UserAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_user_list);
        setupEvents();
        setValues();
    }

    @Override
    public void setupEvents() {

//        사람중 한명을 누르면
//        user_check의 POST 메쏘드로 호출

        binding.userListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                final User clickedUser = users.get(position);

                ServerUtil.postRequestUserCheck(mContext, clickedUser.getId(), new ServerUtil.JsonResponseHandler() {
                    @Override
                    public void onResponse(JSONObject json) {
                        Log.d("찔러보기응답 ", json.toString());

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(mContext, String.format("%s님을 찔렀습니다.",clickedUser.getNickName()), Toast.LENGTH_SHORT).show();
                            }
                        });

                    }
                });
            }
        });


    }

    @Override
    public void setValues() {

        mAdapter = new UserAdapter(mContext, R.layout.user_list_item, users);
        binding.userListView.setAdapter(mAdapter);

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

                try {
                    int code = json.getInt("code");

                    if (code == 200) {
                        JSONObject data = json.getJSONObject("data");
                        JSONArray usersJsonArray = data.getJSONArray("users");

                        for (int i=0 ; i < usersJsonArray.length() ; i++) {
                            JSONObject userJsonObj = usersJsonArray.getJSONObject(i);
                            User user = User.getUserFromJson(userJsonObj);
                            users.add(user);
                        }

//                      notifyDataset 필요하다

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                mAdapter.notifyDataSetChanged();
                            }
                        });



                    }



                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

    }

}
