package com.pengjinfei.concurrence.renderImage;

import com.pengjinfei.concurrence.ExceptionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

/**
 * Created by Pengjinfei on 16/9/27.
 * Description:
 */
public class FutureRender extends  AbstractRender{
    private final ExecutorService executor= Executors.newCachedThreadPool();

    void renderPage(CharSequence source) {
        final List<ImageInfo> imageInfos = scanForImageInfo(source);
        Callable<List<ImageData>> task=new Callable<List<ImageData>>() {
            @Override
            public List<ImageData> call() throws Exception {
                List<ImageData> imageDatas = new ArrayList<>();
                for (ImageInfo imageInfo : imageInfos) {
                    imageDatas.add(imageInfo.downloadImage());
                }
                return imageDatas;
            }
        };

        Future<List<ImageData>> future = executor.submit(task);
        renderText(source);

        try {
            List<ImageData> imageDatas = future.get();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            future.cancel(true);
        } catch (ExecutionException e) {
            throw ExceptionUtils.launderThrowable(e.getCause());
        }
    }
}
