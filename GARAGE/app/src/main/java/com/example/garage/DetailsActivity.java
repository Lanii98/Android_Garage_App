package com.example.garage;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class DetailsActivity extends AppCompatActivity {

    FloatingActionButton fabHome, fabYT, fabDialer;
    TextView txtDetailsYear, txtDetailsPrice;
    ImageView imageView;
    String brand, model, price, year, CATEGORY;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        fabHome = findViewById(R.id.fabHome);
        fabYT = findViewById(R.id.fabYT);
        fabDialer = findViewById(R.id.fabDialer);

        txtDetailsYear = findViewById(R.id.txtDetailsYear);
        txtDetailsPrice = findViewById(R.id.txtDetailsPrice);

        imageView = findViewById(R.id.imageDetailsView);

        //Toolbar in activityDetails that displays item brand and model
        MaterialToolbar details_toolbar = findViewById(R.id.detailsToolbar);

        //Load data onto the Activity display
        Bundle bundle = getIntent().getExtras();
        if(bundle != null)
        {

            //Check value of CATEGORY to assign only required data into the DetailsView: AKA,
            //only the Others category needs less data
            CATEGORY = bundle.getString("CATEGORY");

            if (CATEGORY != null) {
                switch (CATEGORY)
                {
                    case "Cars":
                    case "Bikes":
                        brand = bundle.getString("BRAND");
                        model = bundle.getString("MODEL");
                        year = bundle.getString("YEAR");
                        price = bundle.getString("PRICE");
                        int image = bundle.getInt("IMAGE");

                        //Display item Brand and Model in the toolbar
                        details_toolbar.setTitle(bundle.getString("BRAND") + " " +
                                bundle.getString("MODEL"));

                        txtDetailsYear.setText(year);
                        txtDetailsPrice.setText(price);
                        imageView.setImageResource(image);


                        break;
                    case "Others":
                        brand = bundle.getString("BRAND");
                        price = bundle.getString("PRICE");
                        int image1 = bundle.getInt("IMAGE");

                        txtDetailsYear.setVisibility(View.GONE);

                        //Display item Brand in the toolbar
                        details_toolbar.setTitle(bundle.getString("BRAND"));

                        //Display item Price
                        txtDetailsPrice.setText(price);
                        imageView.setImageResource(image1);

                        //Hides the Youtube FAB, because, why would you want to see a youtube review
                        //of anything in the Others category?!
                        //hmm ok, maybe for the Dash Cam or Radio Systems if I had real products
                        //listed there instead of generic ones!
                        fabYT.setVisibility(View.GONE);

                        break;
                    default:
                        Toast.makeText(getApplicationContext(), "Error gathering array data",
                                Toast.LENGTH_LONG).show();
                        break;

                }
            }
        }


        //Make the FABs actually do something! I love lambda expressions, in this case they are
        //pretty FABulous! :D
        fabHome.setOnClickListener(v -> openMainActivity());
        fabYT.setOnClickListener(v -> searchYT(brand, model, year));
        fabDialer.setOnClickListener(v -> callGarage());

    }


    private void callGarage() {

        Intent intent_dial = new Intent(Intent.ACTION_DIAL);
        // fake number of course but useful for proving the Intent works!
        Uri number = Uri.parse("tel:+353" + "871234567");
        intent_dial.setData(number);

        //Toast to make the user know something is going to happen when they click the FAB
        try {
            Toast.makeText(this, "Opening Phone app...",
                    Toast.LENGTH_LONG).show();
            startActivity(intent_dial);
        } catch(ActivityNotFoundException e) {
            Toast.makeText(this, "Cannot open Phone Dialer :'(",
                    Toast.LENGTH_LONG).show();
        }

    }


    private void searchYT(String ytBrand, String ytModel, String ytYear) {

        //Browsers tend to replace " " with "+" in the URL, so this does that since we require the
        // full URL and not just the search term!
        String concatQuery = ytBrand + "+" + ytModel + "+" + ytYear + " review";
        //Hardcoded to youtube.com
        String URL = "https://www.youtube.com/results?search_query=" + concatQuery;
        Uri uri_URL = Uri.parse(URL);
        Intent ytSearch = new Intent(Intent.ACTION_VIEW, uri_URL);

        //Toast to make the user know something is going to happen when they click the FAB
        try {
            Toast.makeText(this, "Searching for reviews on Youtube...",
                    Toast.LENGTH_LONG).show();
            startActivity(ytSearch);
        } catch (ActivityNotFoundException e) {
            Toast.makeText(this, "Cannot search Youtube :'(",
                    Toast.LENGTH_LONG).show();
        }

    }


    private void openMainActivity() {

        //Lets go home! Back to MainActivity
        Intent mainActivityIntent = new Intent(this, MainActivity.class);
        startActivity(mainActivityIntent);

    }


}