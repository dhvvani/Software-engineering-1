package presentation;

//import required classes
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.R;

import java.util.ArrayList;
import java.util.List;

import objects.Car;


//FavoriteActivity
//  Presentation Layer
//  deals with the presentation of favourites
public class FavoriteActivity extends AppCompatActivity {
    private CarAdapter favoriteAdapter;
    private List<Car> carList;
    int image = R.drawable.display;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite);


        carList = CarActivity.fvList.getFavorite();
        favoriteAdapter = new CarAdapter(getApplicationContext(), (ArrayList<Car>) carList,image);
        ListView listView = findViewById(R.id.favoriteList);
        listView.setAdapter(favoriteAdapter);
        Log.d("aaa",carList.toString());

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int position, long l) {
                Car selected = carList.get(position);
                Log.d("CarActivity", "Before removal: " + carList.size()); // Log before removal
                carList.remove(selected); // Remove the item
                favoriteAdapter.notifyDataSetChanged(); // Notify the adapter
                Log.d("CarActivity", "After removal: " + carList.size()); // Log after removal

                Toast.makeText(FavoriteActivity.this, "Removed from favorite", Toast.LENGTH_SHORT).show();
                return true;
            }//onItemLongClick
        });
    }//onCreate

}//FavoriteActivity