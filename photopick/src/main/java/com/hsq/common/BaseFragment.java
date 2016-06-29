package com.hsq.common;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hsq.common.photopick.core.presenter.IPresenter;
import com.hsq.common.photopick.core.view.IView;
import com.hsq.common.photopick.utils.StringUtil;
import com.hsq.common.photopick.utils.ToastUtil;

/**
 * Created by heshiqi on 16/5/8.
 */
public abstract class BaseFragment<P extends IPresenter> extends Fragment implements IView {


    protected P mPresenter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        onCreated();

        initPresenter();

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(getContentViewId(), container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initData();

        initListener();
    }

    /**
     * 执行{@link #onCreate(Bundle)} 后，所要执行的方法
     */
    protected void onCreated() {
    }


    /**
     * 获取布局的id
     *
     * @return
     */
    abstract protected int getContentViewId();

    /**
     * 初始化Presenter
     */
    abstract protected void initPresenter();

    /**
     * 初始化界面控件
     */
    abstract protected void initView(View view);

    /**
     * 初始化数据
     */
    abstract protected void initData();

    /**
     * 初始化界面的监听回调
     */
    abstract protected void initListener();


    public void showMsg(String msg, int errorType) {

        if (!StringUtil.isNull(msg))
            ToastUtil.show(getContext(), msg);
        else
            switch (errorType) {
                case ERROR_TYPE_LADDING:

                    break;
                case ERROR_TYPE_NO_DATA:

                    break;
                case ERROR_TYPE_SUCCESS:

                    break;
                case ERROR_TYPE_FAIL:

                    break;
            }

    }


    @Override
    public void onDestroy() {
        if (mPresenter != null)
            mPresenter.onDestroy();
        super.onDestroy();
    }
}
