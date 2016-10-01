package com.pengjinfei.concurrence.renderImage;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Pengjinfei on 16/9/27.
 * Description:
 */
public class SingleThreadReader extends AbstractRender {

    void renderPage(CharSequence source) {
        //绘制文本元素
        renderText(source);
        List<ImageData> imageDatas = new ArrayList<>();
        //扫描需要下载的图片，并下载
        for (ImageInfo imageInfo : scanForImageInfo(source)) {
            imageDatas.add(imageInfo.downloadImage());
        }
        //最后绘制图片元素
        for (ImageData imageData : imageDatas) {
            renderImage(imageData);
        }
    }
}
