package cn.baopz.core;

import cn.baopz.entity.Article;
import org.openqa.selenium.Cookie;

import javax.xml.soap.Detail;
import java.util.List;
import java.util.Set;

/**
 * @author baopz
 */
public final class DetailResolveFactory {
    private DetailResolveFactory(){}

    public static DetailResolve getInstance(List<Article> articles, Set<Cookie> cookieSet, String mainUrl){
        return new ColumnCourseDetailResolve(articles,cookieSet,mainUrl);
    }
}
