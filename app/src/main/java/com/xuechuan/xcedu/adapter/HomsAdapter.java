package com.xuechuan.xcedu.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.xuechuan.xcedu.R;
import com.xuechuan.xcedu.XceuAppliciton.MyAppliction;
import com.xuechuan.xcedu.base.DataMessageVo;
import com.xuechuan.xcedu.ui.AgreementActivity;
import com.xuechuan.xcedu.ui.InfomDetailActivity;
import com.xuechuan.xcedu.ui.home.AdvisoryListActivity;
import com.xuechuan.xcedu.ui.home.ArticleListActivity;
import com.xuechuan.xcedu.ui.home.AtirlceListActivity;
import com.xuechuan.xcedu.ui.home.BookActivity;
import com.xuechuan.xcedu.ui.home.SpecasListActivity;
import com.xuechuan.xcedu.utils.PushXmlUtil;
import com.xuechuan.xcedu.utils.StringUtil;
import com.xuechuan.xcedu.vo.AdvisoryBean;
import com.xuechuan.xcedu.vo.ArticleBean;
import com.xuechuan.xcedu.vo.BannerBean;
import com.xuechuan.xcedu.vo.HomePageVo;
import com.xuechuan.xcedu.weight.DividerItemDecoration;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.listener.OnBannerClickListener;
import com.youth.banner.loader.ImageLoader;

import java.util.ArrayList;
import java.util.List;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: xcedu
 * @Package com.xuechuan.xcedu.adapter
 * @Description: todo
 * @author: L-BackPacker
 * @date: 2018/6/2 16:35
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018
 */
public class HomsAdapter extends RecyclerView.Adapter {

    private String code;
    private Context mContext;
    private HomePageVo mData;
    private final LayoutInflater mInflater;

    public HomsAdapter(Context mContext, HomePageVo mData, String code) {
        this.mContext = mContext;
        this.mData = mData;
        this.code = code;
        mInflater = LayoutInflater.from(mContext);
    }

    public void setData(HomePageVo vo, String codes) {
        mData = vo;
        code = codes;
        notifyDataSetChanged();
    }

    //轮播图
    private int BANNER_VIEW_LAYOUT = 100;
    //教材
    private int MENU_VIEW_LAYOUT = 110;
    //资讯
    private int INFOM_VIEW_LAYOUT = 111;
    //文章
    private int WEN_VIEW_LAYOUT = 112;

