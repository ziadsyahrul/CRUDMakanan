package com.ziadsyahrul.crudmakanan.UI.profile;

import android.content.Context;

import com.ziadsyahrul.crudmakanan.model.Login.LoginData;

public interface ProfileContract {

    interface View{
        void showDataUser(LoginData loginData);
    }

    interface Presenter{
        void getDataUser(Context context);
        void logoutSession(Context context);
    }
}
