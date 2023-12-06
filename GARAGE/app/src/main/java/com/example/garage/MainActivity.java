package com.example.garage;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    String selectedCategory;
    RadioGroup rdbGroup;
    RadioButton rdbCars, rdbBikes, rdbOthers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Initialise Radio Button Group + 3 Radio Buttons
        rdbGroup = findViewById(R.id.rdbGroup);
        rdbCars = findViewById(R.id.rdbCars);
        rdbBikes = findViewById(R.id.rdbBikes);
        rdbOthers = findViewById(R.id.rdbOthers);

        //Initialise Start Button + Make it invisible so the user cannot continue without selecting a category
        Button btnStart = findViewById(R.id.btnStart);
        btnStart.setVisibility(View.INVISIBLE);

        //Make button appear if any of the 3 radio buttons are checked, so that the user can continue
        rdbGroup.setOnCheckedChangeListener((group, checkedId) -> {
            if (checkedId != -1) {
                if (group.getCheckedRadioButtonId() == R.id.rdbCars ||
                        group.getCheckedRadioButtonId() == R.id.rdbBikes ||
                        group.getCheckedRadioButtonId() == R.id.rdbOthers) {

                    btnStart.setVisibility(View.VISIBLE);
                    btnStart.setOnClickListener(v -> openListActivity());
                } else {
                    btnStart.setVisibility(View.GONE);
                }
            }
        });

    } //end onCreate()

    private void openListActivity() {

        //Find what radio button the user checked and pass it into the Intent
        selectedCategory = (rdbCars.isChecked()) ? "Cars" : (rdbBikes.isChecked()) ? "Bikes" : (rdbOthers.isChecked()) ? "Others" : "";

        // Start ListActivity + Send selected value via .putExtra
        Intent startButtonAction = new Intent(MainActivity.this, ListActivity.class);
        startButtonAction.putExtra("CATEGORY", selectedCategory);
        startActivity(startButtonAction);

    }
}