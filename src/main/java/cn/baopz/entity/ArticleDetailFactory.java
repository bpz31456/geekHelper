package cn.baopz.entity;

/**
 * @author baopz
 * @date 2018.08.16
 */
public final class ArticleDetailFactory {

    /**
     * instantiation a ArticleDetail
     *
     * @param title
     * @param author
     * @param content
     * @param comment
     * @return
     */
    public static ArticleDetail getInstance(String column,String title, String author,String date
            , StringBuilder content, StringBuilder comment) {
        ArticleDetail articleDetail = new ArticleDetail();
        articleDetail.setColumn(column);
        articleDetail.setAuthor(author);
        articleDetail.setComment(comment);
        articleDetail.setTitle(title);
        articleDetail.setContent(content);
        articleDetail.setDate(date);
        return articleDetail;
    }
}
