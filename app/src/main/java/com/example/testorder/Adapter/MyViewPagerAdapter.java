package com.example.testorder.Adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.testorder.Fragment.AllFragment;
import com.example.testorder.Fragment.BurgerFragment;
import com.example.testorder.Fragment.GaRanFragment;
import com.example.testorder.Fragment.ThucUongFragment;

public class MyViewPagerAdapter extends FragmentStateAdapter {
    public MyViewPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position){
            case 0:
                return new AllFragment();
            case 1:
                return new GaRanFragment();
            case 2:
                return new BurgerFragment();
            case 3:
                return new ThucUongFragment();
            default:
                return new AllFragment();
        }
    }

    @Override
    public int getItemCount() {
        return 4;
    }
}
