package com.example.cms;
import android.graphics.Movie;
import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.support.annotation.Nullable;
import com.example.cms.R;
import com.example.cms.UserProfile;
import java.util.ArrayList;
import java.util.List;

public class Adapter_Complaints<U> extends ArrayAdapter<Movie> {

    private Context mContext;
    private List<UserProfile> comp = new ArrayList<>();

    public Adapter_Complaints(@NonNull Context context,@LayoutRes ArrayList<UserProfile> list) {
        super(context, 0);
        mContext = context;
        comp = list;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItem = convertView;
        if(listItem == null)
            listItem = LayoutInflater.from(mContext).inflate(R.layout.list_view,parent,false);

        UserProfile userProfile = comp.get(position);

        TextView subject = listItem.findViewById(R.id.subject);
        subject.setText(userProfile.getMobile());

        TextView specify = listItem.findViewById(R.id.specify);
        specify.setText(userProfile.getMobile());

        return listItem;
    }
}