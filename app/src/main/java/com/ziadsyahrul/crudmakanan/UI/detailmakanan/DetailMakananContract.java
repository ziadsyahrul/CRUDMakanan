package com.ziadsyahrul.crudmakanan.UI.detailmakanan;

import com.ziadsyahrul.crudmakanan.model.makanan.MakananData;

public interface DetailMakananContract {

    interface View {
        void showProgress();
        void hideProgress();
        void showDetailMakanan(MakananData makananData);
        void showFailureMessage(String msg);
    }

    interface Presenter {
        void getDetailMakanan(String idMakanan);
    }
}
