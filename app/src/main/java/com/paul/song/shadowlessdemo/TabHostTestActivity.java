package com.paul.song.shadowlessdemo;


import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.widget.TabHost;
import android.widget.Toast;

public class TabHostTestActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tabhost);

        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null){
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        initTabHost();
    }

    private void initTabHost() {
        TabHost tabHost = findViewById(R.id.tabhost);
        tabHost.setup();

        LayoutInflater.from(this).inflate(R.layout.tab1, tabHost.getTabContentView());
        LayoutInflater.from(this).inflate(R.layout.tab2, tabHost.getTabContentView());
        LayoutInflater.from(this).inflate(R.layout.tab3, tabHost.getTabContentView());

        tabHost.addTab(tabHost.newTabSpec("tab1").setIndicator(getString(R.string.apple)).setContent(R.id.tab01));
        tabHost.addTab(tabHost.newTabSpec("tab2").setIndicator(getString(R.string.pitaya)).setContent(R.id.tab02));
        tabHost.addTab(tabHost.newTabSpec("tab3").setIndicator(getString(R.string.kiwi)).setContent(R.id.tab03));

        //标签切换事件处理，setOnTabChangedListener
        tabHost.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            @Override
            // tabId是newTabSpec第一个参数设置的tab页名，并不是layout里面的标识符id
            public void onTabChanged(String tabId) {
                if (tabId.equals("tab1")) {
                    Toast.makeText(TabHostTestActivity.this, getString(R.string.apple), Toast.LENGTH_SHORT).show();
                }
                if (tabId.equals("tab2")) {
                    Toast.makeText(TabHostTestActivity.this, getString(R.string.pitaya), Toast.LENGTH_SHORT).show();
                }
                if (tabId.equals("tab3")) {
                    Toast.makeText(TabHostTestActivity.this, getString(R.string.kiwi), Toast.LENGTH_SHORT).show();
                }
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
}
