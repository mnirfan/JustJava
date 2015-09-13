package com.example.android.justjava;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.NumberFormat;

/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends ActionBarActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    public int quantity = 0;

    public boolean iswhipped(){
        CheckBox whipped = (CheckBox) findViewById(R.id.cream_checkbox);
        return whipped.isChecked();
    }

    public boolean ischocolate(){
        CheckBox chocolate = (CheckBox) findViewById(R.id.chocolate_checkbox);
        return chocolate.isChecked();
    }

    public String getname(){
        EditText text = (EditText) findViewById(R.id.name);
        return text.getText().toString();
    }

    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view) {
        int total = quantity*5000;
        if (ischocolate()){
            total += 2000;
        }
        if (iswhipped()){
            total += 1000;
        }

        String summary =
                "Name: " + getname() + "\n" +
                "Add whipped Cream: " + iswhipped() + "\n" +
                "Add chocolate: " + ischocolate() + "\n" +
                "Total price Rp" + total + "\n" +
                "Thank You.";
        displayMessage(summary);
        composeEmail(summary, "Coffee order of " + getname());
    }

    public void composeEmail(String message, String subject) {
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // only email apps should handle this
        intent.putExtra(Intent.EXTRA_TEXT, message);
        intent.putExtra(Intent.EXTRA_SUBJECT, subject);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }

    public void increase(View view){
        quantity = quantity + 1;
        if (quantity > 100){
            Context context = getApplicationContext();
            CharSequence text = "You can't order coffe more than 100, no one allowed, except for Chuck Norris.";
            int duration = Toast.LENGTH_SHORT;

            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
            quantity = 100;
        } else {
            display(quantity);
        }
    }

    public void decrease(View view){
        quantity = quantity - 1;
        if (quantity<1){
            Context context = getApplicationContext();
            CharSequence text = "You can't order coffe less than 1, only herp would do that.";
            int duration = Toast.LENGTH_SHORT;

            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
            quantity = 1;
        } else {
            display(quantity);
        }
    }
    /**
     * This method displays the given quantity value on the screen.
     */
    private void display(int number) {
        TextView quantityTextView = (TextView) findViewById(
                R.id.quantity_text_view);
        quantityTextView.setText("" + number);
    }

    /**
     * This method displays the given price on the screen.
     */
    private void displayPrice(int number) {
        TextView priceTextView = (TextView) findViewById(R.id.price_text_view);
        priceTextView.setText(NumberFormat.getCurrencyInstance().format(number));
    }

    /**
     * This method displays the given text on the screen.
     */
    private void displayMessage(String message) {
        TextView priceTextView = (TextView) findViewById(R.id.price_text_view);
        priceTextView.setText(message);
    }
}