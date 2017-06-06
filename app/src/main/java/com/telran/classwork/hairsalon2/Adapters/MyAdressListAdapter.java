package com.telran.classwork.hairsalon2.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.telran.classwork.hairsalon2.Models.Adress;
import com.telran.classwork.hairsalon2.R;

import java.util.ArrayList;

/**
 * Created by vadim on 02.04.2017.
 */

public class MyAdressListAdapter extends RecyclerView.Adapter<MyAdressListAdapter.MyViewHolder> {
    private final Context context;
    private ArrayList<Adress> adresses = new ArrayList();

    public MyAdressListAdapter(Context context) {
        this.context = context;
    }

    @Override
    public MyAdressListAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_adress_list_row, parent, false);
        return new MyAdressListAdapter.MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Adress adress1 = adresses.get(position);
        holder.adressTxt.setText(adress1.getAdress());
        holder.noteTxt.setText(adress1.getNote());
    }

    @Override
    public int getItemCount() {
        if (adresses == null) {
            return 0;
        }
        return adresses.size();
    }



    public Adress getItem(int position){
        return adresses.get(position);
    }

    public void addItem(Adress item) {
        adresses.add(item);
        notifyItemInserted(adresses.size() - 1);
    }

    public void addItemAtFront(Adress item) {
        adresses.add(item);
        notifyItemInserted(0);
    }

    public void addItem(Adress item, int position) {
        adresses.add(position, item);
        notifyItemInserted(position);
    }

    public void removeItem(Adress item) {
        int position = adresses.indexOf(adresses);
        if (position > 0) {
            adresses.remove(item);
            notifyItemRemoved(position);
        }
    }

    public void removeItem(int position) {
        if (position > 0 && position < adresses.size()) {
            adresses.remove(position);
            notifyItemRemoved(position);
        }
    }

    public void removeItem(int startPosition, int count) {
        if (startPosition > 0 && startPosition < adresses.size() && startPosition + count < adresses.size()) {
            for (int i = startPosition; i < startPosition + count; i++) {
                adresses.remove(i);
            }
            notifyItemRangeRemoved(startPosition, count);
        }
    }

    public void updateItem(Adress item, int position) {
        adresses.set(position, item);
        notifyItemChanged(position);
    }

    public void moveItem(int fromPosition, int toPosition) {
        Adress item = adresses.remove(fromPosition);
        adresses.add(toPosition, item);
        notifyItemMoved(fromPosition, toPosition);
    }

    public void clear() {
        notifyItemRangeRemoved(0, adresses.size());
        adresses.clear();

    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView adressTxt, noteTxt;
        public MyViewHolder(View itemView) {
            super(itemView);
            adressTxt = (TextView) itemView.findViewById(R.id.adress_txt);
            noteTxt = (TextView) itemView.findViewById(R.id.note_txt);
        }
    }
}