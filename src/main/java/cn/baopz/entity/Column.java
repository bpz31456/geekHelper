package cn.baopz.entity;

import java.util.Objects;

/**
 * @author baopz
 * @date 2018.08.16
 */
public class Column {
    private String author;
    private String authorIntro;
    private String title;
    private String desc;
    private String state;
    private String url;
    private int payNum;

    public String getUrl() {
        return url;
    }

    @Override
    public String toString() {
        return "Column{" +
                "author='" + author + '\'' +
                ", authorIntro='" + authorIntro + '\'' +
                ", title='" + title + '\'' +
                ", desc='" + desc + '\'' +
                ", state='" + state + '\'' +
                ", url='" + url + '\'' +
                ", payNum=" + payNum +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Column)) {
            return false;
        }
        Column column = (Column) o;
        return payNum == column.payNum &&
                Objects.equals(author, column.author) &&
                Objects.equals(title, column.title);
    }

    @Override
    public int hashCode() {

        return Objects.hash(author, title, payNum);
    }

    public boolean valid() {
        return state.contains("已订阅");
    }

    public String getTitle() {
        return title;
    }
}
