package com.ziadsyahrul.crudmakanan.adapter;

import android.content.Context;
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
import com.ziadsyahrul.crudmakanan.model.makanan.MakananData;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import es.dmoral.toasty.Toasty;

public class MakananAdapter extends RecyclerView.Adapter<MakananAdapter.ViewHolder> {

    public static final int TYPE_1 = 1;
    public static final int TYPE_2 = 2;
    public static final int TYPE_3 = 3;
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
                // Membuat onClick
                foodNewsViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toasty.info(context, makananData.getNama_makanan(), Toasty.LENGTH_SHORT).show();
                    }
                });
                break;
            case TYPE_2:
                FoodPopulerViewHolder foodPopulerViewHolder = (FoodPopulerViewHolder) viewHolder;

                RequestOptions options1 = new RequestOptions().error(R.drawable.ic_broken_image_black_24dp).placeholder(R.drawable.ic_broken_image_black_24dp);
                Glide.with(context).load(makananData.getUrl_makanan()).apply(options1).into(foodPopulerViewHolder.imgMakanan);

                foodPopulerViewHolder.txtTitle.setText(makananData.getNama_makanan());
                foodPopulerViewHolder.txtView.setText(makananData.getView());

                foodPopulerViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                            Toasty.info(context, makananData.getNama_makanan(), Toasty.LENGTH_SHORT).show();
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
                        Toasty.info(context, makananData.getNama_kategori(), Toasty.LENGTH_SHORT).show();
                    }
                });

                break;
        }




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
}

