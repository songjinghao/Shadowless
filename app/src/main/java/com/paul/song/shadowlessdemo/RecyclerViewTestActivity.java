package com.paul.song.shadowlessdemo;


import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.List;

public class RecyclerViewTestActivity extends AppCompatActivity {

    private List<Fruit> fruitList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recyclerview);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        initFruits();
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        FruitAdapter adapter = new FruitAdapter(fruitList);
        recyclerView.setAdapter(adapter);
    }

    private void initFruits() {
        for (int i = 0; i < 2; i++) {
            Fruit apple = new Fruit(getString(R.string.apple), R.mipmap.apple);
            fruitList.add(apple);
            Fruit banana = new Fruit(getString(R.string.banana), R.mipmap.banana);
            fruitList.add(banana);
            Fruit orange = new Fruit(getString(R.string.orange), R.mipmap.orange);
            fruitList.add(orange);
            Fruit kiwi = new Fruit(getString(R.string.kiwi), R.mipmap.kiwi);
            fruitList.add(kiwi);
            Fruit pitaya = new Fruit(getString(R.string.pitaya), R.mipmap.pitaya);
            fruitList.add(pitaya);
            Fruit pomegranate = new Fruit(getString(R.string.pomegranate), R.mipmap.pomegranate);
            fruitList.add(pomegranate);
            Fruit strawberry = new Fruit(getString(R.string.strawberry), R.mipmap.strawberry);
            fruitList.add(strawberry);
            Fruit watermelon = new Fruit(getString(R.string.watermelon), R.mipmap.watermelon);
            fruitList.add(watermelon);
        }
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
