package cn.baopz.core;

import cn.baopz.utils.PropertiesTool;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * @author baopz
 */
public class UsernameAndPasswordAuthentication implements Authentication{
    private Logger logger = LoggerFactory.getLogger(UsernameAndPasswordAuthentication.class);
    private String mainUrl;
    private String username;
    private String password;
    private WebDriver webDriver;
    @Override
    public boolean check() throws LoginException{
        return login();
    }

    public UsernameAndPasswordAuthentication(WebDriver webDriver){
        this.webDriver = webDriver;
        this.mainUrl = geekTimeMainUrl();
        accountInit();
    }

    private void accountInit() {
        try {
            username = PropertiesTool.getProperty("geekHelper.properties", "geek.username");
            password = PropertiesTool.getProperty("geekHelper.properties", "geek.password");
        } catch (IOException e) {
            //ignore
        }
    }

    /**
     * login
     *
     * @throws LoginException
     */
    private boolean login() throws LoginException {
        loading(2);
        if (webDriver == null) {
            throw new LoginException("webDriver not initialization.");
        }
        if (mainUrl == null) {
            throw new LoginException("geek.main not found in geekHelper.properties.");
        }

        webDriver.get(mainUrl);

        loading(2);
        WebElement userInfo = webDriver.findElement(By.cssSelector("#app > div > div.fixed-header > div > div > div.info > div.userinfo"));
        logger.debug("Are you logged in{}", userInfo.getText());
        if (!(userInfo.getText().contains(KeyWords.NOT_LOGGED_IN.value()))) {
            return false;
        }
        //find login div element
        webDriver.findElement(By.cssSelector("#app > div > div.fixed-header > div > div > div.info > div.userinfo > span > a:nth-child(1)")).click();
        loading(1);
        //input username and password
        webDriver.findElement(By.cssSelector("body > div > div.container > div.card > div.nw-phone-container > div.nw-phone-wrap > input"))
                .sendKeys(new String[]{username});
        webDriver.findElement(By.cssSelector("body > div > div.container > div.card > div.input-wrap > input"))
                .sendKeys(new String[]{password});
        //click login button
        webDriver.findElement(By.cssSelector("body > div > div.container > div.card > button")).click();
        loading(2);
        logger.debug("curUrl:{},main:{}",webDriver.getCurrentUrl(),mainUrl);
        return (mainUrl+"/").equals(webDriver.getCurrentUrl());
    }

    private String geekTimeMainUrl() {
        String url = null;
        try {
            url = PropertiesTool.getProperty("geekHelper.properties", "geek.main");
        } catch (IOException e) {
            //ignore
        }
        return url;
    }

    private void loading(int sec) {
        try {
            TimeUnit.SECONDS.sleep(sec);
        } catch (InterruptedException e) {
            //ignore
        }
        webDriver.manage().timeouts().implicitlyWait(sec, TimeUnit.SECONDS);
    }
}
