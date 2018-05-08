package org.chris.quick.m.glide.progress;

public interface ProgressListener {

    void progress(long bytesRead, long maxLength, boolean done);

}
