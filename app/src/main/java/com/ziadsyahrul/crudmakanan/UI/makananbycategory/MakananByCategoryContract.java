package com.ziadsyahrul.crudmakanan.UI.makananbycategory;

import com.ziadsyahrul.crudmakanan.model.makanan.MakananData;

import java.util.List;

public interface MakananByCategoryContract {

    interface View{
        void showProgress();
        void hideProgress();
        void showFoodByCategory(List<MakananData> foodNewsList);
        void showFailureMessage(String msg);
    }

    interface Presenter{
        void getListFoodByCategory(String idCategory);
    }

}
