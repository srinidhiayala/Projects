package com.example.project5;

import android.graphics.Color;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RecyclingViewHolder extends RecyclerView.ViewHolder {
    /**
     * The imageView field represents the Image View corresponding to a particular
     * item in the RecyclingView
     */
    private ImageView imageView;

    /**
     * The textView field represents the Text View corresponding to a particular item
     * in the Recycling View
     */
    private TextView textView;

    /**
     * The rowView field represents the View object corresponding to a particular item
     * in the Recycling View
     */
    private View rowView;

    /**
     * Constructor to initialize the imageView, textView, and rowView instance variables
     * @param inputView the View corresponding to a particular item in the RecyclingView
     */
    public RecyclingViewHolder(View inputView){
        super(inputView);
//        this.imageView = inputView.findViewById(R.id.imageViewFlavor);
        this.textView = inputView.findViewById(R.id.orderTextView);
        this.rowView = inputView;

    }

    /**
     * Returns the View object corresponding to the current item in the RecyclingView
     * @return the instance variable rowView
     */
    public View getView(){
        return this.rowView;
    }

    /**
     * Populates an item in the RecyclingView with the appropriate data
     * @param textView the Text View corresponding to a particular item in the Recycling View
     * @param order the particular order for this particular item in the Recycling View
     * @param orderList the entire list of Orders for all the orders
     * @param position the particular position in the RecyclingView to change
     * @param adapter the instance of the RecyclingAdapter that is currently in use
     */
    public void setData(String textView, Order order, ArrayList<Order> orderList,
                        int position, RecyclingAdapter adapter){
//        this.imageView.setImageResource(imageView);
        this.textView.setText(textView);
        this.rowView.setBackgroundColor(order.isSelected() ? Color.CYAN : Color.WHITE);
        this.rowView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for(int i = 0; i < orderList.size(); i ++){
                    if(i == position){
                        orderList.get(i).setSelected(true);
                    }
                    else{
                        orderList.get(i).setSelected(false);
                    }
                }
                adapter.notifyDataSetChanged();
            }
        });
    }
}

