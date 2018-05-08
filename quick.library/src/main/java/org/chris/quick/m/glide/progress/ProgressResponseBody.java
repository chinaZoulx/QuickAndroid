package org.chris.quick.m.glide.progress;


import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.ResponseBody;
import okio.Buffer;
import okio.BufferedSource;
import okio.ForwardingSource;
import okio.Okio;
import okio.Source;

/**
 * Created by chenpengfei on 2016/11/9.
 */
public class ProgressResponseBody extends ResponseBody {

    private ResponseBody responseBody;
    private ProgressListener progressListener;
    private BufferedSource bufferedSource;

    public ProgressResponseBody(ResponseBody responseBody, ProgressListener progressListener) {
        this.responseBody = responseBody;
        this.progressListener = progressListener;
    }

    @Override
    public MediaType contentType() {
        return responseBody.contentType();
    }

    @Override
    public long contentLength() {

            return responseBody.contentLength();

    }

    @Override
    public BufferedSource source() {
        if (bufferedSource == null) {
            bufferedSource = Okio.buffer(source(responseBody.source()));
        }
        return bufferedSource;
    }

    private Source source(Source source) {
        return new ForwardingSource(source) {

            long totalBytesRead = 0;

            @Override
            public long read(Buffer sink, long byteCount) throws IOException {
                long bytesRead = super.read(sink, byteCount);
                totalBytesRead += bytesRead != -1 ? bytesRead : 0;
                if (progressListener != null) {
                    Map<String, Object> params = new HashMap<>();
                    params.put("totalBytesRead", totalBytesRead);
                    params.put("responseBodyLength", responseBody.contentLength());
                    params.put("isDone", bytesRead == -1);
                    Observable.just(params).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io()).subscribe(new Consumer<Map<String, Object>>() {
                        @Override
                        public void accept(Map<String, Object> stringObjectMap) throws Exception {
                            long totalBytesRead = (long) stringObjectMap.get("totalBytesRead");
                            long responseBodyLength = (long) stringObjectMap.get("responseBodyLength");
                            boolean isDone = (boolean) stringObjectMap.get("isDone");
                            progressListener.progress(totalBytesRead, responseBodyLength, isDone);
                        }
                    });
                }
                return bytesRead;
            }
        };
    }
}
