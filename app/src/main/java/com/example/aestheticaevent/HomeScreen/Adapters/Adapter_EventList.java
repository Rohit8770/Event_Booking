package com.example.aestheticaevent.HomeScreen.Adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.aestheticaevent.HomeScreen.ActivityEventinfo;
import com.example.aestheticaevent.HomeScreen.ActivitySubCategoryEvent;
import com.example.aestheticaevent.HomeScreen.ActivitySubEvent;
import com.example.aestheticaevent.HomeScreen.Fragment.UpComingFragment;
import com.example.aestheticaevent.HomeScreen.HomeResponse.CategoryListResponse;
import com.example.aestheticaevent.HomeScreen.HomeResponse.Closeevent;
import com.example.aestheticaevent.R;

import java.util.ArrayList;
import java.util.List;

public class Adapter_EventList extends RecyclerView.Adapter<Adapter_EventList.EventViewHolder> {
    Context context;
    List<CategoryListResponse.Getcategory> categoryList;
    List<CategoryListResponse.Getcategory> searchList;

    Adapter_EventList.CategoryInterface categoryInterface;

    public boolean isEmpty() {
        return getItemCount() == 0;
    }
    public interface CategoryInterface {
        void onCategoryClicked(String categoryId, String categoryName);
    }


    public void setCategoryInterface(Adapter_EventList.CategoryInterface categoryInterface) {
        this.categoryInterface = categoryInterface;
    }

    public Adapter_EventList(Context context, List<CategoryListResponse.Getcategory> categoryList) {
        this.context = context;
        this.categoryList = categoryList;
        this.searchList = new ArrayList<>(categoryList);
    }

    @NonNull
    @Override
    public EventViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_event, parent, false);
        return new EventViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EventViewHolder holder, int position) {
        CategoryListResponse.Getcategory category = searchList.get(position);

        try {
            Glide.with(context)
                    .load(searchList.get(position).getCategoryImage())
                    .placeholder(R.drawable.person_image)
                    .error(R.drawable.ic_launcher_foreground)
                    .into(holder.itemEventImage);
        } catch (Exception e) {
            e.printStackTrace();
        }

        holder.itemTextView.setText(category.getCategoryName());

      /*  holder.LayoutId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, ActivitySubCategoryEvent.class);
                i.putExtra("categoryId", category.getCategoryId());
                v.getContext().startActivity(i);
            }
        });*/

        holder.itemView.setOnClickListener(v -> {
            if (categoryInterface != null) {
                categoryInterface.onCategoryClicked(category.getCategoryImage(), category.getCategoryName());
            }
        });


        holder.ivItemLike.setVisibility(View.GONE);

        holder.ivItemUnLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                holder.ivItemUnLike.setVisibility(View.GONE);
                holder.ivItemLike.setVisibility(View.VISIBLE);
            }
        });

        holder.ivItemLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.ivItemLike.setVisibility(View.GONE);
                holder.ivItemUnLike.setVisibility(View.VISIBLE);
            }
        });
    }

    @Override
    public int getItemCount() {
        return searchList.size();
    }

    static class EventViewHolder extends RecyclerView.ViewHolder {
        ImageView itemEventImage,ivItemLike, ivItemUnLike;
        TextView itemTextView;
        CardView LayoutId;

        public EventViewHolder(@NonNull View itemView) {
            super(itemView);
            itemEventImage = itemView.findViewById(R.id.itemEventImage);
            itemTextView = itemView.findViewById(R.id.itemTextView);
            LayoutId = itemView.findViewById(R.id.LayoutId);
            ivItemLike = itemView.findViewById(R.id.ivItemLike);
            ivItemUnLike = itemView.findViewById(R.id.ivItemUnLike);
        }
    }
    public void Search(CharSequence charSequence, RecyclerView categoryListRecyclerView) {
        try {
            String charString = charSequence.toString().toLowerCase().trim();
            if (charString.isEmpty()) {
                searchList = new ArrayList<>(categoryList);
                categoryListRecyclerView.setVisibility(View.VISIBLE);
            } else {
                List<CategoryListResponse.Getcategory> filterList = new ArrayList<>();
                for (CategoryListResponse.Getcategory Row : categoryList) {
                    if (Row.getCategoryName().toLowerCase().contains(charString.toLowerCase())) {
                        filterList.add(Row);
                    }
                }
                searchList = new ArrayList<>(filterList);

                if (searchList.isEmpty()) {
                    categoryListRecyclerView.setVisibility(View.GONE);
                } else {
                    categoryListRecyclerView.setVisibility(View.VISIBLE);
                }
            }
            notifyDataSetChanged();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

  /*  public void Search(CharSequence charSequence, RecyclerView rcv) {
        try {
            String charString = charSequence.toString().toLowerCase().trim();
            if (charString.isEmpty()) {
                searchList = categoryList;
                rcv.setVisibility(View.VISIBLE);
            } else {
                int flag = 0;
                List<CategoryListResponse.Getcategory> filterlist = new ArrayList<>();
                for (CategoryListResponse.Getcategory Row : categoryList) {
                    if (Row.getCategoryName().toLowerCase().contains(charString.toLowerCase())) {
                        filterlist.add(Row);
                        flag = 1;
                    }
                }
                if (flag == 1) {
                    searchList = filterlist;
                    rcv.setVisibility(View.VISIBLE);
                } else {
                    rcv.setVisibility(View.GONE);
                }
            }
            notifyDataSetChanged();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }*/

}
