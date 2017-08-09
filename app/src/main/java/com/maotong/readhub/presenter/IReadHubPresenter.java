package com.maotong.readhub.presenter;

 
public interface IReadHubPresenter extends BasePresenter {
    void getReadHub();
    void getReadHubFromCache(int offset);
    void getMoreReadHubData(int offset);
    void getReadHubFromCache(String offset);
    void getMoreReadHubData(String offset);
}
