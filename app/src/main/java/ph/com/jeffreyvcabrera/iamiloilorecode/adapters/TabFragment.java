package ph.com.jeffreyvcabrera.iamiloilorecode.adapters;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import ph.com.jeffreyvcabrera.iamiloilorecode.R;
import ph.com.jeffreyvcabrera.iamiloilorecode.fragments.Attractions;
import ph.com.jeffreyvcabrera.iamiloilorecode.fragments.Coupon;
import ph.com.jeffreyvcabrera.iamiloilorecode.fragments.Hotels;
import ph.com.jeffreyvcabrera.iamiloilorecode.fragments.News;
import ph.com.jeffreyvcabrera.iamiloilorecode.fragments.PrimaryFragment;
import ph.com.jeffreyvcabrera.iamiloilorecode.fragments.Restaurants;
import ph.com.jeffreyvcabrera.iamiloilorecode.models.UsersModel;
import ph.com.jeffreyvcabrera.iamiloilorecode.utils.SharedPrefManager;

/**
 * Created by Jeffrey on 2/21/2017.
 */
public class TabFragment extends Fragment {

    public static TabLayout tabLayout;
    public static ViewPager viewPager;
    public static int int_items = 5 ;
    TextView title, toolbar_username;
    android.support.v7.widget.Toolbar toolbar;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        /**
         *Inflate tab_layout and setup Views.
         */
        View x =  inflater.inflate(R.layout.tab_layout,null);
        tabLayout = (TabLayout) x.findViewById(R.id.tabs);
        viewPager = (ViewPager) x.findViewById(R.id.viewpager);
        toolbar = (android.support.v7.widget.Toolbar) getActivity().findViewById(R.id.toolbar);
        title = (TextView) getActivity().findViewById(R.id.toolbar_title);
        toolbar_username = (TextView) getActivity().findViewById(R.id.toolbar_username);

        UsersModel um = new SharedPrefManager(getContext()).userGet();

        /**
         *Set an Apater for the View Pager
         */
        viewPager.setAdapter(new MyAdapter(getChildFragmentManager()));

        /**
         * Now , this is a workaround ,
         * The setupWithViewPager dose't works without the runnable .
         * Maybe a Support Library Bug .
         */

        final int[] ICONS = new int[] {
                R.mipmap.supermarket_white,
                R.mipmap.newspaper_white,
                R.mipmap.palm_trees_white,
                R.mipmap.cutlery_white,
                R.mipmap.bed_white,
        };

        final int[] ICONS_SELECTED = new int[] {
                R.mipmap.supermarket_blue,
                R.mipmap.newspaper_blue,
                R.mipmap.palm_trees_blue,
                R.mipmap.cutlery_blue,
                R.mipmap.bed_blue,
        };

        tabLayout.post(new Runnable() {
            @Override
            public void run() {
                tabLayout.setupWithViewPager(viewPager);
                for (int i = 0; i < tabLayout.getTabCount(); i++) {
                    tabLayout.getTabAt(i).setIcon(ICONS[i]);
                }
                if (tabLayout.getTabCount() > 0) {
                    tabLayout.getTabAt(0).setIcon(ICONS_SELECTED[0]);
                }
            }
        });

        title.setText("Coupons");
        String firstname = "";

        firstname = um.getFirstname();
        toolbar_username.setText("Welcome "+firstname+"!");

        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

                if (position == 0) {
                    title.setText("Coupons");
                    if (tabLayout.getTabCount() > 0) {
                        tabLayout.getTabAt(position).setIcon(ICONS_SELECTED[position]);
                    }
                } else {
                    if (tabLayout.getTabCount() > 0) {
                        tabLayout.getTabAt(0).setIcon(ICONS[0]);
                    }
                }

                if (position == 1) {
                    title.setText("News");
                    if (tabLayout.getTabCount() > 0) {
                        tabLayout.getTabAt(position).setIcon(ICONS_SELECTED[position]);
                    }
                } else {
                    if (tabLayout.getTabCount() > 0) {
                        tabLayout.getTabAt(1).setIcon(ICONS[1]);
                    }
                }

                if (position == 2) {
                    title.setText("Attractions");
                    if (tabLayout.getTabCount() > 0) {
                        tabLayout.getTabAt(position).setIcon(ICONS_SELECTED[position]);
                    }
                } else {
                    if (tabLayout.getTabCount() > 0) {
                        tabLayout.getTabAt(2).setIcon(ICONS[2]);
                    }
                }

                if (position == 3) {
                    title.setText("Restaurants");
                    if (tabLayout.getTabCount() > 0) {
                        tabLayout.getTabAt(position).setIcon(ICONS_SELECTED[position]);
                    }
                } else {
                    if (tabLayout.getTabCount() > 0) {
                        tabLayout.getTabAt(3).setIcon(ICONS[3]);
                    }
                }

                if (position == 4) {
                    title.setText("Hotels");
                    if (tabLayout.getTabCount() > 0) {
                        tabLayout.getTabAt(position).setIcon(ICONS_SELECTED[position]);
                    }
                } else {
                    if (tabLayout.getTabCount() > 0) {
                        tabLayout.getTabAt(4).setIcon(ICONS[4]);
                    }
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }

        });

        return x;
    }

    class MyAdapter extends FragmentPagerAdapter {

        public MyAdapter(FragmentManager fm) {
            super(fm);
        }

        /**
         * Return fragment with respect to Position .
         */

        @Override
        public Fragment getItem(int position)
        {
            switch (position){
                case 0 : return new Coupon();
                case 1 : return new News();
                case 2 : return new Attractions();
                case 3 : return new Restaurants();
                case 4 : return new Hotels();
            }
            return null;
        }

        @Override
        public int getCount() {

            return int_items;

        }

        /**
         * This method returns the title of the tab according to the position.
         */

        @Override
        public CharSequence getPageTitle(int position) {
            return null;
        }
    }

}
