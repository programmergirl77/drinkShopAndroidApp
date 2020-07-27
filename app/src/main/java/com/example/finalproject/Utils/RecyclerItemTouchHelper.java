package com.example.finalproject.Utils;

import android.graphics.Canvas;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.example.finalproject.Adapter.CartAdapter;
import com.example.finalproject.Adapter.FavouriteAdapter;

public class RecyclerItemTouchHelper extends ItemTouchHelper.SimpleCallback {

    RecyclerItemTouchHelperListner listner;

    public RecyclerItemTouchHelper(int dragDirs, int swipeDirs,RecyclerItemTouchHelperListner listner) {
        super(dragDirs, swipeDirs);
        this.listner = listner;
    }

    @Override
    public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
        return true;
    }

    @Override
    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {

        if ( listner != null)
            listner.onSwiped(viewHolder,direction,viewHolder.getAdapterPosition());
    }

    @Override
    public void clearView(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
        if (viewHolder instanceof FavouriteAdapter.FavouriteViewHolder)
        {
        View ForeGroundView = ((FavouriteAdapter.FavouriteViewHolder)viewHolder).view_foreGround;
        getDefaultUIUtil().clearView(ForeGroundView);}
        else if (viewHolder instanceof CartAdapter.CartViewHolder)
        {
            View ForeGroundView = ((CartAdapter.CartViewHolder)viewHolder).view_foreGround;
            getDefaultUIUtil().clearView(ForeGroundView);}
    }

    @Override
    public int convertToAbsoluteDirection(int flags, int layoutDirection) {
        return super.convertToAbsoluteDirection(flags, layoutDirection);
    }

    @Override
    public void onSelectedChanged(@Nullable RecyclerView.ViewHolder viewHolder, int actionState) {

        if (viewHolder != null)
        {
            if (viewHolder instanceof FavouriteAdapter.FavouriteViewHolder)
            {
                View ForeGroundView = ((FavouriteAdapter.FavouriteViewHolder)viewHolder).view_foreGround;
                getDefaultUIUtil().onSelected(ForeGroundView);}
            else if (viewHolder instanceof CartAdapter.CartViewHolder)
            {
                View ForeGroundView = ((CartAdapter.CartViewHolder)viewHolder).view_foreGround;
                getDefaultUIUtil().onSelected(ForeGroundView);}
        }
    }

    @Override
    public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {

        if (viewHolder instanceof FavouriteAdapter.FavouriteViewHolder)
        {
            View ForeGroundView = ((FavouriteAdapter.FavouriteViewHolder)viewHolder).view_foreGround;
            getDefaultUIUtil().onDraw(c, recyclerView, ForeGroundView, dX, dY, actionState, isCurrentlyActive);
        }
        else if (viewHolder instanceof CartAdapter.CartViewHolder)
        {
            View ForeGroundView = ((CartAdapter.CartViewHolder)viewHolder).view_foreGround;
            getDefaultUIUtil().onDraw(c, recyclerView, ForeGroundView, dX, dY, actionState, isCurrentlyActive);

        }
        assert viewHolder instanceof FavouriteAdapter.FavouriteViewHolder;
        View ForeGroundView = ((FavouriteAdapter.FavouriteViewHolder)viewHolder).view_foreGround;
        getDefaultUIUtil().onDraw(c, recyclerView, ForeGroundView, dX, dY, actionState, isCurrentlyActive);
    }

    @Override
    public void onChildDrawOver(@NonNull Canvas c, @NonNull RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {

        if (viewHolder instanceof FavouriteAdapter.FavouriteViewHolder)
        {
            View ForeGroundView = ((FavouriteAdapter.FavouriteViewHolder)viewHolder).view_foreGround;
            getDefaultUIUtil().onDrawOver(c, recyclerView, ForeGroundView, dX, dY, actionState, isCurrentlyActive);
        }
        else if (viewHolder instanceof CartAdapter.CartViewHolder)
        {
            View ForeGroundView = ((CartAdapter.CartViewHolder)viewHolder).view_foreGround;
            getDefaultUIUtil().onDrawOver(c, recyclerView, ForeGroundView, dX, dY, actionState, isCurrentlyActive);
        }
    }
}
