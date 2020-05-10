package com.paul.song.shadowlessdemo;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

public class ViewpagerTestActivity extends AppCompatActivity {

    private static final String TAG = ViewpagerTestActivity.class.getSimpleName();

    private ImageView[] mIndicators;
    private ViewPager mViewPager;
    private LinearLayout llIndicator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewpager_test);

        initViewPager();

    }

    private void initViewPager() {
        List<String> colors = new ArrayList<>();
        colors.add("#008577");
        colors.add("#00574B");
        colors.add("#D81B60");
        llIndicator = findViewById(R.id.ll_indicator);

        LinearLayout.LayoutParams margin = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);//每个圆点大小
        // 设置每个小圆点距离左边的间距
        margin.setMargins(10, 0, 10, 0);
        mIndicators = new ImageView[colors.size()];
        for (int i = 0; i < colors.size(); i++) {
            ImageView imageView = new ImageView(this);
            mIndicators[i] = imageView;
            //默认第一张显示选中
            if (i == 0) {
                mIndicators[i].setImageResource(R.drawable.program_indicator_focused);
            } else {
                mIndicators[i].setImageResource(R.drawable.program_indicator_unfocused);
            }
            llIndicator.addView(mIndicators[i], margin);
        }


        mViewPager = findViewById(R.id.viewPager);
        mViewPager.setAdapter(new ViewPagerAdapter(colors));

        mViewPager.addOnPageChangeListener(new GuidePageChangeListener());
    }

    class GuidePageChangeListener implements ViewPager.OnPageChangeListener {

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {

        }

        @Override
        public void onPageSelected(int arg0) {
            Log.d(TAG,"onPageSelected page == " + arg0);
            for (int i = 0; i < mIndicators.length; i++) {
                if (arg0 % mIndicators.length != i) {
                    mIndicators[i].setImageResource(R.drawable.program_indicator_unfocused);
                } else {
                    mIndicators[i].setImageResource(R.drawable.program_indicator_focused);
                }
            }
        }

        @Override
        public void onPageScrollStateChanged(int arg0) {

        }
    }

    class ViewPagerAdapter extends PagerAdapter {

        private List<String> colorList;

        public ViewPagerAdapter(List<String> colorList) {
            this.colorList = colorList;
        }

        @Override
        public int getCount() {
            return colorList.size();
        }

        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
            return view == o;
        }

        @NonNull
        @Override
        public Object instantiateItem(@NonNull ViewGroup container, int position) {
            if (position < 0) {
                position += colorList.size();
            }

            ImageView imageView = new ImageView(container.getContext());
            imageView.setContentDescription("Page " + position);
            imageView.setImageDrawable(new ColorDrawable(Color.parseColor(colorList.get(position))));
            container.addView(imageView);
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
            return imageView;
        }

        @Override
        public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
            container.removeView((View) object);
        }
    }
}
