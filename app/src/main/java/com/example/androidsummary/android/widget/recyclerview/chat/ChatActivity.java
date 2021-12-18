package com.example.androidsummary.android.widget.recyclerview.chat;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.androidsummary.R;
import com.example.androidsummary.base.BaseTitleActivity;
import com.hyphenate.EMCallBack;
import com.hyphenate.EMMessageListener;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMConversation;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.chat.EMOptions;

import java.util.List;

public class ChatActivity extends BaseTitleActivity {
    private static final String TAG = ChatActivity.class.getSimpleName();
    private SwipeRefreshLayout srl_refresh;
    private RecyclerView rv_list;
    private String conversationId = "som";
    private MessageAdapter adapter;
    private EMMessageListener listener;
    private EMConversation conversation;
    
    public static void actionStart(Context context) {
        Intent intent = new Intent(context, ChatActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_chat;
    }

    @Override
    protected void initView() {
        super.initView();
        title_bar = findViewById(R.id.title_bar);
        srl_refresh = findViewById(R.id.srl_refresh);
        rv_list = findViewById(R.id.rv_list);
    }

    @Override
    protected void initListener() {
        super.initListener();
        srl_refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Log.e(TAG, "onRefresh");
                getChatHistory();
            }
        });
        listener = new EMMessageListener() {
            @Override
            public void onMessageReceived(List<EMMessage> messages) {
                Log.e(TAG, "onMessageReceived");
                getChatMessagesFromCache();
            }

            @Override
            public void onCmdMessageReceived(List<EMMessage> messages) {
                Log.e(TAG, "onCmdMessageReceived");
                getChatMessagesFromCache();
            }

            @Override
            public void onMessageRead(List<EMMessage> messages) {

            }

            @Override
            public void onMessageDelivered(List<EMMessage> messages) {

            }

            @Override
            public void onMessageRecalled(List<EMMessage> messages) {

            }

            @Override
            public void onMessageChanged(EMMessage message, Object change) {

            }
        };

    }

    @Override
    protected void initData() {
        super.initData();
        LinearLayoutManager layoutManager = new LinearLayoutManager(mContext);
        layoutManager.setStackFromEnd(true);
        rv_list.setLayoutManager(layoutManager);

        adapter = new MessageAdapter();
        rv_list.setAdapter(adapter);
        
        rv_list.addItemDecoration(new DividerItemDecoration(mContext, DividerItemDecoration.VERTICAL));
        
        initChatSDK();
    }

    private void initChatSDK() {
        EMOptions options = new EMOptions();
        options.setAppKey("easemob-demo#easeim");
        EMClient.getInstance().init(mContext, options);

        EMClient.getInstance().login("ljne", "123", new EMCallBack() {
            @Override
            public void onSuccess() {
                Log.e(TAG, "login success");
                EMClient.getInstance().chatManager().addMessageListener(listener);
                getChatHistory();
            }

            @Override
            public void onError(int code, String error) {

            }

            @Override
            public void onProgress(int progress, String status) {

            }
        });
    }

    private void getChatHistory() {
        Log.e(TAG, "getChatHistory");
        conversation = EMClient.getInstance().chatManager().getConversation(conversationId);
        if(conversation != null) {
            conversation.loadMoreMsgFromDB(null, 20);
        }
        getChatMessagesFromCache();
    }
    
    private void getChatMessagesFromCache() {
        Log.e(TAG, "getChatMessagesFromCache");
        runOnUiThread(()-> {
            if(conversation == null) {
                conversation = EMClient.getInstance().chatManager().getConversation(conversationId);
            }
            if(conversation != null) {
                List<EMMessage> messages = conversation.getAllMessages();
                adapter.setData(messages);
            }
            srl_refresh.setRefreshing(false);
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EMClient.getInstance().chatManager().removeMessageListener(listener);
    }
}
