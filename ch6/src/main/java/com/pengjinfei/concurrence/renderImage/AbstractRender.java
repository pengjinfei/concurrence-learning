package com.pengjinfei.concurrence.renderImage;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Pengjinfei on 16/9/27.
 * Description:
 */
public class AbstractRender {

    public void renderText(CharSequence source) {

    }

    public List<ImageInfo> scanForImageInfo(CharSequence source) {
        return new ArrayList<>();
    }

    public void renderImage(ImageData imageData) {

    }
}
