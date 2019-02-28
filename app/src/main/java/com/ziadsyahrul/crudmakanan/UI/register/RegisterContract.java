package com.ziadsyahrul.crudmakanan.UI.register;

import android.widget.EditText;

import com.ziadsyahrul.crudmakanan.model.Login.LoginData;

public interface RegisterContract {

    interface View{
        void showProgress();
        void hideProgress();
        void showError(String msg);
        void showRegisterSuccess(String msg);
    }

    interface Presenter {
        void doRegisterUser(LoginData loginData);

    }
}
