package com.telran.classwork.hairsalon2;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.telran.classwork.hairsalon2.Fragments.Adress.FragmentAdressAdd;
import com.telran.classwork.hairsalon2.Fragments.Adress.FragmentAdressList;
import com.telran.classwork.hairsalon2.Fragments.Adress.FragmentAdressView;
import com.telran.classwork.hairsalon2.Fragments.FragmentPortfolio;
import com.telran.classwork.hairsalon2.Fragments.FragmentPrivateAccount;
import com.telran.classwork.hairsalon2.Fragments.Schedule.Schedule;
import com.telran.classwork.hairsalon2.Fragments.Services.FragmentServicesAdd;
import com.telran.classwork.hairsalon2.Fragments.Services.FragmentServicesList;
import com.telran.classwork.hairsalon2.Fragments.Services.FragmentServicesView;
import com.telran.classwork.hairsalon2.Models.Adress;
import com.telran.classwork.hairsalon2.Models.Services;

public class PrivateAccountActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,
        FragmentAdressAdd.AddAdressItemFragmentListener,
        FragmentServicesAdd.AddItemFragmentListener {

    FragmentManager manager = getSupportFragmentManager();
    FragmentTransaction transaction;
    FragmentPrivateAccount fragmentPrivateAccount;
    private TextView nameMasterTxt;
    private TextView lastNameMasterTxt;
    private Toolbar toolbar;
    private boolean isDrawerLocked = false;
    private DrawerLayout drawer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_private_account);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Hair Salon :)");
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_action_bar_home);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isDrawerLocked) {
                    drawerStateSwitcher();
                } else {
                    drawer.openDrawer(GravityCompat.START);
                }
            }
        });
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View navView = navigationView.getHeaderView(0);
        nameMasterTxt = (TextView) navView.findViewById(R.id.nav_header_master_name);
        lastNameMasterTxt = (TextView) navView.findViewById(R.id.nav_header_master_last_name);
        Intent intent = getIntent();
        String name = intent.getStringExtra("NAME");
        String lastName = intent.getStringExtra("LAST_NAME");
        nameMasterTxt.setText(name);
        lastNameMasterTxt.setText(lastName);
        fragmentPrivateAccount = new FragmentPrivateAccount();
