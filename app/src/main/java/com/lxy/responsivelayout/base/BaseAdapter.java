package com.lxy.responsivelayout.base;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewbinding.ViewBinding;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

public abstract class BaseAdapter<T> extends RecyclerView.Adapter<BaseAdapter.ViewBindHolder> {

    protected List<T> datas = new ArrayList<>();
    protected Context mContext;
    private OnItemClickListener<T>  itemListener;
    private OnItemLongClickListener<T>  itemLongListener;
    private Map<Integer, OnItemClickListener<T>> itemChildListener = new HashMap<>();
    private Map<Integer, OnItemCheckListener<T>> itemCheckListener = new HashMap<>();

    public void setData(List<T> list){
        datas.clear();
        datas.addAll(list);
        notifyDataSetChanged();
    }

    public void addData(T t){
        datas.add(t);
        notifyItemInserted(datas.size());
    }

    public void addData(T t, int position){
        datas.add(position, t);
        notifyItemInserted(position);
    }

    public void addData(List<T> t){
        datas.addAll(t);
        notifyItemRangeInserted(datas.size() - t.size() - 1, datas.size());
    }

    public void addData(List<T> t, int position){
        datas.addAll(position, t);
//        notifyItemRangeInserted(position, t.size());
//        notifyItemChanged(position - 1);
        notifyDataSetChanged();
    }

    public void remove(int position){
        datas.remove(position);
        notifyDataSetChanged();
    }

    public void removeList(List<T> t){
        datas.removeAll(t);
        notifyDataSetChanged();
    }

    public void remove(T t){
        int i = datas.indexOf(t);
        if (i >= 0){
            remove(i);
        }
    }

    public void removeAll(){
        datas.clear();
        notifyDataSetChanged();
    }

    public void setOnItemClickListener(OnItemClickListener<T> listener){
        this.itemListener = listener;
    }

    public void setOnItemLongClickListener(OnItemLongClickListener<T>  listener){
        this.itemLongListener = listener;
    }

    public void setOnItemChildClickListener(int viewId, OnItemClickListener<T> listener){
        itemChildListener.put(viewId, listener);
    }

    public void setOnItemChildCheckListener(int viewId, OnItemCheckListener<T> listener){
        itemCheckListener.put(viewId, listener);
    }


    @NonNull
    @Override
    public ViewBindHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        this.mContext = parent.getContext();
        return new ViewBindHolder(onCreateViewBinding(viewType,LayoutInflater.from(parent.getContext()), parent));
    }


    @Override
    public void onBindViewHolder(@NonNull ViewBindHolder holder, int position) {
        convert(holder, datas.get(position), position);
        holder.itemView.setOnClickListener(view -> {
            if (itemListener != null){
                itemListener.onItemClick(view, datas.get(position), position);
            }
        });
        holder.itemView.setOnLongClickListener(view -> {
            if (itemLongListener != null){
                itemLongListener.onItemLongClick(view, datas.get(position), position);
            }
            return false;
        });
        Set<Integer> set = this.itemChildListener.keySet();
        for (int viewId : set) {
            holder.getView(viewId).setOnClickListener(view -> {
                this.itemChildListener.get(viewId).onItemClick(view, datas.get(position), position);
            });
        }
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    protected abstract ViewBinding onCreateViewBinding(int viewType, LayoutInflater inflater, ViewGroup parent);

    protected abstract void convert(ViewBindHolder holder, T t, int position);

    public interface OnItemClickListener<T>{
        void onItemClick(View view, T t, int position);
    }

    public interface OnItemCheckListener<T>{
        void onItemCheck(CompoundButton view, boolean result, T t, int position);
    }

    public interface OnItemLongClickListener<T>{
        void onItemLongClick(View view, T t, int position);
    }

    public static class ViewBindHolder extends RecyclerView.ViewHolder{
        private View itemView;
        public ViewBinding binding;

        public ViewBindHolder(@NonNull ViewBinding binding) {
            super(binding.getRoot());
            itemView = binding.getRoot();
            this.binding = binding;
        }

        public <T extends View> T getView(int viewId){
            return itemView.findViewById(viewId);
        }

    }
}
