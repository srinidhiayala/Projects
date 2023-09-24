package com.example.project5;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class CurrentOrderActivity extends AppCompatActivity {
    private Order currentOrder;
    private StoreOrder currentStoreOrder;
    private ListView basketDisplayList;
    private EditText subtotalAmount;
    private EditText taxAmount;
    private EditText totalAmount;
    private TextView ordernum;
    private AlertDialog.Builder emptyBasketRemoveDialogBuilder;
    private AlertDialog.Builder noSelectionDialogBuilder;
    private AlertDialog.Builder emptyBasketSubmitDialogBuilder;
    private ArrayList<String> toBeRemoved;


    /**
     * Executes when the Activity is created.
     * @param savedInstanceState previously held data about this Activity.
     */
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_current_order);
        initializeValues();
        setUpRemoveDialogs();
        setUpSubmitDialogs();
        updateValues();
    }


    private void updateValues(){
        if(currentOrder.getItems().isEmpty()){
            subtotalAmount.setText(getResources().getString(R.string.dollarPlaceholder));
            taxAmount.setText(getResources().getString(R.string.dollarPlaceholder));
            totalAmount.setText(getResources().getString(R.string.dollarPlaceholder));
        }
        subtotalAmount.setText(formatDouble(currentOrder.getCostBeforeTax()));
        taxAmount.setText(formatDouble(currentOrder.getSalesTax()));
        totalAmount.setText(formatDouble(currentOrder.getCostAfterTax()));
    }

    /**
     * Updates the subtotal, tax, and total displays based on the
     * items that are in the user's current order.
     */
    private CharSequence formatDouble(double input){
        if(input < 0){
            input = 0;
        }
        DecimalFormat twoDecimalPlaces = new DecimalFormat(getResources().getString(R.string.dollarPlaceholder));
        return twoDecimalPlaces.format(input);
    }

    /**
     * Handles the control logic for when the user presses the back
     * button on the Android Menu.
     */
    @Override
    public void onBackPressed(){
        MainActivity.currentOrder = this.currentOrder;
        MainActivity.storeOrder = this.currentStoreOrder;
        finish();
    }

    /**
     * Handles the control logic for when the user presses the back
     * button on the Store Activity itself.
     * @param itemChosen The android.view.MenuItem that was selected
     * @return true if the user pressed the back button.
     * Otherwise, return false for normal menu processing or return
     * true to consume the MenuItem.
     */
  /*  @Override
    public boolean onOptionsItemSelected(android.view.MenuItem itemChosen){
        if (itemChosen.getItemId() == android.R.id.home) {
            MainActivity.currentOrder = this.currentOrder;
            MainActivity.currentStoreOrder = this.currentStoreOrder;
            finish();
            return true;
        }
        return super.onOptionsItemSelected(itemChosen);
    }*/

    /**
     * Creates an adapter for the list view of menu items.
     * @return ArrayAdapter for list of MenuItem objects.
     */
    private ArrayAdapter<String> listViewAdapter(){
        return new ArrayAdapter<String>(CurrentOrderActivity.this,
                android.R.layout.simple_list_item_single_choice, currentOrder.getPizzaToString()){
            @Override
            public View getView(int position, View convertView, ViewGroup parent){
                View view = super.getView(position, convertView, parent);
                ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
                layoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT;
                view.setLayoutParams(layoutParams);
                return view;
            }
        };
    }

    /**
     * Initializes instance variables, sets display of items in the
     * current order.
     */
    private void initializeValues(){
        subtotalAmount = (EditText)(findViewById(R.id.subtotalAmount));
        taxAmount = (EditText)(findViewById(R.id.taxAmount));
        totalAmount = (EditText)(findViewById(R.id.totalAmount));

        toBeRemoved = new ArrayList<>();
        this.currentOrder = MainActivity.currentOrder;
        this.currentStoreOrder = MainActivity.storeOrder;

        Button removeFromBasket = (Button) (findViewById(R.id.removeFromBasket));
        removeFromBasket.setOnClickListener(removeListener());
        Button submitOrder = (Button) (findViewById(R.id.submitOrder));
        submitOrder.setOnClickListener(submitListener());
        Button clearFromBasket= (Button) (findViewById(R.id.clearButton));
        clearFromBasket.setOnClickListener(clearListener());

        basketDisplayList = (ListView)(findViewById(R.id.basketDisplayList));
        basketDisplayList.setAdapter(listViewAdapter());
        basketDisplayList.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        basketDisplayList.setOnItemClickListener(selectItemsListener());

        ordernum= (TextView)(findViewById(R.id.textView12));
        ordernum.setText(Integer.toString(currentOrder.getOrderId()));


        updateValues();
    }

    /**
     * Initializes dialog builders to display errors with removing
     * items from the basket.
     */
    public void setUpRemoveDialogs(){
        emptyBasketRemoveDialogBuilder = new AlertDialog.Builder(this);
        emptyBasketRemoveDialogBuilder.setTitle(R.string.RemoveError);
        emptyBasketRemoveDialogBuilder.setMessage(R.string.removeEmptyBasket);
        emptyBasketRemoveDialogBuilder.setIcon(R.drawable.error);
        emptyBasketRemoveDialogBuilder.setPositiveButton(R.string.OKButton, null);

        noSelectionDialogBuilder = new AlertDialog.Builder(this);
        noSelectionDialogBuilder.setTitle(R.string.RemoveError);
        noSelectionDialogBuilder.setMessage(R.string.removeNoSelection);
        noSelectionDialogBuilder.setIcon(R.drawable.error);
        noSelectionDialogBuilder.setPositiveButton(R.string.OKButton, null);
    }

    /**
     * Initializes dialog builders to display errors with submitting
     * an order.
     */
    public void setUpSubmitDialogs(){
        emptyBasketSubmitDialogBuilder = new AlertDialog.Builder(this);
        emptyBasketSubmitDialogBuilder.setTitle(R.string.submitError);
        emptyBasketSubmitDialogBuilder.setMessage(R.string.submitEmptyBasket);
        emptyBasketSubmitDialogBuilder.setIcon(R.drawable.error);
        emptyBasketSubmitDialogBuilder.setPositiveButton(R.string.OKButton, null);
    }

    /**
     * Creates a listener for the list of menu items which adds and
     * removes items from the toBeRemoved list based on their
     * selection status.
     * @return an AdapterView.OnItemClickListener for the basket.
     */
    private AdapterView.OnItemClickListener selectItemsListener(){
        return new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String itemChosen = (String) adapterView.getItemAtPosition(i);
                if(basketDisplayList.isItemChecked(i)){
                    toBeRemoved.add(itemChosen);
                }
                else{
                    toBeRemoved.remove(itemChosen);
                }
            }

        };

    }

    /**
     * Creates a listener for the Remove From Basket button.
     * @return a View.OnClickListener for the remove button.
     */
    private View.OnClickListener removeListener(){
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (currentOrder.getItems().isEmpty()) {
                    AlertDialog alert = emptyBasketRemoveDialogBuilder.create();
                    alert.show();
                    return;
                }

                if(toBeRemoved.isEmpty()){
                    AlertDialog alert = noSelectionDialogBuilder.create();
                    alert.show();
                    return;
                }
                for (int i=0; i<toBeRemoved.size(); i++){
                    int index= currentOrder.getPizzaToString().indexOf(toBeRemoved.get(i));
                    Pizza toRemove= currentOrder.getItems().get(index);
                    currentOrder.remove(toRemove);
                    currentOrder.getPizzaToString().remove(index);
                }


                basketDisplayList.setAdapter(listViewAdapter());
                Context currentContext = getApplicationContext();
                CharSequence text = getResources().getString(R.string.removeItemMessage);
                int timeLimit = Toast.LENGTH_SHORT;
                Toast successToast = Toast.makeText(currentContext, text, timeLimit);
                successToast.show();
                toBeRemoved.clear();
                updateValues();
            }
        };
    }

    /**
     * Creates a listener for the Submit Order button.
     * @return a View.OnClickListener for the submit button.
     */
    private View.OnClickListener submitListener(){
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(currentOrder.getItems().isEmpty()){
                    AlertDialog alert = emptyBasketSubmitDialogBuilder.create();
                    alert.show();
                    return;
                }
                currentStoreOrder.add(currentOrder);
                basketDisplayList.setAdapter(null);
                MainActivity.counter++;
                ordernum.setText(Integer.toString(MainActivity.counter));
                currentOrder = new Order();
                MainActivity.currentOrder=currentOrder;
                MainActivity.storeOrder=currentStoreOrder;
                currentOrder.setOrderID(MainActivity.counter);
                Context currentContext = getApplicationContext();
                CharSequence text = getResources().getString(R.string.submitOrderMessage);
                int timeLimit = Toast.LENGTH_SHORT;
                Toast successToast = Toast.makeText(currentContext, text, timeLimit);
                successToast.show();

                System.out.println(currentOrder.getItems());
                System.out.println(currentOrder.getPizzaToString());

                updateValues();

            }
        };
    }

    /**
     * Creates a listener for the Remove From Basket button.
     * @return a View.OnClickListener for the remove button.
     */
    private View.OnClickListener clearListener(){
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(currentOrder.getItems().isEmpty()){
                    AlertDialog alert = emptyBasketRemoveDialogBuilder.create();
                    alert.show();
                    return;
                }
                currentOrder.getPizzaToString().clear();
                currentOrder.getItems().clear();
                currentOrder.setCostBeforeTax(0);
                MainActivity.storeOrder=currentStoreOrder;
                MainActivity.currentOrder=currentOrder;
                MainActivity.counter--;
                basketDisplayList.setAdapter(null);
                ordernum.setText(Integer.toString(currentOrder.getOrderId()));
                Context currentContext = getApplicationContext();
                CharSequence text = getResources().getString(R.string.clearItems);
                int timeLimit = Toast.LENGTH_SHORT;
                Toast successToast = Toast.makeText(currentContext, text, timeLimit);
                successToast.show();

//                System.out.println(currentOrder.getItems());
//                System.out.println(currentOrder.getPizzaToString());

                updateValues();
            }
        };
    }
}