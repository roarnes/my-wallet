package arnes.respati.mywallet.AddExpenditureActivityClasses;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;

import arnes.respati.mywallet.MyWallet_DBHelper;

public class AddExpenditureController {

    private MyWallet_DBHelper myWallet_dbHelper;
    SQLiteDatabase dbExpenses;
    private AddExpenditureActivity addExpenditureActivity;

    public AddExpenditureController (AddExpenditureActivity addExpenditureActivity) {
        this.addExpenditureActivity = addExpenditureActivity;
        myWallet_dbHelper = new MyWallet_DBHelper(addExpenditureActivity);
    }

    public void deleteAllExpenditure() {
        SQLiteDatabase db = myWallet_dbHelper.getWritableDatabase();
        db.delete(MyWallet_DBHelper.TABLE_EXPENDITURE, null, null);
    }

    public void insertdata(String title, String date, String amount, String category) {
        ContentValues contentValues = new ContentValues();

        myWallet_dbHelper = new MyWallet_DBHelper(addExpenditureActivity);
        dbExpenses = myWallet_dbHelper.getWritableDatabase();

        contentValues.put(MyWallet_DBHelper.COLUMN_EXPENDITURE_TITLE, title);
        contentValues.put(MyWallet_DBHelper.COLUMN_EXPENDITURE_DATE, date);
        contentValues.put(MyWallet_DBHelper.COLUMN_EXPENDITURE_AMOUNT, amount);
        contentValues.put(MyWallet_DBHelper.COLUMN_EXPENDITURE_CATEGORY, category);

        long id = dbExpenses.insert(MyWallet_DBHelper.TABLE_EXPENDITURE, null, contentValues);
    }

}
