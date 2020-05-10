package com.paul.song.analytics.sdk;

import android.app.Activity;
import android.app.Dialog;
import android.app.Fragment;
import android.app.Service;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.annotation.Keep;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.SwitchCompat;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TabHost;
import android.widget.ToggleButton;

import com.paul.song.medialibrary.entity.TrackEntity;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by songjinghao on 2019/10/18.
 */
public class ShadowlessTrackHelper {
    private static final String TAG = ShadowlessTrackHelper.class.getSimpleName();

    private static String globalData = "default";

    public static void trackVoice(Object... args) {
        for (Object arg : args) {
            Log.d(TAG, arg.toString());
        }
    }

    public static void trackPlay(TrackEntity entity) {
//        Log.i(TAG, entity.toString());
        try {
            Field[] fields = entity.getClass().getDeclaredFields();
            Method[] methods = entity.getClass().getDeclaredMethods();
            JSONObject jsonObject = new JSONObject();
            for (Field field : fields) {
                String fieldName = field.getName();
                for (Method method : methods) {
                    String methodName = method.getName();
                    if (methodName.startsWith("get") && methodName.substring(3).equalsIgnoreCase(fieldName)) {
                        jsonObject.put(fieldName, method.invoke(entity));
                    }
                }

            }
//            Log.d(TAG, jsonObject.toString());
            ShadowlessDataAPI.getInstance().track("$AppContent", jsonObject);
        } catch (JSONException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    /**
     * View 被点击，自动埋点
     *
     * @param view View
     */
    @Keep
    public static void trackViewOnClick(View view) {
        try {
            JSONObject properties = new JSONObject();
            properties.put("$element_type", ShadowlessDataPrivate.getElementType(view));
            properties.put("$element_id", ShadowlessDataPrivate.getViewId(view));
//            properties.put("$element_content", ShadowlessDataPrivate.getElementContent(view));
            String viewText = null;
            if (view instanceof ViewGroup) {
                try {
                    StringBuilder stringBuilder = new StringBuilder();
                    viewText = ShadowlessDataPrivate.traverseViewContent(stringBuilder, (ViewGroup) view);
                    if (!TextUtils.isEmpty(viewText)) {
                        viewText = viewText.substring(0, viewText.length() - 1);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                viewText = ShadowlessDataPrivate.getElementContent(view);
            }
            //$element_content
            if (!TextUtils.isEmpty(viewText)) {
                properties.put("$element_content", viewText);
            }

            if (view instanceof ImageView) {
                Drawable background = ((ImageView) view).getBackground();
            }

            Activity activity = ShadowlessDataPrivate.getActivityFromView(view);
            if (activity != null) {
                properties.put("$activity", activity.getClass().getCanonicalName());
            }

            ShadowlessDataAPI.getInstance().track("$AppClick", properties);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * MenuItem 被点击，自动埋点
     *
     * @param object   Object
     * @param menuItem MenuItem
     */
    @Keep
    public static void trackViewOnClick(Object object, MenuItem menuItem) {
        try {
            Context context = null;
            if (object instanceof Context) {
                context = (Context) object;
            }
            JSONObject properties = new JSONObject();
            properties.put("$element_type", "MenuItem");

            properties.put("$element_content", menuItem.getTitle());

            if (context != null) {
                String idString = null;
                try {
                    idString = context.getResources().getResourceEntryName(menuItem.getItemId());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                if (!TextUtils.isEmpty(idString)) {
                    properties.put("$element_id", idString);
                }

                Activity activity = ShadowlessDataPrivate.getActivityFromContext(context);
                if (activity != null) {
                    properties.put("$activity", activity.getClass().getCanonicalName());
                }
            }

            ShadowlessDataAPI.getInstance().track("$AppClick", properties);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Keep
    public static void trackTabHost(String tabName) {
        try {
            JSONObject properties = new JSONObject();

            properties.put("$element_type", "TabHost");
            properties.put("$element_content", tabName);
            ShadowlessDataAPI.getInstance().track("$AppClick", properties);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Keep
    public static void trackTabHostWhole(TabHost tabHost) {
        try {
            Context context = tabHost.getContext();
            if (context == null) {
                return;
            }

            JSONObject properties = new JSONObject();

            Activity activity = ShadowlessDataPrivate.getActivityFromContext(context);
            String idString = ShadowlessDataPrivate.getViewId(tabHost);
            if (!TextUtils.isEmpty(idString)) {
                properties.put("$element_id", idString);
            }

            if (activity != null) {
                properties.put("$activity", activity.getClass().getCanonicalName());
            }
            properties.put("$element_type", "TabHost");
            properties.put("$element_content", tabHost.getCurrentTabTag()); // 此 tag 不是真实显示的 text

            ShadowlessDataAPI.getInstance().track("$AppClick", properties);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Keep
    public static void trackRadioGroup(RadioGroup group, int checkedId) {
        try {
            Context context = group.getContext();
            if (context == null) {
                return;
            }

            JSONObject properties = new JSONObject();
            properties.put("$element_id", ShadowlessDataPrivate.getViewId(group.getContext(), checkedId));

            Activity activity = ShadowlessDataPrivate.getActivityFromContext(context);
            if (activity != null) {
                View view = activity.findViewById(checkedId);
                properties.put("$element_type", ShadowlessDataPrivate.getElementType(view));
                String viewText = null;
                if (view instanceof ViewGroup) {
                    try {
                        StringBuilder stringBuilder = new StringBuilder();
                        viewText = ShadowlessDataPrivate.traverseViewContent(stringBuilder, (ViewGroup) view);
                        if (!TextUtils.isEmpty(viewText)) {
                            viewText = viewText.substring(0, viewText.length() - 1);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    viewText = ShadowlessDataPrivate.getElementContent(view);
                }
                //$element_content
                if (!TextUtils.isEmpty(viewText)) {
                    properties.put("$element_content", viewText);
                }

                properties.put("$activity", activity.getClass().getCanonicalName());
            }

            ShadowlessDataAPI.getInstance().track("$AppClick", properties);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Keep
    public static void trackViewOnClick(android.widget.AdapterView adapterView, android.view.View view, int position) {
        try {
            Context context = adapterView.getContext();
            if (context == null) {
                return;
            }

            JSONObject properties = new JSONObject();

            Activity activity = ShadowlessDataPrivate.getActivityFromContext(context);
            String idString = ShadowlessDataPrivate.getViewId(adapterView);
            if (!TextUtils.isEmpty(idString)) {
                properties.put("$element_id", idString);
            }

            if (activity != null) {
                properties.put("$activity", activity.getClass().getCanonicalName());
            }
            properties.put("$element_position", String.valueOf(position));

            if (adapterView instanceof Spinner) {
                properties.put("$element_type", "Spinner");
                Object item = adapterView.getItemAtPosition(position);
                if (item != null) {
                    if (item instanceof String) {
                        properties.put("$element_content", item);
                    }
                }
            } else {
                if (adapterView instanceof ListView) {
                    properties.put("$element_type", "ListView");
                } else if (adapterView instanceof GridView) {
                    properties.put("$element_type", "GridView");
                }

                String viewText = null;
                if (view instanceof ViewGroup) {
                    try {
                        StringBuilder stringBuilder = new StringBuilder();
                        viewText = ShadowlessDataPrivate.traverseViewContent(stringBuilder, (ViewGroup) view);
                        if (!TextUtils.isEmpty(viewText)) {
                            viewText = viewText.substring(0, viewText.length() - 1);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    viewText = ShadowlessDataPrivate.getElementContent(view);
                }
                //$element_content
                if (!TextUtils.isEmpty(viewText)) {
                    properties.put("$element_content", viewText);
                }
            }
            ShadowlessDataAPI.getInstance().track("$AppClick", properties);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Keep
    public static void trackViewOnClick(CompoundButton view, boolean isChecked) {
        try {
            Context context = view.getContext();
            if (context == null) {
                return;
            }

            JSONObject properties = new JSONObject();

            Activity activity = ShadowlessDataPrivate.getActivityFromContext(context);

            try {
                String idString = context.getResources().getResourceEntryName(view.getId());
                if (!TextUtils.isEmpty(idString)) {
                    properties.put("$element_id", idString);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            if (activity != null) {
                properties.put("$activity", activity.getClass().getCanonicalName());
            }

            String viewText = null;
            String viewType;
            if (view instanceof CheckBox) {
                viewType = "CheckBox";
                CheckBox checkBox = (CheckBox) view;
                if (!TextUtils.isEmpty(checkBox.getText())) {
                    viewText = checkBox.getText().toString();
                }
            } else if (view instanceof SwitchCompat) {
                viewType = "SwitchCompat";
                SwitchCompat switchCompat = (SwitchCompat) view;
                if (isChecked) {
                    if (!TextUtils.isEmpty(switchCompat.getTextOn())) {
                        viewText = switchCompat.getTextOn().toString();
                    }
                } else {
                    if (!TextUtils.isEmpty(switchCompat.getTextOff())) {
                        viewText = switchCompat.getTextOff().toString();
                    }
                }
            } else if (view instanceof ToggleButton) {
                viewType = "ToggleButton";
                ToggleButton toggleButton = (ToggleButton) view;
                if (isChecked) {
                    if (!TextUtils.isEmpty(toggleButton.getTextOn())) {
                        viewText = toggleButton.getTextOn().toString();
                    }
                } else {
                    if (!TextUtils.isEmpty(toggleButton.getTextOff())) {
                        viewText = toggleButton.getTextOff().toString();
                    }
                }
            } else if (view instanceof RadioButton) {
                viewType = "RadioButton";
                RadioButton radioButton = (RadioButton) view;
                if (!TextUtils.isEmpty(radioButton.getText())) {
                    viewText = radioButton.getText().toString();
                }
            } else {
                viewType = view.getClass().getCanonicalName();
            }

            //Content
            if (!TextUtils.isEmpty(viewText)) {
                properties.put("$element_content", viewText);
            }

            if (!TextUtils.isEmpty(viewType)) {
                properties.put("$element_type", viewType);
            }

            properties.put("isChecked", isChecked);

            ShadowlessDataAPI.getInstance().track("$AppClick", properties);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Keep
    public static void trackViewOnClick(DialogInterface dialogInterface, int whichButton) {
        try {
            Dialog dialog = null;
            if (dialogInterface instanceof Dialog) {
                dialog = (Dialog) dialogInterface;
            }

            if (dialog == null) {
                return;
            }

            Context context = dialog.getContext();
            //将Context转成Activity
            Activity activity = ShadowlessDataPrivate.getActivityFromContext(context);

            if (activity == null) {
                activity = dialog.getOwnerActivity();
            }

            JSONObject properties = new JSONObject();
            //$screen_name & $title
            if (activity != null) {
                properties.put("$activity", activity.getClass().getCanonicalName());
            }

            Button button = null;
            if (dialog instanceof android.app.AlertDialog) {
                button = ((android.app.AlertDialog) dialog).getButton(whichButton);
            } else if (dialog instanceof android.support.v7.app.AlertDialog) {
                button = ((android.support.v7.app.AlertDialog) dialog).getButton(whichButton);
            }

            if (button != null) {
                properties.put("$element_content", button.getText());
            }

            properties.put("$element_type", "Dialog");

            ShadowlessDataAPI.getInstance().track("$AppClick", properties);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Keep
    public static void trackViewOnClick(DialogInterface dialogInterface, int whichButton, boolean isChecked) {
        try {
            Dialog dialog = null;
            if (dialogInterface instanceof Dialog) {
                dialog = (Dialog) dialogInterface;
            }

            if (dialog == null) {
                return;
            }

            Context context = dialog.getContext();
            //将Context转成Activity
            Activity activity = ShadowlessDataPrivate.getActivityFromContext(context);

            if (activity == null) {
                activity = dialog.getOwnerActivity();
            }

            JSONObject properties = new JSONObject();
            //$screen_name & $title
            if (activity != null) {
                properties.put("$activity", activity.getClass().getCanonicalName());
            }

            ListView listView = null;
            if (dialog instanceof android.app.AlertDialog) {
                listView = ((android.app.AlertDialog) dialog).getListView();
            } else if (dialog instanceof android.support.v7.app.AlertDialog) {
                listView = ((android.support.v7.app.AlertDialog) dialog).getListView();
            }

            if (listView != null) {
                ListAdapter listAdapter = listView.getAdapter();
                Object object = listAdapter.getItem(whichButton);
                if (object != null) {
                    if (object instanceof String) {
                        properties.put("$element_content", object);
                    }
                }
            }

            properties.put("isChecked", isChecked);
            properties.put("$element_type", "Dialog");

            ShadowlessDataAPI.getInstance().track("$AppClick", properties);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void trackViewPager(ViewPager viewPager, int position) {
        try {
            Context context = viewPager.getContext();
            if (context == null) {
                return;
            }

            JSONObject properties = new JSONObject();

            Activity activity = ShadowlessDataPrivate.getActivityFromContext(context);
            String idString = ShadowlessDataPrivate.getViewId(viewPager);
            if (!TextUtils.isEmpty(idString)) {
                properties.put("$element_id", idString);
            }

            if (activity != null) {
                properties.put("$activity", activity.getClass().getCanonicalName());
            }
            properties.put("$element_position", String.valueOf(position));
            properties.put("$element_type", "ViewPager");

            ShadowlessDataAPI.getInstance().track("$AppSlide", properties);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void trackTabLayout(TabLayout tabLayout, TabLayout.Tab tab) {
        try {
            Context context = tabLayout.getContext();
            if (context == null) {
                return;
            }

            JSONObject properties = new JSONObject();

            Activity activity = ShadowlessDataPrivate.getActivityFromContext(context);
            String idString = ShadowlessDataPrivate.getViewId(tabLayout);
            if (!TextUtils.isEmpty(idString)) {
                properties.put("$element_id", idString);
            }

            if (activity != null) {
                properties.put("$activity", activity.getClass().getCanonicalName());
            }
            properties.put("$element_type", "TabLayout");
            properties.put("$element_content", tab.getText());

            ShadowlessDataAPI.getInstance().track("$AppClick", properties);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void trackLifecycleMethodOnResume(Object object) {
        try {
            if (object == null) {
                return;
            }

            JSONObject properties = new JSONObject();

            if (object instanceof Activity) {
                properties.put("$activity", object.getClass().getCanonicalName());
            } else if (object instanceof Fragment || object instanceof android.support.v4.app.Fragment) {
                properties.put("fragment", object.getClass().getCanonicalName());
            } else {
                return;
            }

            ShadowlessDataAPI.getInstance().track("$AppPageStart", properties);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void trackLifecycleMethodOnPause(Object object) {
        try {
            if (object == null) {
                return;
            }

            JSONObject properties = new JSONObject();

            if (object instanceof Activity) {
                properties.put("$activity", object.getClass().getCanonicalName());
            } else if (object instanceof Fragment || object instanceof android.support.v4.app.Fragment) {
                properties.put("fragment", object.getClass().getCanonicalName());
            } else {
                return;
            }

            ShadowlessDataAPI.getInstance().track("$AppPageEnd", properties);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static final String ACTION_FOO = "com.paul.song.shadowlessdemo.action.FOO";
    private static final String ACTION_BAZ = "com.paul.song.shadowlessdemo.action.BAZ";
    private static final String EXTRA_PARAM1 = "com.paul.song.shadowlessdemo.extra.PARAM1";
    private static final String EXTRA_PARAM2 = "com.paul.song.shadowlessdemo.extra.PARAM2";

    public static void trackIntentFromService(Object service, Intent intent, String extraName) {
        try {
            if (intent == null || !(service instanceof Service)) {
                return;
            }
            Log.d(TAG, "extraName:" + extraName);
            final String action = intent.getAction();
            if (ACTION_FOO.equals(action)) {
                final String param1 = intent.getStringExtra(extraName);
                final String param2 = intent.getStringExtra(EXTRA_PARAM2);

                Log.d(TAG, "Intent param1:" + param1 + ", param2:" + param2);
            } else if (ACTION_BAZ.equals(action)) {
                final String param1 = intent.getStringExtra(extraName);
                final String param2 = intent.getStringExtra(EXTRA_PARAM2);
                Log.d(TAG, "Intent param1:" + param1 + ", param2:" + param2);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
