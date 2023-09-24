package com.example.project5;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ChicagoStyleActivity extends AppCompatActivity {
    private StoreOrder currentStoreOrder;
    private Order currentOrder;
    private ImageView picture;
    private RadioGroup sizes;
    private RadioButton small;
    private Button remove;
    private Button add;
    private Button addToOrder;
    private Spinner spinner;
    private ListView toppingsAvailable;
    private ListView toppingsSelected;
    private TextView price;
    private TextView crust;
    private String [] names = {"Build Your Own", "Deluxe", "Meatzza", "BBQ Chicken"};
    private Topping[] toppings = { Topping.SAUSAGE, Topping.BBQCHICKEN, Topping.PROVOLONE,
            Topping.CHEDDAR, Topping.BEEF, Topping.HAM, Topping.PEPPERONI, Topping.GREENPEPPER,
            Topping.ONION, Topping.MUSHROOM, Topping.PINEAPPLE, Topping.BLACKOLIVES, Topping.SPINACH};

    private List<String> pizzaTypes = Arrays.asList(names);
    private List<Topping> availableToppings  = new ArrayList<>(Arrays.asList(toppings));
    private List<Topping> selectedToppings = new ArrayList<>();
    private ArrayAdapter<String> adapter;
    private ArrayAdapter<Topping> toppingAvailableAdapter;
    private ArrayAdapter<Topping> toppingSelectedAdapter;

    private PizzaFactory pizzaFactory = new ChicagoStylePizzaFactory();
    private DecimalFormat df = new DecimalFormat("#.##");

    private static Pizza currPizza;
    private static Topping selected;
    private static Topping selectedToRemove;
    private static Size currSize = Size.SMALL;

    private AlertDialog.Builder emptyStoreCancelDialogBuilder;
    private AlertDialog.Builder noSelectionDialogBuilder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chicago_style);
        initializeSpinner();
        initializeListViews();
        initializeValues();
        addTopping();
        removeTopping();
        changeSize();
        changeValues();
        addToOrder();
    }

    private void initializeValues(){
        this.currentOrder = MainActivity.currentOrder;
        this.currentStoreOrder = MainActivity.storeOrder;

        price = (TextView) (findViewById(R.id.price));
        crust = (TextView) (findViewById(R.id.crust));

        small = (RadioButton) (findViewById(R.id.radioButton4)) ;
        small.setChecked(true);
        currPizza = pizzaFactory.createBuildYourOwn();
        currPizza.setSize(Size.SMALL);
        price.setText(""+df.format(currPizza.price()));
        crust.setText(currPizza.getCrust().toString());

        picture = (ImageView) findViewById(R.id.imageView3);
    }

    /**
     * Initializes the Spinner
     * NEED TO ADD UI FOR CRUST
     */
    private void initializeSpinner(){
        spinner = (Spinner) findViewById(R.id.spinner);
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, pizzaTypes);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
    }

    private void initializeListViews(){
        toppingsAvailable = (ListView) findViewById(R.id.listView2);
        toppingsAvailable.setSelector(new ColorDrawable(Color.GRAY));
        toppingsAvailable.setChoiceMode(AbsListView.CHOICE_MODE_SINGLE);
        availableToppings  = new ArrayList<>(Arrays.asList(toppings));
        toppingAvailableAdapter = new ArrayAdapter<Topping>(this, android.R.layout.simple_list_item_1,  availableToppings);
        toppingsAvailable.setAdapter(toppingAvailableAdapter);

        toppingsSelected = (ListView) findViewById(R.id.listView);
        toppingsSelected.setSelector(new ColorDrawable(Color.GRAY));
        toppingsSelected.setChoiceMode(AbsListView.CHOICE_MODE_SINGLE);
        selectedToppings = new ArrayList<>();
        toppingSelectedAdapter = new ArrayAdapter<Topping>(this, android.R.layout.simple_list_item_1,  selectedToppings);
        toppingsSelected.setAdapter(toppingSelectedAdapter);
    }

    private void addTopping() {
        add = (Button) findViewById(R.id.button);
        toppingsAvailable.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> adapter, View v, int position, long id) {
                selected = (Topping) adapter.getItemAtPosition(position);
            }
        });

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selected == null) {
                    CharSequence text = getResources().getString(R.string.noToppingsSelectedToAdd);
                    int timeLimit = Toast.LENGTH_LONG;
                    Toast successToast = Toast.makeText(ChicagoStyleActivity.this, text, timeLimit);
                    successToast.show();
                } else if (selectedToppings.size() == 7) {
                    CharSequence text = getResources().getString(R.string.maxToppingsReached);
                    int timeLimit = Toast.LENGTH_LONG;
                    Toast successToast = Toast.makeText(ChicagoStyleActivity.this, text, timeLimit);
                    successToast.show();
                } else {
                    availableToppings.remove(selected);
                    toppingAvailableAdapter.notifyDataSetChanged();
                    selectedToppings.add(selected);
                    toppingSelectedAdapter.notifyDataSetChanged();
                    currPizza.add(selected);
                    price.setText(""+df.format(currPizza.price()));
                    selected = null;
                }
            }
        });
    }

    private void removeTopping() {
        remove = (Button) findViewById(R.id.button2);
        toppingsSelected.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> adapter, View v, int position, long id) {
                selectedToRemove = (Topping) adapter.getItemAtPosition(position);
            }
        });

        remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectedToRemove == null) {
                    CharSequence text = getResources().getString(R.string.noToppingsSelectedToRemove);
                    int timeLimit = Toast.LENGTH_LONG;
                    Toast successToast = Toast.makeText(ChicagoStyleActivity.this, text, timeLimit);
                    successToast.show();
                }
                else {
                    selectedToppings.remove(selectedToRemove);
                    toppingSelectedAdapter.notifyDataSetChanged();
                    availableToppings.add(selectedToRemove);
                    toppingAvailableAdapter.notifyDataSetChanged();
                    currPizza.remove(selectedToRemove);
                    price.setText(""+df.format(currPizza.price()));
                    selectedToRemove= null;
                }
            }
        });
    }

    private void changeSize(){
        sizes = (RadioGroup) findViewById(R.id.radioGroup);
        sizes.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            public void onCheckedChanged(RadioGroup group, int checkedId)
            {
                RadioButton checkedRadioButton = (RadioButton) group.findViewById(checkedId);
                boolean isChecked = checkedRadioButton.isChecked();
                if (isChecked)
                {
                    currSize = Size.valueOf(checkedRadioButton.getText().toString().toUpperCase());
                    currPizza.setSize(currSize);
                    price.setText(""+df.format(currPizza.price()));
                }
            }
        });
    }

    private void changeValues(){
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                String selectedVal = parentView.getSelectedItem().toString();
                add.setEnabled(false);
                remove.setEnabled(false);
                if (selectedVal.equals("Deluxe")){
                    picture.setImageResource(R.drawable.chicagodeluxe);
                    currPizza = pizzaFactory.createDeluxe();
                    }
                else if (selectedVal.equals("Meatzza")){
                    picture.setImageResource(R.drawable.chicagomeatzza);
                    currPizza = pizzaFactory.createMeatzza();
                  }
                else if (selectedVal.equals("BBQ Chicken")){
                    picture.setImageResource(R.drawable.chicagobbq);
                    currPizza = pizzaFactory.createBBQChicken();
                    }
                else {
                    add.setEnabled(true);
                    remove.setEnabled(true);
                    picture.setImageResource(R.drawable.chicagobyo);
                    currPizza = pizzaFactory.createBuildYourOwn();
                   }
                selectedToppings.clear();
                if (!(selectedVal.equals("Build Your Own"))){selectedToppings.addAll(currPizza.getToppings()); }
                toppingSelectedAdapter.notifyDataSetChanged();
                currPizza.setSize(currSize);
                price.setText(""+df.format(currPizza.price()));
                crust.setText(currPizza.getCrust().toString());
            }
            @Override
            public void onNothingSelected(AdapterView<?> parentView) {}
        });
    }

    private void addToOrder(){
        addToOrder = (Button) findViewById(R.id.button4);
        addToOrder.setOnClickListener( new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (selectedToppings.size() == 0) {
                    CharSequence text = getResources().getString(R.string.minOneTopping);
                    int timeLimit = Toast.LENGTH_LONG;
                    Toast successToast = Toast.makeText(ChicagoStyleActivity.this, text, timeLimit);
                    successToast.show();
                } else {
                    currentOrder.add(currPizza);
                    currentOrder.addToStringArray(currPizza, "Chicago Style");
                    CharSequence text = getResources().getString(R.string.pizzaAdded);
                    int timeLimit = Toast.LENGTH_LONG;
                    Toast successToast = Toast.makeText(ChicagoStyleActivity.this, text, timeLimit);
                    successToast.show();
                    // can make alert dialog instead later
                    initializeValues();
                    initializeListViews();
                    initializeSpinner();
                }
            }
        });
    }
    /**
     * Returns a textual representation of a numerical monetary amount.
     *
     * @param input value to be formatted
     * @return String representation of input double in format $0.00.
     */
    private String amountString(double input) {
        DecimalFormat twoDecimalPlaces = new DecimalFormat("$0.00");
        return twoDecimalPlaces.format(input);
    }

    protected void onStart() {
        super.onStart();
        System.out.println("onStart");
    }

    //dummy onPause method
    public void onPause() {
        super.onPause();
        System.out.println("onPause");
    }

    //dummy onResume method
    public void onResume() {
        super.onResume();
        System.out.println("onResume");
    }

}