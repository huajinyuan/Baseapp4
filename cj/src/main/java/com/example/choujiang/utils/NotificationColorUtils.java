package com.example.choujiang.utils;

import android.app.Notification;
import android.content.Context;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.NotificationCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by panx on 2017/6/30.
 */

public class NotificationColorUtils {

    private String DUMMY_TITLE = "DUMMY_TITLE";
    private int titleColor;

    private static NotificationColorUtils instance = new NotificationColorUtils();

    public static NotificationColorUtils getInstance(){
        return instance;
    }

    //验证通知栏文字颜色是否相近于指定颜色
    public boolean isNotificationSimilarColor(Context context,int color){
        return isSimilarColor(color,getNotificationColor(context));
    }

    /**验证color是否相近于baseColor*/
    private boolean isSimilarColor(int baseColor, int color) {
        int simpleBaseColor=baseColor|0xff000000;
        int simpleColor=color|0xff000000;
        int baseRed=Color.red(simpleBaseColor)-Color.red(simpleColor);
        int baseGreen=Color.green(simpleBaseColor)-Color.green(simpleColor);
        int baseBlue=Color.blue(simpleBaseColor)-Color.blue(simpleColor);
        double value=Math.sqrt(baseRed*baseRed+baseGreen*baseGreen+baseBlue*baseBlue);
        if (value<180.0) {
            return true;
        }
        return false;
    }

    //获取通知栏文字颜色
    private int getNotificationColor(Context context){
        if(context instanceof AppCompatActivity){
            return getNotificationColorCompat(context);
        }else{
            return getNotificationColorInternal(context);
        }
    }

    private int getNotificationColorCompat(Context context){
        NotificationCompat.Builder  builder =  new NotificationCompat.Builder(context);
        Notification notification = builder.build();
        int layoutId = notification.contentView.getLayoutId();
        ViewGroup notificationRoot = (ViewGroup)LayoutInflater.from(context).inflate(layoutId,null);
        TextView title = (TextView)notificationRoot.findViewById(android.R.id.title);
        if(title == null){
            final List<TextView> textviews = new ArrayList<TextView>();
            iteratorView(notificationRoot, new Filter() {
                @Override
                public void filter(View view) {
                    if(view instanceof TextView){
                        textviews.add((TextView)view);
                    }
                }
            });
            float minTextSize = Integer.MIN_VALUE;
            int index = 0;
            for(int i = 0,j = textviews.size();i<j;i++){
                float currentSize = textviews.get(i).getTextSize();
                if(currentSize>minTextSize){
                    minTextSize = currentSize;
                    index = i;
                }
            }
            return textviews.get(index).getCurrentTextColor();
        }else{
            return title.getCurrentTextColor();
        }
    }

    private int getNotificationColorInternal(Context context){
        NotificationCompat.Builder  builder =  new NotificationCompat.Builder(context);
        builder.setContentTitle(DUMMY_TITLE);
        Notification notification = builder.build();
        ViewGroup notificationRoot = (ViewGroup)notification.contentView.apply(context,new FrameLayout(context));
        TextView title = (TextView)notificationRoot.findViewById(android.R.id.title);
        if(title == null){
            iteratorView(notificationRoot, new Filter() {
                @Override
                public void filter(View view) {
                    if(view instanceof TextView){
                        TextView textView = (TextView)view;
                        if(DUMMY_TITLE.equals(textView.getText().toString())){
                            titleColor = textView.getCurrentTextColor();
                        }
                    }
                }
            });
            return titleColor;
        }else{
            return title.getCurrentTextColor();
        }
    }

    private void iteratorView(View view, Filter filter){
        if(view == null || filter == null){
            return;
        }
        filter.filter(view);
        if(view instanceof  ViewGroup){
            ViewGroup container = (ViewGroup)view;
            for(int i = 0,j = container.getChildCount();i<j;i++){
                View child = container.getChildAt(i);
                iteratorView(child,filter);
            }
        }
    }

    private interface Filter{
        void filter(View view);
    }




    /**获取通知栏颜色*/
//    public static int getNotificationColor(Context context) {
//        NotificationCompat.Builder builder=new NotificationCompat.Builder(context);
//        Notification notification=builder.build();
//        int layoutId=notification.contentView.getLayoutId();
//        ViewGroup viewGroup= (ViewGroup) LayoutInflater.from(context).inflate(layoutId, null, false);
//        if (viewGroup.findViewById(android.R.id.title)!=null) {
//            return ((TextView) viewGroup.findViewById(android.R.id.title)).getCurrentTextColor();
//        }
//        return findColor(viewGroup);
//    }

//    private static int findColor(ViewGroup viewGroupSource) {
//        int color= Color.TRANSPARENT;
//        LinkedList<ViewGroup> viewGroups=new LinkedList<>();
//        viewGroups.setItemChecked(viewGroupSource);
//        while (viewGroups.size()>0) {
//            ViewGroup viewGroup1=viewGroups.getFirst();
//            for (int i = 0; i < viewGroup1.getChildCount(); i++) {
//                if (viewGroup1.getChildAt(i) instanceof ViewGroup) {
//                    viewGroups.setItemChecked((ViewGroup) viewGroup1.getChildAt(i));
//                }
//                else if (viewGroup1.getChildAt(i) instanceof TextView) {
//                    if (((TextView) viewGroup1.getChildAt(i)).getCurrentTextColor()!=-1) {
//                        color=((TextView) viewGroup1.getChildAt(i)).getCurrentTextColor();
//                    }
//                }
//            }
//            viewGroups.remove(viewGroup1);
//        }
//        return color;
//    }
}
