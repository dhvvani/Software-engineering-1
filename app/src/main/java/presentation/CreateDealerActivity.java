package presentation;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.R;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import business.DealerHandler;
import objects.Dealer;

public class CreateDealerActivity extends AppCompatActivity
{
    //regular expression that makes sure names are between 1-100 characters long and only consist of non-numerical characters
    private static final String NAME_REGEX = "^[a-zA-Z\\s'-]{1,100}$";
    private static final Pattern namePattern = Pattern.compile(NAME_REGEX);

    //regular expression patterns that matches most emails
    private static final String EMAIL_REGEX = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
    private static final Pattern emailPattern = Pattern.compile(EMAIL_REGEX);
    private EditText ed_fName, ed_lName, ed_phoneNumber, ed_email;
    private Button addDealer;

    private Dealer newDealer;

    private DealerHandler dealerHandler;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dealer);

        ed_phoneNumber = findViewById(R.id.number);
        ed_email = findViewById(R.id.email);
        ed_fName = findViewById(R.id.firstName);
        ed_lName = findViewById(R.id.lastName);

        addDealer = findViewById(R.id.dealer_add);
        addDealer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //dealer details
                String[] dealerDets = new String[4];
                EditText[] EditTextDetails = {ed_fName, ed_lName, ed_phoneNumber, ed_email};

                for(int i = 0; i < EditTextDetails.length; i++)
                {
                    dealerDets[i] = EditTextDetails[i].getText().toString();
                    System.out.println(dealerDets[i]);
                }

                boolean validFullName = isValidName(dealerDets[0]) && isValidName(dealerDets[1]);
                boolean validPhoneNumber = isValidPhoneNumber(dealerDets[2]);
                boolean validEmail = isValidEmail(dealerDets[3]);

                boolean blankField = dealerDets[0].equals("") || dealerDets[1].equals("") || dealerDets[2].equals("") || dealerDets[3].equals("");
                if( !blankField && validFullName && validPhoneNumber && validEmail ) {
                    dealerHandler = new DealerHandler();
                    newDealer = new Dealer("9", dealerDets[0] + " " + dealerDets[1], dealerDets[2], dealerDets[3]);

                    newDealer = dealerHandler.insertDealer(newDealer);

                    List allDel = dealerHandler.getAllDealers();

                    for(int i = 0; i < allDel.size(); i++)
                        System.out.println(allDel.get(i).toString());

                    startActivity(new Intent(CreateDealerActivity.this, AddCarActivity.class));
                }//if
                else{
                    if( blankField )
                    {
                        Toast.makeText(CreateDealerActivity.this, "please fill in all mandatory fields", Toast.LENGTH_LONG).show();
                    }
                    else if( !validFullName )
                    {
                        Toast.makeText(CreateDealerActivity.this, "first or last name (or both) provided are invalid", Toast.LENGTH_LONG).show();
                    }
                    else if( !validPhoneNumber )
                    {
                        Toast.makeText(CreateDealerActivity.this, "phone number provided is invalid", Toast.LENGTH_LONG).show();
                    }
                    else if( !validEmail )
                    {
                        Toast.makeText(CreateDealerActivity.this, "email provided is invalid", Toast.LENGTH_LONG).show();
                    }

                }//else
            }//onClick
        });
    }

    public static boolean isValidName(String name) {
        Matcher matcher = namePattern.matcher(name);
        return matcher.matches();
    }

    //any phone number does not consist of non-numeric characters and has to have between 10 and 15 characters(including'+')
    private boolean isValidPhoneNumber(String phoneNumber) {
        return phoneNumber.matches("[0-9+]+") && phoneNumber.length() >= 10 && phoneNumber.length() <= 14;
    }

    public static boolean isValidEmail(String email) {
        Matcher matcher = emailPattern.matcher(email);
        return matcher.matches();
    }
}
