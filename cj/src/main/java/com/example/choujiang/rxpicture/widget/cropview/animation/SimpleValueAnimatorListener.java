package com.example.choujiang.rxpicture.widget.cropview.animation;

public interface SimpleValueAnimatorListener {
  void onAnimationStarted();

  void onAnimationUpdated(float scale);

  void onAnimationFinished();
}
