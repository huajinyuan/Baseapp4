package com.zt.pintuan.rxpicture.widget.cropview.callback;

import android.net.Uri;

public interface SaveCallback extends Callback {
  void onSuccess(Uri uri);
}