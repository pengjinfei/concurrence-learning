package com.pengjinfei.concurrence.renderImage;

import com.pengjinfei.concurrence.ExceptionUtils;

import java.util.List;
import java.util.concurrent.*;

/**
 * Created by Pengjinfei on 16/9/27.
 * Description:
 */
public class Render extends AbstractRender {

    private final ExecutorService executor;

    public Render(ExecutorService executor) {
        this.executor = executor;
    }

    void renderPage(CharSequence source) {
        List<ImageInfo> imageInfos = scanForImageInfo(source);
        CompletionService<ImageData> completionService = new ExecutorCompletionService<ImageData>(executor);
        for (ImageInfo imageInfo : imageInfos) {
            completionService.submit(new Callable<ImageData>() {
                @Override
                public ImageData call() throws Exception {
                    return imageInfo.downloadImage();
                }
            });
        }
        renderText(source);

        try {
            for (int i = 0; i < imageInfos.size(); i++) {
                Future<ImageData> take = completionService.take();
                ImageData imageData = take.get();
                renderImage(imageData);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } catch (ExecutionException e) {
            throw ExceptionUtils.launderThrowable(e.getCause());
        }
    }
}
