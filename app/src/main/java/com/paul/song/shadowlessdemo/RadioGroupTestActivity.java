package com.paul.song.shadowlessdemo;


import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.RadioGroup;

import static com.paul.song.shadowlessdemo.ToastUtil.showToast;

public class RadioGroupTestActivity extends AppCompatActivity {

    private Context context;
    private RadioGroup mRg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_radiogroup);
        context = this;

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        mRg = findViewById(R.id.rg_fruit);
        mRg.setOnCheckedChangeListener((group, checkedId) -> {
            switch (checkedId) {
                case R.id.rbt_apple:
                    showToast(context, "您选中了" + context.getString(R.string.apple));
                    break;
                case R.id.rbt_banana:
                    showToast(context, "您选中了" + context.getString(R.string.banana));
                    break;
                case R.id.rbt_pitaya:
                    showToast(context, "您选中了" + context.getString(R.string.pitaya));
                    break;
                case R.id.rbt_kiwi:
                    showToast(context, "您选中了" + context.getString(R.string.kiwi));
                    break;
                case R.id.rbt_orange:
                    showToast(context, "您选中了" + context.getString(R.string.orange));
                    break;
                case R.id.rbt_strawberry:
                    showToast(context, "您选中了" + context.getString(R.string.strawberry));
                    break;
                case R.id.rbt_watermelon:
                    showToast(context, "您选中了" + context.getString(R.string.watermelon));
                    break;
                case R.id.rbt_pomegranate:
                    showToast(context, "您选中了" + context.getString(R.string.pomegranate));
                    break;
                default:
                    break;
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
