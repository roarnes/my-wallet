package arnes.respati.mywallet.MainActivityClasses;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import arnes.respati.mywallet.MainActivityClasses.ExpensesFragmentClasses.ExpensesFragment;
import arnes.respati.mywallet.MainActivityClasses.IncomeFragmentClasses.IncomeFragment;
import arnes.respati.mywallet.OnBoardingClasses.OnBoardingActivity;
import arnes.respati.mywallet.R;

public class MainActivity extends AppCompatActivity {

    public BottomNavigationView bottomNav;
    private OverviewFragment overviewFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        launchOnBoarding();
        setContentView(R.layout.activity_main);

        bottomNav = findViewById(R.id.bottom_navigation);
        bottomNav.setOnNavigationItemSelectedListener(navListener);

        if (savedInstanceState == null) {
            // The Activity is NOT being re-created so a new Fragment can be instantiated
            // and add it to the Activity
            overviewFragment = new OverviewFragment();

            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, overviewFragment, "overview_fragment")
                    .commit();
        } else {
            // The Activity IS being re-created so we don't need to instantiate the Fragment or add it,
            // use the tag to pass to .replace
            overviewFragment = (OverviewFragment) getSupportFragmentManager().findFragmentByTag("overview_fragment");
        }

    }

    private void launchOnBoarding() {
        Boolean isFirstRun = getSharedPreferences("PREFERENCE", MODE_PRIVATE)
                .getBoolean("isFirstRun", true);

        if (isFirstRun) {
            //show onBoardingActivity
            startActivity(new Intent(MainActivity.this, OnBoardingActivity.class));
//            Toast.makeText(MainActivity.this, "Run only once", Toast.LENGTH_LONG).show();
        }
    }

    //bottom navigation

    public BottomNavigationView.OnNavigationItemSelectedListener
            navListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment selectedFragment = null;

            switch (item.getItemId()) {
                case R.id.nav_overview:
                    selectedFragment = new OverviewFragment();
                    break;
                case R.id.nav_income:
                    selectedFragment = new IncomeFragment();
                    break;
                case R.id.nav_expenses:
                    selectedFragment = new ExpensesFragment();
                    break;
            }

            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, selectedFragment).commit();
            return  true;
        }
    };

//
//    @Override
//    public void onSaveInstanceState(Bundle savedInstanceState) {
//        super.onSaveInstanceState(savedInstanceState);
//        savedInstanceState.putInt("SelectedItemId", bottomNav.getSelectedItemId());
//
//    }
//
//    @Override
//    public void onRestoreInstanceState(Bundle savedInstanceState) {
//        super.onRestoreInstanceState(savedInstanceState);
//        int selectedItemId = savedInstanceState.getInt("SelectedItemId");
//        bottomNav.setSelectedItemId(selectedItemId);
//    }

}
