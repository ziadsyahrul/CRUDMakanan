package com.ziadsyahrul.crudmakanan.UI.profile;

import android.content.Context;
import android.content.SharedPreferences;

import com.ziadsyahrul.crudmakanan.Utils.Constant;
import com.ziadsyahrul.crudmakanan.Utils.SessionManager;
import com.ziadsyahrul.crudmakanan.model.Login.LoginData;

public class ProfilePresenter implements ProfileContract.Presenter{

    private final ProfileContract.View view;
    private SharedPreferences pref;

    public ProfilePresenter(ProfileContract.View view) {
        this.view = view;
    }


    @Override
    public void getDataUser(Context context) {
        // Pengambilan data dari SharedPreference
        pref = context.getSharedPreferences(Constant.pref_name, 0);

        // Membuat object model logindata untuk menampung
        LoginData loginData = new LoginData();

        // Memasukkan data SharedPreference ke dalam model logindata
        loginData.setId_user(pref.getString(Constant.KEY_USER_ID, ""));
        loginData.setNama_user(pref.getString(Constant.KEY_USER_NAMA, ""));
        loginData.setAlamat(pref.getString(Constant.KEY_USER_ALAMAT, ""));
        loginData.setNo_telp(pref.getString(Constant.KEY_USER_NOTELP, ""));
        loginData.setJenkel(pref.getString(Constant.KEY_USER_JENKEL, ""));

        // kirim data model loginData ke view
        view.showDataUser(loginData);

    }

    @Override
    public void logoutSession(Context context) {
        // Membuat kelas session manager untuk memanggil method logout
        SessionManager mSessionManager = new SessionManager(context);
        mSessionManager.logout();
    }
}
