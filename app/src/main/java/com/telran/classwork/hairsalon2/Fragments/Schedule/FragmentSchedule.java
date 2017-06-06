package com.telran.classwork.hairsalon2.Fragments.Schedule;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.telran.classwork.hairsalon2.Adapters.MyScheduleAdaprer;
import com.telran.classwork.hairsalon2.R;

/**
 * Created by vadim on 26.04.2017.
 */

public class FragmentSchedule extends Fragment {
    private ViewPager scheduleViewPager;
    private MyScheduleAdaprer scheduleAdaprer;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_schedule,container,false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        scheduleViewPager = (ViewPager) view.findViewById(R.id.view_pager_schedule);
        scheduleAdaprer = new MyScheduleAdaprer(getActivity().getSupportFragmentManager());
        scheduleViewPager.setAdapter(scheduleAdaprer);
        scheduleViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {
                switch (state) {
                    case ViewPager.SCROLL_STATE_DRAGGING:
                        Log.d("MY_LOG", "SCROLL_STATE_DRAGGING");
                        break;
                    case ViewPager.SCROLL_STATE_IDLE:
                        Log.d("MY_LOG", "SCROLL_STATE_IDLE");
                        break;
                    case ViewPager.SCROLL_STATE_SETTLING:
                        Log.d("MY_LOG", "SCROLL_STATE_SETTLING");
                        break;

                }
            }
        });

    }
}
