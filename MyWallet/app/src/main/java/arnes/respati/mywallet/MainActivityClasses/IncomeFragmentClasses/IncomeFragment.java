package arnes.respati.mywallet.MainActivityClasses.IncomeFragmentClasses;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.Constraints;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;
import arnes.respati.mywallet.R;

public class IncomeFragment extends Fragment {

    IncomeFragmentController incomeController;

    private ListView lvIncome;
    private TextView tvFromDate, tvToDate;
    public String fromDate = new String();
    public String toDate = new String();
    private Button btn_saveDate;
    private DatePickerDialog.OnDateSetListener fromDateListener, toDateListener;
    boolean isButtonClicked = false;

    private Button btn_deleteAll, btn_showAll;

    private View view;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_income, container, false);

        incomeController = new IncomeFragmentController(this);

        initializeComponents();
        registerListeners();

        if (savedInstanceState != null) {
            // Restore last state
            tvFromDate.setText(savedInstanceState.getString("fromDate"));
            tvToDate.setText(savedInstanceState.getString("toDate"));
            isButtonClicked = savedInstanceState.getBoolean("isButtonClicked");
            fromDate = tvFromDate.getText().toString();
            incomeController.setFromDate(fromDate);
            incomeController.setToDate(toDate);
            toDate = tvToDate.getText().toString();
        }
        else {
            tvFromDate.setText(R.string.select_date);
            tvToDate.setText(R.string.select_date);
            fromDate = tvFromDate.getText().toString();
            incomeController.setFromDate(fromDate);
            incomeController.setToDate(toDate);
            toDate = tvToDate.getText().toString();
        }

        return view;
    }

    private void initializeComponents() {
        incomeController = new IncomeFragmentController(this);

        lvIncome = (ListView) view.findViewById(R.id.lvIncome);
        btn_deleteAll = (Button) view.findViewById(R.id.deleteAllIncome);
        btn_showAll = (Button) view.findViewById(R.id.buttonShowAllInc);

        tvFromDate = (TextView) view.findViewById(R.id.tvFromDateInc);
        tvToDate = (TextView) view.findViewById(R.id.tvToDateInc);
        btn_saveDate = (Button) view.findViewById(R.id.btn_saveDateInc);
    }

    public void registerListeners () {
        btn_deleteAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder alert = new AlertDialog.Builder(IncomeFragment.this.getActivity());
                alert.setTitle("Delete All Income Entry");
                alert.setMessage("Are you sure you want to delete all income?");
                alert.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int which) {
                        // continue with delete
                        incomeController.deleteAllIncome();
                    }
                });
                alert.setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // close dialog
                        dialog.cancel();
                    }
                });
                alert.show();
            }
        });

        btn_showAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                incomeController.displayData();
                tvFromDate.setText(R.string.select_date);
                tvToDate.setText(R.string.select_date);
                fromDate = tvFromDate.getText().toString();
                toDate = tvToDate.getText().toString();
                incomeController.setFromDate(fromDate);
                incomeController.setToDate(fromDate);
            }
        });

        lvIncome.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, final int i, long l) {
                final CharSequence[] items = { "Delete" };

                AlertDialog.Builder builder = new AlertDialog.Builder(IncomeFragment.this.getActivity());

                builder.setItems(items, new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int item) {
                        incomeController.deleteEntry(i);
                    }

                });

                AlertDialog alert = builder.create();

                alert.show();
                return false;
            }
        });

        tvFromDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        getActivity(),
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        fromDateListener, year, month, day);

                datePickerDialog.getWindow().setBackgroundDrawable( new ColorDrawable(Color.TRANSPARENT));
                datePickerDialog.show();
            }
        });

        if (!fromDate.equals(tvFromDate.getText().toString())) {
            fromDateListener = new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                    Log.d(Constraints.TAG, "onDateSet: date: " + year + "-" + month + "-" + day);

                    tvFromDate = (TextView) view.findViewById(R.id.tvFromDateInc);

                    if ((month + 1) < 10 && day < 10) {
                        fromDate = Integer.toString(year) + "-0" + Integer.toString(month + 1) + "-0" + Integer.toString(day);
                    }

                    else if ((month + 1) < 10) {
                        fromDate = Integer.toString(year) + "-0" + Integer.toString(month + 1) + "-" + Integer.toString(day);
                    }

                    else if (day < 10) {
                        fromDate = Integer.toString(year) + "-" + Integer.toString(month + 1) + "-0" + Integer.toString(day);
                    }

                    else {fromDate = Integer.toString(year) + "-" + Integer.toString(month + 1) + "-" + Integer.toString(day); }

                    tvFromDate.setText(fromDate);
                    incomeController = new IncomeFragmentController(IncomeFragment.this);
                }
            };
        }

        tvToDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        getActivity(),
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        toDateListener, year, month, day);

                datePickerDialog.getWindow().setBackgroundDrawable( new ColorDrawable(Color.TRANSPARENT));
                datePickerDialog.show();
            }
        });

        if (toDate != tvToDate.getText().toString()) {
            toDateListener = new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                    Log.d(Constraints.TAG, "onDateSet: date: " + year + "-" + month + "-" + day);

                    tvToDate = (TextView) view.findViewById(R.id.tvToDateInc);

                    if ((month + 1) < 10 && day < 10) {
                        toDate = Integer.toString(year) + "-0" + Integer.toString(month + 1) + "-0" + Integer.toString(day);
                    }

                    else if ((month + 1) < 10) {
                        toDate = Integer.toString(year) + "-0" + Integer.toString(month + 1) + "-" + Integer.toString(day);
                    }

                    else if (day < 10) {
                        toDate = Integer.toString(year) + "-" + Integer.toString(month + 1) + "-0" + Integer.toString(day);
                    }

                    else {
                        toDate = Integer.toString(year) + "-" + Integer.toString(month + 1) + "-" + Integer.toString(day);
                    }


                    tvToDate.setText(toDate);
                    incomeController = new IncomeFragmentController(IncomeFragment.this);

                }
            };
        }


        btn_saveDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (tvToDate.getText().toString().equals("select date") || tvFromDate.getText().toString().equals("select date")){
                    Toast.makeText(IncomeFragment.this.getActivity(), "Choose dates first!", Toast.LENGTH_LONG).show();
                }

                else {
                    Log.d(Constraints.TAG, "after click button "+ fromDate + " " + toDate);
                    incomeController.setFromDate(fromDate);
                    incomeController.setToDate(toDate);
                    incomeController.showBetweenDates();
                    isButtonClicked = true;

                    if (lvIncome.getAdapter().getCount() == 0) {
                        Toast.makeText(IncomeFragment.this.getActivity(), "No data to show", Toast.LENGTH_LONG).show();
                    }
                }
            }
        });
    }

    @Override
    public void onResume() {

        if (isButtonClicked) {
            Log.d(Constraints.TAG, "refresh" + toDate + fromDate);
            incomeController.showBetweenDates();
        }

        else incomeController.displayData();

        super.onResume();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        String from_date = tvFromDate.getText().toString();
        String to_date = tvToDate.getText().toString();

        super.onSaveInstanceState(outState);
        outState.putString("fromDate", from_date);
        outState.putString("toDate", to_date);
        outState.putBoolean("isButtonClicked", isButtonClicked);
    }

    @Override
    public void onViewStateRestored(Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        if (savedInstanceState != null) {
            // Restore some state that needs to happen after the views have had
            // their state restored
            tvFromDate.setText(savedInstanceState.getString("fromDate"));
            tvToDate.setText(savedInstanceState.getString("toDate"));
            fromDate = tvFromDate.getText().toString();
            toDate = tvToDate.getText().toString();
            incomeController.setFromDate(fromDate);
            incomeController.setToDate(toDate);
            isButtonClicked = savedInstanceState.getBoolean("isButtonClicked");

//            if (isButtonClicked){
//                expensesFragmentController.showBetweenDates();
//            }
        }
    }

}