//        SharedPreferences sharedPreferences = getSharedPreferences("AUTHLOG", Context.MODE_PRIVATE);
//        Toast.makeText(this, sharedPreferences.getString("TOKEN",""), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();
        transaction = getSupportFragmentManager().beginTransaction();
        if (id == R.id.nav_personal_account) {
            transaction.replace(R.id.content_private_account, fragmentPrivateAccount);
            transaction.commit();
            getSupportActionBar().setTitle("Account");
        } else if (id == R.id.nav_my_adress) {
            /*transaction.replace(R.id.content_private_account, new FragmentAdressList(), "FRAG_ADRESS_LIST");
            transaction.commit();*/
            showAdressListFragment();
            getSupportActionBar().setTitle("My adresses");
        } else if (id == R.id.nav_my_servises) {
            showServicesListFragment();
            getSupportActionBar().setTitle("My services");
        } else if (id == R.id.nav_comments) {

        } else if (id == R.id.nav_schedule) {
            showScheduleFragment();
            getSupportActionBar().setTitle("My schedule");
        } else if (id == R.id.nav_portfolio) {
            transaction.replace(R.id.content_private_account, new FragmentPortfolio(), "FRAG_PORTFOL");
            getSupportActionBar().setTitle("My portfolio");
            transaction.commit();
        } else if (id == R.id.nav_exit) {
            exit();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void showAdressListFragment() {
        FragmentAdressList adress1List = new FragmentAdressList();
        adress1List.setFragmentListener(new FragmentAdressList.ListFragmentListener(){

            @Override
            public void itemSelected(Adress item) {
                showAdressItemViewFragment(item);
                drawerStateSwitcher();

            }

            @Override
            public void addItem() {
                showAdressAddItemFragment();
                drawerStateSwitcher();

            }
        });
        transaction = manager.beginTransaction();
        transaction.replace(R.id.content_private_account, adress1List);
        transaction.commit();
    }
    private void showScheduleFragment() {
        Schedule fragmentSchedule = new Schedule();
        transaction = manager.beginTransaction();
        transaction.replace(R.id.content_private_account, fragmentSchedule);
        transaction.commit();
    }

    private void showAdressAddItemFragment() {
        FragmentAdressAdd addAdress = new FragmentAdressAdd();
        addAdress.onAttach((Context) this);
        transaction = manager.beginTransaction();
        transaction.replace(R.id.content_private_account, addAdress);
        transaction.addToBackStack("FRAG_ADRESS_LIST");
        transaction.commit();
    }

    private void showAdressItemViewFragment(Adress item) {
        FragmentAdressView fragment = FragmentAdressView.newInstance(item.getAdress(),item.getNote());
        transaction = manager.beginTransaction();
        transaction.replace(R.id.content_private_account, fragment);
        transaction.addToBackStack("REPLACE_TO_ADRESS_VIEW");
        transaction.commit();
    }

    private void showServicesListFragment() {
        FragmentServicesList listFragment = new FragmentServicesList();
        listFragment.setFragmentListener(new FragmentServicesList.ListFragmentListener() {
            @Override
            public void itemSelected(Services item) {
                showItemViewFragment(item);
                drawerStateSwitcher();

            }


            @Override
            public void addItem() {
                showAddItemFragment();
                drawerStateSwitcher();
            }
        });

        transaction = manager.beginTransaction();
        transaction.replace(R.id.content_private_account, listFragment);
        transaction.commit();
//        toolbar.setTitle("My list");
    }

    private void showAddItemFragment() {
        FragmentServicesAdd addItemFragment = new FragmentServicesAdd();
        addItemFragment.onAttach((Context) this);
        transaction = manager.beginTransaction();
        transaction.replace(R.id.content_private_account, addItemFragment);
        transaction.addToBackStack("FRAG_SERVICE_LIST");
        transaction.commit();
    }

    private void drawerStateSwitcher() {
        if (isDrawerLocked) {
            drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
//            toolbar.setNavigationIcon(R.drawable.ic_action_bar_home);
            isDrawerLocked = false;
            manager.popBackStack();
        } else {
            drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
//            toolbar.setNavigationIcon(R.drawable.ic_action_bar_back);
            isDrawerLocked = true;
        }
    }

    private void showItemViewFragment(Services item) {
        FragmentServicesView fragment = FragmentServicesView.newInstance(item.getService(), item.getPrice(), item.getTime());

        transaction = manager.beginTransaction();
        transaction.replace(R.id.content_private_account, fragment);
        transaction.addToBackStack("REPLACE_TO_SERVICE_VIEW");
        transaction.commit();
    }

    public void exit() {
        SharedPreferences sharedPreferences = getSharedPreferences("AUTH", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("IS_AUTHORIZED", false);
        editor.commit();
        finish();
    }

    /*@Override
    public void addAddress(Adress adress) {
        getSupportFragmentManager().popBackStack();
        FragmentAdressList fragment = (FragmentAdressList) getSupportFragmentManager().findFragmentByTag("FRAG_LIST");
        if (fragment != null) {
            fragment.addAdressList(adress);

        }
    }

    @Override
    public void AdressSelected(Adress adress) {
        transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.content_private_account, FragmentAdressView.newInstance(adress.getAdress()), "ADRESS_VIEW");
        transaction.addToBackStack("SHOW_ADRESS");
        transaction.commit();
    }

    @Override
    public void addAddress() {
        transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.content_private_account, new FragmentAddAdress(), "ADD_ADRESS");
        transaction.addToBackStack("ADD_ADESS_FRAG");
        transaction.commit();
    }

    @Override
    public void ViewDestroed() {

    }*/

    @Override
    public void callback() {
        drawerStateSwitcher();
    }
}
