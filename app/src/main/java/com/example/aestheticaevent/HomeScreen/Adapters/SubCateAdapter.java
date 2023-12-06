package com.example.aestheticaevent.HomeScreen.Adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.aestheticaevent.HomeScreen.ActivityEventinfo;
import com.example.aestheticaevent.HomeScreen.HomeResponse.CategoryListResponse;
import com.example.aestheticaevent.HomeScreen.HomeResponse.SubCategoryListResponse;
import com.example.aestheticaevent.HomeScreen.HomeResponse.Subcategory;
import com.example.aestheticaevent.R;

import java.util.ArrayList;
import java.util.List;

public class SubCateAdapter extends RecyclerView.Adapter<SubCateAdapter.SubViewHolder> {
    Context context;
    List<Subcategory> subcategoryList;
    List<Subcategory> searchList;
    SubCategoryInterface subCategoryInterface;

    public interface SubCategoryInterface {
        void onSubCategoryClicked(String subCategoryId);
    }

    public void setSubCategoryInterface(SubCategoryInterface subCategoryInterface) {
        this.subCategoryInterface = subCategoryInterface;
    }


    public SubCateAdapter(Context context, List<Subcategory> subcategoryList) {
        this.context = context;
        this.subcategoryList = subcategoryList;
        this.searchList = new ArrayList<>(subcategoryList);
    }

    public void updateData(List<Subcategory> filteredList) {
     //   this.subcategoryList = filteredList;
        this.searchList = filteredList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public SubViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.sub_item_event, parent, false);
        return new SubViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SubViewHolder holder, int position) {
        Subcategory model = searchList.get(position);

            try {
                Glide.with(context)
                        .load(model.getPicture())
                        .placeholder(R.drawable.person_image)
                        .error(R.drawable.ic_launcher_foreground)
                        .into(holder.SubImg);
            } catch (Exception e) {
                e.printStackTrace();
            }
            holder.textView.setText(model.getSubCategoryName());
            holder.txLocation.setText(model.getLocation());
              holder.txDate.setText(model.getDate());
            holder.txTime.setText(model.getTiming());
            holder.txPrice.setText(model.getPrice());

          /*     holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, ActivityEventinfo.class);
                i.putExtra("subCategoryName", model.getSubCategoryName());
                v.getContext().startActivity(i);
            }
        });*/

            holder.itemView.setOnClickListener(v -> {
                if (subCategoryInterface != null) {
                    subCategoryInterface.onSubCategoryClicked(model.getSubCategoryId());
                }
            });


    }

    @Override
    public int getItemCount() {
        return searchList.size();
    }

    public class SubViewHolder extends RecyclerView.ViewHolder {
        TextView textView, txLocation, txDate, txTime, txPrice;
        ImageView SubImg;

        public SubViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.txtEventName);
            SubImg = itemView.findViewById(R.id.SubImg);
            txLocation = itemView.findViewById(R.id.txLocation);
            txDate = itemView.findViewById(R.id.txDate);
            txTime = itemView.findViewById(R.id.txTime);
            txPrice = itemView.findViewById(R.id.txPrice);
        }
    }


    @SuppressLint("NotifyDataSetChanged")
    public void Search(CharSequence charSequence, RecyclerView categoryListRecyclerView) {
        try {
            String charString = charSequence.toString().toLowerCase().trim();
            if (charString.isEmpty()) {
                searchList = subcategoryList;
                categoryListRecyclerView.setVisibility(View.VISIBLE);
            } else {
                int flag = 0;
                List<Subcategory> filterList = new ArrayList<>();
                for (Subcategory Row : subcategoryList) {
                    if (Row.getSubCategoryName().toString().toLowerCase().contains(charString.toLowerCase())) {
                        filterList.add(Row);
                        flag = 1;
                    }
                }
                if (flag == 1) {
                    searchList = filterList;
                    categoryListRecyclerView.setVisibility(View.VISIBLE);
                } else {
                    categoryListRecyclerView.setVisibility(View.GONE);
                }
            }
            notifyDataSetChanged();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
