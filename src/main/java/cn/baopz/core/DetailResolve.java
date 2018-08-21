package cn.baopz.core;

import cn.baopz.entity.ArticleDetail;

import java.util.List;
import java.util.concurrent.Callable;

/**
 * @author baopz
 */
public interface DetailResolve extends Callable {

    /**
     * The detailed resolve of each column may be different
     * @return
     * @throws Exception
     */
    @Override
    List<ArticleDetail> call() throws Exception;
}
