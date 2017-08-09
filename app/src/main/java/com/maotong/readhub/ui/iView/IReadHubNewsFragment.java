package com.maotong.readhub.ui.iView;

import com.maotong.readhub.bean.readhub.news.DataList;
import com.maotong.readhub.bean.readhub.news.ReadHubNews;

public interface IReadHubNewsFragment extends IBaseFragment {
    void updateList(ReadHubNews readHubItems);
    void moreList(DataList moreData);
    void updateListFromCache(DataList readHubItems);
    void show(String news);
}
