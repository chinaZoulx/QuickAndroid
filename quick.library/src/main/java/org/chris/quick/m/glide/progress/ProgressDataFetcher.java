package org.chris.quick.m.glide.progress;

import com.bumptech.glide.Priority;
import com.bumptech.glide.load.data.DataFetcher;

import java.io.IOException;
import java.io.InputStream;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by chenpengfei on 2016/11/9.
 */
public class ProgressDataFetcher implements DataFetcher<InputStream> {

    private String url;
    private ProgressListener progressListener;
    private Call progressCall;
    private InputStream stream;
    private boolean isCancelled;

    public ProgressDataFetcher(String url, ProgressListener progressListener) {
        this.url = url;
        this.progressListener = progressListener;
    }

    @Override
    public InputStream loadData(Priority priority) throws Exception {
        Request request = new Request.Builder().url(url).build();
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(new ProgressInterceptor(progressListener)).build();
        try {
            progressCall = client.newCall(request);
            Response response = progressCall.execute();
            if (isCancelled) {
                return null;
            }
            if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);
            stream = response.body().byteStream();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        return stream;
    }

    @Override
    public void cleanup() {
        if (stream != null) {
            try {
                stream.close();
                stream = null;
            } catch (IOException e) {
                stream = null;
            }
        }
        if (progressCall != null) {
            progressCall.cancel();
        }
    }

    @Override
    public String getId() {
        return url;
    }

    @Override
    public void cancel() {
        isCancelled = true;
    }
}
