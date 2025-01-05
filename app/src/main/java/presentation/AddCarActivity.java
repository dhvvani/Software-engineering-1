package presentation;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.R;

public class AddCarActivity extends AppCompatActivity
{
    Button addWDealer;
    Button addWoutDealer;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_car);

        addWDealer = findViewById(R.id.buttonWDealer);
        addWoutDealer = findViewById(R.id.buttonWoutDealer);

        addWDealer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AddCarActivity.this, CreateDealerActivity.class));
            }
        });

        addWoutDealer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AddCarActivity.this, CreateCarActivity.class));
            }
        });

        }
}
