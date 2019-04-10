package com.ziadsyahrul.crudmakanan.UI.detailmakananbyuser;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
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

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
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

public class DetailMakananByUserActivity extends AppCompatActivity implements DetailMakananByUserContract.View {

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
    @BindView(R.id.layoutSaveMakanan)
    CardView layoutSaveMakanan;
    @BindView(R.id.btn_update)
    Button btnUpdate;
    @BindView(R.id.btn_delete)
    Button btnDelete;
    @BindView(R.id.swipe_refresh)
    SwipeRefreshLayout swipeRefresh;

    private DetailMakananByUserPresenter mDetailMakananByUserPresenter = new DetailMakananByUserPresenter(this);
    private Uri filePath;
    private String idCategory, idMakanan;
    private MakananData mMakananData;
    private String namaFotoMakanan;
    private String[] midCategory;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_makanan_by_user);
        ButterKnife.bind(this);

        // Melakukan pengecekan dan permission untuk bisa mengakses gallery
        PermissionGallery();

        // Menangkap idMakanan yang dikirim dari activity sebelumnya
        idMakanan = getIntent().getStringExtra(Constant.KEY_EXTRA_ID_MAKANAN);

        // Mengambil data category untuk ditampilkan di spinner
        mDetailMakananByUserPresenter.getCategory();

        // setting swipe refresh
        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefresh.setRefreshing(false);



                // Mengambil data category untuk ditampilkan di spinner
                mDetailMakananByUserPresenter.getCategory();
            }
        });

    }

    private void PermissionGallery() {
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
    public void showProgress() {
        swipeRefresh.setRefreshing(true);
    }

    @Override
    public void hideProgress() {
        swipeRefresh.setRefreshing(false);
    }

    @Override
    public void showDetailMakanan(MakananData makananData) {
        // Kita ambil semua data detail makanan
        mMakananData = makananData;

        namaFotoMakanan = makananData.getFoto_makanan();

        // mengambil id category
        idCategory = makananData.getId_kategori();

        // Menampilkan semua data ke layar
        edtName.setText(makananData.getNama_makanan());
        edtDesc.setText(makananData.getDesc_makanan());

        // Memilih spinner sesuai dengan category makanan yang ada di dalam database

        for (int i = 0 ;i < midCategory.length; i++){
            Log.i("cek", "isi loop select mIdcategory: " + midCategory[i]);

            if (Integer.valueOf(midCategory[i]).equals(Integer.valueOf(idCategory))){
                spinCategory.setSelection(i);
                Log.i("cek", "isi select mIdcategory: " + midCategory[i]);
                Log.i("cek", "isi select idCategory: " + idCategory);
            }
        }

        // Menampilkan gambar makanan
        RequestOptions options = new RequestOptions().error(R.drawable.ic_broken_image_black_24dp).placeholder(R.drawable.ic_broken_image_black_24dp);
        Glide.with(this).load(makananData.getUrl_makanan()).apply(options).into(imgPicture);
    }

    @Override
    public void showMessage(String msg) {
        Toasty.info(this, msg, Toasty.LENGTH_SHORT).show();
    }

    @Override
    public void successDelete() {
        finish();
    }

    @Override
    public void successUpdate() {
        mDetailMakananByUserPresenter.getCategory();
    }

    @Override
    public void showSpinnerCategory(final List<MakananData> categoryDataList) {
        // Membuat data penampung untuk spinner
//        List<String> listSpinner = new ArrayList<>();

        String[] namaCategory = new String[categoryDataList.size()];
        midCategory = new String[categoryDataList.size()];


        for (int i = 0; i < categoryDataList.size(); i++){
//            listSpinner.add(categoryDataList.get(i).getNama_kategori());
            namaCategory[i] = categoryDataList.get(i).getNama_kategori();
            midCategory[i] = categoryDataList.get(i).getId_kategori();

            Log.i("cek", "isi show namaCategory: " + namaCategory);
            Log.i("cek", "isi show mIdCategory: " + midCategory[i]);
        }

        // Membuat adapter spinner
        ArrayAdapter<String> categorySpinnerAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, namaCategory);
        // kita setting untuk menampilkan spinner dengan 1 line
        categorySpinnerAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        // Memasukkan adapter ke spinner
        spinCategory.setAdapter(categorySpinnerAdapter);

        spinCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                idCategory = categoryDataList.get(position).getId_kategori();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                
            }
        });

        // Mengambil data detail makanan
        mDetailMakananByUserPresenter.getDetailMakanan(idMakanan);
    }

    @OnClick({R.id.fab_choose_picture, R.id.btn_update, R.id.btn_delete})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.fab_choose_picture:
                // Mengambil gambar dari storage
                showFileChooser();
                break;
            case R.id.btn_update:
                mDetailMakananByUserPresenter.updateDataMakanan(
                        this,
                        filePath,
                        edtName.getText().toString(),
                        edtDesc.getText().toString(),
                        idCategory,
                        namaFotoMakanan,
                        idMakanan
                );
                break;
            case R.id.btn_delete:
                mDetailMakananByUserPresenter.deleteMakanan(idMakanan, namaFotoMakanan);
                break;
        }
    }

    private void showFileChooser() {
        // Membuat object intent untuk dapat memilih data
        Intent intentGallery = new Intent(Intent.ACTION_PICK);
        intentGallery.setType("image/*");
        intentGallery.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intentGallery, "select pictures"), Constant.REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Constant.REQUEST_CODE &&
                resultCode == RESULT_OK &&
                data != null &&
                data.getData() != null){

            // Mengambil data foto dan memasukkan ke dalam variable filePath
            filePath = data.getData();

            try {
                // Mengambil data lalu di convert ke bitmap
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                // Tampilkan gambar yang baru dipilih ke layar
                imgPicture.setImageBitmap(bitmap);
            }catch (IOException e){
                e.printStackTrace();
            }
        }
    }

//    @Override
//    protected void onResume() {
//        super.onResume();
//        // Mengambil data category untuk ditampilkan di spinner
//        mDetailMakananByUserPresenter.getCategory();
//    }
}
