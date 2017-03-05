package com.vstar.sacredsun_android.util;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.support.v4.content.ContextCompat;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;

import com.vstar.sacredsun_android.R;

/**
 * Created by tanghuailong on 2017/3/3.
 */

public class FunctionUtil {

    private static RotateAnimation rotate;
    private static AlphaAnimation blink;
    private static AnimationDrawable switchAnim;

    //开启动画
    public static void startAnimation(ImageView img, String type) {
        Animation animation = getAnimation(type);
        img.setAnimation(animation);
    }

    public static AnimationDrawable startBackgroundAnimation(Context context, ImageView img) {
        if(switchAnim == null) {
            switchAnim = new AnimationDrawable();
            switchAnim.addFrame(ContextCompat.getDrawable(context, R.drawable.door_able), 500);
            switchAnim.addFrame(ContextCompat.getDrawable(context, R.drawable.dooropen_start), 500);
            switchAnim.setOneShot(false);
        }
        img.setBackground(switchAnim);
        return switchAnim;
    }

    //结束动画
    public static void stopAnimation(ImageView img) {
        img.clearAnimation();
    }

    public static void stopBackgroundAnimation(Context context,ImageView img) {
//        animation.stop();
        img.setBackground(ContextCompat.getDrawable(context,R.drawable.door_disable));

    }

    private static Animation getAnimation(String type) {
        Animation result = null;
        switch (type) {
            case "rotate":
                if (rotate == null) {
                    rotate = new RotateAnimation(0, 360, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
                    rotate.setDuration(800);
                    rotate.setFillAfter(true);
                    rotate.setInterpolator(new LinearInterpolator());
                    rotate.setRepeatCount(Animation.INFINITE);
                }
                result = rotate;
                break;
            case "blink":
                if (blink == null) {
                    blink = new AlphaAnimation(1, 0);
                    blink.setDuration(500);
                    blink.setInterpolator(new LinearInterpolator());
                    blink.setRepeatCount(Animation.INFINITE);
                    blink.setRepeatMode(Animation.REVERSE);
                }
                result = blink;
                break;
        }
        return result;
    }

}
