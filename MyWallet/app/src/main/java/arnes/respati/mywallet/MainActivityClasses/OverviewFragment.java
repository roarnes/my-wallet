package arnes.respati.mywallet.MainActivityClasses;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.charts.PieChart;
import com.mynameismidori.currencypicker.ExtendedCurrency;

import java.util.Objects;

import arnes.respati.mywallet.MainActivityClasses.ExpensesFragmentClasses.ExpensesFragment;
import arnes.respati.mywallet.R;

public class OverviewFragment extends Fragment{

    private OverviewFragmentController overviewFragmentController;

    private TextView tvUserName, totExp, totSum, totRev;

    private Button btn_addExpenditure, btn_addIncome;
    private PieChart pie_chart;
    private Boolean show_pieChart = false;
    private Spinner spinner;

    private LinearLayout piechart_parent;

    public View view;

    private static final String PREF_NAME = "names";
    private static final String PREF_CURRENCY = "curr";
    private static final String KEY_NAME = "name";
    private static final String KEY_CURRENCY = "country name";

    private String curSymbol;

    @Nullable
    @Override

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_overview, container, false);

        overviewFragmentController = new OverviewFragmentController(this);

        initializeComponents();
        registerListeners();

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
//        pie_chart.invalidate();
    }

    public void initializeComponents (){
        btn_addExpenditure = (Button) view.findViewById(R.id.btnAddExpenditure) ;
        btn_addIncome = (Button) view.findViewById(R.id.btnAddIncome) ;

        pie_chart = (PieChart) view.findViewById(R.id.pie_chart);
        piechart_parent = (LinearLayout) view.findViewById(R.id.piechart_parent);

        spinner = (Spinner) view.findViewById(R.id.month_spinner);

        SharedPreferences sharedPreferences = Objects.requireNonNull(this.getActivity()).getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        tvUserName = (TextView) view.findViewById(R.id.tvUserName);
        String tempname = sharedPreferences.getString(KEY_NAME,"");
        tvUserName.setText(tempname);

        sharedPreferences = Objects.requireNonNull(this.getActivity()).getSharedPreferences(PREF_CURRENCY, Context.MODE_PRIVATE);
        String curr = sharedPreferences.getString(KEY_CURRENCY, "");
        ExtendedCurrency currency = ExtendedCurrency.getCurrencyByName(curr); //Get currency by its name
        curSymbol = currency.getSymbol();

        totExp = (TextView) view.findViewById(R.id.totExp);
        totSum = (TextView) view.findViewById(R.id.totSum);
        totRev = (TextView) view.findViewById(R.id.totRev);

        if (!show_pieChart){
            piechart_parent.setVisibility(View.GONE);
        }

        setTotalMoney();
    }

    private void setTotalMoney(){
        String exp = Double.toString(overviewFragmentController.getTotalExpenditure());
        String sum = Double.toString(overviewFragmentController.getTotalSum());
        String rev = Double.toString(overviewFragmentController.getTotalIncome());

        if (curSymbol.equals("Rp")) {
            String tempExp = curSymbol + " " + exp + "k";
            String tempSum = curSymbol + " " + sum + "k";
            String tempRev = curSymbol + " " + rev + "k";
            totExp.setText(tempExp);
            totSum.setText(tempSum);
            totRev.setText(tempRev);
        }

        else {
            String tempExp = curSymbol + " " + exp ;
            String tempSum = curSymbol + " " + sum;
            String tempRev = curSymbol + " " + rev;
            totExp.setText(tempExp) ;
            totSum.setText(tempSum);
            totRev.setText(tempRev);
        }
    }

    public void registerListeners(){
        btn_addIncome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                overviewFragmentController.openAddIncome(view);
            }
        });

        btn_addExpenditure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                overviewFragmentController.openAddExpenditure(view);
            }
        });
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            Boolean notselected = true;
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (getSpinnerItem().equals("Choose Month")) {
                    if (notselected){
                        Toast.makeText(OverviewFragment.this.getActivity(), "Month not chosen!", Toast.LENGTH_LONG).show();
                    }
                    piechart_parent.setVisibility(View.GONE);
                }
                else {
                    overviewFragmentController.setMonth(getSpinnerItemID());

                    pie_chart.setData(overviewFragmentController.addPieChartData());
                    pie_chart.animateXY(100, 100);
                    pie_chart.getDescription().setEnabled(false);
                    piechart_parent.setVisibility(View.VISIBLE);
                    show_pieChart = true;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                notselected = true;
            }
        });
    }

    public int getSpinnerItemID(){
        int id;
        id = spinner.getSelectedItemPosition();
        Toast.makeText(OverviewFragment.this.getActivity(), id+"", Toast.LENGTH_LONG).show();
        return id;
    }

    public String getSpinnerItem() {
        String spinner_item;
        spinner_item = spinner.getSelectedItem().toString();

        return spinner_item;
    }

//    @Override
//    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
//        super.onActivityCreated(savedInstanceState);
//        tvFromDate.setText(savedInstanceState.getString("fromDate"));
//        tvToDate.setText(savedInstanceState.getString("toDate"));
//    }

    @Override
    public void onSaveInstanceState(Bundle outState) {

        super.onSaveInstanceState(outState);
        outState.putBoolean("show_piechart", show_pieChart);
    }

    @Override
    public void onViewStateRestored(Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        if (savedInstanceState != null) {
            // Restore some state that needs to happen after the views have had
            // their state restored
            show_pieChart = savedInstanceState.getBoolean("show_piechart");
        }
    }

}
