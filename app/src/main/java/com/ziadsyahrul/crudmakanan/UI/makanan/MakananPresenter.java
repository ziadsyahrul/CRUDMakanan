package com.ziadsyahrul.crudmakanan.UI.makanan;

import android.util.Log;

import com.ziadsyahrul.crudmakanan.data.remote.ApiClient;
import com.ziadsyahrul.crudmakanan.data.remote.ApiInterface;
import com.ziadsyahrul.crudmakanan.model.makanan.MakananResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MakananPresenter implements MakananContract.Presenter{

    // TODO 1 Menyiapkan variable yang dibutuhkan
    private final MakananContract.View view;
    private ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);

    public MakananPresenter(MakananContract.View view) {
        this.view = view;
    }


    @Override
    public void getListFoodNews() {
        view.showProgress();

        Call<MakananResponse> call = apiInterface.getMakananBaru();
        call.enqueue(new Callback<MakananResponse>() {
            @Override
            public void onResponse(Call<MakananResponse> call, Response<MakananResponse> response) {
                view.hideProgress();

                if (response.body().getMakananDataList() != null){
                    view.showFoodNewsList(response.body().getMakananDataList());
                    Log.i("Cek Berhasil", "ada isinya");
                }else {
                    view.showFailureMessage("Data Kosong");
                    Log.i("Cek Error", "Data Kosong");
                }
            }

            @Override
            public void onFailure(Call<MakananResponse> call, Throwable t) {
                view.hideProgress();
                view.showFailureMessage(t.getMessage());

                Log.i("Cek Error", t.getMessage());
            }
        });
    }

    @Override
    public void getListFoodPopuler() {
        view.showProgress();

        Call<MakananResponse> call = apiInterface.getMakananPopuler();
        call.enqueue(new Callback<MakananResponse>() {
            @Override
            public void onResponse(Call<MakananResponse> call, Response<MakananResponse> response) {
                view.hideProgress();

                if (response.body().getMakananDataList() != null){
                    view.showFoodPopuler(response.body().getMakananDataList());
                    Log.i("Cek Berhasil", "ada isinya");
                }else {
                    view.showFailureMessage("Data Kosong");
                    Log.i("Cek Error", "Data Kosong");
                }
            }

            @Override
            public void onFailure(Call<MakananResponse> call, Throwable t) {
                view.hideProgress();
                view.showFailureMessage(t.getMessage());

                Log.i("Cek Error", t.getMessage());
            }
        });
    }

    @Override
    public void getListFoodKategory() {
        view.showProgress();

        Call<MakananResponse> call = apiInterface.getKategoriMakanan();
        call.enqueue(new Callback<MakananResponse>() {
            @Override
            public void onResponse(Call<MakananResponse> call, Response<MakananResponse> response) {
                view.hideProgress();

                if (response.body().getMakananDataList() != null){
                    view.showFoodKategoryList(response.body().getMakananDataList());
                    Log.i("Cek Berhasil", "ada isinya");
                }else {
                    view.showFailureMessage("Data Kosong");
                    Log.i("Cek Error", "Data Kosong");
                }
            }

            @Override
            public void onFailure(Call<MakananResponse> call, Throwable t) {
                view.hideProgress();
                view.showFailureMessage(t.getMessage());

                Log.i("Cek Error", t.getMessage());
            }
        });
    }
}
