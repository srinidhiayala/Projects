package com.example.project5;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class MainActivity extends AppCompatActivity {

    ImageButton chicagoButton;
    ImageButton nyButton;
    ImageButton storeorder;
    ImageButton currOrder;
    public static Order currentOrder = new Order();
    public static int counter = 0;
    public static StoreOrder storeOrder = new StoreOrder();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        chicagoButton = findViewById(R.id.chicagoButton);
        nyButton = findViewById(R.id.nyButton);
        storeorder = findViewById(R.id.storeOrderButton);
        currOrder = findViewById(R.id.currentOrderButton);

        chicagoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ChicagoStyleActivity.class);
                startActivity(intent);
            }
        });

        nyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, NYStyleActivity.class);
                startActivity(intent);
            }
        });

        storeorder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, StoreOrderActivity.class);
                startActivity(intent);
            }
        });

        currOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, CurrentOrderActivity.class);
                startActivity(intent);
            }
        });

    }

    /**
     * Updates the store and current order from the controllers to the main controller.
     *
     * @param currentOrder currently placed order
     * @param storeOrder   store order for store.
     */
    public void updateVariables(Order currentOrder, StoreOrder storeOrder) {
        this.currentOrder = currentOrder;
        this.storeOrder = storeOrder;
    }
}