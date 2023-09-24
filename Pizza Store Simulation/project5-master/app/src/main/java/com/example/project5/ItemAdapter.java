package com.example.project5;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

/**
 * This is an Adapter class to be used to instantiate an adapter for the RecyclerView.
 * Must extend RecyclerView.Adapter, which will enforce you to implement 3 methods:
 *      1. onCreateViewHolder, 2. onBindViewHolder, and 3. getItemCount
 *
 * You must use the data type <thisClassName.yourHolderName>, in this example
 * <ItemAdapter.ItemHolder>. This will enforce you to define a constructor for the
 * ItemAdapter and an inner class ItemsHolder (a static class)
 * The ItemsHolder class must extend RecyclerView.ViewHolder. In the constructor of this class,
 * you do something similar to the onCreate() method in an Activity.
 * @author Lily Chang
 */
class RecyclingAdapter extends RecyclerView.Adapter<RecyclingAdapter.RecyclingHolder> {
   // private Context context; //need the context to inflate the layout
    private ArrayList<Order> items; //need the data binding to each row of RecyclerView
    private static int selected_position = -1;

    public RecyclingAdapter( ArrayList<Order> items) {
       // this.context = context;
        this.items = items;
    }

    /**
     * This method will inflate the row layout for the items in the RecyclerView
     *
     * @param parent
     * @param viewType
     * @return
     */
    @NonNull
    @Override
    public RecyclingHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //inflate the row layout for the items
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.row_view, parent, false);

        return new RecyclingHolder(view);
    }

    public Order getSelectedElem(){
        if (selected_position==-1){
            return null;
        }
        else {
            return items.get(selected_position);
        }
    }

    public void resetSelectedPos() {selected_position = -1;}

    /**
     * Assign data values for each row according to their "position" (index) when the item becomes
     * visible on the screen.
     *
     * @param holder   the instance of ItemsHolder
     * @param position the index of the item in the list of items
     */
    @Override
    public void onBindViewHolder(@NonNull RecyclingHolder holder, int position) {
        //assign values for each row
        holder.pizzaString.setText(items.get(position).toString());
        Order item = items.get(position);
        //holder.itemView.setBackgroundColor(selected_position == position ? Color.GREEN : Color.TRANSPARENT);
        if(position==selected_position)
        {
            //System.out.println(position);
            holder.pizzaString.setText("SELECTED: " + items.get(position).toString());
            holder.pizzaString.setTextColor(Color.RED);
            //holder.cardView.setBackgroundColor(Color.RED);
        }
        else
        {
            holder.pizzaString.setText( items.get(position).toString());
            holder.pizzaString.setTextColor(Color.WHITE);
        }
        //holder.pizzaImage.setImageResource(items.get(position).getImage());
    }

    /**
     * Get the number of items in the ArrayList.
     *
     * @return the number of items in the list.
     */
    @Override
    public int getItemCount() {
        return items.size(); //number of MenuItem in the array list.
    }

    /**
     * Get the views from the row layout file, similar to the onCreate() method.
     */
    public class RecyclingHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView pizzaString;
        private ImageView pizzaImage;
        private CardView cardView;
        private ConstraintLayout parentLayout;//this is the row layout

        public RecyclingHolder(@NonNull View itemView) {
            super(itemView);

            //pizzaImage = itemView.findViewById(R.id.pizzaImage);
            pizzaString = itemView.findViewById(R.id.pizzaString);
            parentLayout = itemView.findViewById(R.id.rowLayout);
            cardView = itemView.findViewById(R.id.cardView);
            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View view) {
            if (getAdapterPosition() == RecyclerView.NO_POSITION) return;

            // Updating old as well as new positions
            notifyItemChanged(selected_position);
            selected_position = getAdapterPosition();
            notifyItemChanged(selected_position);
        }
    }
}

