package com.ziadsyahrul.crudmakanan.model.detailmakanan;

import com.google.gson.annotations.SerializedName;
import com.ziadsyahrul.crudmakanan.model.makanan.MakananData;

import java.util.List;

public class DetailMakananResponse {

    @SerializedName("result")
    private int result;

    @SerializedName("message")
    private String message;

    @SerializedName("data")
    private MakananData makananData;

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public MakananData getMakananData() {
        return makananData;
    }

    public void setMakananData(MakananData makananData) {
        this.makananData = makananData;
    }
}
