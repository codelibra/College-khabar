package com.blogger;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class VersionAdapterRumour extends ArrayAdapter<String> {
    public ArrayList<String> user;
    public ArrayList<String> senttime;
    public TextView num;
    public TextView que;
    public Context myContext;

    VersionAdapterRumour(Context act, ArrayList<String> usernames, ArrayList<String> times) {
        super(act, R.layout.rumour_row, usernames);
        user = usernames;
        senttime = times;
        myContext = act;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null) {
            try {
                convertView = LayoutInflater.from(myContext).inflate(R.layout.rumour_row, null);
            } catch (Exception e) {
                Log.v("erooor yai h yr!!", e.getMessage());
            }
            holder = new ViewHolder();
            holder.person = (TextView) convertView.findViewById(R.id.username_rumour);
            holder.time = (TextView) convertView.findViewById(R.id.time_rumour);

            convertView.setTag(holder);
        } else
            holder = (ViewHolder) convertView.getTag();

        holder.person.setText(user.get(position));
        holder.time.setText(senttime.get(position));

        return convertView;
    }

    private static class ViewHolder {
        TextView person;
        TextView time;
    }
}
