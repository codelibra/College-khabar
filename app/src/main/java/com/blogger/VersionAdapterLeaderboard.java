package com.blogger;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.parse.ParseUser;

import java.util.List;

public class VersionAdapterLeaderboard extends ArrayAdapter<ParseUser> {
    public Context myContext;
    List<ParseUser> mMessages;

    public VersionAdapterLeaderboard(Context act, List<ParseUser> messages) {
        super(act, R.layout.leaderboard_row, messages);
        mMessages = messages;
        myContext = act;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null) {
            try {
                convertView = LayoutInflater.from(myContext).inflate(R.layout.leaderboard_row, null);
            } catch (Exception e) {
                Log.v("erooor yai h yr!!", e.getMessage());
            }
            holder = new ViewHolder();
            holder.person = (TextView) convertView.findViewById(R.id.name);
            holder.branch = (TextView) convertView.findViewById(R.id.textView2);
            holder.score = (TextView) convertView.findViewById(R.id.textView3);
            convertView.setTag(holder);
        } else
            holder = (ViewHolder) convertView.getTag();

        holder.person.setText((position + 1) + ". " + mMessages.get(position).getString(ParseConstants.KEY_USERNAME).toUpperCase());
        holder.branch.setText(" " + mMessages.get(position).get(ParseConstants.KEY_BRANCH));
        holder.score.setText(" " + mMessages.get(position).get(ParseConstants.KEY_SCORE));

        if (position == 0 || position == 1 || position == 2)
            convertView.setBackgroundColor(Color.parseColor("#00ff6d"));
        else
            convertView.setBackgroundColor(Color.WHITE);

        return convertView;
    }

    private static class ViewHolder {
        TextView person;
        TextView branch;
        TextView score;
    }
}
