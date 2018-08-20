package com.yc.YcRecyclerViewBaseAdapter.adapter;

import android.content.Context;
import android.os.Handler;
import android.support.annotation.IntRange;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.yc.YcRecyclerViewBaseAdapter.R;
import com.yc.YcRecyclerViewBaseAdapter.base.YcBaseViewHolder;
import com.yc.YcRecyclerViewBaseAdapter.interfaces.OnItemChildClickListener;
import com.yc.YcRecyclerViewBaseAdapter.interfaces.OnItemClickListener;
import com.yc.YcRecyclerViewBaseAdapter.interfaces.OnLoadMoreListener;
import com.yc.YcRecyclerViewBaseAdapter.utils.UiUtils;

import java.util.ArrayList;
import java.util.List;

import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;
import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;

public abstract class YcBaseAdapter<T, K extends YcBaseViewHolder> extends RecyclerView.Adapter<ViewHolder> {

    private List<T> mDatas = new ArrayList<>();
    private Handler mHandler = new Handler();
    private MyThread mMyThread = new MyThread();
    private boolean mLoadMoreEnable = false; //是否开启加载更多
    private boolean isLoading;//是否正在加载更多

    //隐藏加载更多布局
    private boolean goneLoadingMore;
    private boolean isGoneLoadMoreIng;

    /**
     * 设置一个很大的数字,尽可能避免和用户的adapter冲突
     * 下面的ItemViewType是保留值(ReservedItemViewType),如果用户的adapter与它们重复将会强制抛出异常。
     * 不过为了简化,我们检测到重复时对用户的提示是ItemViewType必须小于100000
     */
    static final int TYPE_COMMON_VIEW = 100001;//普通类型 Item
    private static final int TYPE_FOOTER_VIEW = 100002;//footer类型 Item
    private static final int TYPE_EMPTY_VIEW = 100003;//empty view，即初始化加载时的提示View
    private static final int TYPE_NODATA_VIEW = 100004;//初次加载无数据的默认空白view
    private static final int TYPE_RELOAD_VIEW = 100005;//初次加载无数据的可重新加载或提示用户的view
    private static final int TYPE_NORMAL_HEADER_VIEW = 200000; //是正常的 header
    private static final int TYPE_NORMAL_FOOTER_VIEW = 300000; //是正常的 footer

    private View mLoadingView; //分页加载中view
    private View mLoadFailedView; //分页加载失败view
    private View mLoadEndView; //分页加载结束view
    private FrameLayout mEmptyLayout; //首次预加载view
    private View mReloadView; //首次预加载失败、或无数据的view
    private RelativeLayout mLoadMoreFooterLayout;//加载更多脚布局 footer view
    //header footer
    private LinearLayout mNormalHeaderLayout;
    private LinearLayout mNormalFooterLayout;
    private boolean isRemoveEmptyView; //移除空视图
    private OnLoadMoreListener mLoadMoreListener;

    private Context mContext;

    public static final int STATUS_DEFAULT = 1;//一直显示加载完成
    public static final int STATUS_END_SHOW_GONE = 2;//显示1秒后隐藏 , 持续显示与隐藏
    public static final int STATUS_END_GONE = 3;//隐藏加载完成布局
    private OnItemClickListener<T> mItemClickListener;
    private OnItemChildClickListener mOnItemChildClickListeners;

    public YcBaseAdapter(Context context) {
        mContext = context;
    }

    //子类必须实现将数据传递给父类onBindViewHolder
    protected abstract void convert(YcBaseViewHolder holder, T data, int position);

    //获取子类的布局
    protected abstract int getItemLayoutId();


    private RecyclerView mRecyclerView;

    protected RecyclerView getRecyclerView() {
        return mRecyclerView;
    }

    private void setRecyclerView(RecyclerView recyclerView) {
        mRecyclerView = recyclerView;
    }

    private void checkNotNull() {
        if (getRecyclerView() == null) {
            throw new RuntimeException("YcBaseAdapter -- please bind recyclerView first!");
        }
    }

    /**
     * same as recyclerView.setAdapter(), and save the instance of recyclerView
     */
    public void bindToRecyclerView(RecyclerView recyclerView) {
        if (getRecyclerView() != null) {
            throw new RuntimeException("YcBaseAdapter -- Don't bind twice recyclerView");
        }
        setRecyclerView(recyclerView);
        getRecyclerView().setAdapter(this);
    }


