package com.example.choujiang.network;


import com.example.choujiang.model.ProgressEvent;

import org.greenrobot.eventbus.EventBus;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.ResponseBody;
import okio.Buffer;
import okio.BufferedSource;
import okio.ForwardingSource;
import okio.Okio;
import okio.Source;

/**
 * Created by caiyk on 2017/7/7.
 */

public class DownloadProgressResponseBody extends ResponseBody {

    private ResponseBody mResponseBody;
    private BufferedSource mBufferedSource;

    public DownloadProgressResponseBody(ResponseBody responseBody) {
        this.mResponseBody = responseBody;
    }

    @Override
    public MediaType contentType() {
        return mResponseBody.contentType();
    }

    @Override
    public long contentLength() {
        return mResponseBody.contentLength();
    }

    @Override
    public BufferedSource source() {
        if (mBufferedSource == null) {
            mBufferedSource = Okio.buffer(source(mResponseBody.source()));
        }
        return mBufferedSource;
    }

    private Source source(Source source) {
        return new ForwardingSource(source) {
            long totalBytesRead = 0L;

            @Override
            public long read(Buffer sink, long byteCount) throws IOException {
                long bytesRead = super.read(sink, byteCount);
                totalBytesRead += bytesRead != -1 ? bytesRead : 0;
                EventBus.getDefault().post(new ProgressEvent((int) ((totalBytesRead * 100) / mResponseBody.contentLength())));
                return bytesRead;
            }
        };

    }
}
