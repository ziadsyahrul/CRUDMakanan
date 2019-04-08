package com.ziadsyahrul.crudmakanan.UI.uploadmakanan;

import android.content.Context;
import android.net.Uri;

import com.ziadsyahrul.crudmakanan.model.makanan.MakananData;

import java.util.List;

public interface UploadMakananContract {

    interface View{
        void showProgress();
        void hideProgress();
        void showMessage(String msg);
        void successUpload();
        void showSpinnerCategory(List<MakananData> categoryDataList);
    }

    interface Presenter{
        void getCategory();
        void uploadMakanan(Context context, Uri filePath, String namaMakanan, String descMakanan, String idCategori);
    }

}
