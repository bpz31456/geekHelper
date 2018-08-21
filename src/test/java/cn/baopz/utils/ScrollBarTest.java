package cn.baopz.utils;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * ScrollBarTest
 */
public class ScrollBarTest {
    private static Logger logger = LoggerFactory.getLogger(ScrollBarTest.class);
    static WebDriver webDriver = null;

    @BeforeClass
    public static void before() {
        try {
            System.setProperty("webdriver.chrome.driver", Objects.requireNonNull(PropertiesTool.getProperty("geekHelper.properties", "webDriver")));
        } catch (IOException e) {
            e.printStackTrace();
        }
        webDriver = new ChromeDriver();
    }


    private void loadingAll() {
        //Control  Scroll bar
        JavascriptExecutor jsExecutor = (JavascriptExecutor) webDriver;
        long lastScrollHeight = (Long) jsExecutor.executeScript("return document.body.scrollHeight");
        jsExecutor.executeScript("(scrollHeight) => window.scrollTo(0,scrollHeight)", lastScrollHeight+1);

        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Long curScrollHeight = (long) jsExecutor.executeScript("return document.body.scrollHeight");
        logger.debug("curScrollHeight:{}",curScrollHeight);
        if (curScrollHeight != lastScrollHeight) {
            loadingAll();
        }
    }

    @Test
    public void scrollBarPointTest() {
        webDriver.get("https://www.taobao.com/");
        JavascriptExecutor javascriptExecutor = ((JavascriptExecutor) webDriver);
        javascriptExecutor.executeScript("window.scrollTo(0,document.body.scrollHeight)");
        Object y = javascriptExecutor.executeScript("return document.body.scrollHeight");
        logger.debug("y.class:{},y：{}",y.getClass().getName(),y);

        //loading all
        loadingAll();
        try {
            TimeUnit.SECONDS.sleep(5);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @AfterClass
    public static void after() {
        webDriver.close();
        webDriver.quit();
    }
}
