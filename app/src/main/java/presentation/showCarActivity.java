package presentation;

//import required classes
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
//import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.R;

import java.util.ArrayList;
import java.util.List;

import business.CarHandler;
import business.Filter;
import objects.Car;


//showCarActivity
//  Presentation layer
//  shows the cars on the screen
public class showCarActivity extends AppCompatActivity {

    private List<Car> carList;

    private Filter carFilter;
    private CarHandler helper;

    private ArrayAdapter<Car> carArrayAdapter;
    int image = R.drawable.display;
    ListView listView;

    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter_result);
        listView = findViewById(R.id.resultList);
        Bundle extra = getIntent().getExtras();
        ArrayList<String> criteria = new ArrayList<>();

        String[] labels = {"make", "model", "trans", "fuel", "year", "km"};

        for(int i = 0; i < labels.length; i++)
        {
            criteria.add(extra.getString(labels[i], ""));
        }//show all the labels for each car

        System.out.println(criteria);

        //create filter results if needed
        helper =new CarHandler();
        carFilter = new Filter(criteria,helper.getAllCars());
        carList = carFilter.getFilterResult();
        System.out.println(carList);


        CarAdapter adapter = new CarAdapter(getApplicationContext(), (ArrayList<Car>) carList, image);
        listView.setAdapter(adapter);

        listView.setClickable(true);
        
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Intent intent = new Intent(getApplicationContext(), CarActivity.class);
                Car curCar = carList.get(position);

                String[] labels = {"make", "model", "price", "transmission", "fuel", "description", "dealer","id"};
                String[] info = {curCar.getMake(), curCar.getModel(), curCar.getPrice(), curCar.getTrans(),
                        curCar.getFuel(), curCar.getDes(), curCar.getOwner().toString()};

                for(int i = 0; i < labels.length; i++)
                {
                    if(labels.length-1 == i) {
                        intent.putExtra(labels[i],curCar.getId());
                    } else {
                        intent.putExtra(labels[i], info[i]);
                    }
                }//print labels

                startActivity(intent);
            }//onItemCLick
        });

    }//onCreate
}//showCarActivity