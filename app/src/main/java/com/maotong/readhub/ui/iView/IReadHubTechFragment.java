package com.maotong.readhub.ui.iView;

import com.maotong.readhub.bean.readhub.tech.ReadHubTech;
import com.maotong.readhub.bean.readhub.tech.newdata.DataList;

public interface IReadHubTechFragment extends IBaseFragment {
    void updateList(ReadHubTech readHubItems);
    void moreList(DataList moreData);
    void updateListFromCache(DataList readHubItems);
    void show(String news);
}
