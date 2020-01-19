package arnes.respati.mywallet.MainActivityClasses;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.View;
import android.widget.ListView;

import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import arnes.respati.mywallet.AddExpenditureActivityClasses.AddExpenditureActivity;
import arnes.respati.mywallet.AddIncomeActivityClasses.AddIncomeActivity;
import arnes.respati.mywallet.MyWallet_DBHelper;
import arnes.respati.mywallet.R;


public class OverviewFragmentController {

    private MyWallet_DBHelper myWallet_dbHelper;

    OverviewFragment overviewFragment;

    private String chooseMonth;

    ListView listView;

    public OverviewFragmentController (OverviewFragment overviewFragment) {
        this.overviewFragment = overviewFragment;
        myWallet_dbHelper = new MyWallet_DBHelper(overviewFragment.getActivity());
    }

    public double getTotalExpenditure() {
        SQLiteDatabase db = myWallet_dbHelper.getReadableDatabase();
        double sum = 0;
        Cursor cursor = db.rawQuery(
                "SELECT SUM(" + (myWallet_dbHelper.COLUMN_EXPENDITURE_AMOUNT) +
                        ") FROM " + (myWallet_dbHelper.TABLE_EXPENDITURE) + " ;", null);
        cursor.moveToFirst();
        if(cursor.getCount()>0) {
            sum = cursor.getDouble(0);
        }
        return sum;
    }

    public double getTotalIncome() {
        SQLiteDatabase db = myWallet_dbHelper.getReadableDatabase();

        double sum = 0;
        Cursor cursor = db.rawQuery(
                "SELECT SUM(" + (myWallet_dbHelper.COLUMN_INCOME_AMOUNT) +
                        ") FROM " + (myWallet_dbHelper.TABLE_INCOME) + " ;" , null);
        cursor.moveToFirst();
        if(cursor.getCount()>0) {
            sum = cursor.getDouble(0);
        }
        return sum;
    }

    public double getTotalSum(){
        double a = getTotalExpenditure();
        double b = getTotalIncome();
        return (b - a);
    }

    public void openAddIncome(View view) {
        Intent intent = new Intent(overviewFragment.getActivity(), AddIncomeActivity.class);
        overviewFragment.startActivity(intent);
    }

    public void openAddExpenditure(View view) {
        Intent intent = new Intent(overviewFragment.getActivity(), AddExpenditureActivity.class);
        overviewFragment.startActivity(intent);
    }

    public PieData addPieChartData (){
        SQLiteDatabase db = myWallet_dbHelper.getReadableDatabase();

        float[] amount = new float[9];
        String[] categories = new String[]{overviewFragment.getString(R.string.food), overviewFragment.getString(R.string.leisure), overviewFragment.getString(R.string.education), overviewFragment.getString(R.string.travel), overviewFragment.getString(R.string.accommodation), overviewFragment.getString(R.string.grocery), overviewFragment.getString(R.string.fashion), overviewFragment.getString(R.string.entertain), overviewFragment.getString(R.string.other)};

        String getCategories = "SELECT DISTINCT " + myWallet_dbHelper.COLUMN_EXPENDITURE_CATEGORY
                + ", " + myWallet_dbHelper.COLUMN_EXPENDITURE_AMOUNT
                + " FROM " + myWallet_dbHelper.TABLE_EXPENDITURE +  ";";

        for (int i = 0; i < categories.length  ; i++) {
            String query = "SELECT SUM (amount) FROM table_expenditure WHERE category = \'" + categories[i] + "\' " +
                    "AND strftime('%m', date) = \'" + getMonth() + "\';" ;

            Cursor cursor = db.rawQuery(query, null);
            cursor.moveToFirst();
            if(cursor.getCount()>0) {
                amount[i] = cursor.getFloat(0);
            }
            cursor.close();

            System.out.println("ABC amount: " + amount[i] + ", Query: " + query);
        }

        List <PieEntry> expenses = new ArrayList<>();

        for (int i = 0; i < categories.length ; i++) {
            if (amount[i] > 0.0) {
                expenses.add(new PieEntry(amount[i], categories[i]));
            }

        }
        PieDataSet dataSet = new PieDataSet(expenses, "Category");
        dataSet.setColors(ColorTemplate.COLORFUL_COLORS);

        PieData data = new PieData(dataSet);

        return data;
    }

    public void setMonth (int month){
        if (month < 10){
            this.chooseMonth = "0" + month;
        }
    }

    public String getMonth(){
//        if (this.chooseMonth == null){
//            Calendar calendar = Calendar.getInstance();
////            int month = calendar.get(Calendar.MONTH);
////            String months[] = {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
////            chooseMonth = months[month];
//            chooseMonth = String.valueOf(calendar.get(Calendar.MONTH) + 1);
//        }
//
//        if (Integer.parseInt(chooseMonth) < 10){
//            chooseMonth = "0" + chooseMonth;
//        }

        return this.chooseMonth;
    }


}
