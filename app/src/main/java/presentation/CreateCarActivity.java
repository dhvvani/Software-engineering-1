package presentation;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.R;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import business.CarHandler;
import business.DealerHandler;
import objects.Car;
import objects.Dealer;

import java.util.Calendar;

public class CreateCarActivity extends AppCompatActivity
{
    EditText ed_make, ed_model, ed_year,ed_km, ed_des, ed_price;
    Spinner sp_trans,sp_fuel, sp_dealer;
    CarHandler data = new CarHandler();
    DealerHandler dealerData = new DealerHandler();

    List<Dealer> allDealers;
    ArrayList<String> arrayList_dealer = new ArrayList<>();
    ArrayAdapter<String> arrayAdapter_dealer;

    ArrayList<String> arrayList_trans = new ArrayList<>();
    ArrayAdapter<String>arrayAdapter_trans;

    ArrayList<String> arrayList_fuel = new ArrayList<>();
    ArrayAdapter<String> arrayAdapter_fuel;

    Car newCar;


    private Button addCar;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_car);

        ed_make = findViewById(R.id.make_ip);
        ed_model = findViewById(R.id.model_ip);
        ed_year = findViewById(R.id.year_ip);
        ed_km = findViewById(R.id.km_ip);
        ed_des = findViewById(R.id.des_ip);
        ed_price = findViewById(R.id.price_ip);

        sp_trans = findViewById(R.id.trans_ip);
        sp_fuel = findViewById(R.id.fuel_ip);
        sp_dealer = findViewById(R.id.dealer_ip);

        addCar = findViewById(R.id.create_car);

        allDealers = dealerData.getAllDealers();

        for(int i = 0; i < allDealers.size(); i++)
        {
            arrayList_dealer.add(allDealers.get(i).getName());
        }

        ArrayList<String> nameList = arrayList_dealer.stream().distinct().collect(Collectors.toCollection(ArrayList::new));
        arrayAdapter_dealer = new ArrayAdapter<>(getApplicationContext(),android.R.layout.simple_spinner_item,nameList);
        sp_dealer.setAdapter(arrayAdapter_dealer);

        arrayList_trans.add("manual");
        arrayList_trans.add("automatic");
        arrayList_trans = arrayList_trans.stream().distinct().collect(Collectors.toCollection(ArrayList::new));
        arrayAdapter_trans = new ArrayAdapter<>(getApplicationContext(),android.R.layout.simple_spinner_item,arrayList_trans);
        sp_trans.setAdapter(arrayAdapter_trans);

        arrayList_fuel.add("gasoline");
        arrayList_fuel.add("diesel");
        arrayList_fuel = arrayList_fuel.stream().distinct().collect(Collectors.toCollection(ArrayList::new));
        arrayAdapter_fuel = new ArrayAdapter<>(getApplicationContext(),android.R.layout.simple_spinner_item,arrayList_fuel);
        sp_fuel.setAdapter(arrayAdapter_fuel);

        addCar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String[] carDets = new String[6];
                EditText[] EditTextDetails = {ed_make, ed_model, ed_year, ed_km, ed_des, ed_price};

                for(int i = 0; i < EditTextDetails.length; i++)
                {
                    carDets[i] = EditTextDetails[i].getText().toString();
                    System.out.println(carDets[i]);
                }

                boolean validYear = validYear(carDets[2]);
                boolean validMileage = validMileage((carDets[3]));
                boolean validPrice = validPrice(carDets[5]);
                boolean blankField = carDets[0].equals("") || carDets[1].equals("") || carDets[2].equals("") || carDets[3].equals("") || carDets[5].equals("");

                if(!blankField  && validYear && validMileage && validPrice)
                {
                    String transChosen= sp_trans.getSelectedItem().toString();
                    String fuelTypeChosen= sp_fuel.getSelectedItem().toString();
                    String dealerNameChosen= sp_dealer.getSelectedItem().toString();
                    Dealer curDel = null;
                    for(int i = 0; i < allDealers.size(); i++)
                    {
                        if( allDealers.get(i).getName().equalsIgnoreCase(dealerNameChosen)) {
                            curDel = allDealers.get(i);
                            break;
                        }
                    }

                    //assign a random id, since database uses the default
                    newCar = new Car(9, carDets[0], carDets[1], carDets[2], carDets[3], carDets[4], carDets[5], transChosen, fuelTypeChosen, curDel);
                    data.insertCar(newCar);

                    Toast.makeText(CreateCarActivity.this, "car placed on e-market for sale", Toast.LENGTH_LONG).show();
                    startActivity(new Intent(CreateCarActivity.this, MainActivity.class));
                }
                else
                {
                    if( blankField )
                    {
                        Toast.makeText(CreateCarActivity.this, "please fill in all mandatory fields", Toast.LENGTH_LONG).show();
                    }
                    else if( !validYear )
                    {
                        Toast.makeText(CreateCarActivity.this, "year provided is invalid", Toast.LENGTH_LONG).show();
                    }
                    else if( !validMileage )
                    {
                        Toast.makeText(CreateCarActivity.this, "mileage provided is invalid", Toast.LENGTH_LONG).show();
                    }
                    else if( !validPrice )
                    {
                        Toast.makeText(CreateCarActivity.this, "price provided is invalid", Toast.LENGTH_LONG).show();
                    }
                }

            }
        });

    }

    private boolean validYear(String year)
    {
        // Check if the input consists of numeric characters
        if (!year.matches("\\d+")) {
            return false;
        }

        // Convert the input to an integer
        int yearNumeric = Integer.parseInt(year);

        // Check if the year is within a reasonable range
        int currentYear = Calendar.getInstance().get(Calendar.YEAR);
        final int minYear = 1900; // Adjust this as needed
        return yearNumeric >= minYear && yearNumeric <= currentYear && year.length() == 4;
    }

    private boolean validMileage(String mileage)
    {
        String input = mileage.trim();

        // Check if the input consists of numeric characters and that it is rounded to the nearest kilometer
        if (!mileage.matches("\\d+") || input.isEmpty()) {
            return false;
        }
        int numericMil = Integer.parseInt(mileage);

        return numericMil >= 0;
    }

    private boolean validPrice(String price)
    {
        String input = price.trim();

        // Check if the input consists of numeric characters and that it is rounded to the nearest dollar
        if (!price.matches("\\d+")  || input.isEmpty() ) {
            return false;
        }
        int numericPrice = Integer.parseInt(price);

        return numericPrice >= 0;
    }


}
