package com.example.asus.medicinegod;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import java.util.ArrayList;
import java.util.List;


public class ManagerView extends AppCompatActivity implements RadioGroup.OnCheckedChangeListener{

    private RadioGroup mRadioGroup;
    private List<Fragment> fragments = new ArrayList<>();
    private Fragment fragment;
    private FragmentManager fm;
    private FragmentTransaction transaction;
    private RadioButton rb_Home,rb_Search,rb_Shop,rb_Center;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.managerview);
        initView(); //初始化组件
        mRadioGroup.setOnCheckedChangeListener((RadioGroup.OnCheckedChangeListener) this); //点击事件
        fragments = getFragments(); //添加布局
        //添加默认布局
        normalFragment();
    }

    //默认布局
    private void normalFragment() {
        fm=getFragmentManager();
        transaction=fm.beginTransaction();
        fragment=fragments.get(0);
        transaction.replace(R.id.managerFragment,fragment);
        transaction.commit();
    }

    private void initView() {
        mRadioGroup = (RadioGroup) findViewById(R.id.radiobutton_manager_view_main);
        rb_Home= (RadioButton) findViewById(R.id.radiobutton_manager_main_mainpage);
        rb_Search= (RadioButton) findViewById(R.id.radiobutton_manager_main_search);
        rb_Shop= (RadioButton) findViewById(R.id.radiobutton_manager_main_purchase);
        rb_Center= (RadioButton) findViewById(R.id.radiobutton_manager_main_center);
    }

    public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
        fm=getFragmentManager();
        transaction=fm.beginTransaction();
        switch (checkedId){
            case R.id.radiobutton_manager_main_mainpage:
                fragment=fragments.get(0);
                transaction.replace(R.id.managerFragment,fragment);
                break;
            case R.id.radiobutton_manager_main_search:
                fragment=fragments.get(1);
                transaction.replace(R.id.managerFragment,fragment);
                break;
            case R.id.radiobutton_manager_main_purchase:
                fragment=fragments.get(2);
                transaction.replace(R.id.managerFragment,fragment);
                break;
            case R.id.radiobutton_manager_main_center:
                fragment=fragments.get(3);
                transaction.replace(R.id.managerFragment,fragment);
                break;
        }
        setTabState();
        transaction.commit();
    }

    //设置选中和未选择的状态
    private void setTabState() {
        setHomeState();
        setSearchState();
        setShopState();
        setCenterState();
    }

    private void setHomeState() {
        if (rb_Home.isChecked()){
            rb_Home.setTextColor(ContextCompat.getColor(this,R.color.colorbutton));
        }else{
            rb_Home.setTextColor(ContextCompat.getColor(this,R.color.colorAccent));
        }
    }

    private void setSearchState() {
        if (rb_Search.isChecked()){
            rb_Search.setTextColor(ContextCompat.getColor(this,R.color.colorbutton));
        }else{
            rb_Search.setTextColor(ContextCompat.getColor(this,R.color.colorAccent));
        }
    }

    private void setShopState() {
        if (rb_Shop.isChecked()){
            rb_Shop.setTextColor(ContextCompat.getColor(this,R.color.colorbutton));
        }else{
            rb_Shop.setTextColor(ContextCompat.getColor(this,R.color.colorAccent));
        }
    }

    private void setCenterState() {
        if (rb_Center.isChecked()){
            rb_Center.setTextColor(ContextCompat.getColor(this,R.color.colorbutton));
        }else{
            rb_Center.setTextColor(ContextCompat.getColor(this,R.color.colorAccent));
        }
    }

    public List<Fragment> getFragments() {
        fragments.add(new UserHomeFragment());
        fragments.add(new UserSearchFragment());
        fragments.add(new UserPurchaseFragment());
        fragments.add(new UserCenterFragment());
        return fragments;
    }

}
