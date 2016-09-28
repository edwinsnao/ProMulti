package com.example.king.fragement.midea;

import android.text.TextUtils;

import util.HtmlUtil;

/**
 * Created by Kings on 2016/4/10.
 */
public class ArticleDetailPresenter extends BasePresenter<ArticleDetailView> {

    /**
     * 加载文章的具体内容,先从数据库中加载,如果数据库中有，那么则不会从网络上获取
     *
     * @param postId
     */
    public void fetchArticleContent(final String postId,String title) {
        // 从数据库上获取文章内容缓存
        // ArticleDetail cacheDetail =
        // DatabaseHelper.getInstance().loadArticleDetail(postId);
        // String articleContent = cacheDetail.content;

        String articleContent = loadArticleContentFromDB(postId);
        if (!TextUtils.isEmpty(articleContent)) {
            String htmlContent = HtmlUtil.wrapArticleContent(title, articleContent);
            mView.onFetchedArticleContent(htmlContent);
        } else {
            fetchContentFromServer(postId, title);
        }
    }

    public String loadArticleContentFromDB(String postId) {
        return DatabaseHelper.getInstance().loadArticleDetail(postId).content;
    }

    protected void fetchContentFromServer(final String postId,final String title) {
        mView.onShowLoding();
        String reqURL = "http://www.devtf.cn/api/v1/?type=article&post_id=" + postId;
        HttpFlinger.get(reqURL,
                new DataListener<String>() {
                    @Override
                    public void onComplete(String result) {
                        mView.onHideLoding();
                        if (TextUtils.isEmpty(result)) {
                            result = "未获取到文章内容~";
                        }
                        mView.onFetchedArticleContent(HtmlUtil.wrapArticleContent(title, result));
                        DatabaseHelper.getInstance().saveArticleDetail(
                                new ArticleDetail(postId, result));
                    }
                });
    }
}
