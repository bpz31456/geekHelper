package cn.baopz.entity;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;

/**
 * @author baopz
 * @date 2018.08.16
 */
public class ColumnBuilder {
    private Column column;
    private Logger logger= LoggerFactory.getLogger(ColumnBuilder.class);

    public ColumnBuilder() {
        this.column = new Column();
    }

    public ColumnBuilder setUrl(String url) {
        logger.debug("url={}",url);
        try {
            Field field = column.getClass().getDeclaredField("url");
            field.setAccessible(true);
            field.set(column, url);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return this;
    }

    public ColumnBuilder setState(String state) {
        logger.debug("state={}",state);
        try {
            Field field = column.getClass().getDeclaredField("state");
            field.setAccessible(true);
            field.set(column, state);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return this;
    }

    public ColumnBuilder setDesc(String desc) {
        logger.debug("desc={}",desc);
        try {
            Field field = column.getClass().getDeclaredField("desc");
            field.setAccessible(true);
            field.set(column, desc);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return this;
    }

    public ColumnBuilder setTitle(String title) {
        logger.debug("title={}",title);
        try {
            Field field = column.getClass().getDeclaredField("title");
            field.setAccessible(true);
            field.set(column, title);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return this;
    }

    public ColumnBuilder setAuthorIntro(String authorIntro) {
        logger.debug("authorIntro={}",authorIntro);
        try {
            Field field = column.getClass().getDeclaredField("authorIntro");
            field.setAccessible(true);
            field.set(column, authorIntro);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return this;
    }

    public ColumnBuilder setAuthor(String author) {
        logger.debug("author={}",author);
        try {
            Field field = column.getClass().getDeclaredField("author");
            field.setAccessible(true);
            field.set(column, author);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return this;
    }

    public ColumnBuilder setPayNum(int payNum) {
        logger.debug("payNum={}",payNum);
        try {
            Field field = column.getClass().getDeclaredField("payNum");
            field.setAccessible(true);
            field.set(column, payNum);
        } catch (NoSuchFieldException | IllegalAccessException e) {
           
            e.printStackTrace();
        }
        return this;
    }

    public Column build(){
        return column;
    }
}
