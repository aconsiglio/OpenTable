package com.example.costcoproject.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.costcoproject.CostcoApplication;
import com.example.costcoproject.Networker.RestaurantEntry;
import com.example.costcoproject.R;

import java.util.ArrayList;
import java.util.List;

public class RestaurantDetailedFragment extends BaseFragment {

    private ImageView price1;
    private ImageView price2;
    private ImageView price3;
    private ImageView price4;
    private ImageView fullImage;
    private TextView phone;
    private TextView address;
    private TextView name;
    private RestaurantEntry restaurantEntry;
    private int id;
    private static String ARG_ID_KEY = "arg-id-key";
    public static String NAME = "restaurant-detailed-fragment";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (getArguments() != null) {
            id = getArguments().getInt(ARG_ID_KEY, 0);
        }
        if (id != 0) {
           List<RestaurantEntry> restaurantEntries = CostcoApplication.instance.getRestaurants();
            for (RestaurantEntry entry : restaurantEntries) {
                if (entry.getId() == id) {
                    restaurantEntry = entry;
                    break;
                }
            }
        }
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_restaurant_detailed, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        price1 = (ImageView) view.findViewById(R.id.price1);
        price2 = (ImageView) view.findViewById(R.id.price2);
        price3 = (ImageView) view.findViewById(R.id.price3);
        price4 = (ImageView) view.findViewById(R.id.price4);
        fullImage = (ImageView) view.findViewById(R.id.fullImage);
        phone = (TextView) view.findViewById(R.id.phoneNumber);
        address = (TextView) view.findViewById(R.id.address);
        name = (TextView) view.findViewById(R.id.name);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (getActivity() != null) {
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (getContext() != null) {
                        Glide.with(getContext())
                                .load(restaurantEntry.getImage_url())
                                .apply(RequestOptions.centerCropTransform())
                                .into(fullImage);
                        address.setText(getContext().getString(R.string.address_concat , restaurantEntry.getAddress(), restaurantEntry.getCity(), restaurantEntry.getState(), restaurantEntry.getPostal_code()));
                        phoneDisplay();
                    }
                    priceDisplay((int)restaurantEntry.getPrice());
                    name.setText(restaurantEntry.getName());

                }
            });
        }
    }

    private void phoneDisplay() {
        String[] strArr = restaurantEntry.getPhone().split("x");
        phone.setText(getContext().getString(R.string.phone_concat, "+1", strArr[0]));
    }

    private void priceDisplay(int price) {
        switch (price) {
            case 1: showOneStar();
                    break;
            case 2: showTwoStar();
                    break;
            case 3: showThreeStar();
                    break;
            case 4: showFourStar();
                    break;
            default: showNoStars();
        }
    }

    private void showOneStar() {
        price1.setVisibility(View.VISIBLE);
        price2.setVisibility(View.GONE);
        price3.setVisibility(View.GONE);
        price4.setVisibility(View.GONE);
    }

    private void showTwoStar() {
        price1.setVisibility(View.VISIBLE);
        price2.setVisibility(View.VISIBLE);
        price3.setVisibility(View.GONE);
        price4.setVisibility(View.GONE);
    }

    private void showThreeStar() {
        price1.setVisibility(View.VISIBLE);
        price2.setVisibility(View.VISIBLE);
        price3.setVisibility(View.VISIBLE);
        price4.setVisibility(View.GONE);
    }

    private void showFourStar() {
        price1.setVisibility(View.VISIBLE);
        price2.setVisibility(View.VISIBLE);
        price3.setVisibility(View.VISIBLE);
        price4.setVisibility(View.VISIBLE);
    }

    private void showNoStars() {
        price1.setVisibility(View.GONE);
        price2.setVisibility(View.GONE);
        price3.setVisibility(View.GONE);
        price4.setVisibility(View.GONE);
    }


    public static RestaurantDetailedFragment newInstance(int id) {
        RestaurantDetailedFragment restaurantDetailedFragment = new RestaurantDetailedFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_ID_KEY, id);
        restaurantDetailedFragment.setArguments(args);
        return restaurantDetailedFragment;
    }
}
