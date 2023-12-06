package com.example.garage;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {

    private final RecyclerViewInterface recyclerViewInterface;
    Context context;
    ArrayList<ListItem> listItems;

    public MyAdapter(Context context,ArrayList<ListItem> listItems,
                     RecyclerViewInterface recyclerViewInterface) {
        this.context = context;
        this.listItems = listItems;
        this.recyclerViewInterface = recyclerViewInterface;
    }

    @NonNull
    @Override
    public MyAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(context);
        View v = inflater.inflate(R.layout.list_item, parent, false);
        return new MyAdapter.ViewHolder(v, recyclerViewInterface);

    }

    @Override
    public void onBindViewHolder(@NonNull MyAdapter.ViewHolder holder, int position) {

        holder.text_brand.setText(listItems.get(position).getBrand());
        holder.text_model.setText(listItems.get(position).getModel());
        holder.text_year.setText(listItems.get(position).getYear());
        holder.text_price.setText(listItems.get(position).getPrice());
        holder.imageView.setImageResource(listItems.get(position).getImage());

    }

    @Override
    public int getItemCount() {
        return listItems.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView text_brand, text_model, text_price, text_year;
        public ImageView imageView;
        public ConstraintLayout constraintLayout;


        public ViewHolder(@NonNull View itemView, RecyclerViewInterface recyclerViewInterface) {

            super(itemView);

            text_brand = itemView.findViewById(R.id.text_brand);
            text_model = itemView.findViewById(R.id.text_model);
            text_price = itemView.findViewById(R.id.text_price);
            text_year = itemView.findViewById(R.id.text_year);
            imageView = itemView.findViewById(R.id.itemListImage);
            constraintLayout = itemView.findViewById(R.id.constraint_layout);

            itemView.setOnClickListener(v -> {
                if (recyclerViewInterface != null) {
                    int pos = getAdapterPosition();

                    if(pos != RecyclerView.NO_POSITION)
                        recyclerViewInterface.onItemClick(pos);
                }
            });

        }

    }

}
