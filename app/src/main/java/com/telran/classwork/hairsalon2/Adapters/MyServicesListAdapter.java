package com.telran.classwork.hairsalon2.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.telran.classwork.hairsalon2.Models.Services;
import com.telran.classwork.hairsalon2.R;

import java.util.ArrayList;

/**
 * Created by TelRan on 22.03.2017.
 */
public class MyServicesListAdapter extends RecyclerView.Adapter<MyServicesListAdapter.MyViewHolder> {
    private final Context context;
    private ArrayList<Services> items = new ArrayList();

    public MyServicesListAdapter(Context context) {
        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_services_list_row, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Services item = items.get(position);
        holder.servicesTxt.setText(item.getService());
        holder.priceTxt.setText(item.getPrice());
        holder.timeTxt.setText(item.getTime());
    }

    @Override
    public int getItemCount() {
        if (items == null) {
            return 0;
        }
        return items.size();
    }

    public Services getItem(int position){
        return items.get(position);
    }

    public void addItem(Services item) {
        items.add(item);
        notifyItemInserted(items.size() - 1);
    }

    public void addItemAtFront(Services item) {
        items.add(item);
        notifyItemInserted(0);
    }

    public void addItem(Services item, int position) {
        items.add(position, item);
        notifyItemInserted(position);
    }

    public void removeItem(Services item) {
        int position = items.indexOf(items);
        if (position > 0) {
            items.remove(item);
            notifyItemRemoved(position);
        }
    }

    public void removeItem(int position) {
        if (position > 0 && position < items.size()) {
            items.remove(position);
            notifyItemRemoved(position);
        }
    }

    public void removeItem(int startPosition, int count) {
        if (startPosition > 0 && startPosition < items.size() && startPosition + count < items.size()) {
            for (int i = startPosition; i < startPosition + count; i++) {
                items.remove(i);
            }
            notifyItemRangeRemoved(startPosition, count);
        }
    }

    public void updateItem(Services item, int position) {
        items.set(position, item);
        notifyItemChanged(position);
    }

    public void moveItem(int fromPosition, int toPosition) {
        Services item = items.remove(fromPosition);
        items.add(toPosition, item);
        notifyItemMoved(fromPosition, toPosition);
    }

    public void clear() {

        notifyItemRangeRemoved(0, items.size());
        items.clear();

    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView servicesTxt, priceTxt, timeTxt;
        public MyViewHolder(View itemView) {
            super(itemView);
            servicesTxt = (TextView) itemView.findViewById(R.id.services_txt);
            priceTxt = (TextView) itemView.findViewById(R.id.price_txt);
            timeTxt = (TextView) itemView.findViewById(R.id.time_txt);
        }
    }
}