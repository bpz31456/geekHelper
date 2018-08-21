package cn.baopz;

import cn.baopz.core.DefaultArticleFetcher;
import cn.baopz.threadPool.ArticleFetcherThreadPoolManager;

import java.util.concurrent.ExecutorService;

/**
 * application enter
 *
 * @author baopz
 * @date 2018.08.16
 */
public class GeekHelperApplication {

    public static void main(String[] args) {
        ExecutorService executorService = ArticleFetcherThreadPoolManager.getSingleThreadPool("baopz-account-%d");
        executorService.execute(new DefaultArticleFetcher());
        executorService.shutdown();
    }
}
