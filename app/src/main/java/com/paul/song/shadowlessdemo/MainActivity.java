package com.paul.song.shadowlessdemo;


import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatCheckBox;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.SeekBar;

import com.paul.song.analytics.sdk.ShadowlessTrackParameter;
import com.paul.song.analytics.sdk.ShadowlessTrackViewOnClick;
import com.paul.song.analytics.sdk.ShadowlessTrackVoice;
import com.paul.song.medialibrary.entity.TrackEntity;
import com.paul.song.medialibrary.player.AudioPlayer;

import static com.paul.song.shadowlessdemo.ToastUtil.showToast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initEditText();

        initButton();

        initLambdaButton();

        initRecyclerViewTest();

        initTabHostButton();

        initRadioGroupButton();

        initAdapterViewTest();

        initCheckBox();

        initShowDialogButton();

        initShowMultiChoiceDialogButton();

        initSeekBar();

        initViewPagerButton();
        
        initTabLayoutButton();

        initIntentServiceButton();

        initServiceButton();

    }

    private void initServiceButton() {
        AppCompatButton button = findViewById(R.id.serviceButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TestService.startActionFoo(MainActivity.this, "Service-参数一", "Service-参数二");
            }
        });
    }

    private void initIntentServiceButton() {
        AppCompatButton button = findViewById(R.id.intentServiceButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TestIntentService.startActionBaz(MainActivity.this, "IntentService-参数一", "IntentService-参数二");
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    private void initTabLayoutButton() {
        AppCompatButton button = findViewById(R.id.tabLayoutButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, TabLayoutTestActivity.class);
                startActivity(intent);
            }
        });
    }

    private void initViewPagerButton() {
        AppCompatButton button = findViewById(R.id.viewPagerButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ViewpagerTestActivity.class);
                startActivity(intent);
            }
        });
    }

    private void initSeekBar() {
        SeekBar seekBar = findViewById(R.id.seekBar);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        seekBar.setMax(9527);
    }

    private void initShowMultiChoiceDialogButton() {
        AppCompatButton button = findViewById(R.id.showMultiChoiceDialogButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showMultiChoiceDialog(MainActivity.this);
            }
        });
    }

    private void showMultiChoiceDialog(Context context) {
        boolean[] selected = new boolean[]{true, true, true, true};
        CharSequence[] items = {"牛角包", "脏脏包", "椰蓉面包", "肉松面包"};
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("面包种类");
        DialogInterface.OnMultiChoiceClickListener mutiListener =
                new DialogInterface.OnMultiChoiceClickListener() {

                    @Override
                    public void onClick(DialogInterface dialogInterface,
                                        int which, boolean isChecked) {
                        selected[which] = isChecked;
                    }
                };
        builder.setMultiChoiceItems(items, selected, mutiListener);
        builder.setNegativeButton("取消", (dialog, which) -> {

        });
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        Dialog dialog = builder.create();
        dialog.show();
    }

    private void initShowDialogButton() {
        AppCompatButton button = findViewById(R.id.showDialogButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog(MainActivity.this);
            }
        });
    }

    private void showDialog(Context context) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("标题");
        builder.setMessage("内容");
        builder.setNegativeButton("取消", (dialog, which) -> {

        });
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        AlertDialog dialog = builder.create();

        dialog.show();
    }

    private void initCheckBox() {
        AppCompatCheckBox checkBox = findViewById(R.id.checkBox);
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

            }
        });
    }

    private void initAdapterViewTest() {
        AppCompatButton button = findViewById(R.id.adapterViewTest);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AdapterViewTestActivity.class);
                startActivity(intent);
            }
        });
    }

    private void initRadioGroupButton() {
        AppCompatButton button = findViewById(R.id.radioGroupButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, RadioGroupTestActivity.class);
                startActivity(intent);
            }
        });
    }

    private void initTabHostButton() {
        AppCompatButton button = findViewById(R.id.tabHostButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, TabHostTestActivity.class);
                startActivity(intent);
            }
        });
    }

    private void initRecyclerViewTest() {
        AppCompatButton button = findViewById(R.id.RecyclerViewTest);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, RecyclerViewTestActivity.class);
                startActivity(intent);
            }
        });
    }

    private void initLambdaButton() {
        findViewById(R.id.lambdaTest).setOnClickListener(view -> {
            showToast(this, "Lambda OnClick");
            AudioPlayer.getInstance().play(new TrackEntity("周杰伦", "安静", "范特西"));
        });
    }

    private void initButton() {
        AppCompatButton button = findViewById(R.id.normalButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showToast(MainActivity.this, "Normal OnClick");
                AudioPlayer.getInstance().play(new TrackEntity("周杰伦", "黑色毛衣", "十一月的萧邦"));
            }
        });

        registerForContextMenu(button);
    }

    private void initEditText() {
        EditText edit = findViewById(R.id.edit);

        edit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                Log.d("sjh", "beforeTextChanged: " + s);
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Log.d("sjh", "onTextChanged: " + s);
            }

            @Override
            public void afterTextChanged(Editable s) {
                Log.d("sjh", "afterTextChanged: " + s);
            }
        });
    }

    @ShadowlessTrackViewOnClick
    public void clickTv3(@ShadowlessTrackParameter View view) {
        showToast(this, "android:onclick OnClick");
        AudioPlayer.getInstance().play(new TrackEntity("周杰伦", "退后", "依然范特西"));

        String a = "a";
        String b = "b";
        String c = "c";
//        testParameter("a", "b", "c");
        testParameter(a, b, c);
    }

    @ShadowlessTrackVoice
    public void testParameter(String a, @ShadowlessTrackParameter String b, @ShadowlessTrackParameter String c) {
//        ShadowlessTrackHelper.trackVoice(b, c);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        super.onCreateContextMenu(menu, v, menuInfo);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        showToast(this, item.getTitle().toString());
        return super.onContextItemSelected(item);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_more1: {
                ViewGroup rootView = findViewById(R.id.rootView);
                AppCompatButton button = new AppCompatButton(this);
                button.setText("动态创建的 Button1");
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                    }
                });
                rootView.addView(button);
                break;
            }
            case R.id.menu_more2: {
                ViewGroup rootView = findViewById(R.id.rootView);
                AppCompatButton button = new AppCompatButton(this);
                button.setText("动态创建的 Button2");
                button.setOnClickListener(view -> {

                });
                rootView.addView(button);
                break;
            }
        }
        return super.onOptionsItemSelected(item);
    }
}
