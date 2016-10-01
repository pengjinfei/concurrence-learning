package com.pengjinfei.concurrence.timeout;

import java.util.concurrent.*;

/**
 * Created by Pengjinfei on 16/9/27.
 * Description: 在指定时间内获取广告信息
 */
public class AdPageRender {

    private final ExecutorService executor = Executors.newCachedThreadPool();

    //加载失败时默认的广告
    private final Ad DEFAULT_AD = new Ad();

    Page renderPageWithAd() throws InterruptedException {
        Future<Ad> adFuture = executor.submit(new Callable<Ad>() {
            @Override
            public Ad call() throws Exception {
                return new Ad();
            }
        });
        //渲染页面，等待加载广告
        Page page = renderPageBody();
        Ad ad;
        try {
            ad = adFuture.get(2, TimeUnit.SECONDS);
        } catch (ExecutionException e) {
            ad = DEFAULT_AD;
        } catch (TimeoutException e) {
            ad = DEFAULT_AD;
            adFuture.cancel(true);
        }
        page.setAd(ad);
        return page;
    }

    private Page renderPageBody() {
        return new Page();
    }

    static class Page {

        private Ad ad;

        public void setAd(Ad ad) {
            this.ad = ad;
        }
    }

    static class Ad {

    }
}
