package com.hsq.common.photopick.core.view;

/**
 * Created by heshiqi on 16/5/8.
 */
public interface IView {

    public final int ERROR_TYPE_LADDING = 1;

    public final int ERROR_TYPE_SUCCESS = 1 << 1;

    public final int ERROR_TYPE_FAIL = 1 << 2;

    public final int ERROR_TYPE_NO_DATA = 1 << 3;

    void showMsg(String msg, int errorType);


}
