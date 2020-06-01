package kr.co.tjoeun.apipractice_20200527.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

import kr.co.tjoeun.apipractice_20200527.R;
import kr.co.tjoeun.apipractice_20200527.datas.User;

public class UserAdapter extends ArrayAdapter<User> {

    Context mContext;
    List<User> mList;
    LayoutInflater inf;

    public UserAdapter(@NonNull Context context, int resource, @NonNull List<User> objects) {
        super(context, resource, objects);

        mContext = context;
        mList = objects;
        inf = LayoutInflater.from(mContext);

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View row = convertView;

        if (row == null) {
            row = inf.inflate(R.layout.user_list_item, null);
        }


        return row;

    }
}
