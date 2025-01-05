package presentation;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.myapplication.R;

//CarListView
//  Presentation Layer
/*  create a custom list view*/
public class CarListView extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_custom_list_view);
    }//onCreate
}//CarListView