package com.example.pizzaapp;

import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import org.apache.commons.lang3.BooleanUtils;
import android.widget.AdapterView.OnItemSelectedListener;
import java.util.ArrayList;
import java.util.List;

public class OrderingMenuPage extends AppCompatActivity implements OnItemSelectedListener {

    private static final Integer base_rate = 5;
    private static final Integer chicken_rate = 3;
    private static final Integer sausage_rate = 3;
    private static final Integer pepperoni_rate = 3;
    private static final Integer veggies_rate = 3;
    private static final Integer ec_rate = 2;
    private static final Integer small_size = 4;
    private static final Integer regular_size = 8;
    private static final Integer large_size = 12;
    private static String item;
    float total_price;
    Integer pizza_quantity = 1;
    String SummaryList;

    // From the XML Layout
    EditText CustomerNameText;
    TextView PizzaQuantity;
    CheckBox chickenCheckbox, sausageCheckbox, pepperoniCheckbox, veggiesCheckbox;
    RadioButton extraCheese;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ordering_menu_page);
        // Finding the XML elements by ID
        PizzaQuantity = findViewById(R.id.pizza_quantity);
        CustomerNameText = findViewById(R.id.customer_name);
        chickenCheckbox = findViewById(R.id.chicken_id_XML);
        sausageCheckbox = findViewById(R.id.sausage_id_XML);
        pepperoniCheckbox = findViewById(R.id.pepperoni_id_XML);
        veggiesCheckbox = findViewById(R.id.veggies_id_XML);
        extraCheese = findViewById(R.id.extraCheeseXML);
        // Spinner class here
        Spinner s = (Spinner) findViewById(R.id.spinner);
        // click listener for spinner
        s.setOnItemSelectedListener(this);
        // Drop down elements for spinner
        List<String> sizes = new ArrayList<String>();
        sizes.add("Small");
        sizes.add("Regular");
        sizes.add("Large");
        // Spinner adapter
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, sizes);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        s.setAdapter(dataAdapter);
    }
    // Spinner Function
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        item = parent.getItemAtPosition(position).toString();
        ((TextView) parent.getChildAt(0)).setTextColor(0xffffffff);
    }
    public void onNothingSelected(AdapterView<?> arg0) {
    }

    // Checking for Error inUserName
    private boolean ifEmptyUser(){
        // Checking If username is present or not
        String customerName = CustomerNameText.getText().toString();
        if(customerName == null || customerName.isEmpty()){
            Context c = getApplicationContext();
            String limit = "User Name is Mandatory.";
            int time = Toast.LENGTH_SHORT;
            Toast t = Toast.makeText(c, limit, time);
            t.show();
            return true;
        }
        return false;
    }

    // Getting Information
    private String getInfo() {
        boolean chicken_checkbox = chickenCheckbox.isChecked();
        boolean sausage_checkbox = sausageCheckbox.isChecked();
        boolean pepperoni_checkbox = pepperoniCheckbox.isChecked();
        boolean veggies_checkbox = veggiesCheckbox.isChecked();
        boolean extracheese_checkbox = extraCheese.isChecked();

        // Total Price of Purchase.
        total_price = findPrice(chicken_checkbox, sausage_checkbox, pepperoni_checkbox, veggies_checkbox, extracheese_checkbox,item,pizza_quantity);
        // Making Summary of the order
        return getSummaryList(CustomerNameText.getText().toString(), chicken_checkbox, sausage_checkbox, pepperoni_checkbox, veggies_checkbox, extracheese_checkbox, total_price);
    }



    // Order
    public void finalPizzaOrder(View view) {
        if (!ifEmptyUser()) {
            SummaryList = getInfo();
            Intent emailIntent = new Intent(Intent.ACTION_SEND);
            // declaring "text/plain" MIME type because intent don't have URI
            emailIntent.setType("plain/text");
            // Mail Recipients
            emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[] {"vandiths_pizzacorner@gmail.com"});
            // Mail Subject
            emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Summary List");
            // Adding the Summary List
            emailIntent.putExtra(Intent.EXTRA_TEXT, SummaryList);
            // Going to Email Intent
            startActivity(Intent.createChooser(emailIntent, ""));
        }
    }
    // On Click of Order Pizza
    private String getSummaryList(String customerName, boolean chicken_checkbox, boolean sausage_checkbox,
                                  boolean pepperoni_checkbox, boolean veggies_checkbox, boolean extraCheese_checkbox, float total_price) {
        return getString(R.string.summary_list_name, customerName) +"\n"+"\n"+
                "Size: "+item +"\n"+
                getString(R.string.summary_list_chicken, BooleanUtils.toStringYesNo(chicken_checkbox))+"\n"+
                getString(R.string.summary_list_sausage,BooleanUtils.toStringYesNo(sausage_checkbox)) +"\n"+
                getString(R.string.summary_list_pepperoni,BooleanUtils.toStringYesNo(pepperoni_checkbox)) +"\n"+
                getString(R.string.summary_list_veggies,BooleanUtils.toStringYesNo(veggies_checkbox)) +"\n"+
                getString(R.string.summary_list_extracheese,BooleanUtils.toStringYesNo(extraCheese_checkbox)) +"\n"+
                getString(R.string.summary_list_quantity, pizza_quantity)+"\n"+
                getString(R.string.summary_list_total_price, total_price) +"\n"+"\n"+
                getString(R.string.thank_you);
    }
    // Summary List
    public void SummaryListClass(View view) {
        if (!ifEmptyUser()) {
            SummaryList = getInfo();
            Intent intent = new Intent(OrderingMenuPage.this, SummaryPage.class);
            intent.putExtra("SummaryList", SummaryList);
            startActivity(intent);
        }
    }
    // to Find total Price
    private float findPrice(boolean chicken, boolean sausage, boolean pepperoni, boolean veggies, boolean exCheese,String size, Integer quantity) {
        int basePrice = base_rate;
        if (chicken) { basePrice += chicken_rate; }
        if (sausage) { basePrice += sausage_rate; }
        if (pepperoni){ basePrice += pepperoni_rate; }
        if (size=="Small"){ basePrice += small_size;}
        if (size=="Regular"){ basePrice += regular_size;}
        if (size=="Large"){ basePrice += large_size;}
        if(veggies){ basePrice += veggies_rate; }
        if(exCheese){ basePrice += ec_rate; }
        return quantity * basePrice;
    }
    // to increase the quantity
    public void increase(View view) {
        if (pizza_quantity < 15) { pizza_quantity = pizza_quantity + 1; display(pizza_quantity); }
        else {
            Log.i("Order", "Please select not more than 15 Pizzas");
            Context c = getApplicationContext();
            String limit = "Please select not more than 15 Pizzas";
            int time = Toast.LENGTH_SHORT;
            Toast t = Toast.makeText(c, limit, time);
            t.show();
            return;
        }
    }
    // to decrease quantity
    public void decrease(View view) {
        if (pizza_quantity > 1) { pizza_quantity = pizza_quantity - 1; display(pizza_quantity); }
        else {
            Log.i("OrderingMenuPage", "Order Minimum 1 Pizza");
            Context c = getApplicationContext();
            String limit = "Order Minimum 1 Pizza";
            int time = Toast.LENGTH_SHORT;
            Toast t = Toast.makeText(c, limit, time);
            t.show();
            return;
        }
    }

    // Displaying the Quantity
    private void display(int pizza_count) {
        PizzaQuantity.setText("" + pizza_count);
    }

    // On Click of Contact
    public void callStore(View view) {
        String phone_number= "9876543210";
        Intent callIntent = new Intent(Intent.ACTION_DIAL);
        callIntent.setData(Uri.parse("tel:"+phone_number));
        // Going to Email Intent
        startActivity(Intent.createChooser(callIntent, ""));
    }
    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {
    }
}




















