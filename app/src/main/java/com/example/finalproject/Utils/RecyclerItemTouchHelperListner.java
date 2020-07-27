package com.example.finalproject.Utils;

import androidx.recyclerview.widget.RecyclerView;

public interface RecyclerItemTouchHelperListner {
    void onSwiped(RecyclerView.ViewHolder viewHolder,int direction,int position);
}
