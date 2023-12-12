package com.example.aestheticaevent.HomeScreen.Adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.aestheticaevent.HomeScreen.HomeResponse.Closeevent;
import com.example.aestheticaevent.HomeScreen.HomeResponse.Subcategory;
import com.example.aestheticaevent.R;

import java.util.ArrayList;
import java.util.List;

public class CompleteAdapter extends RecyclerView.Adapter<CompleteAdapter.CompleteViewHolder> {
    Context context;
    List<Closeevent> closeevents;
    List<Closeevent> searchList;

    public CompleteAdapter(Context context, List<Closeevent> closeevents) {
        this.context = context;
        this.closeevents = closeevents;
        this.searchList = new ArrayList<>(closeevents);
    }
    public boolean isEmpty() {
        return getItemCount() == 0;
    }

    @NonNull
    @Override
    public CompleteAdapter.CompleteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.sub_item_event, parent, false);
        return new CompleteViewHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull CompleteAdapter.CompleteViewHolder holder, int position) {
        Closeevent model = searchList.get(position);

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


    }

    @Override
    public int getItemCount() {
        return searchList.size();
    }


    public class CompleteViewHolder extends RecyclerView.ViewHolder {

        TextView textView, txLocation, txDate, txTime,txPrice;
        ImageView SubImg;
        public CompleteViewHolder(@NonNull View itemView) {
            super(itemView);

            textView = itemView.findViewById(R.id.txtEventName);
            SubImg = itemView.findViewById(R.id.SubImg);
            txLocation = itemView.findViewById(R.id.txLocation);
            txDate = itemView.findViewById(R.id.txDate);
            txTime = itemView.findViewById(R.id.txTime);
            txPrice = itemView.findViewById(R.id.txPrice);
        }
    }

    public void Search(CharSequence charSequence, RecyclerView categoryListRecyclerView) {
        try {
            String charString = charSequence.toString().toLowerCase().trim();
            if (charString.isEmpty()) {
                searchList = new ArrayList<>(closeevents);
                categoryListRecyclerView.setVisibility(View.VISIBLE);
            } else {
                List<Closeevent> filterList = new ArrayList<>();
                for (Closeevent Row : closeevents) {
                    if (Row.getSubCategoryName().toLowerCase().contains(charString.toLowerCase())) {
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

}

