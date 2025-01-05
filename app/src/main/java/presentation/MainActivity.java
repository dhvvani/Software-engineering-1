package presentation;

//import required classes
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.example.myapplication.R;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import persistence.utils.DBHelper;
import business.CarHandler;
import objects.Car;


//MainActivity
//  Presentation Layer
//  MainActivity used to interact with the logic layer and use the handlers/
public class MainActivity extends AppCompatActivity {
    public static boolean shouldClearData = false;
    CarHandler database;
    int image = R.drawable.display;


    ListView listView;
    Button filter;
    Button favorite;
    Button addCar;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //should clear data is set to true only when a system test is called
        if (shouldClearData) {
            clearApplicationData();
            shouldClearData = false; // reset the flag
        }//clear

        DBHelper.copyDatabaseToDevice(this);
        database = new CarHandler();

        //allow listings in database to be displayed
        listView = findViewById(R.id.customListView);
        ArrayList<Car> allCars = new ArrayList<>();

        System.out.println("Here i am, in MainActvity.java onCreate()");
        allCars.addAll(database.getAllCars());
        CarAdapter carAdpt = new CarAdapter(getApplicationContext(), allCars, image);

        listView.setAdapter(carAdpt);
        listView.setClickable(true);

        //set up actions upon clicking a listing
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


            Intent intent = new Intent(getApplicationContext(), CarActivity.class);
            List<Car> db = database.getAllCars();
            intent.putExtra("car_profile", image);

            String[] labels = {"make", "model", "price", "transmission", "fuel", "description", "dealer", "id"};
            Car curCar = db.get(position);
            String[] info = {curCar.getMake(), curCar.getModel(), curCar.getPrice(), curCar.getTrans(),
            curCar.getFuel(), curCar.getDes(), curCar.getOwner().toString()};
            for(int i = 0; i < labels.length; i++)
            {
                if(labels.length-1 == i) {
                    intent.putExtra(labels[i],curCar.getId());
                } else {
                    intent.putExtra(labels[i], info[i]);
                }
            }

            startActivity(intent);
            }
        });
        filter = findViewById(R.id.filterButton);
        filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, FilterActivity.class));
            }
        });

        favorite = findViewById(R.id.favoriteButton);
        favorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, FavoriteActivity.class));
            }//onClick
        });

        addCar = findViewById(R.id.addCarButton);

        addCar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, AddCarActivity.class));
            }//onClick
        });
    }//onCreate


    /*only called when tests run after setting the boolean flag to true, else, the cache is never cleared
    Clear the application data to help aid with independent testing of system tests. We had to resort to this
    alternative as rolling back transactions would result in a database lock acquisition error since the actual
    application would still run on the emulator in the background, not giving up the lock. We also tried
    copying the database, however, even though the script file existed, the file could not be found.*/
    public void clearApplicationData() {
        File cache = getCacheDir();
        File appDir = new File(cache.getParent());
        if (appDir.exists()) {
            String[] children = appDir.list();
            for (String s : children) {
                if (!s.equals("lib")) {
                    deleteDir(new File(appDir, s));
                    Log.i("TAG", "File /data/data/APP_PACKAGE/" + s + " DELETED");
                }
            }
        }
    }

    //Helper function for clearing application data
    public static boolean deleteDir(File dir) {
        if (dir != null && dir.isDirectory()) {
            String[] children = dir.list();
            for (int i = 0; i < children.length; i++) {
                boolean success = deleteDir(new File(dir, children[i]));
                if (!success) {
                    return false;
                }
            }
            return dir.delete();
        } else if (dir != null && dir.isFile()) {
            return dir.delete();
        } else {
            return false;
        }
    }


}//MainActivity