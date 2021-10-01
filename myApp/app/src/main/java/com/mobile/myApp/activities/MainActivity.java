package com.mobile.myApp.activities;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.FileUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.ViewTarget;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.mobile.myApp.R;
import com.mobile.myApp.fragments.main_page.ContactsFragment;
import com.mobile.myApp.fragments.main_page.GroupsFragment;
import com.mobile.myApp.fragments.main_page.HomeFragment;
import com.mobile.util.StaticData.AccountData;
import com.mobile.util.app.Activity;
import com.mobile.util.model.db.entity.User;

import net.qiujuer.genius.ui.widget.FloatActionButton;


import java.io.File;

import butterknife.BindView;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;


/**
 * MainActivity，继承我们自己写的util里的activity
 */
public class MainActivity extends Activity implements BottomNavigationView.OnNavigationItemSelectedListener {
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.main_appbar)
    View appBar;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.main_content)
    FrameLayout content;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.main_bottom)
    BottomNavigationView bottomNavigationView;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.main_float)
    FloatActionButton floatActionButton;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.main_portrait)
    CircleImageView portraitView;


    protected HomeFragment homeFragment = new HomeFragment();
    protected ContactsFragment contactsFragment = new ContactsFragment();
    protected GroupsFragment groupsFragment = new GroupsFragment();
    // 避免一直增加fragments
    private int count_frags = 0;

    /**
     * MainActivity 显示的入口
     *
     * @param context 上下文
     */
    public static void show(Context context) {
        context.startActivity(new Intent(context, MainActivity.class));
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    protected boolean checkBundle(Bundle bundle) {
        if (AccountData.userInfoFinished()) {
            return super.checkBundle(bundle);
        }
        UserActivity.show(this);
        return false; // 之后main activity会结束掉
    }

    @Override
    protected void initialWidget() {
        super.initialWidget();

        // 导航栏背景图
        Glide.with(this).load(R.drawable.background)
                .into(new ViewTarget<View, GlideDrawable>(appBar) {
                    @Override
                    public void onResourceReady(GlideDrawable resource, GlideAnimation<? super GlideDrawable> glideAnimation) {
                        this.view.setBackground(resource.getCurrent());
                    }
                });

        // 设置底部导航栏回调
        bottomNavigationView.setOnNavigationItemSelectedListener(this);

        // 设置头像，从数据库拿
        User user = AccountData.getUser();
        if (user != null) {
            File file = new File(user.getPortrait());
            Uri uri = Uri.fromFile(file);
            portraitView.setImageURI(uri);
        }
    }

    @Override
    protected int getContentLayoutId() {
        return R.layout.main_activity;
    }

    @Override
    protected void initialData() {
        super.initialData();

        // app一进来就进home
        Menu menu = bottomNavigationView.getMenu();
        menu.performIdentifierAction(R.id.navigation_home, 0);
        count_frags += 1;
        hideFloat();
    }

    /**
     * 搜索监听事件
     */
    @SuppressLint("NonConstantResourceId")
    @OnClick(R.id.main_search)
    void onClickSearch() {

    }

    /**
     * 浮动按钮监听事件（添加联系人）
     */
    @SuppressLint("NonConstantResourceId")
    @OnClick(R.id.main_float)
    void onClickFloat() {

    }

    /**
     * 点击头像监听事件
     */
    @SuppressLint("NonConstantResourceId")
    @OnClick(R.id.main_portrait)
    void onClickPortrait() {
        UserActivity.show(this);
    }

    /**
     * 切换导航栏监听
     *
     * @param item
     * @return
     */
    @SuppressLint({"CommitTransaction", "SetTextI18n", "RestrictedApi"})
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.navigation_home) {
            if (count_frags == 0) {
                // 如果是初次操作，添加fragment
                getSupportFragmentManager().beginTransaction()
                        .add(R.id.main_content, homeFragment)
                        .commit();
                count_frags += 1;
            } else {
                // 否则把当前fragment替换掉
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.main_content, homeFragment)
                        .commit();
            }
            hideFloat();
        } else if (item.getItemId() == R.id.navigation_contacts) {
            if (count_frags == 0) {
                getSupportFragmentManager().beginTransaction()
                        .add(R.id.main_content, contactsFragment)
                        .commit();
                count_frags += 1;
            } else {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.main_content, contactsFragment)
                        .commit();
            }
            showFloat();
        } else if (item.getItemId() == R.id.navigation_groups) {
            if (count_frags == 0) {
                getSupportFragmentManager().beginTransaction()
                        .add(R.id.main_content, groupsFragment)
                        .commit();
                count_frags += 1;
            } else {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.main_content, groupsFragment)
                        .commit();
            }
            showFloat();
        } else {
            return false;
        }


        if (getSupportFragmentManager().getFragments() != null) {
            Log.e("Fragment Number", String.valueOf(getSupportFragmentManager().getFragments().size()));
        }
        return true;
    }


    /**
     * 只有在groups和contacts页面才会显示float
     */
    public void showFloat() {
        floatActionButton.animate().rotation(360)
                .translationY(-180)
                .setDuration(300)
                .start();
    }

    /**
     * 别的页面隐藏float
     */
    public void hideFloat() {
        floatActionButton.animate().rotation(-360)
                .translationY(180)
                .setDuration(300)
                .start();
    }


}
