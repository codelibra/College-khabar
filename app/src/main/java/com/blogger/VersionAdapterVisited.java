package com.blogger;

import android.app.Dialog;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;

public class VersionAdapterVisited extends ArrayAdapter<String> {
    public List<String> company;
    public List<String> details;
    public Context myContext;
    public String name;

    VersionAdapterVisited(Context act, List<String> companynames) {
        super(act, R.layout.rumour_row, companynames);
        company = companynames;
        myContext = act;
    }

    void DetailsCopied(List<String> content) {
        details = new ArrayList<String>(content);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null) {
            try {
                convertView = LayoutInflater.from(myContext).inflate(R.layout.new_post_list_row,
                        null);
            } catch (Exception e) {
                Log.v("erooor yai h yr!!", e.getMessage());
            }

            holder = new ViewHolder();

            holder.company_name = (TextView) convertView.findViewById(R.id.company_visited);
            holder.details = (Button) convertView.findViewById(R.id.details_visited);

            convertView.setTag(holder);
        } else
            holder = (ViewHolder) convertView.getTag();

        holder.company_name.setText(company.get(position));
        holder.details.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                ParseQuery<ParseObject> query = ParseQuery.getQuery("Details");

                RelativeLayout vw = (RelativeLayout) v.getParent();
                TextView child = (TextView) vw.getChildAt(1);

                Log.v("Clicked details of ", (String) child.getText());
                name = (String) child.getText();

                query.whereEqualTo("companyname", name);

                boolean isInCache = query.hasCachedResult();

                if (isInCache == false)
                    query.findInBackground(new FindCallback<ParseObject>() {

                        @Override
                        public void done(List<ParseObject> objects, ParseException e) {
                            if (e == null) {
                                if (objects.size() != 0) {
                                    ParseObject temp = objects.get(0);
                                    final Dialog dialog = new Dialog(myContext,
                                            R.style.Theme_Dialog);
                                    dialog.setContentView(R.layout.visited_custom_xml);

                                    final TextView userInput = (TextView) dialog
                                            .findViewById(R.id.company);
                                    final TextView userInput3 = (TextView) dialog
                                            .findViewById(R.id.pack);
                                    final TextView sender = (TextView) dialog
                                            .findViewById(R.id.process);

                                    final Button ok = (Button) dialog.findViewById(R.id.btn_ok);

                                    userInput.setText(name);
                                    userInput3.setText(temp.getString("package") + " lpa");
                                    sender
                                            .setText(temp.getString("process").replace('+', '\n'));
                                    dialog.show();

                                    ok.setOnClickListener(new View.OnClickListener() {

                                        @Override
                                        public void onClick(View v) {
                                            dialog.dismiss();
                                        }
                                    });

                                }
                            }
                        }
                    });

            }
        });

        return convertView;
    }

    private static class ViewHolder {
        TextView company_name;
        Button details;
    }

}
