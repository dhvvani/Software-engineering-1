package presentation;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.myapplication.R;

// Assuming CarFilterManager is in a suitable package
import business.CarFilterManager;
import business.CarHandler;


public class FilterActivity extends AppCompatActivity {

    Spinner sp_make, sp_model, sp_trans, sp_fuel;
    EditText ed_year, ed_km;
    Button search;

    // New instance of CarFilterManager
    CarFilterManager filterManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);

        sp_make = findViewById(R.id.make);
        sp_model = findViewById(R.id.model);
        sp_trans = findViewById(R.id.trans);
        sp_fuel = findViewById(R.id.fuel);
        ed_year = findViewById(R.id.yearSearch);
        ed_km = findViewById(R.id.kmSearch);
        search = findViewById(R.id.search);

        // Initialize CarFilterManager with CarHandler data source
        filterManager = new CarFilterManager(new CarHandler());

        // Setup Spinners
        setupMakeSpinner();
        setupTransSpinner();
        setupFuelSpinner();

        // Setup Search Button Click Listener
        search.setOnClickListener(view -> performSearch());
    }

    private void setupMakeSpinner() {
        ArrayAdapter<String> arrayAdapter_make = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, filterManager.getDistinctMakes());
        arrayAdapter_make.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp_make.setAdapter(arrayAdapter_make);

        sp_make.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                setupModelSpinner(sp_make.getSelectedItem().toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    private void setupModelSpinner(String make) {
        ArrayAdapter<String> arrayAdapter_model = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, filterManager.getModelsForMake(make));
        arrayAdapter_model.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp_model.setAdapter(arrayAdapter_model);
    }

    private void setupTransSpinner() {
        ArrayAdapter<String> arrayAdapter_trans = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, filterManager.getDistinctTransmissions());
        arrayAdapter_trans.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp_trans.setAdapter(arrayAdapter_trans);
    }

    private void setupFuelSpinner() {
        ArrayAdapter<String> arrayAdapter_fuel = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, filterManager.getDistinctFuels());
        arrayAdapter_fuel.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp_fuel.setAdapter(arrayAdapter_fuel);
    }

    private void performSearch() {
        Intent intent = new Intent(FilterActivity.this, showCarActivity.class);
        intent.putExtra("make", sp_make.getSelectedItem().toString());
        intent.putExtra("model", sp_model.getSelectedItem().toString());
        intent.putExtra("trans", sp_trans.getSelectedItem().toString());
        intent.putExtra("fuel", sp_fuel.getSelectedItem().toString());

        if (!ed_year.getText().toString().isEmpty() && !ed_km.getText().toString().isEmpty()) {
            intent.putExtra("year", ed_year.getText().toString());
            intent.putExtra("km", ed_km.getText().toString());
            startActivity(intent);
        } else {
            Toast.makeText(FilterActivity.this, "Please fill all the fields", Toast.LENGTH_SHORT).show();
        }
    }
}
