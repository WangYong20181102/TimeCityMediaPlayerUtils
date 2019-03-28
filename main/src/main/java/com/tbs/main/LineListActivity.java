package com.tbs.main;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mr.Wang on 2019/3/28 10:47.
 */
public class LineListActivity extends AppCompatActivity {
    private ListView listView;
    private MyBaseAdapder adapder;
    private List<String> stringList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_line);
        initView();
    }

    private void initView() {
        stringList = new ArrayList<>();
        listView = findViewById(R.id.list_view);
        for (int i = 0; i < 30; i++) {
            stringList.add("item" + i);
        }
        adapder = new MyBaseAdapder(this);
        listView.setAdapter(adapder);
    }

    class MyBaseAdapder extends BaseAdapter {
        private Context context;

        public MyBaseAdapder(Context context) {
            this.context = context;
        }

        @Override
        public int getCount() {
            return stringList.size();
        }

        @Override
        public Object getItem(int i) {
            return stringList.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            MyViewHolder holder = null;
            if (view == null) {
                holder = new MyViewHolder();
                view = LayoutInflater.from(context).inflate(R.layout.list_item, null);
                holder.textView = view.findViewById(R.id.text_view);
                holder.textView1 = view.findViewById(R.id.text_view1);
                holder.textView2 = view.findViewById(R.id.text_view2);
                holder.view = view.findViewById(R.id.view);
                view.setTag(holder);
            } else {
                holder = (MyViewHolder) view.getTag();
            }
            if (i == 1|| i==3||i==8){
                holder.textView1.setVisibility(View.GONE);
            }else {
                holder.textView1.setVisibility(View.VISIBLE);
            }
            if (i == 4|| i==5||i==10|| i==3||i==14|| i==7||i==20|| i==2||i==25||i==8){
                holder.textView2.setVisibility(View.GONE);
            }else {
                holder.textView2.setVisibility(View.VISIBLE);
            }
            if (i == stringList.size() -1){
                holder.view.setVisibility(View.GONE);
            }else {
                holder.view.setVisibility(View.VISIBLE);
            }

            holder.textView.setText(stringList.get(i));
            return view;
        }

        class MyViewHolder {
            TextView textView;
            TextView textView1;
            TextView textView2;
            View view;
        }

    }

}
