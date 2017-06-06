package com.telran.classwork.hairsalon2.Adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.telran.classwork.hairsalon2.Fragments.Schedule.FragmentSchedulePage;

import java.util.Calendar;
import java.util.GregorianCalendar;


/**
 * Created by vadim on 26.04.2017.
 */

public class MyScheduleAdaprer extends FragmentPagerAdapter {

    String[] date = new String[7];
    GregorianCalendar calendar = new GregorianCalendar(2017, Calendar.DAY_OF_MONTH,31);
    public MyScheduleAdaprer(FragmentManager fm) {
        super(fm);

    }

    @Override
    public Fragment getItem(int position) {
        return FragmentSchedulePage.newInstance(date[position]);
    }

    @Override
    public int getCount() {
        return date.length;
    }
    @Override
    public CharSequence getPageTitle(int position) {
        return date[position];
    }

}
