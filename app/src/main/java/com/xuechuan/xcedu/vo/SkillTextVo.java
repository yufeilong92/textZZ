package com.xuechuan.xcedu.vo;

import com.xuechuan.xcedu.base.BaseVo;

import java.util.List;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: xcedu
 * @Package com.xuechuan.xcedu.vo
 * @Description: 资讯
 * @author: L-BackPacker
 * @date: 2018/4/27 10:41
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018
 */
public class SkillTextVo extends BaseVo {


    private List<DatasBean> datas;

    public List<DatasBean> getDatas() {
        return datas;
    }

    public void setDatas(List<DatasBean> datas) {
        this.datas = datas;
    }

    public static class DatasBean {
        /**
         * children : [{"children":[],"id":109,"isend":true,"parentid":1,"title":"第一篇第一章 燃烧基础知识"},{"children":[],"id":110,"isend":true,"parentid":1,"title":"第一篇第二章 火灾基础知识"},{"children":[],"id":111,"isend":true,"parentid":1,"title":"第一篇第三章 爆炸基础知识"},{"children":[],"id":112,"isend":true,"parentid":1,"title":"第一篇第四章 易燃易爆危险品消防安全知识"}]
         * id : 1
         * isend : false
         * parentid : 0
         * title : 第一篇 消防基础知识
         */

        private int id;
        private boolean isend;
        private int parentid;
        private String title;
        /**
         * 子题数据
         */
        private int qnum;
        private List<ChildrenBeanVo> children;

        public int getQnum() {
            return qnum;
        }

        public void setQnum(int qnum) {
            this.qnum = qnum;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public boolean isIsend() {
            return isend;
        }

        public void setIsend(boolean isend) {
            this.isend = isend;
        }

        public int getParentid() {
            return parentid;
        }

        public void setParentid(int parentid) {
            this.parentid = parentid;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public List<ChildrenBeanVo> getChildren() {
            return children;
        }

        public void setChildren(List<ChildrenBeanVo> children) {
            this.children = children;
        }
/*
        public static class ChildrenBean {
            *//**
         * children : []
         * id : 109
         * isend : true
         * parentid : 1
         * title : 第一篇第一章 燃烧基础知识
         *//*

            private int id;
            private boolean isend;
            private int parentid;
            private String title;
            private List<?> children;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public boolean isIsend() {
                return isend;
            }

            public void setIsend(boolean isend) {
                this.isend = isend;
            }

            public int getParentid() {
                return parentid;
            }

            public void setParentid(int parentid) {
                this.parentid = parentid;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public List<?> getChildren() {
                return children;
            }

            public void setChildren(List<?> children) {
                this.children = children;
            }
        }*/
    }
}
