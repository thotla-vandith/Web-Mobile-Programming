package com.example.pizzaapp;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import android.text.Html;

public class SummaryPage extends AppCompatActivity {
    TextView summaryInfo;
    Button ButtonToOrder;
    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.summary_list);
        summaryInfo = findViewById(R.id.summaryText);
        ButtonToOrder = findViewById(R.id.orderPizza);
        summaryInfo.setText(Html.fromHtml("<u>Your Pizza Order Summary</u><br/><br/>"));

        if (getIntent() != null) { summaryInfo.append(getIntent().getStringExtra("SummaryList")); }
        else { summaryInfo.setText("No orders yet !!!"); }

        summaryInfo.append(Html.fromHtml("<br/>"));
        summaryInfo.setVisibility(View.VISIBLE);
        ButtonToOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reDirectToOrderingMenuPage();
            }
        });
    }
        public void reDirectToOrderingMenuPage() {
            Intent intent = new Intent(SummaryPage.this, OrderingMenuPage.class);
            startActivity(intent);
        }
}