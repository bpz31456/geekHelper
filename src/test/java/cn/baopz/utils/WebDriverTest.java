package cn.baopz.utils;

import cn.baopz.core.WebDriverFactory;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.TimeUnit;

public class WebDriverTest {
    private Logger logger = LoggerFactory.getLogger(WebDriverTest.class);
    private WebDriver webDriver;

    @Before
    public void before() {
        webDriver = WebDriverFactory.getChromeWebDriver();
    }

    @Test
    public void webDriverGetPageSourceTest() {

        webDriver.get("https://blog.csdn.net/a_xin21/article/details/81741395");
        String s = webDriver.getPageSource();
        logger.debug("pageSource:{}", s);

        webDriver.close();
        webDriver.quit();
    }

    @Test
    public void takesScreenshotTest() {
        webDriver.get("https://blog.csdn.net/bpz31456/article/details/81875735");
        File source = ((TakesScreenshot) webDriver).getScreenshotAs(OutputType.FILE);
        try {
            String targetPath = new String("C:\\Users\\baopz\\Desktop\\test\\image\\geek");
            Path target = Paths.get(targetPath);
            if (!Files.exists(target)) {
                Files.createDirectories(target);
            }
            target = target.resolve("1.png");
            if (!Files.exists(target)) {
                Files.createFile(target);
            }
            Files.copy(source.toPath(), target);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            TimeUnit.SECONDS.sleep(5);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        webDriver.quit();
    }

    @After
    public void after() {
        webDriver.close();
        webDriver.quit();
    }

}
