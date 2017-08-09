package com.maotong.readhub.ui.iView;


public interface IBaseFragment  {
    void showProgressDialog();

    void hidProgressDialog();

    void showError(String error);
}
