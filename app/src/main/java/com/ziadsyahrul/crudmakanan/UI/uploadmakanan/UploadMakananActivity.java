package com.ziadsyahrul.crudmakanan.UI.uploadmakanan;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import com.ziadsyahrul.crudmakanan.R;
import com.ziadsyahrul.crudmakanan.Utils.Constant;
import com.ziadsyahrul.crudmakanan.model.makanan.MakananData;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import es.dmoral.toasty.Toasty;

public class UploadMakananActivity extends AppCompatActivity implements UploadMakananContract.View {

    @BindView(R.id.img_picture)
    ImageView imgPicture;
    @BindView(R.id.fab_choose_picture)
    FloatingActionButton fabChoosePicture;
    @BindView(R.id.layoutPicture)
    CardView layoutPicture;
    @BindView(R.id.edt_name)
    EditText edtName;
    @BindView(R.id.edt_desc)
    EditText edtDesc;
    @BindView(R.id.spin_category)
    Spinner spinCategory;
    @BindView(R.id.layoutUploadMakanan)
    CardView layoutUploadMakanan;
    @BindView(R.id.btn_upload)
    Button btnUpload;
    @BindView(R.id.swipe_refresh)
    SwipeRefreshLayout swipeRefresh;

    // TODO 1 Menyiapkan Variable yang dibutuhkan
    private UploadMakananPresenter mUploadMakananPresenter = new UploadMakananPresenter(this);
    // Untuk menampung idCategory
    private String mIdCategory;
    // Untuk menampung gambar
    private Uri filePath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_makanan);
        ButterKnife.bind(this);

        swipeRefresh = findViewById(R.id.swipe_refresh);

        // Meminta permission untuk mengakses external storage
        PermissionGalery();
        // Menampilkan data category untuk di tampilkan ke spinner
        mUploadMakananPresenter.getCategory();

        swipeRefresh.post(new Runnable() {
            @Override
            public void run() {
                hideProgress();
            }
        });

    }

    private void PermissionGalery() {
        // Mencek apakah user sudah memberikan permission untuk mengakses external storage
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED)
            return;

        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_EXTERNAL_STORAGE)) {
            //If the user has denied the permission previously your code will come to this block
            //Here you can explain why you need this permission
            //Explain here why you need this permission
        }
        //And finally ask for the permission
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, Constant.STORAGE_PERMISSION_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == Constant.STORAGE_PERMISSION_CODE) {
        //If permission is granted
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
        //Displaying a toast
                showMessage("Permission granted now you can read the storage");
                Log.i("Permission on", "onRequestPermissionsResult: " + String.valueOf(grantResults));
            } else {
        //Displaying another toast if permission is not granted
                showMessage("Oops you just denied the permission");
                Log.i("Permission off", "onRequestPermissionsResult: " + String.valueOf(grantResults));

            }
        }
    }

    @Override
    public void showProgress() {
        swipeRefresh.setRefreshing(true);
    }

    @Override
    public void hideProgress() {
        swipeRefresh.setRefreshing(false);
    }

    @Override
    public void showMessage(String msg) {
        Toasty.success(this, msg, Toasty.LENGTH_SHORT).show();
    }

    @Override
    public void successUpload() {
        finish();
    }

    @Override
    public void showSpinnerCategory(final List<MakananData> categoryDataList) {
        // Membuat data penampung untuk spinner
        List<String> listSpinner = new ArrayList<>();
        for (int i = 0; i < categoryDataList.size(); i++){
            listSpinner.add(categoryDataList.get(i).getNama_kategori());
        }
        // Membuat adapter spinner
        ArrayAdapter<String> cateforySpinnerAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, listSpinner);
        // kita setting untuk menampilkan spinner dengan 1 line
        cateforySpinnerAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        // Memasukkan adapter ke spinner
        spinCategory.setAdapter(cateforySpinnerAdapter);

        spinCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mIdCategory = categoryDataList.get(position).getId_kategori();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    @OnClick({R.id.fab_choose_picture, R.id.btn_upload})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.fab_choose_picture:
                showFileChooser();
                break;
            case R.id.btn_upload:
                mUploadMakananPresenter.uploadMakanan(this,
                    filePath,
                    edtName.getText().toString(),
                    edtDesc.getText().toString(),
                    mIdCategory
                );
                break;
        }
    }

    private void showFileChooser(){
        // Membuaka media external storage
        Intent intentGallery = new Intent(Intent.ACTION_PICK);
        intentGallery.setType("image/*");
        intentGallery.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intentGallery, "select pictures"), Constant.REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // Mencek apakah request code dan data ada
        if (requestCode == Constant.REQUEST_CODE && resultCode == RESULT_OK && data != null && data.getData() != null){
            // Mengambil data image yang sudah dipilih user
            filePath = data.getData();

            try {
                // Mengubah file image pilihan user menjadi bitmap
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                // Menampilkan gambar preview yang di pilih
                imgPicture.setImageBitmap(bitmap);
            }catch (IOException e){
                e.printStackTrace();
            }
        }
    }
}

