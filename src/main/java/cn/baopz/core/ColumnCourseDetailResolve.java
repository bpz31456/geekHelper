package cn.baopz.core;

import cn.baopz.entity.Article;
import cn.baopz.entity.ArticleDetail;
import cn.baopz.entity.ArticleDetailFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * Column Course Detail Resolve
 *
 * @author baopz
 */
public class ColumnCourseDetailResolve implements DetailResolve {
    private List<Article> articles;
    private WebDriver webDriver;
    private Logger logger = LoggerFactory.getLogger(ColumnCourseDetailResolve.class);

    public ColumnCourseDetailResolve(List<Article> articles, Set<Cookie> cookieSet, String mainUrl) {
        this.articles = articles;
        webDriver = WebDriverFactory.getChromeWebDriver();
        webDriver.get(mainUrl);
        for (Cookie cookie : cookieSet) {
            webDriver.manage().addCookie(cookie);
        }
    }

    @Override
    public List<ArticleDetail> call() {
        List<ArticleDetail> articleDetails = new ArrayList<>(2 << 5);
        for (Article article : articles) {
            ArticleDetail articleDetail = articleExplain(article);
            articleDetails.add(articleDetail);
        }
        //quit
        webDriver.quit();
        return articleDetails;
    }

    private ArticleDetail articleExplain(Article article) {
        webDriver.get(article.getUrl());
        loading(3);
        String column = webDriver.findElement(By.cssSelector("#app > div > div > div.breadcrumb.breadcrumb > span:nth-child(2) > a")).getText();
        String title = webDriver.findElement(By.cssSelector("#app > div > div > h1")).getText();
        String date = webDriver.findElement(By.cssSelector("#app > div > div > div.article-info > span:nth-child(1)")).getText();
        String author = webDriver.findElement(By.cssSelector("#app > div > div > div.article-info > span:nth-child(2)")).getText();
        String content = webDriver.findElement(By.cssSelector("#app > div > div > div.article-content.typo.common-content")).getText();
        String comment = webDriver.findElement(By.cssSelector("#app > div > div > div.article-comments")).getText();
        ArticleDetail articleDetail = ArticleDetailFactory.getInstance(column, title, author, date, new StringBuilder(content), new StringBuilder(comment));
        logger.debug("article detail {}", articleDetail);
        return articleDetail;
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
