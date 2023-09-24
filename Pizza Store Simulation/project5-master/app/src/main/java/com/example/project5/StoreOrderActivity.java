package com.example.project5;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Arrays;

public class StoreOrderActivity extends AppCompatActivity {
    private StoreOrder currentStoreOrder;
    //    private ListView orderDisplayList;
    private ArrayList<Order> cancelingThisOrder;
    private AlertDialog.Builder emptyStoreCancelDialogBuilder;
    private AlertDialog.Builder noSelectionDialogBuilder;
    private ArrayList<Item> itemList = new ArrayList<>();
    private RecyclingAdapter adapter;
    private RecyclerView recyclerView;
    /**
     * Manages the view and display if the user presses the back button on the
     * Android Application.
     */
    @Override
    public void onBackPressed() {
        MainActivity.storeOrder = this.currentStoreOrder;
        finish();
    }

    /**
     * Executes when the Activity is created.
     *
     * @param savedInstanceState previously held data about this Activity.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store_order);
        initializeValues();
        setUpCancelDialogs();
        initializeRecyclerView();
    }

    /**
     * Manages the view and display if the user presses the back button on
     * the Store Activity.
     *
     * @param itemChosen The android.view.MenuItem that was selected
     * @return a true value if the button that the user pressed is the back
     * button. Else, return false.
     */
    @Override
    public boolean onOptionsItemSelected(android.view.MenuItem itemChosen) {
        if (itemChosen.getItemId() == android.R.id.home) {
            MainActivity.storeOrder = this.currentStoreOrder;
            finish();
            return true;
        }
        return super.onOptionsItemSelected(itemChosen);
    }

    /**
     * Creates an adapter for the list view of orders.
     *
     * @return ArrayAdapter that is used for list of Order items.
     */
    private ArrayAdapter<Order> listViewAdapter() {
        return new ArrayAdapter<Order>(StoreOrderActivity.this,
                android.R.layout.simple_list_item_single_choice,
                currentStoreOrder.getOrders()) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View view = super.getView(position, convertView, parent);
                ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
                layoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT;
                view.setLayoutParams(layoutParams);
                return view;
            }
        };
    }

    private void setUpMenuItems(){
        ArrayList<Order> orders = this.currentStoreOrder.getOrders();
        for (Order o: orders) {
            itemList.add(new Item(o.toString()));
        }
    }

    /**
     * Initializes the RecyclerView
     */
    private void initializeRecyclerView(){
//        this. = createFlavorArray(0);
        recyclerView = findViewById(R.id.recyclerView);
       // setUpMenuItems();
       // System.out.println(itemList.get(0));
        adapter = new RecyclingAdapter( this.currentStoreOrder.getOrders() ); //create the adapter
        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        //LinearLayoutManager layoutManagerRecyclerView = new LinearLayoutManager(this);
        //layoutManagerRecyclerView.setOrientation(LinearLayoutManager.VERTICAL);
       // recyclerView.setLayoutManager(layoutManagerRecyclerView);
    }

    /**
     * Initializes the various instance variables to their respective values.
     */
    private void initializeValues() {
        cancelingThisOrder = new ArrayList<>();
        this.currentStoreOrder = MainActivity.storeOrder;

//        orderDisplayList = (ListView) (findViewById(R.id.listViewStoreOrders));
//        orderDisplayList.setAdapter(listViewAdapter());
//        orderDisplayList.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
//        orderDisplayList.setOnItemClickListener(selectItemsListener());
        /*recyclerView = (RecyclerView) (findViewById(R.id.recyclerView));
        RecyclingAdapter adapter = new RecyclingAdapter(currentStoreOrder.getOrders());
        recyclerView.setAdapter(adapter);*/
//        recyclerView.
        Button cancelOrders = (Button) (findViewById(R.id.cancelOrders));
        cancelOrders.setOnClickListener(cancelListener());

        Button cancelAllOrders = (Button) (findViewById(R.id.cancelAllOrders));
        cancelAllOrders.setOnClickListener(cancelListenerAll());
    }

    /**
     * Display respective errors if an order is cancelled in correctly.
     */
    public void setUpCancelDialogs() {
        emptyStoreCancelDialogBuilder = new AlertDialog.Builder(this);
        emptyStoreCancelDialogBuilder.setTitle(R.string.cancelError);
        emptyStoreCancelDialogBuilder.setMessage(R.string.cancelEmptyStore);
        emptyStoreCancelDialogBuilder.setIcon(R.drawable.error);
        emptyStoreCancelDialogBuilder.setPositiveButton(R.string.OKButton, null);

        noSelectionDialogBuilder = new AlertDialog.Builder(this);
        noSelectionDialogBuilder.setTitle(R.string.cancelError);
        noSelectionDialogBuilder.setMessage(R.string.cancelNoSelection);
        noSelectionDialogBuilder.setIcon(R.drawable.error);
        noSelectionDialogBuilder.setPositiveButton(R.string.OKButton, null);
    }

    /**
     * Establishes a listener that organizes the orders that adds and removes
     * items from the list called cancelingThisOrder based on what was selected.
     *
     * @return an AdapterView.OnItemClickListener for the order list.
     */
    private AdapterView.OnItemClickListener selectItemsListener() {
        return new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Order itemChosen = (Order) (adapterView.getItemAtPosition(i));
                if (recyclerView.isSelected()) {
          //     if (orderDisplayList.isItemChecked(i)) {
                    cancelingThisOrder.add(itemChosen);
                } else {
                    cancelingThisOrder.remove(itemChosen);
                }
            }
        };
    }

    /**
     * Establishes a listener for when the Cancel Order button is clicked. First
     * checks if the total orders and the selected orders are not empty, and then
     * removed the orders that need to be removed.
     *
     * @return a View.OnClickListener for the cancel button.
     */
    private View.OnClickListener cancelListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (currentStoreOrder.getOrders().isEmpty()) {
                    AlertDialog alert = emptyStoreCancelDialogBuilder.create();
                    alert.show();
                    return;
                }
                if (adapter.getSelectedElem()==null) {
                    AlertDialog alert = noSelectionDialogBuilder.create();
                    alert.show();
                    return;
                } else {
                    currentStoreOrder.getOrders().remove(adapter.getSelectedElem());
                    RecyclingAdapter adapter = new RecyclingAdapter(currentStoreOrder.getOrders());
                    recyclerView.setAdapter(adapter);
//                orderDisplayList.setAdapter(listViewAdapter());
                    Context currentContext = getApplicationContext();
                    CharSequence text = getResources().getString(R.string.cancelOrderMessage);
                    int timeLimit = Toast.LENGTH_SHORT;
                    Toast successToast = Toast.makeText(currentContext, text, timeLimit);
                    successToast.show();
                    cancelingThisOrder.clear();
                    adapter.resetSelectedPos();
                }
            }
        };
    }

    private View.OnClickListener cancelListenerAll() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (currentStoreOrder.getOrders().isEmpty()) {
                    AlertDialog alert = emptyStoreCancelDialogBuilder.create();
                    alert.show();
                    return;
                }
                currentStoreOrder = new StoreOrder();
                Context currentContext = getApplicationContext();
                CharSequence text = getResources().getString(R.string.cancelAllOrderMessage);
                int timeLimit = Toast.LENGTH_SHORT;
                Toast successToast = Toast.makeText(currentContext, text, timeLimit);
                successToast.show();
//                orderDisplayList.setAdapter(null);
                recyclerView.setAdapter(null);
            }
        };
    }
}
