package cn.baopz.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Properties;

/**
 * @author baopz
 * @date 2018.07.09
 */
public final class PropertiesTool {
    private static Logger logger = LoggerFactory.getLogger(PropertiesTool.class);
    private volatile static Properties prop;

    /**
     *
     * @param config
     * @param key
     * @return
     * @throws IOException
     */
    public static String getProperty(String config, String key) throws IOException {
        if (prop == null) {
            synchronized (PropertiesTool.class) {
                if (prop == null) {
                    prop = new Properties();
                    InputStream inputStream = PropertiesTool.class.getClassLoader().getResourceAsStream(config);
                    if (inputStream == null) {
                        logger.warn("没有发现配置文件。{}", config);
                        return null;
                    }
                    try {
                        prop.load(new InputStreamReader(inputStream, "utf-8"));
                    } finally {
                        inputStream.close();
                    }
                }
            }
        }
        return prop.getProperty(key);
    }
}
