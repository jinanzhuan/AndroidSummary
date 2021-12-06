package com.example.androidsummary.android.widget.recyclerview;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.bumptech.glide.load.engine.GlideException;
import com.example.androidsummary.R;
import com.example.androidsummary.android.widget.recyclerview.contactAdapter.ContentBean;
import com.example.androidsummary.android.widget.recyclerview.contactAdapter.StackFromEndAdapter;
import com.example.androidsummary.base.BaseTitleActivity;

import java.util.ArrayList;
import java.util.List;

public class StackFromEndActivity extends BaseTitleActivity implements View.OnClickListener {
    private RecyclerView rvList;
    private Button btn_add;
    private Button btn_remove;
    private List<ContentBean> contentData;
    private StackFromEndAdapter contentAdapter;
    private DividerItemDecoration decoration;
    private static final String[] ulrs = {"https://t7.baidu.com/it/u=2295973985,242574375&fm=193&f=GIF"
            , "https://t7.baidu.com/it/u=2340400811,4174965252&fm=193&f=GIF"
            , "https://t7.baidu.com/it/u=1522757721,1408622889&fm=193&f=GIF"
            , "https://t7.baidu.com/it/u=3833781341,2118894364&fm=193&f=GIF"
            , "https://t7.baidu.com/it/u=3859735106,2767469648&fm=193&f=GIF"
            , "https://t7.baidu.com/it/u=2077212613,1695106851&fm=193&f=GIF"
            , "https://t7.baidu.com/it/u=1117794894,4154991203&fm=193&f=GIF"
            , "https://t7.baidu.com/it/u=3979143318,65711957&fm=193&f=GIF"
            , "https://t7.baidu.com/it/u=289562621,657690689&fm=193&f=GIF"
            , "https://t7.baidu.com/it/u=2301872827,3431176467&fm=193&f=GIF"
            , "https://t7.baidu.com/it/u=1392863136,806273060&fm=193&f=GIF"
            , "https://t7.baidu.com/it/u=1730346367,2509634164&fm=193&f=GIF"
            , "https://t7.baidu.com/it/u=607149606,2289064684&fm=193&f=GIF"
            , "https://t7.baidu.com/it/u=4127536459,3471889489&fm=193&f=GIF"
            , "https://t7.baidu.com/it/u=1340524541,2382325266&fm=193&f=GIF"
            , "https://t7.baidu.com/it/u=568703415,2738567992&fm=193&f=GIF"
            , "https://t7.baidu.com/it/u=568703415,2738567992&fm=193&f=GIF"
            , "https://t7.baidu.com/it/u=3642794260,4259860304&fm=193&f=GIF"
            , "https://t7.baidu.com/it/u=91559597,2460124813&fm=193&f=GIF"
            , "https://t7.baidu.com/it/u=2815364097,2879086091&fm=193&f=GIF"
            , "https://t7.baidu.com/it/u=2295110163,3344731801&fm=193&f=GIF"
            , "https://t7.baidu.com/it/u=4135823832,1485331801&fm=193&f=GIF"
            , "https://t7.baidu.com/it/u=671375251,1357626043&fm=193&f=GIF"
            , "https://t7.baidu.com/it/u=2164021293,410323803&fm=193&f=GIF"
            , "https://t7.baidu.com/it/u=1049905209,2156712912&fm=193&f=GIF"
            , "https://t7.baidu.com/it/u=2619053574,1636471061&fm=193&f=GIF"
            , "https://cdn3-banquan.ituchong.com/weili/l/918174689796292636.webp"
            , "https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fpic.jj20.com%2Fup%2Fallimg%2F512%2F040912153611%2F120409153611-11.jpg&refer=http%3A%2F%2Fpic.jj20.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=jpeg?sec=1641368650&t=cd0014cbc44d0d02d8f3c152653c62a9"
            , "https://gimg2.baidu.com/image_search/src=http%3A%2F%2F01.minipic.eastday.com%2F20170823%2F20170823144759_d41d8cd98f00b204e9800998ecf8427e_7.jpeg&refer=http%3A%2F%2F01.minipic.eastday.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=jpeg?sec=1641368689&t=02c3093894d0dd15f2256036e1cd4b3b"
            , "https://t7.baidu.com/it/u=4035902981,2712308148&fm=193&f=GIF"};
    private String longItemContent;
    private SwipeRefreshLayout srl_refresh;
    private LinearLayoutManager layoutManager;

