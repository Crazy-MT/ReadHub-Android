package com.maotong.readhub.ui.iView;

import com.maotong.readhub.bean.readhub.hot.ReadHub;
import com.maotong.readhub.bean.readhub.hot.newdata.DataList;

public interface IReadHubFragment extends IBaseFragment {
    void updateList(ReadHub readHubItems);
    void moreList(DataList moreData);
    void updateListFromCache(DataList readHubItems);
    void show(String news);
}
