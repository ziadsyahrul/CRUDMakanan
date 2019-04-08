package com.ziadsyahrul.crudmakanan.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.ziadsyahrul.crudmakanan.R;
import com.ziadsyahrul.crudmakanan.UI.detailmakanan.DetailMakananActivity;
import com.ziadsyahrul.crudmakanan.UI.detailmakananbyuser.DetailMakananByUserActivity;
import com.ziadsyahrul.crudmakanan.UI.makananbycategory.MakananByCategoryActivity;
import com.ziadsyahrul.crudmakanan.UI.makananbycategory.MakananByCategoryContract;
import com.ziadsyahrul.crudmakanan.Utils.Constant;
import com.ziadsyahrul.crudmakanan.model.makanan.MakananData;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import es.dmoral.toasty.Toasty;

public class MakananAdapter extends RecyclerView.Adapter<MakananAdapter.ViewHolder> {

    public static final int TYPE_1 = 1;
    public static final int TYPE_2 = 2;
    public static final int TYPE_3 = 3;
    public static final int TYPE_4 = 4;
    public static final int TYPE_5 = 5;
    Integer viewType;
    private final Context context;
    private final List<MakananData> makananDataList;



    public MakananAdapter(Integer viewType, Context context, List<MakananData> makananDataList) {
        this.context = context;
        this.makananDataList = makananDataList;
        this.viewType = viewType;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view;

        switch (i) {
            case TYPE_1:
                view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_food_news, null);
                return new FoodNewsViewHolder(view);
            case TYPE_2:
                view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_food_populer, null);
                return new FoodPopulerViewHolder(view);
            case TYPE_3:
                view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_food_kategori, null);
                return new FoodKategoryViewHolder(view);
            case TYPE_4:
                view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_food_by_category, null);
                return new FoodNewsViewHolder(view);
            case TYPE_5:
                view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_food_by_category, null);
                return new FoodByUserViewHolder(view);
            default:
                return null;
        }
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        // Mengambil data lalu memasukkan ke dalam model
        final MakananData makananData = makananDataList.get(i);

        // Mengambil viewtype dari user atau contractor
        int mViewType = viewType;
        switch (mViewType){
            case TYPE_1:
                // Membuat holder untuk dapat mengakses widget
                FoodNewsViewHolder foodNewsViewHolder = (FoodNewsViewHolder) viewHolder;
                // RequestOption untuk error dan placeholder gambar
                RequestOptions options = new RequestOptions().error(R.drawable.ic_broken_image_black_24dp).placeholder(R.drawable.ic_broken_image_black_24dp);
                Glide.with(context).load(makananData.getUrl_makanan()).apply(options).into(foodNewsViewHolder.imgMakanan);
                // Menampilkan title dan jumlah view
                foodNewsViewHolder.txtTitle.setText(makananData.getNama_makanan());
                foodNewsViewHolder.txtView.setText(makananData.getView());

                // Menampilkan waktu upload
                foodNewsViewHolder.txtTime.setText(newDate(makananData.getInsert_time()));

                // Membuat onClick
                foodNewsViewHolder.itemView.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        // Berpindah halaman ke DetailMakanan
                        context.startActivity(new Intent(context, DetailMakananActivity.class).putExtra(Constant.KEY_EXTRA_ID_MAKANAN, makananData.getId_makanan()));
                    }
                });
                break;
            case TYPE_2:
                FoodPopulerViewHolder foodPopulerViewHolder = (FoodPopulerViewHolder) viewHolder;

                RequestOptions options1 = new RequestOptions().error(R.drawable.ic_broken_image_black_24dp).placeholder(R.drawable.ic_broken_image_black_24dp);
                Glide.with(context).load(makananData.getUrl_makanan()).apply(options1).into(foodPopulerViewHolder.imgMakanan);

                foodPopulerViewHolder.txtTitle.setText(makananData.getNama_makanan());
                foodPopulerViewHolder.txtView.setText(makananData.getView());

                // Menampilkan waktu upload
                foodPopulerViewHolder.txtTime.setText(newDate(makananData.getInsert_time()));

                foodPopulerViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        context.startActivity(new Intent(context, DetailMakananActivity.class).putExtra(Constant.KEY_EXTRA_ID_MAKANAN, makananData.getId_makanan()));
                    }
                });
                break;
            case TYPE_3:
                FoodKategoryViewHolder foodKategoryViewHolder = (FoodKategoryViewHolder) viewHolder;

                RequestOptions options2 = new RequestOptions().error(R.drawable.ic_broken_image_black_24dp).placeholder(R.drawable.ic_broken_image_black_24dp);
                Glide.with(context).load(makananData.getUrl_makanan()).apply(options2).into(foodKategoryViewHolder.image);

                foodKategoryViewHolder.txtNamaKategory.setText(makananData.getNama_kategori());
                foodKategoryViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        context.startActivity(new Intent(context,MakananByCategoryActivity.class).putExtra(Constant.KEY_EXTRA_ID_CATEGORY, makananData.getId_kategori()));
                    }
                });

                break;

            case TYPE_4:
                // Membuat holder untuk dapat mengakses widget
                FoodNewsViewHolder foodNewsViewHolder1 = (FoodNewsViewHolder) viewHolder;

                RequestOptions options3 = new RequestOptions().error(R.drawable.ic_broken_image_black_24dp).placeholder(R.drawable.ic_broken_image_black_24dp);
                Glide.with(context).load(makananData.getUrl_makanan()).apply(options3).into(foodNewsViewHolder1.imgMakanan);

                // Menampilkan title dan jumlah view
                foodNewsViewHolder1.txtTitle.setText(makananData.getNama_makanan());
                foodNewsViewHolder1.txtView.setText(makananData.getView());

                foodNewsViewHolder1.txtTime.setText(newDate(makananData.getInsert_time()));

                foodNewsViewHolder1.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // berpindah halaman ke detail makanan
                        context.startActivity(new Intent(context, MakananByCategoryActivity.class).putExtra(Constant.KEY_EXTRA_ID_MAKANAN, makananData.getId_makanan()));
                    }
                });
                break;

            case TYPE_5:
                FoodByUserViewHolder foodByUserViewHolder = (FoodByUserViewHolder) viewHolder;

                RequestOptions options4 = new RequestOptions().error(R.drawable.ic_broken_image_black_24dp).placeholder(R.drawable.ic_broken_image_black_24dp);
                Glide.with(context).load(makananData.getUrl_makanan()).apply(options4).into(foodByUserViewHolder.imgMakanan);

                foodByUserViewHolder.txtTitle.setText(makananData.getNama_makanan());
                foodByUserViewHolder.txtView.setText(makananData.getView());

                foodByUserViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        context.startActivity(new Intent(context, DetailMakananByUserActivity.class).putExtra(Constant.KEY_EXTRA_ID_MAKANAN, makananData.getId_makanan()));
                    }
                });
        }

    }

    public String newDate(String insert_time) {
        // Mmebuat variable penampung tanggal
        Date date = null;
        // Membuat Penampung date untuk format yang baru
        String newDate = insert_time;

        // Membuat date dengan format sesuai dengan tanggal yang sudah dimiliki
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        // Mengubah tanggal yang dimiliki menjadi tipe date
        try {
            date = sdf.parse(insert_time);
        }catch (ParseException e){
            e.printStackTrace();
        }
        // Kita cek format date yang kita miliki sesuai dengan yang kita inginkan
        if (date != null){
            // Mengubah date yang dimiliki menjadi format date yang baru
            newDate = new SimpleDateFormat("dd MMMM yyyy HH:mm:ss").format(date);
        }
        return newDate;
    }

    @Override
    public int getItemCount() {
        return makananDataList.size();
    }

    public int getItemViewType(int position) {
        return viewType;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }

    class FoodNewsViewHolder extends ViewHolder {

        @BindView(R.id.img_makanan)
        ImageView imgMakanan;
        @BindView(R.id.txt_title)
        TextView txtTitle;
        @BindView(R.id.img_view)
        ImageView imgView;
        @BindView(R.id.txt_view)
        TextView txtView;
        @BindView(R.id.txt_time)
        TextView txtTime;

        public FoodNewsViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    class FoodPopulerViewHolder extends ViewHolder {

        @BindView(R.id.img_makanan)
        ImageView imgMakanan;
        @BindView(R.id.txt_title)
        TextView txtTitle;
        @BindView(R.id.img_view)
        ImageView imgView;
        @BindView(R.id.txt_view)
        TextView txtView;
        @BindView(R.id.txt_time)
        TextView txtTime;

        public FoodPopulerViewHolder(final View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    class FoodKategoryViewHolder extends ViewHolder {

        @BindView(R.id.image)
        ImageView image;
        @BindView(R.id.txt_nama_kategory)
        TextView txtNamaKategory;

        public FoodKategoryViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    class FoodByUserViewHolder extends ViewHolder {

        @BindView(R.id.img_makanan)
        ImageView imgMakanan;
        @BindView(R.id.txt_title)
        TextView txtTitle;
        @BindView(R.id.img_view)
        ImageView imgView;
        @BindView(R.id.txt_view)
        TextView txtView;
        @BindView(R.id.txt_time)
        TextView txtTime;

        public FoodByUserViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, itemView);
        }
    }
}
