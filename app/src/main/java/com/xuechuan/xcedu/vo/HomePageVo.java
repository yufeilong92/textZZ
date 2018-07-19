package com.xuechuan.xcedu.vo;

import com.xuechuan.xcedu.base.BaseVo;

import java.util.List;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: xcedu
 * @Package com.xuechuan.xcedu.vo
 * @Description: todo
 * @author: L-BackPacker
 * @date: 2018/4/18 16:41
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018
 */
public class HomePageVo extends BaseVo {

    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }
    public static class DataBean {
        private List<AdvisoryBean> advisory;
        private List<ArticleBean> article;
        private List<BannerBean> banner;
        public List<AdvisoryBean> getAdvisory() {
            return advisory;
        }
        public void setAdvisory(List<AdvisoryBean> advisory) {
            this.advisory = advisory;
        }
        public List<ArticleBean> getArticle() {
            return article;
        }
        public void setArticle(List<ArticleBean> article) {
            this.article = article;
        }
        public List<BannerBean> getBanner() {
            return banner;
        }
        public void setBanner(List<BannerBean> banner) {
            this.banner = banner;
        }

    }
}
