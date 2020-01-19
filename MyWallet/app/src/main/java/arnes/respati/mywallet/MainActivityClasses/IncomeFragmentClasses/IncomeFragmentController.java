package arnes.respati.mywallet.MainActivityClasses.IncomeFragmentClasses;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.constraint.Constraints;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import arnes.respati.mywallet.MyWallet_DBHelper;
import arnes.respati.mywallet.R;

public class IncomeFragmentController {

    private IncomeFragment incomeFragment;
    private IncomeAdapter incomeAdapter;

    private MyWallet_DBHelper myWallet_dbHelper;

    private ArrayList<String> Title = new ArrayList<String>();
    private ArrayList<String> Date = new ArrayList<String>();
    private ArrayList<String> Amount = new ArrayList<String>();
    private ArrayList<String> Category = new ArrayList<String>();
    private ArrayList<Integer> Code = new ArrayList<Integer>();

    private String fromDate = getFromDate();
    private String toDate = getToDate();

    ListView lvIncome;

    public IncomeFragmentController (IncomeFragment incomeFragment){
        this.incomeFragment = incomeFragment;
        myWallet_dbHelper = new MyWallet_DBHelper(this.incomeFragment.getActivity());
    }

    public void deleteAllIncome() {
        SQLiteDatabase db = myWallet_dbHelper.getWritableDatabase();
        db.delete(myWallet_dbHelper.TABLE_INCOME, null, null);

        Toast.makeText(incomeFragment.getActivity(), "Deleted successfully", Toast.LENGTH_LONG).show();
        displayData();
    }

    public void displayData() {
        SQLiteDatabase db = myWallet_dbHelper.getReadableDatabase();

        lvIncome = (ListView) incomeFragment.getView().findViewById(R.id.lvIncome);

        Cursor cursor = db.rawQuery("SELECT * FROM " + myWallet_dbHelper.TABLE_INCOME + " ORDER BY date DESC", null);
        Title.clear();
        Date.clear();
        Amount.clear();
        Category.clear();
        Code.clear();

        if (cursor.moveToFirst()) {
            do {
                Title.add(cursor.getString(cursor.getColumnIndex("title")));
                Date.add(cursor.getString(cursor.getColumnIndex("date")));
                Amount.add(cursor.getString(cursor.getColumnIndex("amount")));
                Category.add(cursor.getString(cursor.getColumnIndex("category")));
                Code.add(cursor.getInt(cursor.getColumnIndex("code")));
            }
            while (cursor.moveToNext());
        }

        incomeAdapter = new IncomeAdapter(incomeFragment.getActivity(), Code, Title, Date, Amount, Category);
        lvIncome.setAdapter(incomeAdapter);

        //code to set adapter to populate list
        cursor.close();
    }

    public void deleteEntry(int pos){
        SQLiteDatabase db = myWallet_dbHelper.getWritableDatabase();

        int income_code =  incomeAdapter.getClickedCode(pos);

        String delete_query = "DELETE FROM " + myWallet_dbHelper.TABLE_INCOME + " WHERE code = " + income_code;

        db.execSQL(delete_query);
        Toast.makeText(incomeFragment.getActivity(), "Deleted successfully", Toast.LENGTH_LONG).show();
        displayData();
    }

    public void showBetweenDates () {

        Log.d(Constraints.TAG, "fromdate "  + this.fromDate + " todate " + this.toDate );
        myWallet_dbHelper = new MyWallet_DBHelper(incomeFragment.getActivity());
        SQLiteDatabase db = myWallet_dbHelper.getReadableDatabase();

        lvIncome = (ListView) incomeFragment.getView().findViewById(R.id.lvIncome);

        Cursor cursor = db.rawQuery("SELECT * FROM " + myWallet_dbHelper.TABLE_INCOME +
                        " WHERE date(date) BETWEEN date('" + fromDate + "') AND date('" + toDate + "')"
                , null);


        Title.clear();
        Date.clear();
        Amount.clear();
        Category.clear();
        Code.clear();

        if (cursor.moveToFirst()) {
            do {
                Title.add(cursor.getString(cursor.getColumnIndex("title")));
                Date.add(cursor.getString(cursor.getColumnIndex("date")));
                Amount.add(cursor.getString(cursor.getColumnIndex("amount")));
                Category.add(cursor.getString(cursor.getColumnIndex("category")));
                Code.add(cursor.getInt(cursor.getColumnIndex("code")));
            }
            while (cursor.moveToNext());
        }

        incomeAdapter = new IncomeAdapter(incomeFragment.getActivity(), Code, Title, Date, Amount, Category);
        lvIncome.setAdapter(incomeAdapter);

        //code to set adapter to populate list
        cursor.close();
        db.close();
    }

    public void setFromDate (String fromDate){
        this.fromDate = fromDate;
    }

    public void setToDate (String toDate){
        this.toDate = toDate;
    }

    public String getFromDate (){
        return fromDate;
    }

    public String getToDate(){
        return toDate;
    }
}
