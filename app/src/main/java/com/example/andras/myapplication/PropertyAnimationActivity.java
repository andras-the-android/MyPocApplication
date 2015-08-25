package com.example.andras.myapplication;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.BounceInterpolator;
import android.view.animation.OvershootInterpolator;
import android.widget.ImageView;

/**
 Usable properties at ObjectAnimator:

 - translationX and translationY: These properties control where the View is located as a delta from its left and top coordinates which are set by its layout container.
 - rotation, rotationX, and rotationY: These properties control the rotation in 2D (rotation property) and 3D around the pivot point.
 - scaleX and scaleY: These properties control the 2D scaling of a View around its pivot point.
 - pivotX and pivotY: These properties control the location of the pivot point, around which the rotation and scaling transforms occur. By default, the pivot point is located at the center of the object.
 - x and y: These are simple utility properties to describe the final location of the View in its container, as a sum of the left and top values and translationX and translationY values.
 - alpha: Represents the alpha transparency on the View. This value is 1 (opaque) by default, with a value of 0 representing full transparency (not visible).
 */
public class PropertyAnimationActivity extends Activity {

    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_property_animation);
        imageView = (ImageView) findViewById(R.id.imageView);
        //this is animated since the android:animateLayoutChanges flag is set on the layout
        imageView.setVisibility(View.VISIBLE);
    }


    public void onStartButtonClick(View view) {
        restoreOriginalState();

        ValueAnimator valueAnimator = configureValueAnimator();
        ObjectAnimator objectAnimator = configureObjectAnimator();

        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.play(valueAnimator).before(objectAnimator);
        animatorSet.start();
    }

    @NonNull
    private ObjectAnimator configureObjectAnimator() {
        //CSAK OLYAN PROPERTIVEL FOG MŰKÖDNI, AMINEK VAN PUBLIKUS SETTERE!!!
        //esetenként előfordulhat value animatorhoz hasonlóan requestLayout()-ot kell hívni a update listenerben
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(imageView, "rotation", 0f, 90f);
        objectAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                configureViewPropertyAnimator();
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        objectAnimator.setDuration(1000);
        return objectAnimator;
    }

    private void configureViewPropertyAnimator() {
        imageView.animate().translationY(200f).alpha(0.1f).setInterpolator(new BounceInterpolator());
    }

    private ValueAnimator configureValueAnimator() {
        final ViewGroup.MarginLayoutParams layoutParams = (ViewGroup.MarginLayoutParams) imageView.getLayoutParams();
        ValueAnimator valueAnimator = ValueAnimator.ofInt(0, 400);
        //for listening animation lifecycle events use valueAnimator.addListener(new Animator.AnimatorListener() {....});
        //or extend AnimatorListenerAdapter instead of implementing the whole Animator.AnimatorListener
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                layoutParams.leftMargin = (int) animation.getAnimatedValue();
                imageView.requestLayout();
            }
        });
        valueAnimator.setInterpolator(new OvershootInterpolator(3f));
        valueAnimator.setDuration(2000);
        return valueAnimator;
    }

    private void restoreOriginalState() {
        imageView.setRotation(0f);
        imageView.setY(0f);
        imageView.setAlpha(1f);
        ((ViewGroup.MarginLayoutParams)imageView.getLayoutParams()).leftMargin = 0;
        imageView.requestLayout();
    }

}
