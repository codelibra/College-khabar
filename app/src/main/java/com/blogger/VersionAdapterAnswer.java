package com.blogger;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class VersionAdapterAnswer extends ArrayAdapter<String> {

    public int len;
    public ArrayList<String> post;
    public ArrayList<String> author;
    public ArrayList<String> write;
    public Bitmap result;
    public ImageView img;
    public int lastelement;
    public Context mycontext;
    private LayoutInflater layoutinflator;

    public VersionAdapterAnswer(Context context, DisplayAnswer act, int sz, ArrayList<String> data,
                                ArrayList<String> extra, ArrayList<String> writer, Bitmap imageu) {
        super(act, R.layout.quesiton_row, data);
        layoutinflator = (LayoutInflater) act.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        len = sz;
        post = data;
        author = extra;
        result = imageu;
        write = writer;
        mycontext = context;
        lastelement = post.size() - 1;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;

        if (convertView == null) {
            convertView = layoutinflator.inflate(R.layout.answer_row, null);
            holder = new ViewHolder();
            holder.img = (ImageView) convertView.findViewById(R.id.logo);
            holder.num = (TextView) convertView.findViewById(R.id.votes);
            holder.que = (TextView) convertView.findViewById(R.id.text);
            holder.wr = (TextView) convertView.findViewById(R.id.authour);

            convertView.setTag(holder);
        } else
            holder = (ViewHolder) convertView.getTag();

        holder.num.setText(author.get(position));
        holder.que.setText(post.get(position));
        holder.wr.setText(write.get(position));
        holder.img.setImageBitmap(result);

        if (position % 2 == 0)
            convertView.setBackgroundColor(Color.parseColor("#ECFAC8"));
        else
            convertView.setBackgroundColor(Color.WHITE);
        return convertView;
    }

    private static class ViewHolder {
        ImageView logo;
        ImageView img;
        TextView num;
        TextView que;
        TextView wr;
    }

}
