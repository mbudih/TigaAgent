package com.tiga.agent;



import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.ColorInt;
import android.support.annotation.ColorRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.tiga.fragment.AccountFragment;
import com.tiga.fragment.BuyFragment;
import com.tiga.fragment.StockFragment;
import com.tiga.menu.DrawerAdapter;
import com.tiga.menu.DrawerItem;
import com.tiga.menu.SimpleItem;
import com.yarolegovich.slidingrootnav.SlidingRootNav;
import com.yarolegovich.slidingrootnav.SlidingRootNavBuilder;

import java.util.Arrays;

public class MainActivity extends AppCompatActivity implements DrawerAdapter.OnItemSelectedListener {

    private static final int MENU_1 = 0;
    private static final int MENU_2 = 1;
    private static final int MENU_3 = 2;
    private static final int MENU_4 = 3;
    private static final int MENU_5 = 4;

    private String[] screenTitles;
    private Drawable[] screenIcons;

    private SlidingRootNav slidingRootNav;
    private Toolbar toolbar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        slidingRootNav = new SlidingRootNavBuilder(this)
                .withToolbarMenuToggle(toolbar)
                .withMenuOpened(false)
                .withContentClickableWhenMenuOpened(false)
                .withSavedState(savedInstanceState)
                .withMenuLayout(R.layout.menu_left_drawer)
                .inject();

        screenIcons = loadScreenIcons();
        screenTitles = loadScreenTitles();

        DrawerAdapter adapter = new DrawerAdapter(Arrays.asList(
                createItemFor(MENU_1).setChecked(true),
                createItemFor(MENU_2),
                createItemFor(MENU_3),
                createItemFor(MENU_4),
                createItemFor(MENU_5)
        ));
        adapter.setListener(this);

        RecyclerView list = findViewById(R.id.list);
        list.setNestedScrollingEnabled(false);
        list.setLayoutManager(new LinearLayoutManager(this));
        list.setAdapter(adapter);

        adapter.setSelected(MENU_1);
    }

    @Override
    public void onItemSelected(int position) {
        Fragment selectedScreen = BuyFragment.createFor(screenTitles[position]);
        switch (position) {
            case MENU_1: {
                toolbar.setTitle("BELI");
//                selectedScreen = BuyFragment.createFor(screenTitles[position]);
                break;
            }
            case MENU_2: {
                toolbar.setTitle("STOK");
                selectedScreen = StockFragment.createFor(screenTitles[position]);
                break;
            }
            case MENU_3: {
                toolbar.setTitle("RIWAYAT");
                selectedScreen = AccountFragment.createFor(screenTitles[position]);
                break;
            }
            case MENU_4: {
                toolbar.setTitle("KELUAR");
                selectedScreen = AccountFragment.createFor(screenTitles[position]);
                break;
            }
            case MENU_5: {
                toolbar.setTitle("Menu 5");
                selectedScreen = AccountFragment.createFor(screenTitles[position]);
                break;
            }
            default: {
                break;
            }
        }
        slidingRootNav.closeMenu();
        showFragment(selectedScreen);
    }

    private void showFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.container, fragment);
        fragmentTransaction.commit();
    }

    private DrawerItem createItemFor(int position) {
        return new SimpleItem(screenIcons[position], screenTitles[position])
                .withIconTint(color(R.color.textColorSecondary))
                .withTextTint(color(R.color.textColorPrimary))
                .withSelectedIconTint(color(R.color.colorAccent))
                .withSelectedTextTint(color(R.color.colorAccent));
    }

    private String[] loadScreenTitles() {
        return getResources().getStringArray(R.array.ld_activityScreenTitles);
    }

    private Drawable[] loadScreenIcons() {
        TypedArray ta = getResources().obtainTypedArray(R.array.ld_activityScreenIcons);
        Drawable[] icons = new Drawable[ta.length()];
        for (int i = 0; i < ta.length(); i++) {
            int id = ta.getResourceId(i, 0);
            if (id != 0) {
                icons[i] = ContextCompat.getDrawable(this, id);
            }
        }
        ta.recycle();
        return icons;
    }

    @ColorInt
    private int color(@ColorRes int res) {
        return ContextCompat.getColor(this, res);
    }

    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }
}