    private int MDATA = BANNER_VIEW_LAYOUT;


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == BANNER_VIEW_LAYOUT) {
            View inflate = mInflater.inflate(R.layout.home_banner, null);
            return new BannerViewHolder(inflate);
        } else if (viewType == MENU_VIEW_LAYOUT) {
            return new MenuViewHolder(mInflater.inflate(R.layout.home_meun, null));
        } else if (viewType == INFOM_VIEW_LAYOUT) {
            return new InfomViewHolder(mInflater.inflate(R.layout.home_infom, null));
        } else if (viewType == WEN_VIEW_LAYOUT) {
            return new WenvIewHolder(mInflater.inflate(R.layout.home_wen, null));
        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        if (holder instanceof BannerViewHolder) {
            BannerViewHolder banner = (BannerViewHolder) holder;
            setBanner(banner);
        } else if (holder instanceof MenuViewHolder) {
            MenuViewHolder meun = (MenuViewHolder) holder;
            setMenu(meun);
        } else if (holder instanceof InfomViewHolder) {
            InfomViewHolder infomViewHolder = (InfomViewHolder) holder;
            setInfom(infomViewHolder);
        } else if (holder instanceof WenvIewHolder) {
            WenvIewHolder wenvIewHolder = (WenvIewHolder) holder;
            setWen(wenvIewHolder);
        }

    }

    private void setWen(WenvIewHolder wenvIewHolder) {
        if (mData == null)
            return;
        List<ArticleBean> article = mData.getData().getArticle();
        HomeAllAdapter allAdapter = new HomeAllAdapter(mContext, article);
        GridLayoutManager manager = new GridLayoutManager(mContext, 1);
        manager.setOrientation(GridLayoutManager.VERTICAL);
        wenvIewHolder.mRlvRecommendAll.addItemDecoration(new DividerItemDecoration(mContext, DividerItemDecoration.BOTH_SET, R.drawable.recyclerline));
        wenvIewHolder.mRlvRecommendAll.setLayoutManager(manager);
        wenvIewHolder.mRlvRecommendAll.setAdapter(allAdapter);
        allAdapter.setClickListener(new HomeAllAdapter.onItemClickListener() {
            @Override
            public void onClickListener(Object obj, int position) {
                ArticleBean vo = (ArticleBean) obj;
                Intent intent = InfomDetailActivity.startInstance(mContext, vo.getGourl(),
                        String.valueOf(vo.getId()), vo.getTitle());
                mContext.startActivity(intent);
            }

        });
        wenvIewHolder.mTvArticleMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent instance = AtirlceListActivity.newInstance(mContext, "");
                instance.putExtra(ArticleListActivity.CSTR_EXTRA_TITLE_STR, mContext.getResources().getString(R.string.Allarticle));
                mContext.startActivity(instance);
            }
        });
    }

    private void setInfom(InfomViewHolder infomViewHolder) {
        if (mData == null)
            return;
        List<AdvisoryBean> advisory = mData.getData().getAdvisory();
        HomeContentAdapter allAdapter = new HomeContentAdapter(mContext, advisory);
        GridLayoutManager manager = new GridLayoutManager(mContext, 1);
        manager.setOrientation(GridLayoutManager.VERTICAL);
        infomViewHolder.mRlvRecommendContent.addItemDecoration(new DividerItemDecoration(mContext, DividerItemDecoration.BOTH_SET, R.drawable.recyclerline));
        infomViewHolder.mRlvRecommendContent.setLayoutManager(manager);
        infomViewHolder.mRlvRecommendContent.setAdapter(allAdapter);
        allAdapter.setClickListener(new HomeContentAdapter.onItemClickListener() {
            @Override
            public void onClickListener(Object obj, int position) {
                AdvisoryBean vo = (AdvisoryBean) obj;
              /*  Intent intent = InfomDetailActivity.startInstance(mContext, vo.getGourl(),
                        String.valueOf(vo.getId()), DataMessageVo.USERTYPEA);*/
                Intent intent = AgreementActivity.newInstance(mContext, vo.getGourl(),
                        AgreementActivity.SHAREMARK, vo.getTitle(), vo.getShareurl());
                mContext.startActivity(intent);
            }
        });
        infomViewHolder.mTvInfomMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String str = null;
                str = PushXmlUtil.getInstance().getLocationProvice(mContext, code);
                Intent intent1 = AdvisoryListActivity.newInstance(mContext, code, str);
//                intent1.putExtra(AdvisoryListActivity.CSTR_EXTREA_TITLE, str);
                mContext.startActivity(intent1);
            }
        });
    }

    private void setMenu(MenuViewHolder meun) {
        meun.mIvHomeBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent3 = BookActivity.newInstance(mContext, null, null);
                intent3.putExtra(BookActivity.CSTR_EXTRA_TITLE_STR,
                        mContext.getResources().getString(R.string.home_teacherMateri));
                mContext.startActivity(intent3);
            }
        });

        meun.mIvHomeStandard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2 = SpecasListActivity.newInstance(mContext, null, null);
                intent2.putExtra(SpecasListActivity.CSTR_EXTRA_TITLE_STR,
                        mContext.getResources().getString(R.string.home_specs));
                mContext.startActivity(intent2);
            }
        });
    }

    private void setBanner(final BannerViewHolder banner) {
        if (mData == null)
            return;
        final List<BannerBean> beanList = mData.getData().getBanner();
        ArrayList<String> list = new ArrayList<>();
        for (int i = 0; i < beanList.size(); i++) {
            list.add(beanList.get(i).getImageurl());
        }
        banner.mBanHome.setBannerStyle(BannerConfig.CIRCLE_INDICATOR);
        banner.mBanHome.setIndicatorGravity(BannerConfig.CENTER);
        banner.mBanHome.setDelayTime(2000);
        banner.mBanHome.setImageLoader(new ImageLoader() {
            @Override
            public void displayImage(Context context, Object path, ImageView imageView) {
                MyAppliction.getInstance().displayImages(imageView, (String) path, false);
            }
        });
        banner.mBanHome.setImages(list);

        banner.mBanHome.start();
        banner.mBanHome.setOnBannerClickListener(new OnBannerClickListener() {
            @Override
            public void OnBannerClick(int position) {
                BannerBean bean;
                if (position <= 0) {
                    bean = beanList.get(position);
                } else
                    bean = beanList.get(position - 1);
                if (!StringUtil.isEmpty(bean.getGourl()))
                    mContext.startActivity(AgreementActivity.newInstance(mContext,
                            bean.getGourl(), AgreementActivity.NOSHAREMARK, "", ""));

            }
        });
    }


    @Override
    public int getItemCount() {
        return 4;
    }

    @Override
    public int getItemViewType(int position) {

        if (position == 0) {
            MDATA = BANNER_VIEW_LAYOUT;
        } else if (position == 1) {
            MDATA = MENU_VIEW_LAYOUT;
        } else if (position == 2) {
            MDATA = INFOM_VIEW_LAYOUT;
        } else if (position == 3) {
            MDATA = WEN_VIEW_LAYOUT;
        }
        return MDATA;

    }

    public class BannerViewHolder extends RecyclerView.ViewHolder {
        public Banner mBanHome;

        public BannerViewHolder(View itemView) {
            super(itemView);
            this.mBanHome = (Banner) itemView.findViewById(R.id.ban_home);
        }
    }

    public class MenuViewHolder extends RecyclerView.ViewHolder {
        public ImageView mIvHomeBook;
        public ImageView mIvHomeStandard;

        public MenuViewHolder(View itemView) {
            super(itemView);
            this.mIvHomeBook = (ImageView) itemView.findViewById(R.id.iv_home_book);
            this.mIvHomeStandard = (ImageView) itemView.findViewById(R.id.iv_home_standard);
        }
    }

    public class InfomViewHolder extends RecyclerView.ViewHolder {
        public TextView mTvInfomMore;
        public RecyclerView mRlvRecommendContent;

        public InfomViewHolder(View itemView) {
            super(itemView);
            this.mTvInfomMore = (TextView) itemView.findViewById(R.id.tv_infom_more);
            this.mRlvRecommendContent = (RecyclerView) itemView.findViewById(R.id.rlv_recommend_content);
        }
    }

    public class WenvIewHolder extends RecyclerView.ViewHolder {
        public TextView mTvArticleMore;
        public RecyclerView mRlvRecommendAll;

        public WenvIewHolder(View itemView) {
            super(itemView);
            this.mTvArticleMore = (TextView) itemView.findViewById(R.id.tv_article_more);
            this.mRlvRecommendAll = (RecyclerView) itemView.findViewById(R.id.rlv_recommend_all);
        }
    }


}
