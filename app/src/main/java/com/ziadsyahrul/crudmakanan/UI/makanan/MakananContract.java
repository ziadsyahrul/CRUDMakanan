package com.ziadsyahrul.crudmakanan.UI.makanan;

import com.ziadsyahrul.crudmakanan.model.makanan.MakananData;

import java.util.List;

public interface MakananContract {

    interface View{
        void showProgress();
        void hideProgress();
        void showFoodNewsList(List<MakananData> foodNewsList);
        void showFoodPopuler(List<MakananData> foodPopulerList);
        void showFoodKategoryList(List<MakananData> foodKategoryList);
        void showFailureMessage(String msg);
    }

    interface Presenter{
        void getListFoodNews();
        void getListFoodPopuler();
        void getListFoodKategory();
    }
}
