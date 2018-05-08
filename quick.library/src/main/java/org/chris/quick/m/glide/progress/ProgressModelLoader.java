package org.chris.quick.m.glide.progress;

import com.bumptech.glide.load.data.DataFetcher;
import com.bumptech.glide.load.model.stream.StreamModelLoader;

import java.io.InputStream;

public class ProgressModelLoader implements StreamModelLoader<String> {

    private ProgressListener progressListener;

    public ProgressModelLoader(ProgressListener progressListener) {
        this.progressListener = progressListener;
    }

    @Override
    public DataFetcher<InputStream> getResourceFetcher(String model, int width, int height) {
        return new ProgressDataFetcher(model, progressListener);
    }

}
