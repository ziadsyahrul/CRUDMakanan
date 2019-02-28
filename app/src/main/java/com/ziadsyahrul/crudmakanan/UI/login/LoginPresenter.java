package com.ziadsyahrul.crudmakanan.UI.login;

import com.ziadsyahrul.crudmakanan.data.remote.ApiClient;
import com.ziadsyahrul.crudmakanan.data.remote.ApiInterface;
import com.ziadsyahrul.crudmakanan.model.Login.LoginData;
import com.ziadsyahrul.crudmakanan.model.Login.LoginResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginPresenter implements LoginContract.Presenter{

    private final LoginContract.View view;
    private ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);

    public LoginPresenter(LoginContract.View view) {
        this.view = view;
    }

    @Override
    public void doLogin(String username, String password) {
        // mencek username dan password
        if (username.isEmpty()) {
            view.usernameError("Username is empty");
            return;
        }

        if (password.isEmpty()) {
            view.passwordError("Password is empty");
            return;
        }

        view.showProgress();
        Call<LoginResponse> call = apiInterface.loginUser(username, password);
        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                view.hideProgress();

                if (response.body() != null){
                    if (response.body().getResult() == 1){
                        if (response.body().getData() != null){
                            LoginData loginData = response.body().getData();
                            String message = response.body().getMessage();
                            view.loginSuccess(message, loginData);
                        }else {
                            view.loginFailure("Data tidak ada");
                        }
                    }else {
                        view.loginFailure(response.body().getMessage());
                    }
                } else {
                    view.loginFailure("Data tidak ada");
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                view.hideProgress();
                view.loginFailure(t.getMessage());
            }
        });
    }
}
