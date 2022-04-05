package com.example.loaddata;

import android.os.Bundle;
import android.os.Parcelable;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Filter;
import android.widget.FilterQueryProvider;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class recyclerAdapter extends RecyclerView.Adapter<recyclerAdapter.MyViewHolder> implements ItemTouchHelperAdapter {

    private ArrayList<Series> seriesList;
    private ArrayList<Series> seriesListFull;
    private OnClickListener onClickListener;//Clickable
    private ItemTouchHelper iTouchHelper;
    private ImageView status;

    @Override
    public void onItemMove(int fromPosition, int toPosition) {
        Series fromSeries = seriesList.get(fromPosition);
        seriesList.remove(fromSeries);
        seriesList.add(toPosition, fromSeries);
        notifyItemMoved(fromPosition, toPosition);
    }

    @Override
    public void onItemSwiped(int position) {
        seriesList.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(0,seriesList.size());
    }


    //instantiate TouchHelper
    public void setTouchHelper(ItemTouchHelper touchHelper){
        this.iTouchHelper = touchHelper;
    }


    public interface OnClickListener {
        void onClickListener(int position);
    }

    public recyclerAdapter(ArrayList<Series>seriesList,OnClickListener onClickListener){
        this.seriesList = seriesList;
        seriesListFull = new ArrayList<>(seriesList);//Copy
        this.onClickListener = onClickListener;
    }


    public class MyViewHolder extends RecyclerView.ViewHolder implements
            View.OnTouchListener,
            GestureDetector.OnGestureListener {
        private TextView titleTxt;
        private TextView index;
        private ImageView status;

        OnClickListener onClickListener;
        GestureDetector mGestureDetector;

        public MyViewHolder(@NonNull View view, OnClickListener onClickListener){
            super(view);
            titleTxt = view.findViewById(R.id.titleView); //titelname
            index = view.findViewById(R.id.IndexNumber);//index
            status = view.findViewById(R.id.signal); //status

            mGestureDetector = new GestureDetector(itemView.getContext(),this);
            this.onClickListener = onClickListener;
            view.setOnTouchListener(this);
        }



        @Override
        public boolean onDown(MotionEvent e) {
            return false;
        }

        @Override
        public void onShowPress(MotionEvent e) {

        }

        @Override
        public boolean onSingleTapUp(MotionEvent e) {
            onClickListener.onClickListener(getAdapterPosition());
            return true;
        }

        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
            return false;
        }

        @Override
        public void onLongPress(MotionEvent e) {
            iTouchHelper.startDrag(this);
        }

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            return true;
        }

        @Override
        public boolean onTouch(View v, MotionEvent event) {
            mGestureDetector.onTouchEvent(event);
            return true;
        }
    }


    @NonNull
    @Override
    public recyclerAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_items, parent, false);
        return new MyViewHolder(itemView, onClickListener);
    }

    //Binds Holder with Serie.class
    @Override
    public void onBindViewHolder(@NonNull recyclerAdapter.MyViewHolder holder, int position) {
    String title = seriesList.get(position).getTitle();
    holder.titleTxt.setText(title);

     String status = seriesList.get(position).getEpisode();
        switch (status){
            case  "Finished":
              holder.status.setImageResource(R.drawable.grey_circle);
                break;

            case  "0":
                holder.status.setImageResource(R.drawable.orange_circle);
                break;

            default:
                holder.status.setImageResource(R.drawable.green_circle);
                break;
        }

    int index = position +1;
    holder.index.setText(String.valueOf(index)); //Display index
    }

    @Override
    public int getItemCount() {
        return seriesList.size();
    }



}
