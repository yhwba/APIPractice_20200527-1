package kr.co.tjoeun.apipractice_20200527.fcm;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class MyFCMService extends FirebaseMessagingService {

    @Override
    public void onNewToken(@NonNull String s) {
        super.onNewToken(s);
        Log.d("디바이스토큰 새로발급", s);
    }

    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);

//        Log.d("푸시알림수신", "이벤트발생");
//
//        Log.d("메세지 확인",remoteMessage.getNotification().getTitle());
//        Log.d("메세지 확인",remoteMessage.getNotification().getBody());



        final String messageBody = remoteMessage.getNotification().getBody();

        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(getApplicationContext(), messageBody, Toast.LENGTH_SHORT).show();
            }
        });




    }

}
