package presentation;

//import static java.lang.Math.round;

//Import required classes
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.R;

import business.Favorite;
import business.IcalculatorEMI;
import business.CarHandler;
import objects.Car;
import objects.Dealer;


// CarActivity
//  Presentation Layer
//  use car activity to set on click activity
public class CarActivity extends AppCompatActivity {

    public static Favorite fvList = new Favorite();
    Button add;
    Button buy;
    public CarHandler removeCar = new CarHandler();

    //Calculates the monthly installments
    Button calculateEMI;
    private Car favoriteCar;
    private String []parts;
    private String dealer;
    private Dealer owner;

    //onCreate - initializes the required
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car);
        Bundle extras = getIntent().getExtras();

        //Material that is required to be displayed
        // Getting the right strings to be displayed
        if (extras != null) {
            TextView make_TV = findViewById(R.id.text_make);
            make_TV.setText(extras.getString("make", ""));

            TextView model_TV = findViewById(R.id.text_model);
            model_TV.setText(extras.getString("model", ""));

            TextView price_TV = findViewById(R.id.text_price);
            price_TV.setText(extras.getString("price", ""));

            TextView trans_TV = findViewById(R.id.text_transmission);
            trans_TV.setText(extras.getString("transmission", ""));

            TextView fuel_TV = findViewById(R.id.text_fuel);
            fuel_TV.setText(extras.getString("fuel", ""));

            TextView description_TV = findViewById(R.id.text_description);
            description_TV.setText(extras.getString("description", ""));

            EditText downPayment_ET = findViewById(R.id.EMI_Downpayment);

            EditText interestRate_ET = findViewById(R.id.EMI_InterestRate);

            EditText loanTerm_ET = findViewById(R.id.EMI_LoanTerm);

            TextView paymentValue_TV=findViewById(R.id.EMI_Value);


            //Calculates and deals with the EMI tool based on click of the button
            calculateEMI= findViewById(R.id.EMI_Calculate);
            calculateEMI.setOnClickListener(new View.OnClickListener(){
                @SuppressLint("DefaultLocale")
                @Override
                public void onClick(View view){
                    double downP,interest,loanTerm;

                    downP=getIntFromEditText(downPayment_ET);

                    interest=getIntFromEditText(interestRate_ET);

                    loanTerm=getIntFromEditText(loanTerm_ET);

                    double price = Double.parseDouble(extras.getString("price", ""));
                    String truncatedResult="";
                    String result;
                    if(loanTerm > 0) {
                        truncatedResult = String.format("%.2f", IcalculatorEMI.calculateEMI(price, downP, interest, loanTerm));
                        result = "$"+ truncatedResult;
                    }
                    else{
                        truncatedResult = "Loan term cannot be 0";
                        result = truncatedResult;
                    }

                    paymentValue_TV.setText(result);
                }

                // getIntFromEditText - returns the int value from the text field provided
                public double getIntFromEditText(EditText textField) {
                    if (textField.getText().toString().equals("") ) {
                        Toast.makeText(getApplicationContext(), "Enter Values for the field " , Toast.LENGTH_SHORT).show();
                        return 0;
                    }
                    else {
                        return Double.parseDouble(textField.getText().toString());
                    }
                }//getIntFromEditText


            });

            dealer = extras.getString("dealer", "");
            parts = dealer.split("\n");
            owner = new Dealer(parts[0], parts[1], parts[2], parts[3]);

            favoriteCar = new Car(extras.getInt("id"),
                    extras.getString("make", ""),
                    extras.getString("model", ""),
                    extras.getString("year", ""),
                    extras.getString("km", ""),
                    extras.getString("description", ""),
                    extras.getString("price", ""),
                    extras.getString("transmission", ""),
                    extras.getString("fuel", ""), owner);

        }//if


        //favourite car button
        add = findViewById(R.id.addFavorite);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fvList.addToFavorite(favoriteCar);
                Toast.makeText(getApplicationContext(), "favorite", Toast.LENGTH_SHORT).show();
            }//onClick listener
        });

        // buy button
        buy = findViewById(R.id.buyButton);
        buy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setContentView(R.layout.activity_contact);


                TextView dealer_TV = findViewById(R.id.text_dealer);
                dealer_TV.setText(extras.getString("dealer", ""));

                TextView price_TV = findViewById(R.id.text_price);
                price_TV.setText(extras.getString("price", ""));

                // buy button
                Button confirm = findViewById(R.id.confirmBuy);
                confirm.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Car carbyID = removeCar.getCarByID(extras.getInt("id"));

                        Log.d("CarActivity", "Before removal: " + removeCar.getAllCars().size()); // Log before removal
                        removeCar.deleteCar(carbyID);
                        Log.d("CarActivity", "After removal: " + removeCar.getAllCars().size()); // Log after removal
                        Toast toast = Toast.makeText(getApplicationContext(), "You purchased the car!", Toast.LENGTH_SHORT);
                        toast.show();

                        startActivity(new Intent(CarActivity.this, MainActivity.class));
                    }
                });
            }//onClick listener
        });
    }//onCreate




}//CarActivity