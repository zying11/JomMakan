package com.example.jommakan;

import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.google.android.material.imageview.ShapeableImageView;
import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

public class HomeFragment extends Fragment {

    TextView welcome_text_with_name;

    // Top 5
    ArrayList<Food> top_5_food_list;
    SliderAdapter sliderAdapter;
    SliderView top_5_image_slider;

    // Home Menu
    ArrayList<Food> menu_food_list;
    RecyclerView home_menu_recycle_view;
    HomeMenuItemAdapter homeMenuItemAdapter;
    ImageButton menu_view_all_button;
    FoodDatabase foodDatabase;

    // Home Location
    RecyclerView home_location_recycle_view;
    ArrayList<Location> menu_location_list;
    HomeLocationItemAdapter homeLocationItemAdapter;
    ImageButton location_view_all_button;
    LocationDatabase locationDatabase;

    // Random Food Picker
    ShapeableImageView random_food_picker;
    Food random_food;
    CartItem cartItem;
    CartItemDatabase cartItemDatabase;
    ArrayList<CartFood> cart_food_list;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        return view;
    }

    // Declare and assign views here
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        // Toolbar
        Toolbar toolbar = ((MainActivity) getActivity()).findViewById(R.id.toolbar);
        toolbar.setVisibility(View.VISIBLE);
        TextView toolbar_title = getActivity().findViewById(R.id.toolbar_title);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            toolbar_title.setTextAppearance(R.style.toolbar_text_main);
            toolbar_title.setText("JomMakan");
        }

        // Database connection
        foodDatabase = Room.databaseBuilder(getActivity(), FoodDatabase.class, "FoodDB").allowMainThreadQueries().build();
        locationDatabase = Room.databaseBuilder(getActivity(), LocationDatabase.class, "LocationDB").allowMainThreadQueries().build();
        cartItemDatabase = Room.databaseBuilder(getActivity(), CartItemDatabase.class, "CartItemDB").allowMainThreadQueries().build();

        // Welcome text
        welcome_text_with_name = view.findViewById(R.id.welcome_text_with_name);
        welcome_text_with_name.setText("Hi, " + UserInstance.getUsername());

        // Randomly get 5 food from database
        top_5_food_list = new ArrayList<>();
        getTop5Food();
        // Top 5 Slider
        sliderAdapter = new SliderAdapter(top_5_food_list, getActivity());
        top_5_image_slider = view.findViewById(R.id.top_5_image_slider);
        top_5_image_slider.setSliderAdapter(sliderAdapter);
        top_5_image_slider.setIndicatorAnimation(IndicatorAnimationType.WORM);
        top_5_image_slider.setSliderTransformAnimation(SliderAnimations.DEPTHTRANSFORMATION);
        top_5_image_slider.startAutoCycle();

        // Randomly get 3 food from database
        menu_food_list = new ArrayList<>();
        getThreeFood();
        // Home Menu
        homeMenuItemAdapter = new HomeMenuItemAdapter(getActivity(), menu_food_list);
        home_menu_recycle_view = view.findViewById(R.id.home_menu_recycle_view);
        home_menu_recycle_view.setAdapter(homeMenuItemAdapter);
        home_menu_recycle_view.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.HORIZONTAL, false));
        home_menu_recycle_view.setHasFixedSize(true);
        home_menu_recycle_view.setNestedScrollingEnabled(false);

        menu_view_all_button = view.findViewById(R.id.menu_view_all_button);
        menu_view_all_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(view).navigate(R.id.DestMenu);
            }
        });

        // Randomly get 3 locations from database
        menu_location_list = new ArrayList<>();
        getThreeLocations();

        homeLocationItemAdapter = new HomeLocationItemAdapter(getActivity(), menu_location_list);
        home_location_recycle_view = view.findViewById(R.id.home_location_recycle_view);
        home_location_recycle_view.setAdapter(homeLocationItemAdapter);
        home_location_recycle_view.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.HORIZONTAL, false));
        home_location_recycle_view.setHasFixedSize(true);
        home_location_recycle_view.setNestedScrollingEnabled(false);

        location_view_all_button = view.findViewById(R.id.location_view_all_button);
        location_view_all_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(view).navigate(R.id.DestMenu);
            }
        });

        // Random Food Picker
        random_food_picker = view.findViewById(R.id.random_food_picker);
        do {
            // Check if the stall is open
            // So that we can only get the food that its stall is open
            random_food = foodDatabase.foodDAO().getAllFood().get(new Random().nextInt(foodDatabase.foodDAO().getAllFood().size()));
        } while (!checkOpenClose(random_food));
        random_food_picker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Pass data between fragments using bundle
                Bundle bundle = new Bundle();
                bundle.putSerializable("food", (Serializable) random_food);
                getCartFoodList(random_food.getLocation(), random_food.getStall());
                bundle.putSerializable("cart_food_list", (Serializable) cart_food_list);
                bundle.putString("stall", random_food.getStall());
                bundle.putString("location", random_food.getLocation());
                Navigation.findNavController(v).navigate(R.id.DestFood, bundle);
            }
        });
    }

    // Randomly get 5 food from database
    private void getTop5Food() {
        top_5_food_list.addAll(foodDatabase.foodDAO().getTop5Food());
    }

    // Randomly get 3 food from database
    private void getThreeFood() {
        menu_food_list.addAll(foodDatabase.foodDAO().getThreeFood());
    }

    // Randomly get 3 locations from database
    private void getThreeLocations() {
        menu_location_list.addAll(locationDatabase.locationDAO().getRandomLocations());
    }

    // Get the food of that stall that user has added to cart from database
    private void getCartFoodList(String location, String stall) {
        cartItem = cartItemDatabase.cartItemDAO().getCartItem(UserInstance.getUser_email_address(), location, stall);
        if (cartItem == null) {
            cart_food_list = new ArrayList<>();
        } else {
            cart_food_list = cartItem.getCart_food_list();
        }
    }

    // Check whether the stall is open
    // Return true if the stall is open
    private boolean checkOpenClose(Food random_food) {
        SimpleDateFormat format = new SimpleDateFormat("h.mm a");
        Calendar time = Calendar.getInstance();
        Calendar time1 = Calendar.getInstance();
        Calendar time2 = Calendar.getInstance();
        try {
            time.setTime(format.parse(format.format(new Date())));
            time.add(Calendar.DATE, 1);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        try {
            time1.setTime(format.parse(random_food.getOpenAndClose().get(0)));
            time2.setTime(format.parse(random_food.getOpenAndClose().get(1)));
            time1.add(Calendar.DATE, 1);
            time2.add(Calendar.DATE, 1);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Date currentTime = time.getTime();
        Date open = time1.getTime();
        Date close = time2.getTime();

        return currentTime.after(open) && currentTime.before(close);
    }
}

