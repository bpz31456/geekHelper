package cn.baopz.core;

import cn.baopz.entity.ArticleDetail;
import cn.baopz.utils.FileTool;
import cn.baopz.utils.PropertiesTool;
import cn.baopz.utils.Serializer;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Set;

/**
 * @author baopz
 * @date 2018.08.16
 */
public class DefaultArticleFetcher extends AbstractFetcher {

    @Override
    protected void persistence(Set<ArticleDetail> articleDetails) throws PersistenceException {
        articleDetails.forEach(articleDetail -> {
            try {
                Path savePath = Paths.get(PropertiesTool.getProperty("geekHelper.properties", "persistence.path")).resolve(FileTool.windowsFileNameFilter(articleDetail.getColumn()));
                if (!Files.exists(savePath)) {
                    Files.createDirectories(savePath);
                }
                savePath = savePath.resolve(FileTool.windowsFileNameFilter(articleDetail.getTitle()));
                if (!Files.exists(savePath)) {
                    Files.createFile(savePath);
                }
                Serializer.save(articleDetail, savePath);
            } catch (IOException e) {
                logger.warn("persistence.path not found in geekHelper.properties");
            }
        });
    }
}
