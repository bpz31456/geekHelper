package cn.baopz.threadPool;

import com.google.common.util.concurrent.ThreadFactoryBuilder;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.*;

/**
 * @author baopz
 * @date 2018.08.16
 */
public final class ArticleFetcherThreadPoolManager {
    private static Map<String, ExecutorService> serviceMap;

    private ArticleFetcherThreadPoolManager() {
    }

    public synchronized static ExecutorService getSingleThreadPool(String nameFormat) {
        if (serviceMap == null) {
            serviceMap = new HashMap<>(2 << 2);
        }
        ExecutorService executor = serviceMap.computeIfAbsent(nameFormat, ss -> {
            ThreadFactory threadFactory = new ThreadFactoryBuilder().setNameFormat(nameFormat).build();
            ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(4, 10, 0, TimeUnit.SECONDS
                    , new ArrayBlockingQueue<>(10), threadFactory, new ThreadPoolExecutor.AbortPolicy());
            return threadPoolExecutor;
        });
        return executor;
    }
}
