package com.example.jommakan;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentContainerView;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.room.Room;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    TextView toolbar_title;
    FragmentContainerView fragment_container;

    NavController navController;
    AppBarConfiguration appBarConfiguration;

    // Database class
    FoodDatabase foodDatabase;
    LocationDatabase locationDatabase;
    StallDatabase stallDatabase;

    private static final String USER_FILE_NAME = "user_file";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        User user = (User) bundle.get("user");

        // Store user credential into UserInstance class
        UserInstance.setUsername(user.getUsername());
        UserInstance.setUser_email_address(user.getUser_email_address());
        UserInstance.setPhone_number(user.getPhone_number());
        UserInstance.setPassword(user.getPassword());
        UserInstance.setWallet_balance(user.getWallet_balance());

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        // TextView toolbar_title = findViewById(R.id.toolbar_title);
        setSupportActionBar(toolbar);
        // toolbar_title.setText(toolbar.getTitle());
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        NavHostFragment host = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_container);
        navController = host.getNavController();

        appBarConfiguration = new AppBarConfiguration.Builder(R.id.DestHome, R.id.DestMenu, R.id.DestCart, R.id.DestAccount)
                .build();
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);

        setupBottomNavMenu(navController);

        // Database connection
        foodDatabase = Room.databaseBuilder(this, FoodDatabase.class, "FoodDB").allowMainThreadQueries().build();
        locationDatabase = Room.databaseBuilder(this, LocationDatabase.class, "LocationDB").allowMainThreadQueries().build();
        stallDatabase = Room.databaseBuilder(this, StallDatabase.class, "StallDB").allowMainThreadQueries().build();

        // Add necessary data into database
        addFood();
        addLocation();
        addStall();
    }

    // Bottom Navigation Bar
    private void setupBottomNavMenu (NavController navController) {
        BottomNavigationView bottom_navigation_bar = findViewById(R.id.bottom_navigation_bar);
        NavigationUI.setupWithNavController(bottom_navigation_bar, navController, false);
    }

    // Add food into database
    private void addFood() {
        // KK3
        // Restoran Al-Ehsan
        Food food1 = new Food("Nasi Goreng Kampung", "Kolej Kediaman Tuanku Kursiah (KK3)", "Restoran Al-Ehsan", 6.00, new ArrayList<>(Arrays.asList("Spicy")), R.drawable.nasi_goreng_kampung, new ArrayList<>(Arrays.asList("7.00 AM", "10.00 PM")));
        Food food2 = new Food("Mi Goreng", "Kolej Kediaman Tuanku Kursiah (KK3)", "Restoran Al-Ehsan", 4.00, new ArrayList<>(Arrays.asList("Delicious", "Spicy")), R.drawable.mi_goreng, new ArrayList<>(Arrays.asList("7.00 AM", "10.00 PM")));


        // KK8
        // Restoran Murni
        Food food3 = new Food("Nasi Goreng Kampung", "Kolej Kediaman Kinabalu (KK8)", "Restoran Murni", 5.00, new ArrayList<>(Arrays.asList("Aromatic", "Greasy")), R.drawable.nasi_goreng_kampung, new ArrayList<>(Arrays.asList("7.00 AM", "10.00 PM")));
        Food food4 = new Food("Nasi Goreng Cina", "Kolej Kediaman Kinabalu (KK8)", "Restoran Murni", 5.00, new ArrayList<>(Arrays.asList("Nutritious")), R.drawable.nasi_goreng_cina, new ArrayList<>(Arrays.asList("7.00 AM", "10.00 PM")));
        Food food5 = new Food("Nasi Goreng Tomyam", "Kolej Kediaman Kinabalu (KK8)", "Restoran Murni", 5.50, new ArrayList<>(Arrays.asList("Spicy", "Contains prawn", "Sour")), R.drawable.nasi_goreng_tomyam, new ArrayList<>(Arrays.asList("7.00 AM", "10.00 PM")));
        Food food6 = new Food("Nasi Goreng Pattaya", "Kolej Kediaman Kinabalu (KK8)", "Restoran Murni", 5.00, new ArrayList<>(Arrays.asList("Delicious")), R.drawable.nasi_goreng_pattaya, new ArrayList<>(Arrays.asList("7.00 AM", "10.00 PM")));

        // Restoran Bidayah Bistro
        Food food7 = new Food("Roti Canai", "Kolej Kediaman Kinabalu (KK8)", "Restoran Bidayah Bistro", 1.20, new ArrayList<>(Arrays.asList("Local delight")), R.drawable.roti_canai, new ArrayList<>(Arrays.asList("7.00 AM", "10.00 PM")));
        Food food8 = new Food("Roti Bawang", "Kolej Kediaman Kinabalu (KK8)", "Restoran Bidayah Bistro", 2.80, new ArrayList<>(Arrays.asList("Delicious")), R.drawable.roti_bawang, new ArrayList<>(Arrays.asList("7.00 AM", "10.00 PM")));


        // KK4
        // Restoran Famidah
        Food food9 = new Food("Nasi Goreng Kampung", "Kolej Kediaman Bestari (KK4)", "Restoran Famidah", 5.00, new ArrayList<>(Arrays.asList("Spicy", "Delicious")), R.drawable.nasi_goreng_kampung, new ArrayList<>(Arrays.asList("7.00 AM", "10.00 PM")));
        Food food10 = new Food("Nasi Goreng Cina", "Kolej Kediaman Bestari (KK4)", "Restoran Famidah", 5.00, new ArrayList<>(Arrays.asList("Aromatic")), R.drawable.nasi_goreng_cina, new ArrayList<>(Arrays.asList("7.00 AM", "10.00 PM")));

        // Restoran Al-Safuan
        Food food11 = new Food("Roti Canai", "Kolej Kediaman Bestari (KK4)", "Restoran Al-Safuan", 1.20, new ArrayList<>(Arrays.asList("Local delight")), R.drawable.roti_canai, new ArrayList<>(Arrays.asList("8.00 AM", "7.00 PM")));
        Food food12 = new Food("Nasi Lemak", "Kolej Kediaman Bestari (KK4)", "Restoran Al-Safuan", 6.00, new ArrayList<>(Arrays.asList("Local delight", "Spicy")), R.drawable.nasi_lemak, new ArrayList<>(Arrays.asList("8.00 AM", "7.00 PM")));

        // Brotherhood Western & Grill
        Food food13 = new Food("Chicken Chop", "Kolej Kediaman Bestari (KK4)", "Brotherhood Western & Grill", 9.00, new ArrayList<>(Arrays.asList("Crispy", "Greasy")), R.drawable.chicken_chop, new ArrayList<>(Arrays.asList("11.00 AM", "10.00 PM")));
        Food food14 = new Food("Fish and Chips", "Kolej Kediaman Bestari (KK4)", "Brotherhood Western & Grill", 9.00, new ArrayList<>(Arrays.asList("Crispy")), R.drawable.fish_and_chips, new ArrayList<>(Arrays.asList("11.00 AM", "10.00 PM")));
        Food food15 = new Food("Spaghetti Carbonara", "Kolej Kediaman Bestari (KK4)", "Brotherhood Western & Grill", 7.00, new ArrayList<>(Arrays.asList("Aromatic", "Decadent")), R.drawable.spaghetti_carbonara, new ArrayList<>(Arrays.asList("11.00 AM", "10.00 PM")));


        // Faculty of Education
        // Restoran Abu Khalid
        Food food16 = new Food("Chicken Chop", "Faculty of Education", "Restoran Abu Khalid", 9.00, new ArrayList<>(Arrays.asList("Crispy")), R.drawable.chicken_chop, new ArrayList<>(Arrays.asList("7.00 AM", "10.00 PM")));
        Food food17 = new Food("Nasi Kerabu", "Faculty of Education", "Restoran Abu Khalid", 6.00, new ArrayList<>(Arrays.asList("Local delight", "Spicy")), R.drawable.nasi_kerabu, new ArrayList<>(Arrays.asList("7.00 AM", "10.00 PM")));

        // Piccadilly
        Food food18 = new Food("Bihun Tomyam", "Faculty of Education", "Piccadilly", 5.00, new ArrayList<>(Arrays.asList("Spicy", "Contains prawn")), R.drawable.bihun_tomyam, new ArrayList<>(Arrays.asList("7.00 AM", "10.00 PM")));
        Food food19 = new Food("Mi Goreng", "Faculty of Education", "Piccadilly", 4.00, new ArrayList<>(Arrays.asList("Delicious")), R.drawable.mi_goreng, new ArrayList<>(Arrays.asList("7.00 AM", "10.00 PM")));
        Food food20 = new Food("Maggi Kerabu", "Faculty of Education", "Piccadilly", 5.00, new ArrayList<>(Arrays.asList("Sour", "Contains prawn")), R.drawable.maggi_kerabu, new ArrayList<>(Arrays.asList("7.00 AM", "10.00 PM")));


        // Faculty of Computer Science and Information Technology
        // Ali Food Corner
        Food food21 = new Food("Nasi Lemak", "Faculty of Computer Science and Information Technology", "Ali Food Corner", 6.00, new ArrayList<>(Arrays.asList("Local delight", "Spicy")), R.drawable.nasi_lemak, new ArrayList<>(Arrays.asList("7.00 AM", "10.00 PM")));
        Food food22 = new Food("Maggi Goreng", "Faculty of Computer Science and Information Technology", "Ali Food Corner", 4.00, new ArrayList<>(Arrays.asList("Delicious")), R.drawable.maggi_goreng, new ArrayList<>(Arrays.asList("7.00 AM", "10.00 PM")));

        // Add food into database
        foodDatabase.foodDAO().insertAll(food1, food2, food3, food4, food5, food6, food7, food8, food9, food10, food11, food12, food13, food14, food15, food16, food17, food18, food19, food20, food21, food22);
    }

    // Add locations into database
    private void addLocation() {
        Location location1 = new Location("Kolej Kediaman Tuanku Kursiah (KK3)", R.drawable.kk3, new ArrayList<>(Arrays.asList("Restoran Al-Ehsan")));
        Location location2 = new Location("Kolej Kediaman Kinabalu (KK8)", R.drawable.kk8, new ArrayList<>(Arrays.asList("Restoran Murni", "Restoran Bidayah Bistro")));
        Location location3 = new Location("Kolej Kediaman Bestari (KK4)", R.drawable.kk4, new ArrayList<>(Arrays.asList("Restoran Famidah", "Restoran Al-Safuan", "Brotherhood Western & Grill")));
        Location location4 = new Location("Faculty of Education", R.drawable.edu, new ArrayList<>(Arrays.asList("Restoran Abu Khalid", "Piccadilly")));
        Location location5 = new Location("Faculty of Computer Science and Information Technology", R.drawable.cs, new ArrayList<>(Arrays.asList("Ali Food Corner")));

        // Add locations into database
        locationDatabase.locationDAO().insertAll(location1, location2, location3, location4, location5);
    }

    // Add stalls into database
    private void addStall() {
        Stall stall1 = new Stall("Restoran Al-Ehsan", "Kolej Kediaman Tuanku Kursiah (KK3)", new ArrayList<>(Arrays.asList("Nasi Goreng", "Nasi Goreng Kampung", "Mi Goreng")), "Good for large group", R.drawable.restoran_al_ehsan, new ArrayList<>(Arrays.asList("7.00 AM", "10.00 PM")));
        Stall stall2 = new Stall("Restoran Murni", "Kolej Kediaman Kinabalu (KK8)", new ArrayList<>(Arrays.asList("Nasi Goreng Kampung", "Nasi Goreng Cina", "Nasi Goreng Tomyam", "Nasi Goreng Pattaya")), "Local favourite", R.drawable.restoran_murni, new ArrayList<>(Arrays.asList("7.00 AM", "10.00 PM")));
        Stall stall3 = new Stall("Restoran Bidayah Bistro", "Kolej Kediaman Kinabalu (KK8)", new ArrayList<>(Arrays.asList("Roti Canai", "Roti Bawang")), "Budget eat", R.drawable.restoran_bidayah_bistro, new ArrayList<>(Arrays.asList("7.00 AM", "10.00 PM")));
        Stall stall4 = new Stall("Restoran Famidah", "Kolej Kediaman Bestari (KK4)", new ArrayList<>(Arrays.asList("Nasi Goreng Kampung", "Nasi Goreng Cina")), "Small area", R.drawable.restoran_famidah, new ArrayList<>(Arrays.asList("7.00 AM", "10.00 PM")));
        Stall stall5 = new Stall("Restoran Al-Safuan", "Kolej Kediaman Bestari (KK4)", new ArrayList<>(Arrays.asList("Roti Canai", "Nasi Lemak")), "Suitable for vegetarian", R.drawable.restoran_al_safuan, new ArrayList<>(Arrays.asList("8.00 AM", "7.00 PM")));
        Stall stall6 = new Stall("Brotherhood Western & Grill", "Kolej Kediaman Bestari (KK4)", new ArrayList<>(Arrays.asList("Chicken Chop", "Fish and Chips", "Spaghetti Carbonara")), "Good for celebrations", R.drawable.brotherhood_western_grill, new ArrayList<>(Arrays.asList("11.00 AM", "10.00 PM")));
        Stall stall7 = new Stall("Restoran Abu Khalid", "Faculty of Education", new ArrayList<>(Arrays.asList("Chicken Chop", "Nasi Kerabu")), "Lively Environment", R.drawable.restoran_abu_khalid, new ArrayList<>(Arrays.asList("7.00 AM", "10.00 PM")));
        Stall stall8 = new Stall("Piccadilly", "Faculty of Education", new ArrayList<>(Arrays.asList("Bihun Tomyam", "Mi Goreng", "Maggi Kerabu")), "Good for gathering", R.drawable.piccadilly, new ArrayList<>(Arrays.asList("7.00 AM", "10.00 PM")));
        Stall stall9 = new Stall("Ali Food Corner", "Faculty of Computer Science and Information Technology", new ArrayList<>(Arrays.asList("Nasi Lemak", "Maggi Goreng")), "Good for gathering", R.drawable.ali_food_corner, new ArrayList<>(Arrays.asList("7.00 AM", "10.00 PM")));

        // Add stalls into database
        stallDatabase.stallDAO().insertAll(stall1, stall2, stall3, stall4, stall5, stall6, stall7, stall8, stall9);
    }
}