    @Override
    public int getItemCount() {

        int count;
        if (getEmptyViewCount() == 1) {
            count = 1;
            if (getNormalHeaderLayoutCount() != 0) {
                count++;
            }

            if (getNormalFooterLayoutCount() != 0) {
                count++;
            }

        } else {
            count = getNormalHeaderLayoutCount() + mDatas.size() + getNormalFooterLayoutCount() + getLoadMoreFooterViewCount();
        }
        return count;
    }


    @Override
    public int getItemViewType(int position) {

        if (getEmptyViewCount() == 1) {
            if (mEmptyLayout != null && !isRemoveEmptyView && getNormalHeaderLayoutCount() == 0 && getNormalFooterLayoutCount() == 0) {
                return TYPE_EMPTY_VIEW;
            }

            if (isRemoveEmptyView && mReloadView != null) {
                return TYPE_RELOAD_VIEW;
            }

            boolean header = getNormalHeaderLayoutCount() != 0;
            switch (position) {
                case 0:
                    if (header) {
                        return TYPE_NORMAL_HEADER_VIEW;
                    } else {
                        return TYPE_EMPTY_VIEW;
                    }
                case 1:
                    if (header) {
                        return TYPE_EMPTY_VIEW;
                    } else {
                        return TYPE_NORMAL_FOOTER_VIEW;
                    }
                case 2:
                    return TYPE_NORMAL_FOOTER_VIEW;
                default:
                    return TYPE_EMPTY_VIEW;
            }

        }
        if (isNormalHeaderView(position) && getNormalHeaderLayoutCount() != 0) {
            return TYPE_NORMAL_HEADER_VIEW;
        }
        if (isNormalFooterView(position) && getNormalFooterLayoutCount() != 0) {
            return TYPE_NORMAL_FOOTER_VIEW;
        }
        if (isLoadMoreFooterView(position) && getLoadMoreFooterLayoutCount() != 0) {
            return TYPE_FOOTER_VIEW;
        }


        return TYPE_COMMON_VIEW;
    }

