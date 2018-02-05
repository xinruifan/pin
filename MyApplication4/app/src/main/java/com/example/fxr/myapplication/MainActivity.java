package com.example.fxr.myapplication;


import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.graphics.Color;
import android.media.Image;
import android.net.wifi.p2p.WifiP2pManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity implements OnClickListener{
    private index_fragment indexFragment;
    private message_fragment messageFragment;
    private mine_fragment mineFragment;
    private release_fragment releaseFragment;
    private View messageLayout;
    private View indexLayout;
    private View mineLayout;
    private View releaseLayout;
    private ImageView messageImage;
    private ImageView contactsImage;
    private ImageView newsImage;
    private ImageView settingImage;
    private TextView messageText;
    private TextView contactsText;
    private TextView newsText;
    private TextView settingText;
    private FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // 初始化布局元素
        initViews();
        fragmentManager = getFragmentManager();
        // 第一次启动时选中第0个tab
        setTabSelection(0);
    }

    private void initViews() {
        messageLayout = findViewById(R.id.message_layout);
        indexLayout = findViewById(R.id.index_layout);
        mineLayout = findViewById(R.id.mine_layout);
        releaseLayout = findViewById(R.id.release_layout);
        messageImage = (ImageView) findViewById(R.id.message_image);
        contactsImage = (ImageView) findViewById(R.id.contacts_image);
        newsImage = (ImageView) findViewById(R.id.news_image);
        settingImage = (ImageView) findViewById(R.id.setting_image);
        messageText = (TextView) findViewById(R.id.message_text);
        contactsText = (TextView) findViewById(R.id.contacts_text);
        newsText = (TextView) findViewById(R.id.news_text);
        settingText = (TextView) findViewById(R.id.setting_text);
        messageLayout.setOnClickListener(this);
        indexLayout.setOnClickListener(this);
        mineLayout.setOnClickListener(this);
        releaseLayout.setOnClickListener(this);


    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.index_layout:
                // 当点击了消息tab时，选中第1个tab
                setTabSelection(0);
                break;
            case R.id.release_layout:
                // 当点击了联系人tab时，选中第2个tab
                setTabSelection(1);
                break;
            case R.id.message_layout:
                // 当点击了动态tab时，选中第3个tab
                setTabSelection(2);
                break;
            case R.id.mine_layout:
                // 当点击了设置tab时，选中第4个tab
                setTabSelection(3);
                break;
            default:
                break;
        }
    }
    /**
     * 根据传入的index参数来设置选中的tab页。
     *
     * @param index
     *            每个tab页对应的下标。0表示首页，1表示发布，2表示消息，3表示我的。
     */
    private void setTabSelection(int index) {
        // 每次选中之前先清楚掉上次的选中状态
        clearSelection();
        // 开启一个Fragment事务
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        // 先隐藏掉所有的Fragment，以防止有多个Fragment显示在界面上的情况
        hideFragments(transaction);
        switch (index) {
            case 0:
                // 当点击了消息tab时，改变控件的图片和文字颜色
                messageImage.setImageResource(R.drawable.home_2);
                messageText.setTextColor(Color.GREEN);
                if (indexFragment == null) {
                    // 如果indexFragment为空，则创建一个并添加到界面上
                    indexFragment = new index_fragment();
                    transaction.add(R.id.content, indexFragment);
                } else {
                    // 如果indexFragment不为空，则直接将它显示出来
                    transaction.show(indexFragment);
                }
                break;
            case 1:
                // 当点击了联系人tab时，改变控件的图片和文字颜色
                contactsImage.setImageResource(R.drawable.fabu_2);
                contactsText.setTextColor(Color.GREEN);
                if (releaseFragment == null) {
                    // 如果releaseFragment为空，则创建一个并添加到界面上
                    releaseFragment = new release_fragment();
                    transaction.add(R.id.content, releaseFragment);
                } else {
                    // 如果releaseFragment不为空，则直接将它显示出来
                    transaction.show(releaseFragment);
                }
                break;
            case 2:
                // 当点击了动态tab时，改变控件的图片和文字颜色
                newsImage.setImageResource(R.drawable.message_2);
                newsText.setTextColor(Color.GREEN);
                if (messageFragment == null) {
                    // 如果messageFragment为空，则创建一个并添加到界面上
                    messageFragment = new message_fragment();
                    transaction.add(R.id.content, messageFragment);
                } else {
                    // 如果messageFragment不为空，则直接将它显示出来
                    transaction.show(messageFragment);
                }
                break;
            case 3:
            default:
                // 当点击了设置tab时，改变控件的图片和文字颜色
                settingImage.setImageResource(R.drawable.mine_2);
                settingText.setTextColor(Color.GREEN);
                if (mineFragment == null) {
                    // 如果mineFragment为空，则创建一个并添加到界面上
                    mineFragment = new mine_fragment();
                    transaction.add(R.id.content,mineFragment);
                } else {
                    // 如果mineFragment不为空，则直接将它显示出来
                    transaction.show(mineFragment);
                }
                break;
        }
        transaction.commit();
    }
    /**
     * 清除掉所有的选中状态。
     */
    private void clearSelection() {
        messageImage.setImageResource(R.drawable.home_1);
        messageText.setTextColor(Color.parseColor("#82858b"));
        contactsImage.setImageResource(R.drawable.fabu_1);
        contactsText.setTextColor(Color.parseColor("#82858b"));
        newsImage.setImageResource(R.drawable.message_1);
        newsText.setTextColor(Color.parseColor("#82858b"));
        settingImage.setImageResource(R.drawable.mine_1);
        settingText.setTextColor(Color.parseColor("#82858b"));
    }
    /**
     * 将所有的Fragment都置为隐藏状态。
     *
     * @param transaction
     *            用于对Fragment执行操作的事务
     */
    private void hideFragments(FragmentTransaction transaction) {
        if (indexFragment != null) {
            transaction.hide(indexFragment);
        }
        if (releaseFragment != null) {
            transaction.hide(releaseFragment);
        }
        if (messageFragment != null) {
            transaction.hide(messageFragment);
        }
        if (mineFragment != null) {
            transaction.hide(mineFragment);
        }
    }

}


