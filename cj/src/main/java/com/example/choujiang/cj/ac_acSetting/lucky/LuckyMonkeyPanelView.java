package com.example.choujiang.cj.ac_acSetting.lucky;

import android.content.Context;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.example.choujiang.R;

import java.util.ArrayList;

/**
 * Created by jeanboy on 2017/4/20.
 */

public class LuckyMonkeyPanelView extends FrameLayout {


    private ImageView bg_1;
    private ImageView bg_2;

    private PanelItemView itemView1, itemView2, itemView3,
            itemView4, itemView6,
            itemView7, itemView8, itemView9;

    private ItemView[] itemViewArr = new ItemView[8];
    private int currentIndex = 0;
    private int currentTotal = 0;
    private int stayIndex = 0;

    private boolean isMarqueeRunning = false;
    private boolean isGameRunning = false;
    private boolean isTryToStop = false;

    private static final int DEFAULT_SPEED = 150;
    private static final int MIN_SPEED = 50;
    private int currentSpeed = DEFAULT_SPEED;
    private ClickListener listener;

    public LuckyMonkeyPanelView(@NonNull Context context) {
        this(context, null);
    }

    public LuckyMonkeyPanelView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LuckyMonkeyPanelView(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        inflate(context, R.layout.view_lucky_mokey_panel, this);
        setupView();
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        startMarquee();
    }

    @Override
    protected void onDetachedFromWindow() {
        stopMarquee();
        super.onDetachedFromWindow();
    }

    private void setupView() {
        bg_1 = (ImageView) findViewById(R.id.bg_1);
        bg_2 = (ImageView) findViewById(R.id.bg_2);
        itemView1 = (PanelItemView) findViewById(R.id.item1);
        itemView2 = (PanelItemView) findViewById(R.id.item2);
        itemView3 = (PanelItemView) findViewById(R.id.item3);
        itemView4 = (PanelItemView) findViewById(R.id.item4);
        itemView6 = (PanelItemView) findViewById(R.id.item6);
        itemView7 = (PanelItemView) findViewById(R.id.item7);
        itemView8 = (PanelItemView) findViewById(R.id.item8);
        itemView9 = (PanelItemView) findViewById(R.id.item9);

//        itemViewArr[0] = itemView4;
//        itemViewArr[1] = itemView1;
//        itemViewArr[2] = itemView2;
//        itemViewArr[3] = itemView3;
//        itemViewArr[4] = itemView6;
//        itemViewArr[5] = itemView9;
//        itemViewArr[6] = itemView8;
//        itemViewArr[7] = itemView7;

        itemViewArr[0] = itemView1;
        itemViewArr[1] = itemView2;
        itemViewArr[2] = itemView3;
        itemViewArr[3] = itemView6;
        itemViewArr[4] = itemView9;
        itemViewArr[5] = itemView8;
        itemViewArr[6] = itemView7;
        itemViewArr[7] = itemView4;
    }

    private void stopMarquee() {
        isMarqueeRunning = false;
        isGameRunning = false;
        isTryToStop = false;
    }

    private void startMarquee() {
        isMarqueeRunning = true;
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (isMarqueeRunning) {
                    try {
                        Thread.sleep(250);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    post(new Runnable() {
                        @Override
                        public void run() {
                            if (bg_1 != null && bg_2 != null) {
                                if (VISIBLE == bg_1.getVisibility()) {
                                    bg_1.setVisibility(GONE);
                                    bg_2.setVisibility(VISIBLE);
                                } else {
                                    bg_1.setVisibility(VISIBLE);
                                    bg_2.setVisibility(GONE);
                                }
                            }
                        }
                    });
                }
            }
        }).start();
    }

    private long getInterruptTime() {
        currentTotal++;
        if (isTryToStop) {
            currentSpeed += 10;
            if (currentSpeed > DEFAULT_SPEED) {
                currentSpeed = DEFAULT_SPEED;
            }
        } else {
            if (currentTotal / itemViewArr.length > 0) {
                currentSpeed -= 10;
            }
            if (currentSpeed < MIN_SPEED) {
                currentSpeed = MIN_SPEED;
            }
        }
        return currentSpeed;
    }

    public boolean isGameRunning() {
        return isGameRunning;
    }

    public void startGame() {
        isGameRunning = true;
        isTryToStop = false;
        currentSpeed = DEFAULT_SPEED;
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (isGameRunning) {
                    try {
                        Thread.sleep(getInterruptTime());
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

//                    post(new Runnable() {
//                        @Override
//                        public void run() {
//                            int preIndex = currentIndex;
//                            currentIndex++;
//                            if (currentIndex >= itemViewArr.length) {
//                                currentIndex = 0;
//                            }
//
//                            itemViewArr[preIndex].setFocus(false);
//                            itemViewArr[currentIndex].setFocus(true);
//
//                            if (isTryToStop && currentSpeed == DEFAULT_SPEED && stayIndex == currentIndex) {
//                                isGameRunning = false;
//                            }
//                        }
//                    });

                    post(new Runnable() {
                        @Override
                        public void run() {
                            int preIndex = currentIndex-1;
                            if (currentIndex == 0) {
                                preIndex = 7;
                            }
                            itemViewArr[currentIndex].setFocus(true);
                            itemViewArr[preIndex].setFocus(false);

                            if (isTryToStop && currentSpeed == DEFAULT_SPEED && stayIndex == currentIndex) {
                                isGameRunning = false;
                            }
                            currentIndex++;
                            if (currentIndex == 8) {
                                currentIndex = 0;
                            }
                        }
                    });
                }
            }
        }).start();
    }

    public void tryToStop(int position) {
        stayIndex = position;
        isTryToStop = true;
    }

    public void setImage(ArrayList<String> urls) {
        for(int i=0;i<urls.size();i++) {
            itemViewArr[i].setImage(urls.get(i));
        }
    }

    public void setImage(int position, String url) {
        itemViewArr[position].setImage(url);
    }

    public interface ClickListener{
        void click(int position);
    }
    public void setClickListener(ClickListener mListener){
        itemView1.setOnClickListener(view -> listener.click(0));
        itemView2.setOnClickListener(view -> listener.click(1));
        itemView3.setOnClickListener(view -> listener.click(2));
        itemView6.setOnClickListener(view -> listener.click(3));
        itemView9.setOnClickListener(view -> listener.click(4));
        itemView8.setOnClickListener(view -> listener.click(5));
        itemView7.setOnClickListener(view -> listener.click(6));
        itemView4.setOnClickListener(view -> listener.click(7));

        listener = mListener;
    }
}
