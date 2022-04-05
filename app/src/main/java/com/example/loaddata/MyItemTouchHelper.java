package com.example.loaddata;

import android.content.Context;
import android.graphics.Color;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;
public class MyItemTouchHelper extends ItemTouchHelper.Callback {

    private final ItemTouchHelperAdapter touchAdapter;


    //Constructor
    public  MyItemTouchHelper(ItemTouchHelperAdapter touchAdapter){
        this.touchAdapter = touchAdapter;
    }

    @Override
    public boolean isLongPressDragEnabled() {
        return false;
    }


    @Override
    public boolean isItemViewSwipeEnabled() {
        return true;
    }


    //Gets called when itemm get dropped
    @Override
    public void clearView(@NonNull @NotNull RecyclerView recyclerView, @NonNull @NotNull RecyclerView.ViewHolder viewHolder) {
        super.clearView(recyclerView, viewHolder);
        //Change color back when get dropped
        viewHolder.itemView.setBackgroundColor(ContextCompat.getColor(viewHolder.itemView.getContext(), R.color.red));
    }

    @Override
    public void onSelectedChanged(@Nullable @org.jetbrains.annotations.Nullable RecyclerView.ViewHolder viewHolder, int actionState) {
        super.onSelectedChanged(viewHolder, actionState);
        if(actionState == ItemTouchHelper.ACTION_STATE_DRAG){
            viewHolder.itemView.setBackgroundColor(ContextCompat.getColor(viewHolder.itemView.getContext(), R.color.purple_500));
        }
    }


    @Override
    public int getMovementFlags(@NonNull @NotNull RecyclerView recyclerView, @NonNull @NotNull RecyclerView.ViewHolder viewHolder) {

        final int dragFlags = ItemTouchHelper.UP| ItemTouchHelper.DOWN;
        final  int swipeFlags = ItemTouchHelper.START | ItemTouchHelper.END;
        return makeMovementFlags(dragFlags, swipeFlags);
    }

    @Override
    public boolean onMove(@NonNull @NotNull RecyclerView recyclerView, @NonNull @NotNull RecyclerView.ViewHolder viewHolder, @NonNull @NotNull RecyclerView.ViewHolder target) {
       touchAdapter.onItemMove(viewHolder.getAdapterPosition(), target.getAdapterPosition());
    return true;
    }

    @Override
    public void onSwiped(@NonNull @NotNull RecyclerView.ViewHolder viewHolder, int direction) {
        touchAdapter.onItemSwiped(viewHolder.getAdapterPosition());
    }
}
