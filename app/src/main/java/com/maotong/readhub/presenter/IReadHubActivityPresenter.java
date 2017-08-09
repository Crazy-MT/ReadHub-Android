package com.maotong.readhub.presenter;


public interface IReadHubActivityPresenter extends BasePresenter {
    void loadView();

    void loadMore(int offset);

    void refresh();

}
