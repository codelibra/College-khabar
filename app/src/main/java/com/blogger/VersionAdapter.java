package com.blogger;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class VersionAdapter extends ArrayAdapter<String> {
    public ArrayList<String> post;
    public Bitmap result;
    public TextView num;
    public TextView que;
    public ImageView logo;
    public int lastelement;
    public Context myContext;

    VersionAdapter(Context act, ArrayList<String> temp, Bitmap imageu) {
        super(act, R.layout.quesiton_row, temp);
        post = temp;
        result = imageu;
        lastelement = temp.size() - 1;
        myContext = act;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null) {
            Log.v("aya k nai dekhte h!!!", "dekho yr!!");
            try {
                convertView = LayoutInflater.from(myContext).inflate(R.layout.quesiton_row, null);

            } catch (Exception e) {
                Log.v("erooor yai h yr!!", e.getMessage());
            }
            holder = new ViewHolder();
            holder.logo = (ImageView) convertView.findViewById(R.id.companylogo);
            holder.num = (TextView) convertView.findViewById(R.id.votes);
            holder.que = (TextView) convertView.findViewById(R.id.questiontext);

            convertView.setTag(holder);
        } else
            holder = (ViewHolder) convertView.getTag();

        if (position == lastelement) {
            holder.num.setText("");
            holder.que.setText(post.get(position));
            int photo = R.drawable.turnbaby;
            holder.logo.setImageResource(photo);
            convertView.setBackgroundColor(Color.WHITE);
        } else {
            holder.num.setText("Que:- " + (position + 1));
            holder.que.setText(post.get(position));
            holder.logo.setImageBitmap(result);

            if (position % 2 == 0)
                convertView.setBackgroundColor(Color.LTGRAY);
            else
                convertView.setBackgroundColor(Color.WHITE);
        }
        return convertView;
    }

    private static class ViewHolder {
        ImageView logo;
        TextView num;
        TextView que;
    }
}
