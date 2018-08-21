package cn.baopz.entity;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * @author baopz
 * @date 2018.08.16
 */
public final class ArticleFactory {
    private ArticleFactory() {
    }

    public static Article getInstance(String title, String date, String cover, String desc,String url) {
        Article article = new Article();
        article.setCover(cover);
        article.setTitle(title);
        article.setDate(date);
        article.setDesc(desc);
        article.setUrl(url);
        return article;
    }

}
