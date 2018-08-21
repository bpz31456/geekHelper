package cn.baopz.core;

import cn.baopz.entity.*;
import cn.baopz.threadPool.ArticleFetcherThreadPoolManager;
import cn.baopz.utils.PropertiesTool;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author baopz
 * https://time.geekbang.org/paid-content template
 */
public abstract class AbstractFetcher implements Runnable {
    private static final Pattern pattern = Pattern.compile("(\\d+)(\\D+)");
    private Authentication authentication;
    private String paidContentUrl;
    private String mainUrl;

    protected WebDriver webDriver;
    protected Logger logger = LoggerFactory.getLogger(this.getClass());
    protected Set<Column> columns = new HashSet<>();
    protected Map<Column, List<Article>> articleMaps = new ConcurrentHashMap<>();
    protected Set<ArticleDetail> articleDetails = new CopyOnWriteArraySet<>();

    protected AbstractFetcher() {
        webDriver = WebDriverFactory.getChromeWebDriver();
        authentication = AuthenticationFactory.getInstance(webDriver);
        paidContentUrl = geekTimePaidContentUrl();
        mainUrl = geekTimeMainUrl();
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

    private String geekTimePaidContentUrl() {
        String url = null;
        try {
            url = PropertiesTool.getProperty("geekHelper.properties", "geek.paidContent");
        } catch (IOException e) {
            //ignore
        }
        return url;
    }

    /**
     * Visit a section
     *
     * @throws PaidContentBrowseException
     */
    protected void paidContent() throws PaidContentBrowseException {
        //wait a moment
        loading(5);
        webDriver.get(paidContentUrl);
        loading(2);
        List<WebElement> webColumn2s = webDriver.findElements(By.cssSelector("#app > div > div.content-wrap > div:nth-child(2) > div"));
        List<WebElement> webColumn4s = webDriver.findElements(By.cssSelector("#app > div > div.content-wrap > div:nth-child(4) > div"));
        List<WebElement> webColumn6s = webDriver.findElements(By.cssSelector("#app > div > div.content-wrap > div:nth-child(6) > div"));

        List<WebElement> webElements = new ArrayList<>(webColumn2s.size() + webColumn4s.size() + webColumn6s.size());
        webElements.addAll(webColumn2s);
        webElements.addAll(webColumn4s);
        webElements.addAll(webColumn6s);

        for (WebElement webColumn : webElements) {
            ColumnBuilder columnBuilder = new ColumnBuilder();
            String url = webColumn.getAttribute("data-gk-spider-link");
            String author = webColumn.findElement(By.cssSelector("div.column-item-bd > div > div.column-item-bd-info-hd > div > div.column-item-author-info > div.column-item-author-name")).getText();
            String authorIntro = webColumn.findElement(By.cssSelector("div.column-item-bd > div > div.column-item-bd-info-hd > div > div.column-item-author-info > div.column-item-author-intro")).getText();
            String title = webColumn.findElement(By.cssSelector("div.column-item-bd > div > div.column-item-bd-info-bd > span.column-item-bd-info-bd-title")).getText();
            String desc = webColumn.findElement(By.cssSelector("div.column-item-bd > div > div.column-item-bd-info-bd > span.column-item-bd-info-bd-desc")).getText();
            String state = webColumn.findElement(By.cssSelector("div.column-item-ft > span")).getText();
            String payNum = webColumn.findElement(By.cssSelector("div.column-item-ft > p > span:nth-child(1)")).getText();

            Column column = columnBuilder.setUrl(mainUrl + url)
                    .setAuthor(author)
                    .setAuthorIntro(authorIntro)
                    .setPayNum(splitPayNum(payNum))
                    .setState(state)
                    .setDesc(desc)
                    .setTitle(title)
                    .build();
            logger.debug(column.toString());
            columns.add(column);
        }
    }

    private int splitPayNum(String payNum) {
        Matcher matcher = pattern.matcher(payNum);
        if (matcher.find()) {
            return Integer.valueOf(matcher.group(1));
        }
        return 0;
    }

    /**
     * Access all columns,Sort the articles in the corresponding column
     *
     * @throws ColumnVisitorExcption
     */
    protected void readColumns() throws ColumnVisitorExcption {
        logger.debug("column length of collection{}", columns.size());
        for (Column column : columns) {
            if (!column.valid()) {
                continue;
            }
            webDriver.get(column.getUrl());
            loading(new Random().nextInt(3) + 2);

            List<WebElement> webArticles = loadingAllArticles(0);

            List<Article> articles = new ArrayList<>(2 << 5);
            for (WebElement webArticle : webArticles) {
                String title = webArticle.findElement(By.cssSelector("div > h2")).getText();
                String date = webArticle.findElement(By.cssSelector("div > div.article-item-hd > span")).getText();
                String cover = webArticle.findElement(By.cssSelector("div > img")).getAttribute("src");
                String desc = webArticle.findElement(By.cssSelector("div > p")).getText();
                String url = webArticle.findElement(By.cssSelector("div > div.article-item-more > a")).getAttribute("href");
                Article article = ArticleFactory.getInstance(title, date, cover, desc, url);
                articles.add(article);
                logger.debug(article.toString());
            }
            articleMaps.put(column, articles);
        }
    }

    private List<WebElement> loadingAllArticles(int lastSize) {
        List<WebElement> webArticles = webDriver.findElements(By.cssSelector("#app > div > div.main-wrapper > div.content-list > div > div"));
        int curSize = webArticles.size();
        if (curSize == 0 || lastSize == curSize) {
            return webArticles;
        }
        JavascriptExecutor jsExecutor = (JavascriptExecutor) webDriver;
        jsExecutor.executeScript("arguments[0].scrollIntoView(true);", webArticles.get(curSize - 1));
        loading(5);
        return loadingAllArticles(curSize);
    }

    @Deprecated
    private void loadingAllColumns() {
        logger.debug("enter loadingAllColumns function.");
        //Control  Scroll bar
        JavascriptExecutor jsExecutor = (JavascriptExecutor) webDriver;
        long lastScrollHeight = (Long) jsExecutor.executeScript("return document.body.scrollHeight");
        jsExecutor.executeScript("(scrollHeight) => window.scrollTo(0,scrollHeight)", lastScrollHeight);

        loading(2);

        Long curScrollHeight = (long) jsExecutor.executeScript("return document.body.scrollHeight");
        logger.debug("lastScrollHeight:{},curScrollHeight:{}", lastScrollHeight, curScrollHeight);
        if (curScrollHeight != lastScrollHeight) {
            loadingAllColumns();
        }
    }

    protected void loading(int sec) {
        try {
            TimeUnit.SECONDS.sleep(sec);
        } catch (InterruptedException e) {
            //ignore
        }
        webDriver.manage().timeouts().implicitlyWait(sec, TimeUnit.SECONDS);
    }

    private void readDetail() throws ArticleReadException, PersistenceException {
        ExecutorService executorService = ArticleFetcherThreadPoolManager.getSingleThreadPool("detail-%d");
        List<Future<List<ArticleDetail>>> futures = new ArrayList<>();
        for (Column column : articleMaps.keySet()) {
            DetailResolve detailResolve = DetailResolveFactory.getInstance(articleMaps.get(column), webDriver.manage().getCookies(), mainUrl);
            Future<List<ArticleDetail>> future = executorService.submit(detailResolve);
            futures.add(future);
        }
        for (Future<List<ArticleDetail>> future : futures) {
            try {
                articleDetails.addAll(future.get());
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        }
        executorService.shutdown();
    }

    @Override
    public void run() {
        //login
        try {
            authentication.check();
        } catch (LoginException e) {
            e.printStackTrace();
        }
        try {
            paidContent();
        } catch (PaidContentBrowseException e) {
            e.printStackTrace();
            logger.error(e.getMessage());
            webDriver.quit();
            return;
        }
        try {
            readColumns();
        } catch (ColumnVisitorExcption e) {
            e.printStackTrace();
            logger.error(e.getMessage());
            webDriver.quit();
            return;
        }

        try {
            readDetail();
        } catch (ArticleReadException e) {
            e.printStackTrace();
            logger.error(e.getMessage());
            webDriver.quit();
            return;
        } catch (PersistenceException e) {
            e.printStackTrace();
            logger.error(e.getMessage());
        }

        try {
            persistence(articleDetails);
        } catch (PersistenceException e) {
            e.printStackTrace();
        }

        if (webDriver != null) {
            webDriver.quit();
        }
    }

    /**
     * date persistence
     * @param articleDetails
     * @throws PersistenceException
     */
    protected abstract void persistence(Set<ArticleDetail> articleDetails) throws PersistenceException;

}
