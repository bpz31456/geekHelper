package cn.baopz.entity;


import java.io.Serializable;
import java.util.Objects;

/**
 * @author baopz
 * @date 2018.08.16
 */
public class ArticleDetail implements Serializable {
    private String column;
    private String title;
    private String author;
    private String date;
    private StringBuilder content;
    private StringBuilder comment;

    @Override
    public String toString() {
        return "ArticleDetail{" +
                "column='" + column + '\'' +
                ", title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", date='" + date + '\'' +
                '}';
    }

    public String getColumn() {
        return column;
    }

    public void setColumn(String column) {
        this.column = column;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ArticleDetail)) {
            return false;
        }
        ArticleDetail that = (ArticleDetail) o;
        return Objects.equals(getTitle(), that.getTitle());
    }

    @Override
    public int hashCode() {

        return Objects.hash(getTitle());
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public StringBuilder getComment() {
        return comment;
    }

    public void setComment(StringBuilder comment) {
        this.comment = comment;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public StringBuilder getContent() {
        return content;
    }

    public void setContent(StringBuilder content) {
        this.content = content;
    }
}
