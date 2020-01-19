package arnes.respati.mywallet.AddIncomeActivityClasses;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;

import arnes.respati.mywallet.MyWallet_DBHelper;
import arnes.respati.mywallet.OnBoardingClasses.OnBoardingActivity;

public class AddIncomeController {

    private MyWallet_DBHelper myWallet_dbHelper;
    private AddIncomeActivity addIncomeActivity;
    private SQLiteDatabase dbIncome;

    public AddIncomeController (AddIncomeActivity addIncomeActivity) {
        this.addIncomeActivity = addIncomeActivity;
        myWallet_dbHelper = new MyWallet_DBHelper(addIncomeActivity);
    }


    public void insertdata(String title, String date, String amount, String category) {
        ContentValues contentValues = new ContentValues();

        myWallet_dbHelper = new MyWallet_DBHelper(addIncomeActivity);
        dbIncome = myWallet_dbHelper.getWritableDatabase();

        contentValues.put(myWallet_dbHelper.COLUMN_INCOME_TITLE, title);
        contentValues.put(myWallet_dbHelper.COLUMN_INCOME_DATE, date);
        contentValues.put(myWallet_dbHelper.COLUMN_INCOME_AMOUNT, amount);
        contentValues.put(myWallet_dbHelper.COLUMN_INCOME_CATEGORY, category);

        long id = dbIncome.insert(myWallet_dbHelper.TABLE_INCOME, null, contentValues);
    }
}
