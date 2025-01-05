package presentation;

//import required classes
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.myapplication.R;

import java.util.ArrayList;

import objects.Car;


//CarAdapter
//  Presentation Layer
//      Extends BaseAdapter
public class CarAdapter extends BaseAdapter
{
    private final Context context;
    private final ArrayList<Car> carList;
    private final int image;
    private final LayoutInflater inflater;

    public CarAdapter(Context ctx, ArrayList<Car> carList, int image)
    {
        this.context = ctx;
        this.carList = carList;
        this.image = image;
        inflater = LayoutInflater.from(ctx);
    }

    @Override
    public int getCount() {
        return carList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = inflater.inflate(R.layout.activity_custom_list_view, null);
        ImageView carImg = convertView.findViewById(R.id.imageIcon);

        Car toShow = carList.get(position);

        setText(R.id.textView, toShow.getMake(), convertView);
        setText(R.id.secTextView, toShow.getModel(), convertView);
        setText(R.id.thirdTextView, "$"+toShow.getPrice(), convertView);

        carImg.setImageResource(image);
        return convertView;
    }

    private void setText(int rid, String text, View convertView)
    {
        TextView textView = convertView.findViewById(rid);
        textView.setText((text));
    }
}
