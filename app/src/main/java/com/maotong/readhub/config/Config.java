package com.maotong.readhub.config;

import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;

import com.maotong.readhub.R;
import com.maotong.readhub.ui.fragment.ReadHubHotFragment;
import com.maotong.readhub.ui.fragment.ReadHubNewsFragment;
import com.maotong.readhub.ui.fragment.ReadHubTechFragment;


public class Config {

    public static final String DB__IS_READ_NAME = "IsRead";
    public static final String READHUB = "readhub";
    public static final String READHUB_NEWS = "readhubnews";
    public static final String READHUB_TECH = "readhubtech";

    public static boolean isNight = false;

    public enum TabFragment {
        READHUB_HOT(R.id.navigation_hot, ReadHubHotFragment.class),
        READHUB_NEWS(R.id.navigation_news, ReadHubNewsFragment.class),
        READHUB_TECH(R.id.navigation_tech, ReadHubTechFragment.class);

        private Fragment fragment;
        private final int menuId;
        private final Class<? extends Fragment> clazz;



        TabFragment(@IdRes int menuId, Class<? extends Fragment> clazz) {
            this.menuId = menuId;
            this.clazz = clazz;
        }

        @NonNull
        public Fragment fragment(){
            if (fragment == null){
                try {
                    fragment = clazz.newInstance();
                } catch (Exception e) {
                    e.printStackTrace();
                    fragment = new Fragment();
                }
            }
            return fragment;
        }

        public static TabFragment from(int itemId){
            for (TabFragment fragment : values()){
                if (fragment.menuId == itemId){
                    return fragment;
                }
            }
            return READHUB_HOT;
        }
        public static void onDestroy(){
            for (TabFragment fragment : values()){
                fragment.fragment = null;
            }
        }


    }
}
