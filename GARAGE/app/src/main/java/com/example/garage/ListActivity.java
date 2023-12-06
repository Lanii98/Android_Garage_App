package com.example.garage;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.util.ArrayList;

public class ListActivity extends AppCompatActivity implements RecyclerViewInterface {

    FloatingActionButton fabListItem;
    String selected_value;
    ArrayList<ListItem> listItems = new ArrayList<>();
    int[] listImages; // Handle images from drawable folder separately

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        //Go to MainActivity if FAB is clicked
        fabListItem = findViewById(R.id.fabListItem);
        fabListItem.setOnClickListener(v -> openMainActivity());

        //get selectedCategory from MainActivity
        Intent startButtonAction = getIntent();
        selected_value = startButtonAction.getStringExtra("CATEGORY");

        assert selected_value != null; //Android Studio told me to add this line since it thinks selected_value is always null...

        //Load the data, the most time consuming part of the project cause it kept breaking on me ;-;, but it works now :D
        load_data(selected_value);

        //Setting up RecyclerView + binding it to a newly created Adapter: Do this AFTER settings up the data
        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        MyAdapter adapter = new MyAdapter(this, listItems, this);
        recyclerView.setAdapter(adapter);
        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL);
        staggeredGridLayoutManager.offsetChildrenVertical(-20);
        recyclerView.setLayoutManager(staggeredGridLayoutManager);
    }

    private void load_data(String type_category) {

        //Use Switch case to load data depending on category
        switch (type_category) {
            case "Cars":

                //Get data from each of the 4 car related string-arrays in strings.xml + Load them into local arrays
                String[] car_brand_array = getResources().getStringArray(R.array.car_brand_array);
                String[] car_model_array = getResources().getStringArray(R.array.car_model_array);
                String[] car_year_array = getResources().getStringArray(R.array.car_year_array);
                String[] car_price_array = getResources().getStringArray(R.array.car_price_array);

                //This is where the image array is handled separately, well...1 of 3 times!
                listImages = new int[]{
                        R.drawable.car1,
                        R.drawable.car2,
                        R.drawable.car3,
                        R.drawable.car4,
                        R.drawable.car5,
                        R.drawable.car6,
                        R.drawable.car7,
                        R.drawable.car8,
                        R.drawable.car9,
                        R.drawable.car10,
                        R.drawable.car11,
                        R.drawable.car12,
                        R.drawable.car13,
                        R.drawable.car14
                };

                //Loop through each element in the array and add each value to listItems
                //Does not matter which of the 4 arrays we use to determine how many times the loop iterates
                for(int i = 0; i < car_brand_array.length; i++) {

                    listItems.add(new ListItem(
                            car_brand_array[i],
                            car_model_array[i],
                             car_year_array[i],
                            "€" + car_price_array[i],
                            listImages[i]
                    ));

                }

                break;
            case "Bikes":
                String[] bike_brand_array = getResources().getStringArray(R.array.bike_brand_array);
                String[] bike_model_array = getResources().getStringArray(R.array.bike_model_array);
                String[] bike_year_array = getResources().getStringArray(R.array.bike_year_array);
                String[] bike_price_array = getResources().getStringArray(R.array.bike_price_array);

                listImages = new int[] {
                        R.drawable.bike1,
                        R.drawable.bike2,
                        R.drawable.bike3
                };

                for(int i = 0; i < bike_brand_array.length; i++) {

                    listItems.add(new ListItem(
                            bike_brand_array[i],
                            bike_model_array[i],
                             bike_year_array[i],
                            "€" + bike_price_array[i],
                            listImages[i]
                    ));

                }

                break;
            case "Others":
                String[] others_name_array = getResources().getStringArray(R.array.others_name_array);
                String[] others_price_array = getResources().getStringArray(R.array.others_price_array);

                listImages = new int[]{
                        R.drawable.other1,
                        R.drawable.other2,
                        R.drawable.other3,
                        R.drawable.other4,
                        R.drawable.other5,
                        R.drawable.other6,
                        R.drawable.other7,
                        R.drawable.other8,
                        R.drawable.other9
                };

                for(int i = 0; i < others_name_array.length; i++) {

                    //Method Overloading to the rescue!!
                    listItems.add(new ListItem(
                            others_name_array[i],
                            "€" + others_price_array[i],
                            listImages[i]
                    ));
                }

                break;
            default:
                Toast.makeText(getApplicationContext(), "Error gathering array data", Toast.LENGTH_LONG).show();
                break;
        }

    }

    private void openMainActivity() {

        Intent mainActivityIntent = new Intent(this, MainActivity.class);
        ActivityOptions.makeSceneTransitionAnimation(this);
        startActivity(mainActivityIntent);

    }

    @Override
    public void onItemClick(int position) {

        String selected = selected_value;
        Bundle detailsBundle = new Bundle();

        switch (selected)
        {

            //Since "Others" needs less values than the other 2 categories, we use the current selected category to determine what data to include
            //in our bundle!

            //Found out I can have two cases here at once, pretty neat! If they run the same code anyway
            case "Cars":
            case "Bikes":
                detailsBundle.putString("CATEGORY", selected);
                detailsBundle.putString("BRAND", listItems.get(position).getBrand());
                detailsBundle.putString("MODEL", listItems.get(position).getModel());
                detailsBundle.putString("YEAR", listItems.get(position).getYear());
                detailsBundle.putString("PRICE", listItems.get(position).getPrice());
                detailsBundle.putInt("IMAGE", listItems.get(position).getImage());

                break;
            case "Others":
                detailsBundle.putString("CATEGORY", selected);
                detailsBundle.putString("BRAND", listItems.get(position).getBrand());
                detailsBundle.putString("PRICE", listItems.get(position).getPrice());
                detailsBundle.putInt("IMAGE", listItems.get(position).getImage());

                break;
            default:
                Toast.makeText(getApplicationContext(), "Error creating bundle", Toast.LENGTH_LONG).show();
                break;
        }

        Intent intent = new Intent(ListActivity.this, DetailsActivity.class);
        intent.putExtras(detailsBundle);
        startActivity(intent);

    }
}