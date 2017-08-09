package com.maotong.readhub.ui.iView;

import com.maotong.readhub.bean.readhub.hot.ReadHub;
import com.maotong.readhub.bean.readhub.hot.newdata.DataList;

/**
 * Created by yoush on 2017/8/6.
 */

public interface IReadHub {
    void loadViewPager();
    void completeRefresh();
    void showError(String errorMessage);

    void updateList(ReadHub readHub);

    void moreList(DataList readHubHot);
}
