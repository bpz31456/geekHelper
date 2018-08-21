package cn.baopz.entity;

import org.openqa.selenium.WebElement;

/**
 * @author baopz
 * @date 2018.08.16
 */
public class ArticleContext {
    private String url;
    private WebElement webElement;

    public ArticleContext(String url, WebElement webElement) {
        this.url = url;
        this.webElement = webElement;
    }

    public String getUrl() {
        return url;
    }

    public WebElement getWebElement() {
        return webElement;
    }
}
