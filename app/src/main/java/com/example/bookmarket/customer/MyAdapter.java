package com.example.bookmarket.customer;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.bookmarket.R;

import java.util.ArrayList;

public class MyAdapter extends BaseAdapter {
    ArrayList<MyItem> itemList = new ArrayList<>();

    public MyAdapter(ArrayList<MyItem> itemList) {
        this.itemList = itemList;
    }

    @Override
    public int getCount() {
        return itemList.size();
    }

    @Override
    public Object getItem(int i) {
        return itemList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        Context context = viewGroup.getContext();
        MyItem item = itemList.get(i);

        View convertView = view;
        LayoutInflater inflater =(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(R.layout.activity_board_item, viewGroup, false);
        TextView tvtitle = convertView.findViewById(R.id.textView_Title);
        TextView tvDate = convertView.findViewById(R.id.textView_Date);
        TextView tvId = convertView.findViewById(R.id.textView_UserID);
        tvtitle.setText(item.title);
        tvDate.setText(item.date);
        tvId.setText(item.id);


        return convertView;
    }
}
