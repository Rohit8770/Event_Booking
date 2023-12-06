package com.example.aestheticaevent.MoreSettings.Ticket.TicketAdapterss;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.aestheticaevent.MoreSettings.Ticket.TicketRespomse.Ticketdetails;
import com.example.aestheticaevent.R;

import java.util.ArrayList;
import java.util.List;

public class TicketAdapter extends RecyclerView.Adapter<TicketAdapter.TicketViewHolder>{
    Context context;
    List<Ticketdetails> ticketdetailsList;
    List<Ticketdetails> searchList;

    public TicketAdapter(Context context, List<Ticketdetails> ticketdetailsList) {
        this.context = context;
        this.ticketdetailsList = ticketdetailsList;
        this.searchList =  new ArrayList<>(ticketdetailsList);
    }


    @NonNull
    @Override
    public TicketAdapter.TicketViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.ticket_item_file, parent, false);
        return new TicketViewHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull TicketAdapter.TicketViewHolder holder, int position) {
        Ticketdetails userdetails = searchList.get(position);
        holder.txtUserName.setText(userdetails.getUsername());
        holder.txtEventSubCateName.setText(userdetails.getSubCategoryName());
        holder.txtEventTime.setText(userdetails.getTiming());
        holder.txtEventDate.setText(userdetails.getDate());
        holder.txtEventAddress.setText(userdetails.getLocation());
        holder.txtPassPrice.setText(userdetails.getTotal_price());
        holder.txtBookingTime.setText(userdetails.getTicketTime());
        holder.txTicketNumber.setText(userdetails.getTicketId());
        holder.txtBookingPerson.setText(userdetails.getQty_member());
    }

    @Override
    public int getItemCount() {
        return searchList.size();
    }

    public class TicketViewHolder extends RecyclerView.ViewHolder {

        TextView txtUserName,txtEventSubCateName,txtEventDate,txtEventTime,txtEventAddress,txtPassPrice,txtBookingTime,txTicketNumber,txtBookingPerson;
        public TicketViewHolder(@NonNull View itemView) {
            super(itemView);

            txtUserName=itemView.findViewById(R.id.txtUserName);
            txtEventDate=itemView.findViewById(R.id.txtEventDate);
            txtEventTime=itemView.findViewById(R.id.txtEventTime);
            txtEventAddress=itemView.findViewById(R.id.txtEventAddress);
            txtPassPrice=itemView.findViewById(R.id.txtPassPrice);
            txtBookingTime=itemView.findViewById(R.id.txtBookingTime);
            txtEventSubCateName=itemView.findViewById(R.id.txtEventSubCateName);
            txTicketNumber=itemView.findViewById(R.id.txTicketNumber);
            txtBookingPerson=itemView.findViewById(R.id.txtBookingPerson);

        }
    }




    @SuppressLint("NotifyDataSetChanged")
    public void Search(CharSequence charSequence, RecyclerView categoryListRecyclerView) {
        try{
            String charString=charSequence.toString().toLowerCase().trim();
            if(charString.isEmpty()){
                searchList=ticketdetailsList;
                categoryListRecyclerView.setVisibility(View.VISIBLE);
            }else{
                int flag=0;
                List<Ticketdetails> filterList=new ArrayList<>();
                for(Ticketdetails Row:ticketdetailsList){
                    if(Row.getSubCategoryName().toString().toLowerCase().contains(charString.toLowerCase())){
                        filterList.add(Row);
                        flag=1;
                    }
                }
                if (flag == 1) {
                    searchList=filterList;
                    categoryListRecyclerView.setVisibility(View.VISIBLE);
                }
                else{
                    categoryListRecyclerView.setVisibility(View.GONE);
                }
            }
            notifyDataSetChanged();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
