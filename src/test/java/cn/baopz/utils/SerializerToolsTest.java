package cn.baopz.utils;

import cn.baopz.entity.ArticleDetail;
import cn.baopz.entity.ArticleDetailFactory;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class SerializerToolsTest {

    private ArticleDetail articleDetail;
    private Path target;

    @Before
    public void before() throws IOException {
        articleDetail = ArticleDetailFactory.getInstance("column","title", "author", "date"
                , new StringBuilder("content"), new StringBuilder("comment"));
        target = Paths.get(PropertiesTool.getProperty("geekHelper.properties", "persistence.path"));
        String bean = "bean.bin";
        target = target.resolve(bean);
        if (!Files.exists(target)) {
            Files.createFile(target);
        }
    }

    @Test
    public void saveTest() throws IOException {

        boolean result = Serializer.save(articleDetail, target);
        Assert.assertTrue(result);
    }

    @Test
    public void backTest() {
        Object re = Serializer.back(target);
        Assert.assertNotNull(re);
        System.out.println(re.toString());
    }
}