    public static void actionStart(Context context) {
        Intent intent = new Intent(context, StackFromEndActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_stack_from_end;
    }

    @Override
    protected void initView() {
        super.initView();
        title_bar = findViewById(R.id.title_bar);
        rvList = findViewById(R.id.rv_list);
        btn_add = findViewById(R.id.btn_add);
        btn_remove = findViewById(R.id.btn_remove);
        srl_refresh = findViewById(R.id.srl_refresh);
    }

    @Override
    protected void initListener() {
        super.initListener();
        btn_add.setOnClickListener(this);
        btn_remove.setOnClickListener(this);
        srl_refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if(layoutManager.getStackFromEnd()) {
                    layoutManager.setStackFromEnd(false);
                }
                addData();
            }
        });
    }

    @Override
    protected void initData() {
        super.initData();
        contentData = new ArrayList<>();

        //设置为垂直线性布局
        layoutManager = new LinearLayoutManager(mContext);
        layoutManager.setStackFromEnd(true);

        rvList.setLayoutManager(layoutManager);
        contentAdapter = new StackFromEndAdapter(contentData);
        
        contentAdapter.setGlideLoadListener(new GlideLoadListener() {
            @Override
            public void onResourceReady(int position) {
                Log.e("TAG", "onResourceReady");
                checkPositionLocation(position);
            }

            @Override
            public void onLoadFailed(int position, @Nullable GlideException e) {
                Log.e("TAG", "onLoadFailed");
            }
        });
        //设置适配器
        rvList.setAdapter(contentAdapter);
        //添加DividerItemDecoration
        decoration = new DividerItemDecoration(mContext, DividerItemDecoration.VERTICAL);
        decoration.setDrawable(ContextCompat.getDrawable(mContext, R.drawable.divider_bg));
        //设置itemDecoration
        rvList.addItemDecoration(decoration);

        longItemContent = "上了飞机阿里斯顿卷发的时间；反对；发非可视角度；反对福建；的房间啊；" +
                "但是空间；阿警方的；事件；的空间姐；开房间啊；是的肌肤；姐；发空间啊；是的肌肤；善良的肌肤；" +
                "阿是的肌肤；见识到了飞机善良的肌肤离开是的肌肤了是的肌肤来说就到了飞机善良的房间类似肌肤了是的肌肤了" +
                "睡觉了飞机上的了加了飞机上的了飞机类似的房间里是的肌肤类似剪短发来见识到了飞机善良大方姐是的肌肤来说" +
                "空间都发了是的肌肤了是的肌肤类似剪短发了是的肌肤拉德斯基发了涉及到法律上加了飞机上的了飞机善良的姐法律" +
                "上的就快疯了涉及到法律就是代理费就善良的肌肤来说就啊；都发啦是的肌肤来说肯定法律上的克己复礼时间法律就" +
                "是代理费，梅琳达看就发绿没的vleoiemvlmdljsdljdlfjsldfjmvljdlfjldsjfsldjfsldjflsdjfnvljsdlf" +
                "jeifoweiuflsjdfeoiufoweiujdlfj就到法律上代理费了是的肌肤森林狼队飞机阿里加辣酱豆腐示范基地；阿警" +
                "方将了是的肌肤了领导飞机善良的姐是的肌肤来说的肌肤啦时间到了飞机啊收到了肌肤了是的肌肤类似剪短发了就啊是" +
                "的飞机善良大家法律姐萨卡肌肤的垃圾堆里肌肤善良剪短发了姐啦啦的肌肤阿里加萨了就啊了是的肌肤啦科技发；就啊" +
                "老大房间啊逻辑发逻辑都发啦的肌肤啦剪短发了阿剪短发了阿加代理费就啊老大肌肤阿剪短发垃圾袋理发经历的房间阿" +
                "里激发了大家福利剪短发垃圾袋飞机上的了肌肤离开肌肤类似肌肤类似肌肤了了是的肌肤类似的房间善良的房间";

        addData();
        //seekToLastPosition();
    }

    private void addData() {
        contentData.clear();
        ContentBean content;
        for(int i = 0; i < 16; i++) {
            content = new ContentBean();
            content.setName("content"+(i+1));
            content.setUrl(ulrs[i % (ulrs.length - 1)]);
            contentData.add(content);
        }
        ContentBean contentBean = contentData.get(contentData.size() - 1);
        contentBean.setName(longItemContent);
        contentAdapter.notifyDataSetChanged();
        if(srl_refresh != null) {
            srl_refresh.setRefreshing(false);
        }
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_add :
                addItem();
                break;
            case R.id.btn_remove :
                removeItem();
                break;
        }
    }

    private void addItem() {
        if(!layoutManager.getStackFromEnd()) {
            layoutManager.setStackFromEnd(true);
        }
        ContentBean content;
        for(int i = 0; i < 1; i++) {
            content = new ContentBean();
            content.setName("content"+(contentData.size()+1));
            if(contentData.size() % 2 == 0) {
                content.setName(longItemContent);
            }
            content.setUrl(ulrs[contentData.size() % (ulrs.length -1)]);
            if(contentData.size() % 5 == 0) {
                content.setName(longItemContent+longItemContent+longItemContent+longItemContent+longItemContent+longItemContent);
                content.setUrl(null);
            }
            contentData.add(content);
        }
        contentAdapter.notifyItemRangeChanged(contentData.size(), 1);
        //contentAdapter.notifyDataSetChanged();
        seekToLastPosition();
    }

    private void removeItem() {
        contentData.remove(contentData.size() - 1);
        contentAdapter.notifyItemRemoved(contentData.size() - 1);
        contentAdapter.notifyItemChanged(contentData.size() - 1);
    }

    private synchronized void seekToLastPosition() {
        int lastPosition = contentData.size() - 1;
        layoutManager.scrollToPositionWithOffset(lastPosition, 0);
        // 如果是长文字呢？
        checkPositionLocation(lastPosition);
    }

    private synchronized void checkPositionLocation(int position) {
        // Check the last position
        Log.e("TAG", "position: "+position+" last position: "+(contentData.size() - 1));
        if(position != contentData.size() - 1) {
            return;
        }
        // Check if recyclerView can scroll
        if(!rvList.canScrollVertically(1)) {
            rvList.postDelayed(new Runnable() {
                @Override
                public void run() {
                    if(!rvList.canScrollVertically(1)) {
                        Log.e("TAG", "after delay 100ms still can not scroll");
                        return;
                    }
                    Log.e("TAG", "delay 100ms to check whether can scroll vertical");
                    checkWhetherNeedScroll(rvList, position);
                }
            }, 100);
            return;
        }
        Log.e("TAG", "can scroll vertical");
        checkWhetherNeedScroll(rvList, position);
    }

    private void checkWhetherNeedScroll(RecyclerView rvList, int position) {
        if(rvList == null) {
            return;
        }
        RecyclerView.LayoutManager layoutManager = rvList.getLayoutManager();
        if(!(layoutManager instanceof LinearLayoutManager)) {
            return;
        }
        View lastView = layoutManager.findViewByPosition(position);
        if(lastView != null) {
            int bottom = lastView.getBottom();
            int parentHeight = rvList.getHeight();
            int intrinsicHeight = getDividerHeight(rvList);
            Log.e("TAG", "divider height: "+intrinsicHeight);
            if(bottom + intrinsicHeight > parentHeight) {
                int dy = bottom + intrinsicHeight - parentHeight;
                rvList.smoothScrollBy(0, dy);
            }
        }
    }

    private int getDividerHeight(RecyclerView rvList) {
        int itemDecorationCount = rvList.getItemDecorationCount();
        if(itemDecorationCount <= 0) {
            return 0;
        }
        int dividerHeight = 0;
        for(int i = 0; i < itemDecorationCount; i++) {
            RecyclerView.ItemDecoration decoration = rvList.getItemDecorationAt(i);
            if(decoration instanceof DividerItemDecoration) {
                Drawable drawable = ((DividerItemDecoration) decoration).getDrawable();
                if(drawable != null) {
                    dividerHeight += drawable.getIntrinsicHeight();
                }
            }
        }
        return dividerHeight;
    }
}
