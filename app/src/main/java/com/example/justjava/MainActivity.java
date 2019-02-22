package com.example.justjava;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import java.text.NumberFormat;

/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * This method is called when the order plus is clicked.
     */
    int quantity = 0;

    public void increment(View view) {
        if(quantity == 100)
        {
            return;
        }
        quantity = quantity + 1;
        displayQuantity(quantity);
    }

    /**
     * This method is called when the order minus is clicked.
     */

    public void decrement(View view) {
        if (quantity == 0) {
        return;
    }
        quantity = quantity - 1;
        displayQuantity(quantity);
    }

    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view) {
        EditText nameField = (EditText) findViewById(R.id.name_field);
        String name = nameField.getText().toString();
        Log.v("MainActivity", "Name: " + name);

        CheckBox whippedCreamCheckBox = (CheckBox) findViewById(R.id.whipped_crearm_checkbox);
        boolean hasWhippedCream = whippedCreamCheckBox.isChecked();
        Log.v("MainActivity", "Has whipped cream: " + hasWhippedCream);

        CheckBox ChoclateCheckBox = (CheckBox) findViewById(R.id.Choclate_checkbox);
        boolean hasChoclate = ChoclateCheckBox.isChecked();
        Log.v("MainActivity", "Has Choclate: " + hasChoclate);


        int price = calculatePrice(hasWhippedCream, hasChoclate);
        String priceMessage = createOrederSummary(name, price, hasWhippedCream, hasChoclate);

        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // only email apps should handle this
        intent.putExtra(Intent.EXTRA_SUBJECT, "just java order for " + name);
        intent.putExtra(Intent.EXTRA_TEXT, priceMessage);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }


    }

    /**
     * Calculates the price of the order.
     */
    private int calculatePrice(boolean addWhippedCream, boolean addChoclate) {

        int basePrice = 5;
        if (addWhippedCream) {
            basePrice = basePrice + 1;
        }
        if (addChoclate) {
            basePrice = basePrice + 2;
        }

        return quantity * basePrice;
    }

    private String createOrederSummary(String name, int price, boolean addWhippedCream, boolean addChoclate) {
        String priceMessage = getString(R.string.order_summary_name,name);
        priceMessage +="\nQuantity  " + quantity + "  cup of coffee";
        priceMessage +="\nAdd whipped cream:  " + addWhippedCream;
        priceMessage +="\nAdd Choclate:  " + addChoclate;
        priceMessage +="\nTotal: £" + price;
        priceMessage +=" \n " + getString(R.string.thank_you);
        return priceMessage;

    }

    /**
     * This method displays the given quantity value on the screen.
     */
    private void displayQuantity(int number) {
        TextView quantityTextView = (TextView) findViewById(
                R.id.quantity_text_view);
        quantityTextView.setText("" + number);
    }

}