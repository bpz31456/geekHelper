package cn.baopz.utils;

import cn.baopz.core.Authentication;
import cn.baopz.core.AuthenticationFactory;
import cn.baopz.core.LoginException;
import cn.baopz.core.WebDriverFactory;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;

public class ShareCookiesTest {
    private Logger logger = LoggerFactory.getLogger(ShareCookiesTest.class);
    private WebDriver mainDriver;

    /**
     * 在执行Test方法之前，登录
     */
    @Before
    public void login() {
        mainDriver = WebDriverFactory.getChromeWebDriver();
        Authentication authentication = AuthenticationFactory.getInstance(mainDriver);
        try {
            Assert.assertTrue(authentication.check());
            try {
                TimeUnit.SECONDS.sleep(5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } catch (LoginException e) {
            e.printStackTrace();
            mainDriver.close();
            mainDriver.quit();
        }
    }

    /**
     * 共享cookies
     */
    @Test
    public void shareCookies() {
        WebDriver webDriver = WebDriverFactory.getChromeWebDriver();
        webDriver.get("https://time.geekbang.org");
        for (Cookie cookie : mainDriver.manage().getCookies()) {
            logger.debug("{}:{}", cookie.getName(), cookie.getValue());
            try {
                webDriver.manage().addCookie(cookie);
            } catch (Exception e) {
                //ignore
            }
        }
        webDriver.get("https://time.geekbang.org/column/article/13091");
        try {
            TimeUnit.SECONDS.sleep(5);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        webDriver.close();
        webDriver.quit();

        mainDriver.close();
        mainDriver.quit();
    }
}
