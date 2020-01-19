package arnes.respati.mywallet;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class MyWallet_DBHelper extends SQLiteOpenHelper {

    //DATABASE MY WALLET
    public static final String DATABASE_NAME = "my_wallet";
    public static final int DATABASE_VERSION = 1;

    //TABLE EXPENDITURE
    public static final String TABLE_EXPENDITURE = "table_expenditure";
    public  static final String COLUMN_EXPENDITURE_CODE = "code";
    public static final String COLUMN_EXPENDITURE_TITLE = "title";
    public static final String COLUMN_EXPENDITURE_DATE = "date";
    public static final String COLUMN_EXPENDITURE_AMOUNT = "amount";
    public static final String COLUMN_EXPENDITURE_CATEGORY = "category";

    private static final String DATABASE_CREATE_TABLE_EXPENDITURE = "create table "
            + TABLE_EXPENDITURE + "("
            + COLUMN_EXPENDITURE_CODE + " integer not null primary key autoincrement, "
            + COLUMN_EXPENDITURE_TITLE + " varchar not null, "
            + COLUMN_EXPENDITURE_DATE + " text not null, "
            + COLUMN_EXPENDITURE_AMOUNT + " double not null, "
            + COLUMN_EXPENDITURE_CATEGORY + " varchar not null);";

    //TABLE INCOME
    public static final String TABLE_INCOME = "table_income";
    public  static final String COLUMN_INCOME_CODE = "code";
    public static final String COLUMN_INCOME_TITLE = "title";
    public static final String COLUMN_INCOME_DATE = "date";
    public static final String COLUMN_INCOME_AMOUNT = "amount";
    public static final String COLUMN_INCOME_CATEGORY = "category";

    private static final String DATABASE_CREATE_TABLE_INCOME = "create table "
            + TABLE_INCOME + "("
            + COLUMN_INCOME_CODE + " integer not null primary key autoincrement, "
            + COLUMN_INCOME_TITLE + " varchar not null, "
            + COLUMN_INCOME_DATE + " text not null, "
            + COLUMN_INCOME_AMOUNT + " double not null, "
            + COLUMN_INCOME_CATEGORY + " varchar not null);";



    public MyWallet_DBHelper (Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DATABASE_CREATE_TABLE_EXPENDITURE);
        db.execSQL(DATABASE_CREATE_TABLE_INCOME);
    }

    @Override

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(MyWallet_DBHelper.class.getName(),"Upgrading database from version "
                + oldVersion+ " to "+ newVersion+ ", which will destroy all old data");
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_EXPENDITURE);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_INCOME);
        onCreate(db);
    }
}
