package com.ziadsyahrul.crudmakanan.UI.main;

import android.content.Context;

import com.ziadsyahrul.crudmakanan.Utils.SessionManager;

public class MainPresenter implements MainContract.Presenter{
    @Override
    public void logoutSession(Context context) {
        // Membuat object SessionManager untuk dapat digunakan
        SessionManager mSessionManager = new SessionManager(context);
        mSessionManager.logout();
    }
}
