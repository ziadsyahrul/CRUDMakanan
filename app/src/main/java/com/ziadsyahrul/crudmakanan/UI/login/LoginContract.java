package com.ziadsyahrul.crudmakanan.UI.login;

import com.ziadsyahrul.crudmakanan.model.Login.LoginData;

public interface LoginContract {

    interface View{
        void showProgress();
        void hideProgress();
        void loginSuccess(String msg, LoginData loginData);
        void loginFailure(String msg);
        void usernameError(String msg);
        void passwordError(String msg);
    }

    interface Presenter{
        void doLogin(String username, String password);
    }
}
