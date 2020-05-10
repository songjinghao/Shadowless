package com.paul.song.shadowlessdemo;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class AdapterViewTestActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adapterview);

        initListView();
        initGridView();

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    private void initGridView() {
        GridView gridView = findViewById(R.id.gridView);
        BaseAdapter adapter = new DataAdapter(this);
        gridView.setAdapter(adapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ToastUtil.showToast(AdapterViewTestActivity.this, "dd：" + position);
            }
        });
    }

    private void initListView() {
        ListView listView = findViewById(R.id.listView);
        BaseAdapter adapter = new DataAdapter(this);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(AdapterViewTestActivity.this, "dd：" + position, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    class DataAdapter extends BaseAdapter {

        private final int[] imgIds = {R.mipmap.apple, R.mipmap.banana, R.mipmap.kiwi, R.mipmap.orange, R.mipmap.pitaya, R.mipmap.pomegranate, R.mipmap.strawberry, R.mipmap.watermelon};
        private final int[] strIds = {R.string.apple, R.string.banana, R.string.kiwi, R.string.orange, R.string.pitaya, R.string.pomegranate, R.string.strawberry, R.string.watermelon};

        private Context mContext;
        private LayoutInflater mLayoutInflater;

        public DataAdapter(Context context) {
            mContext = context;
            this.mLayoutInflater = LayoutInflater.from(context);
        }

        @Override
        public int getCount() {
            return imgIds.length;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = mLayoutInflater.inflate(R.layout.item_listview, parent, false);
                ViewHolder holder = new ViewHolder(convertView);
                convertView.setTag(holder);
            }
            ViewHolder holder = (ViewHolder) convertView.getTag();
            holder.iv.setImageResource(imgIds[position]);
            holder.desc.setText(mContext.getString(strIds[position]));
            return convertView;
        }

        private class ViewHolder {
            ViewHolder(View viewRoot) {
                iv = viewRoot.findViewById(R.id.img);
                desc = viewRoot.findViewById(R.id.desc);
            }

            public ImageView iv;
            public TextView desc;
        }
    }
}
