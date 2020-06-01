package kr.co.tjoeun.apipractice_20200527;

import androidx.appcompat.app.AlertDialog;
import androidx.databinding.DataBindingUtil;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import kr.co.tjoeun.apipractice_20200527.adapters.TopicReplyAdapter;
import kr.co.tjoeun.apipractice_20200527.databinding.ActivityMainBinding;
import kr.co.tjoeun.apipractice_20200527.datas.Topic;
import kr.co.tjoeun.apipractice_20200527.datas.TopicReply;
import kr.co.tjoeun.apipractice_20200527.datas.User;
import kr.co.tjoeun.apipractice_20200527.utils.ContextUtil;
import kr.co.tjoeun.apipractice_20200527.utils.ServerUtil;

public class MainActivity extends BaseActivity {

    ActivityMainBinding binding;

    List<TopicReply> replyList = new ArrayList<>();
    TopicReplyAdapter tra;

    Topic thisWeekTopic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        setupEvents();
        setValues();
    }

    @Override
    public void setupEvents() {

        binding.goToUserListBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(mContext, UserListActivity.class);
                startActivity(myIntent);
            }
        });

        binding.postReplyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent myIntent = new Intent(mContext, EditReplyActivity.class);
                myIntent.putExtra("topic", thisWeekTopic); // 댓글 달 주제를 넘겨주자.
                startActivity(myIntent);

            }
        });

        binding.logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder alert = new AlertDialog.Builder(mContext);
                alert.setTitle("로그아웃");
                alert.setMessage("정말 로그아웃 하시겠습니까?");
                alert.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ContextUtil.setLoginUserToken(mContext, "");

                        Intent myIntent = new Intent(mContext, LoginActivity.class);
                        startActivity(myIntent);

                        finish();
                    }
                });
                alert.setNegativeButton("취소", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(mContext, "로그아웃을 취소했습니다.", Toast.LENGTH_SHORT).show();
                    }
                });
                alert.show();




            }
        });

    }


    @Override
    protected void onResume() {
        super.onResume();

        ServerUtil.getRequestMainInfo(mContext, new ServerUtil.JsonResponseHandler() {
            @Override
            public void onResponse(JSONObject json) {
                Log.d("메인화면응답", json.toString());


                try {
                    int code = json.getInt("code");

                    if (code == 200) {

//                        댓글 목록이 중복되지 않도록, 기존의 댓글 내용을 다 날려버린다.
                        replyList.clear();

                        JSONObject data = json.getJSONObject("data");

                        JSONObject user = data.getJSONObject("user");

                        final User me = User.getUserFromJson(user);

                        JSONObject topic = data.getJSONObject("topic");

                        thisWeekTopic = Topic.getTopicFromJson(topic);

//                        댓글목록도 파싱하자

                        JSONArray replies = data.getJSONArray("replies");

                        for (int i=0; i < replies.length() ; i++) {
                            JSONObject reply = replies.getJSONObject(i);

                            Log.d("댓글내용", reply.getString("content"));

                            replyList.add(TopicReply.getTopicReplyFromJson(reply));

                        }


                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                binding.nickNameTxt.setText(me.getNickName());
                                binding.emailTxt.setText(me.getEmail());

                                Glide.with(mContext).load(thisWeekTopic.getImageUrl()).into(binding.topicImg);
                                binding.topicTitleTxt.setText(thisWeekTopic.getTitle());

                                tra.notifyDataSetChanged();
                            }
                        });


                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        });
    }

    @Override
    public void setValues() {



        tra = new TopicReplyAdapter(mContext, R.layout.topic_reply_list_item, replyList);
        binding.replyListView.setAdapter(tra);

    }

}
