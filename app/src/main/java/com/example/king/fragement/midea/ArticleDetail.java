package com.example.king.fragement.midea;

/**
 * Created by Kings on 2016/4/10.
 */
public class ArticleDetail {
    public String postId;
    public String content;

    public ArticleDetail() {
    }

    public ArticleDetail(String pid, String html) {
        postId = pid;
        content = html;
    }

}
