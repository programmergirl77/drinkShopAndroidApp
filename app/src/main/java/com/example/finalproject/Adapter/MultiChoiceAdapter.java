package com.example.finalproject.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import androidx.recyclerview.widget.RecyclerView;

import com.example.finalproject.Model.Drinks;
import com.example.finalproject.R;
import com.example.finalproject.Utils.Common;

import java.util.List;

import io.reactivex.rxjava3.annotations.NonNull;

public class MultiChoiceAdapter extends RecyclerView.Adapter<MultiChoiceAdapter.MultiChoiceViewHolder> {


    Context context;
    List<Drinks> OptionList ;

    public MultiChoiceAdapter(Context context, List<Drinks> optionList) {
        this.context = context;
        OptionList = optionList;
    }

    @NonNull
    @Override
    public MultiChoiceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View itemview = LayoutInflater.from(context).inflate(R.layout.multi_check_layout,null);
        return new MultiChoiceViewHolder(itemview);
    }

    @Override
    public void onBindViewHolder(@NonNull MultiChoiceViewHolder holder, int position) {
        holder.checkBox.setText(OptionList.get(position).Name);
        holder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked)
                {
                    Common.toppingAdded.add(buttonView.getText().toString());
                    Common.Toppingprice+=Double.parseDouble(OptionList.get(position).price);
                }
                else {
                    Common.toppingAdded.remove(buttonView.getText().toString());
                    Common.Toppingprice-=Double.parseDouble(OptionList.get(position).price);

                }

            }
        });

    }

    @Override
    public int getItemCount() {
        return OptionList.size();
    }

    class MultiChoiceViewHolder extends RecyclerView.ViewHolder{

        CheckBox checkBox;

        public MultiChoiceViewHolder(@NonNull View itemView) {
            super(itemView);

            checkBox = itemView.findViewById(R.id.chb_topping);
        }
    }
}