    @Override
    public YcBaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (mContext == null) {
            mContext = parent.getContext();
        }
        YcBaseViewHolder viewHolder = null;
        switch (viewType) {
            case TYPE_NORMAL_HEADER_VIEW:
                if (mNormalHeaderLayout == null && mContext != null) {
                    mNormalHeaderLayout = new LinearLayout(mContext);
                }
                viewHolder = YcBaseViewHolder.create(mNormalHeaderLayout);
                break;

            case TYPE_NORMAL_FOOTER_VIEW:
                if (mNormalFooterLayout == null && mContext != null) {
                    mNormalFooterLayout = new LinearLayout(mContext);
                }
                viewHolder = YcBaseViewHolder.create(mNormalFooterLayout);
                break;
            case TYPE_FOOTER_VIEW:
                if (mLoadMoreFooterLayout == null && mContext != null) {
                    mLoadMoreFooterLayout = new RelativeLayout(mContext);
                }
                viewHolder = YcBaseViewHolder.create(mLoadMoreFooterLayout);
                break;
            case TYPE_EMPTY_VIEW:

                if (mEmptyLayout == null && mContext != null) {
                    mEmptyLayout = new FrameLayout(mContext);
                }
                viewHolder = YcBaseViewHolder.create(mEmptyLayout);
                break;

            case TYPE_RELOAD_VIEW:
                viewHolder = YcBaseViewHolder.create(mReloadView);
                break;
            default:
                //正常布局
                viewHolder = YcBaseViewHolder.create(mContext, getItemLayoutId(), parent);
        }
        //在创建view的时候 将当前适配器传递给viewholder
        viewHolder.setAdapter(this);
        return viewHolder;


    }

    //判断是那种类型的条目 ,空布局,脚布局 , 无数据布局,加载的脚布局,父类的头布局
    protected boolean isCommonItemView(int viewType) {
        return viewType != TYPE_EMPTY_VIEW && viewType != TYPE_FOOTER_VIEW
                && viewType != TYPE_NODATA_VIEW && viewType != TYPE_RELOAD_VIEW
                && viewType != TYPE_NORMAL_HEADER_VIEW && viewType != TYPE_NORMAL_FOOTER_VIEW;
    }


    public class MyThread implements Runnable {

        public synchronized void run() {
            isGoneLoadMoreIng = false;
            notifyItemRemoved(getItemCount());
        }

    }

    /**
     * 添加新的footer view
     *
     * @param footerView
     */
    private void addLoadMoreFooterView(View footerView) {

        if (mLoadMoreFooterLayout == null && mContext != null) {
            mLoadMoreFooterLayout = new RelativeLayout(mContext);
        }

        if (!mLoadMoreEnable || footerView == null) {
            return;
        }

        removeLoadMoreFooterView();
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        mLoadMoreFooterLayout.addView(footerView, params);
    }

    /**
     * 清空footer view
     */
    private void removeLoadMoreFooterView() {
        if (mLoadMoreFooterLayout != null) {
            mLoadMoreFooterLayout.removeAllViews();
        }
    }

    /**
     * 初始化加载中布局
     *
     * @param loadingView
     */
    public void setLoadingView(View loadingView) {
        mLoadingView = loadingView;
        addLoadMoreFooterView(mLoadingView);
    }

    public void setLoadingView(int loadingId) {
        if (mContext == null)
            return;
        setLoadingView(UiUtils.inflate(mContext, loadingId));
    }

    /**
     * 初始加载失败布局
     *
     * @param loadFailedView
     */
    public void setLoadFailedView(View loadFailedView) {
        mLoadFailedView = loadFailedView;
    }

    public void setLoadFailedView(int loadFailedId) {
        if (mContext == null)
            return;

        setLoadFailedView(UiUtils.inflate(mContext, loadFailedId));
    }

    /**
     * 初始化全部加载完成布局
     *
     * @param loadEndView
     */
    public void setLoadEndView(View loadEndView) {
        mLoadEndView = loadEndView;
    }

    public void setLoadEndView(int loadEndId) {
        if (mContext == null)
            return;
        setLoadEndView(UiUtils.inflate(mContext, loadEndId));
    }

    public void setEmptyView() {
        if (mContext == null) {
            throw new RuntimeException("YcBaseAdapter -- please Create Adapter first!");
        }
        setEmptyView(UiUtils.inflate(mContext, R.layout.empty_layout));
    }

    public void setEmptyView(int layoutEmptyId) {
        if (mContext == null)
            return;
        setEmptyView(UiUtils.inflate(mContext, layoutEmptyId));
    }

    /**
     * 初始化emptyView
     *
     * @param emptyView
     */
    public void setEmptyView(View emptyView) {
        setEmptyView(emptyView, RecyclerView.LayoutParams.MATCH_PARENT);
    }

    /**
     * 初始化emptyView
     *
     * @param emptyView
     */
    public void setEmptyView(View emptyView, int height) {
        setEmptyView(emptyView, RecyclerView.LayoutParams.MATCH_PARENT, height);
    }


    /**
     * 初始化emptyView
     *
     * @param emptyView
     */
    public void setEmptyView(View emptyView, int width, int height) {
        boolean insert = false;
        if (mEmptyLayout == null) {
            mEmptyLayout = new FrameLayout(emptyView.getContext());
            final RecyclerView.LayoutParams layoutParams = new RecyclerView.LayoutParams(width, height);
            final ViewGroup.LayoutParams lp = emptyView.getLayoutParams();
            if (lp != null) {
                layoutParams.width = lp.width;
                layoutParams.height = lp.height;
            }
            mEmptyLayout.setLayoutParams(layoutParams);
            insert = true;
        }
        mEmptyLayout.removeAllViews();
        mEmptyLayout.addView(emptyView);
        if (insert) {
            if (getEmptyViewCount() == 1) {
                int position = 0;
                if (getNormalHeaderLayoutCount() != 0) {
                    position++;
                }
                notifyItemInserted(position);
            }
        }
    }

    /**
     * When the current adapter is empty, the BaseQuickAdapter can display a special view
     * called the empty view. The empty view is used to provide feedback to the user
     * that no data is available in this AdapterView.
     *
     * @return The view to show if the adapter is empty.
     */
    public View getEmptyView() {
        return mEmptyLayout;
    }

    /**
     * 移除emptyView
     */
    public void removeEmptyView() {
        isRemoveEmptyView = true;
        notifyDataSetChanged();
        if (mEmptyLayout != null)
            mEmptyLayout.removeAllViews();
    }

    /**
     * 初次预加载失败、或无数据可显示该view，进行重新加载或提示用户无数据
     *
     * @param reloadView
     */
    public void setReloadView(View reloadView) {
        mReloadView = reloadView;
        removeEmptyView();
    }

    public void setReloadView(int reloadId) {
        if (mContext == null)
            return;
        setReloadView(UiUtils.inflate(mContext, reloadId));
    }

    /**
     * StaggeredGridLayoutManager模式时，HeaderView、FooterView可占据一行
     * <p>
     * Called when a view created by this adapter has been attached to a window.
     * simple to solve item will layout using all
     * {@link #setFullSpan(RecyclerView.ViewHolder)}
     *
     * @param holder
     */
    @Override
    public void onViewAttachedToWindow(RecyclerView.ViewHolder holder) {
        super.onViewAttachedToWindow(holder);
        int type = holder.getItemViewType();
        if (!isCommonItemView(type)) {
            setFullSpan(holder);
        }
        //        int position = holder.getLayoutPosition();
        //        if (isLoadMoreFooterView(position) || isHeaderView(position)) {
        //            //设置RecyclerView 的 setFullSpan 为全屏
        //            setFullSpan(holder);
        //        }
    }

    /**
     * When set to true, the item will layout using all span area. That means, if orientation
     * is vertical, the view will have full width; if orientation is horizontal, the view will
     * have full height.
     * if the hold view use StaggeredGridLayoutManager they should using all span area
     *
     * @param holder True if this item should traverse all spans.
     */
    protected void setFullSpan(RecyclerView.ViewHolder holder) {
        ViewGroup.LayoutParams lp = holder.itemView.getLayoutParams();
        if (lp != null && lp instanceof StaggeredGridLayoutManager.LayoutParams) {
            StaggeredGridLayoutManager.LayoutParams params = (StaggeredGridLayoutManager.LayoutParams) lp;
            params.setFullSpan(true);
        }
    }

    /**
     * GridLayoutManager模式时， HeaderView、FooterView可占据一行，判断RecyclerView是否到达底部
     *
     * @param recyclerView
     */
    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        //获取布局管理器
        final RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
        if (layoutManager instanceof GridLayoutManager) {
            final GridLayoutManager gridManager = ((GridLayoutManager) layoutManager);
            gridManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {
                    int type = getItemViewType(position);
                    if (!isCommonItemView(type)) {
                        return gridManager.getSpanCount();
                    }
                    //                     if (isLoadMoreFooterView(position) || isHeaderView(position)) {
                    //                        return gridManager.getSpanCount();
                    //                    }

                    return 1;
                }
            });
        }
        startLoadMore(recyclerView, layoutManager);
    }

    /**
     * 判断列表是否滑动到底部
     *
     * @param recyclerView
     * @param layoutManager
     */
    private void startLoadMore(RecyclerView recyclerView, final RecyclerView.LayoutManager layoutManager) {

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                //The RecyclerView is not currently scrolling.
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    //获取加载更多条目的位置 需要加上加载的脚布局
                    int footerViewCount = getLoadMoreFooterViewCount();
                    int lastVisibleItemPosition = findLastVisibleItemPosition(layoutManager) + footerViewCount;
                    int itemCount = getItemCount();
                    Log.d("onScrolled", footerViewCount + " = " + lastVisibleItemPosition + " = " + itemCount);
                    if (lastVisibleItemPosition == itemCount) {
                        Log.d("onScrolled", "执行加载前");
                        if (mLoadMoreEnable && mLoadMoreFooterLayout.getChildAt(0) == mLoadingView && !isLoading) {
                            Log.d("onScrolled", "加载数据");
                            if (mLoadMoreListener != null) {
                                isLoading = true;
                                mLoadMoreListener.onLoadMore(false);
                            }
                        } else if (goneLoadingMore && !isGoneLoadMoreIng) {
                            isGoneLoadMoreIng = true;
                            mHandler.postDelayed(mMyThread, 500);
                        }
                    }
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);


            }
        });
    }

    /**
     * 找到最后可见项目位置
     *
     * @param layoutManager
     * @return
     */
    private int findLastVisibleItemPosition(RecyclerView.LayoutManager layoutManager) {
        if (layoutManager instanceof LinearLayoutManager) {
            return ((LinearLayoutManager) layoutManager).findLastVisibleItemPosition();
        } else if (layoutManager instanceof StaggeredGridLayoutManager) {
            final StaggeredGridLayoutManager staggeredGridLayoutManager = (StaggeredGridLayoutManager) layoutManager;
            final int[] positions = new int[staggeredGridLayoutManager.getSpanCount()];
            //            int[] lastVisibleItemPositions = ((StaggeredGridLayoutManager) layoutManager).findLastVisibleItemPositions(null);
            int[] lastVisibleItemPositions = ((StaggeredGridLayoutManager) layoutManager).findLastVisibleItemPositions(positions);
            return UiUtils.findMax(lastVisibleItemPositions);
        }
        return -1;
    }

    /**
     * bind recyclerView {@link #bindToRecyclerView(RecyclerView)} before use!
     *
     * @see #disableLoadMoreIfNotFullPage(RecyclerView)
     */
    public void disableLoadMoreIfNotFullPage() {
        checkNotNull();
        disableLoadMoreIfNotFullPage(getRecyclerView());
    }

    /**
     * check if full page after {@link #setNewData(List)}, if full, it will enable load more again.
     * <p>
     * 不是配置项！！
     * <p>
     * 这个方法是用来检查是否满一屏的，所以只推荐在 {@link #setNewData(List)} 之后使用
     * 原理很简单，先关闭 load more，检查完了再决定是否开启
     * <p>
     * 不是配置项！！
     *
     * @param recyclerView your recyclerView
     * @see #setNewData(List)
     */
    public void disableLoadMoreIfNotFullPage(RecyclerView recyclerView) {
        setEnableLoadMore(false);
        if (recyclerView == null)
            return;
        RecyclerView.LayoutManager manager = recyclerView.getLayoutManager();
        if (manager == null)
            return;
        if (manager instanceof LinearLayoutManager) {
            final LinearLayoutManager linearLayoutManager = (LinearLayoutManager) manager;
            recyclerView.postDelayed(new Runnable() {
                @Override
                public void run() {
                    if ((linearLayoutManager.findLastCompletelyVisibleItemPosition() + 1) != getItemCount()) {
                        setEnableLoadMore(true);
                    }
                }
            }, 50);
        } else if (manager instanceof StaggeredGridLayoutManager) {
            final StaggeredGridLayoutManager staggeredGridLayoutManager = (StaggeredGridLayoutManager) manager;
            recyclerView.postDelayed(new Runnable() {
                @Override
                public void run() {
                    final int[] positions = new int[staggeredGridLayoutManager.getSpanCount()];
                    staggeredGridLayoutManager.findLastCompletelyVisibleItemPositions(positions);
                    int pos = getTheBiggestNumber(positions) + 1;
                    if (pos != getItemCount()) {
                        setEnableLoadMore(true);
                    }
                }
            }, 50);
        }
    }


    private int getTheBiggestNumber(int[] numbers) {
        int tmp = -1;
        if (numbers == null || numbers.length == 0) {
            return tmp;
        }
        for (int num : numbers) {
            if (num > tmp) {
                tmp = num;
            }
        }
        return tmp;
    }

    /**
     * Return root layout of header
     */

    public LinearLayout getHeaderLayout() {
        return mNormalHeaderLayout;
    }

    /**
     * Return root layout of footer
     */
    public LinearLayout getFooterLayout() {
        return mNormalFooterLayout;
    }

    /**
     * Append header to the rear of the mHeaderLayout.
     *
     * @param header
     */
    public int addHeaderView(View header) {
        return addHeaderView(header, -1);
    }

    /**
     * Add header view to mHeaderLayout and set header view position in mHeaderLayout.
     * When index = -1 or index >= child count in mHeaderLayout,
     * the effect of this method is the same as that of {@link #addHeaderView(View)}.
     *
     * @param header
     * @param index  the position in mHeaderLayout of this header.
     *               When index = -1 or index >= child count in mHeaderLayout,
     *               the effect of this method is the same as that of {@link #addHeaderView(View)}.
     */
    public int addHeaderView(View header, int index) {
        return addHeaderView(header, index, LinearLayout.VERTICAL);
    }

    /**
     * @param header
     * @param index
     * @param orientation
     */
    public int addHeaderView(View header, int index, int orientation) {
        if (mNormalHeaderLayout == null) {
            mNormalHeaderLayout = new LinearLayout(header.getContext());
            if (orientation == LinearLayout.VERTICAL) {
                mNormalHeaderLayout.setOrientation(LinearLayout.VERTICAL);
                mNormalHeaderLayout.setLayoutParams(new RecyclerView.LayoutParams(MATCH_PARENT, WRAP_CONTENT));
            } else {
                mNormalHeaderLayout.setOrientation(LinearLayout.HORIZONTAL);
                mNormalHeaderLayout.setLayoutParams(new RecyclerView.LayoutParams(WRAP_CONTENT, MATCH_PARENT));
            }
        }
        final int childCount = mNormalHeaderLayout.getChildCount();
        if (index < 0 || index > childCount) {
            index = childCount;
        }
        mNormalHeaderLayout.addView(header, index);
        if (mNormalHeaderLayout.getChildCount() == 1) {
            int position = getNormalHeaderViewPosition();
            if (position != -1) {
                notifyItemInserted(position);
            }
        }
        return index;
    }

    public int setHeaderView(View header) {
        return setHeaderView(header, 0, LinearLayout.VERTICAL);
    }

    public int setHeaderView(View header, int index) {
        return setHeaderView(header, index, LinearLayout.VERTICAL);
    }

    public int setHeaderView(View header, int index, int orientation) {
        if (mNormalHeaderLayout == null || mNormalHeaderLayout.getChildCount() <= index) {
            return addHeaderView(header, index, orientation);
        } else {
            mNormalHeaderLayout.removeViewAt(index);
            mNormalHeaderLayout.addView(header, index);
            return index;
        }
    }

    /**
     * remove header view from mHeaderLayout.
     * When the child count of mHeaderLayout is 0, mHeaderLayout will be set to null.
     *
     * @param header
     */
    public void removeNormalHeaderView(View header) {
        if (getNormalHeaderLayoutCount() == 0)
            return;

        mNormalHeaderLayout.removeView(header);
        if (mNormalHeaderLayout.getChildCount() == 0) {
            int position = getNormalHeaderViewPosition();
            if (position != -1) {
                notifyItemRemoved(position);
            }
        }
    }

    /**
     * remove all header view from mHeaderLayout and set null to mHeaderLayout
     */
    public void removeAllNormalHeaderView() {
        if (getNormalHeaderLayoutCount() == 0)
            return;

        mNormalHeaderLayout.removeAllViews();
        int position = getNormalHeaderViewPosition();
        if (position != -1) {
            notifyItemRemoved(position);
        }
    }

    /**
     * Append footer to the rear of the mFooterLayout.
     *
     * @param footer
     */
    public int addFooterView(View footer) {
        return addFooterView(footer, -1, LinearLayout.VERTICAL);
    }

    public int addFooterView(View footer, int index) {
        return addFooterView(footer, index, LinearLayout.VERTICAL);
    }

    /**
     * Add footer view to mFooterLayout and set footer view position in mFooterLayout.
     * When index = -1 or index >= child count in mFooterLayout,
     * the effect of this method is the same as that of {@link #addFooterView(View)}.
     *
     * @param footer
     * @param index  the position in mFooterLayout of this footer.
     *               When index = -1 or index >= child count in mFooterLayout,
     *               the effect of this method is the same as that of {@link #addFooterView(View)}.
     */
    public int addFooterView(View footer, int index, int orientation) {
        if (mNormalFooterLayout == null) {
            mNormalFooterLayout = new LinearLayout(footer.getContext());
            if (orientation == LinearLayout.VERTICAL) {
                mNormalFooterLayout.setOrientation(LinearLayout.VERTICAL);
                mNormalFooterLayout.setLayoutParams(new RecyclerView.LayoutParams(MATCH_PARENT, WRAP_CONTENT));
            } else {
                mNormalFooterLayout.setOrientation(LinearLayout.HORIZONTAL);
                mNormalFooterLayout.setLayoutParams(new RecyclerView.LayoutParams(WRAP_CONTENT, MATCH_PARENT));
            }
        }
        final int childCount = mNormalFooterLayout.getChildCount();
        if (index < 0 || index > childCount) {
            index = childCount;
        }
        mNormalFooterLayout.addView(footer, index);
        if (mNormalFooterLayout.getChildCount() == 1) {
            int position = getFooterViewPosition();
            if (position != -1) {
                notifyItemInserted(position);
            }
        }
        return index;
    }

    public int setFooterView(View header) {
        return setFooterView(header, 0, LinearLayout.VERTICAL);
    }

    public int setFooterView(View header, int index) {
        return setFooterView(header, index, LinearLayout.VERTICAL);
    }

    public int setFooterView(View header, int index, int orientation) {
        if (mNormalFooterLayout == null || mNormalFooterLayout.getChildCount() <= index) {
            return addFooterView(header, index, orientation);
        } else {
            mNormalFooterLayout.removeViewAt(index);
            mNormalFooterLayout.addView(header, index);
            return index;
        }
    }

    /**
     * remove footer view from mFooterLayout,
     * When the child count of mFooterLayout is 0, mFooterLayout will be set to null.
     *
     * @param footer
     */
    public void removeFooterView(View footer) {
        if (getNormalFooterLayoutCount() == 0)
            return;

        mNormalFooterLayout.removeView(footer);
        if (mNormalFooterLayout.getChildCount() == 0) {
            int position = getFooterViewPosition();
            if (position != -1) {
                notifyItemRemoved(position);
            }
        }
    }

    /**
     * remove all footer view from mFooterLayout and set null to mFooterLayout
     */
    public void removeAllFooterView() {
        if (getNormalFooterLayoutCount() == 0)
            return;

        mNormalFooterLayout.removeAllViews();
        int position = getFooterViewPosition();
        if (position != -1) {
            notifyItemRemoved(position);
        }
    }


    private int getNormalHeaderViewPosition() {
        //Return to header view notify position
        if (getEmptyViewCount() == 1) {
            return 0;
        }
        return -1;
    }

    private int getFooterViewPosition() {
        //Return to footer view notify position
        if (getEmptyViewCount() == 1) {
            int position = 1;
            if (getNormalHeaderLayoutCount() != 0) {
                return position++;
            }
        } else {
            return getNormalHeaderLayoutCount() + mDatas.size();
        }
        return -1;
    }

    /**
     * if show empty view will be return 1 or not will be return 0
     *
     * @return
     */
    public int getEmptyViewCount() {
        if (mEmptyLayout == null || mEmptyLayout.getChildCount() == 0) {
            return 0;
        }

        if (mDatas.size() != 0) {
            return 0;
        }
        return 1;
    }

    /**
     * 是否是正常头布局
     *
     * @param position
     * @return
     */
    private boolean isNormalHeaderView(int position) {
        return position < getNormalHeaderLayoutCount();
    }

    /**
     * 是否是加载更多FooterView
     *
     * @param position
     * @return
     */
    private boolean isLoadMoreFooterView(int position) {
        return mLoadMoreEnable && position >= getItemCount() - getLoadMoreFooterLayoutCount();
    }

    /**
     * 是否是FooterView
     *
     * @param position
     * @return
     */
    private boolean isNormalFooterView(int position) {
        return position == getItemCount() - getNormalFooterLayoutCount() - getLoadMoreFooterLayoutCount();
    }

    /**
     * 返回 footer view数量 （是否有脚布局）
     *
     * @return
     */
    public int getLoadMoreFooterViewCount() {
        if (!mLoadMoreEnable || mLoadMoreFooterLayout == null || mLoadMoreFooterLayout.getChildCount() == 0) {
            return 0;
        }

        if (mDatas.size() == 0) {
            return 0;
        }

        return 1;
    }

    /**
     * if addHeaderView will be return 1, if not will be return 0
     */
    public int getNormalHeaderLayoutCount() {
        if (mNormalHeaderLayout == null || mNormalHeaderLayout.getChildCount() == 0) {
            return 0;
        }
        return 1;
    }

    /**
     * if addFooterView will be return 1, if not will be return 0
     */
    public int getNormalFooterLayoutCount() {
        if (mNormalFooterLayout == null || mNormalFooterLayout.getChildCount() == 0) {
            return 0;
        }
        return 1;
    }

    /**
     * 子view
     * if addFooterView will be return 1, if not will be return 0
     */
    public int getLoadMoreFooterLayoutCount() {
        if (mLoadMoreFooterLayout == null || mLoadMoreFooterLayout.getChildCount() == 0) {
            return 0;
        }
        return 1;
    }


    /**
     * Set the enabled state of load more.
     *
     * @param enable True if load more is enabled, false otherwise.
     */
    public void setEnableLoadMore(boolean enable) {
        mLoadMoreEnable = enable;
        isLoading = !enable;


        if (mLoadMoreEnable) {
            if (mLoadingView == null) {
                //加载更多数 , 更新footer view提示
                setLoadingView(R.layout.load_loading_layout);
            } else {
                setLoadingView(mLoadingView);
            }
            notifyItemInserted(getItemCount());
        } else if (getLoadMoreFooterLayoutCount() > 0) {
            notifyItemRemoved(getItemCount());
            removeLoadMoreFooterView();
        }
    }

    /**
     * Refresh end, no more data , gone Load more
     */
    public void loadMoreEnd() {
        loadMoreEnd(STATUS_DEFAULT);
    }

    /**
     * Refresh end, no more data
     *
     * @param loadMoreStatus show the load more view status
     */
    public void loadMoreEnd(int loadMoreStatus) {

        if (loadMoreStatus != STATUS_END_GONE) {
            if (mLoadEndView == null) {
                //加载完成，更新footer view提示
                setLoadEndView(R.layout.load_end_layout);
            }
            addLoadMoreFooterView(mLoadEndView);
        }
        switch (loadMoreStatus) {
            case STATUS_DEFAULT:
                goneLoadingMore = false;
                break;
            case STATUS_END_SHOW_GONE:
                goneLoadingMore = true;
                if (goneLoadingMore) {
                    mHandler.postDelayed(mMyThread, 500);
                }
                break;
            case STATUS_END_GONE:
                removeLoadMoreFooterView();
                break;
        }
    }

    /**
     * Refresh complete
     */
    public void loadMoreComplete() {
        isLoading = false;
        notifyItemRemoved(getItemCount());
    }

    /**
     * 数据加载失败
     */
    public void loadFailed() {
        if (mLoadFailedView == null) {
            //加载失败，更新footer view提示
            setLoadFailedView(R.layout.load_failed_layout);
        }
        addLoadMoreFooterView(mLoadFailedView);

        mLoadFailedView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addLoadMoreFooterView(mLoadingView);
                if (mLoadMoreListener != null) {
                    mLoadMoreListener.onLoadMore(true);
                }
            }
        });
    }

    /**
     * 获得列表数据个数
     *
     * @return
     */
    public int getDataCount() {
        return mDatas.size();
    }

    public List<T> getAllData() {
        return mDatas;
    }

    /**
     * 下拉刷新，得到的新数据插入到原数据头部
     *
     * @param datas
     */
    public void setRefreshData(List<T> datas) {
        setRefreshData(0, datas);
    }

    /**
     * 得到的新数据插入到某个位置
     *
     * @param datas
     */
    public void setRefreshData(int position, List<T> datas) {
        mDatas.addAll(position, datas);
        notifyItemRangeInserted(position, datas.size());

        //        notifyDataSetChanged();
    }

    /**
     * 初次加载、或下拉刷新要替换全部旧数据时刷新数据
     *
     * @param datas
     */
    public void setNewData(List<T> datas) {
        //        if (isReset) {
        //            isReset = false;
        //        }
        isLoading = false;
        mDatas.clear();
        mDatas.addAll(datas);
        notifyDataSetChanged();
    }

    /**
     * 插入数据  从就的数据的位置开始  到新数据的数量大小的位置
     *
     * @param datas
     */
    public void addData(List<T> datas) {
        isLoading = false;
        mDatas.addAll(datas);
        notifyItemRangeInserted(mDatas.size() - datas.size() + getNormalHeaderLayoutCount(), datas.size());
    }

    /**
     * 刷新加载更多的数据
     *
     * @param datas
     */
    public void setLoadMoreData(List<T> datas) {
        isLoading = false;
        int size = mDatas.size();
        mDatas.addAll(datas);
        notifyItemInserted(size + getNormalHeaderLayoutCount());
    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        int viewType = holder.getItemViewType();
        if (isCommonItemView(viewType)) {
            position = position - getNormalHeaderLayoutCount();
            if (getItem(position) != null) {
                bindCommonItem(holder, position);
            }
        }
    }

    //设置加载更多的监听器
    public void setOnLoadMoreListener(OnLoadMoreListener loadMoreListener) {
        mLoadMoreListener = loadMoreListener;

    }

    /**
     * 设置条目点击监听
     *
     * @param itemClickListener
     */
    public void setOnItemClickListener(OnItemClickListener<T> itemClickListener) {
        mItemClickListener = itemClickListener;
    }

    /**
     * @return 获取条目点击监听
     */
    public final OnItemClickListener getOnItemClickListener() {
        return mItemClickListener;
    }

    /**
     * 设置条目中子view的点击监听
     *
     * @param listener
     */
    public void setOnItemChildClickListener(OnItemChildClickListener listener) {
        mOnItemChildClickListeners = listener;
    }

    /**
     * 获取条目中子view的点击监听
     */
    @Nullable
    public final OnItemChildClickListener getOnItemChildClickListener() {
        return mOnItemChildClickListeners;
    }

    private void bindCommonItem(RecyclerView.ViewHolder holder, final int position) {
        final YcBaseViewHolder viewHolder = (YcBaseViewHolder) holder;

        convert(viewHolder, getAllData().get(position), position);

        viewHolder.getConvertView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (getOnItemClickListener() != null) {
                    mItemClickListener.onItemClick(viewHolder, getAllData().get(position), position);
                }
            }
        });
    }

    @Nullable
    public T getItem(@IntRange(from = 0) int position) {
        if ((position >= 0 && position < getAllData().size()))
            return getAllData().get(position);
        else
            return null;
    }
}
