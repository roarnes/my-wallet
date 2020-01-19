package arnes.respati.mywallet.MainActivityClasses.IncomeFragmentClasses;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import arnes.respati.mywallet.MyWallet_DBHelper;
import arnes.respati.mywallet.R;

public class IncomeAdapter extends BaseAdapter {

    private Context mContext;
    MyWallet_DBHelper incomeDBHelper;

    private ArrayList<String> Title = new ArrayList<String>();
    private ArrayList<String> Date = new ArrayList<String>();
    private ArrayList<String> Amount = new ArrayList<String>();
    private ArrayList<String> Category = new ArrayList<String>();
    private ArrayList<Integer> Code = new ArrayList<>();

    public IncomeAdapter (Context context, ArrayList <Integer> Code , ArrayList <String> Title, ArrayList<String> Date,
                          ArrayList <String> Amount, ArrayList <String> Category){
        this.mContext = context;
        this.Title = Title;
        this.Date = Date;
        this.Amount = Amount;
        this.Category =Category;
        this.Code = Code;
    }

    public int getClickedCode (int position) {
        return Code.get(position);
    }

    @Override
    public int getCount() {
        return Title.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        final    viewHolder holder;
        incomeDBHelper = new MyWallet_DBHelper(mContext);
        LayoutInflater layoutInflater;

        if (convertView == null) {
            layoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.table_income, null);
            holder = new viewHolder();
            holder.Title = (TextView) convertView.findViewById(R.id.tvTitle);
            holder.Date = (TextView) convertView.findViewById(R.id.tvDate);
            holder.Amount = (TextView) convertView.findViewById(R.id.tvAmount);
            holder.Category = (TextView) convertView.findViewById(R.id.tvCategory);
        }

        else {
            holder = (viewHolder) convertView.getTag();
        }

        holder.Title.setText(Title.get(position));
        holder.Date.setText(Date.get(position));
        holder.Amount.setText(Amount.get(position));
        holder.Category.setText(Category.get(position));

        switch (Category.get(position)) {
            case "Savings":
                holder.Category.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_saving, 0);
                break;
            case "Salary":
                holder.Category.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_salary, 0);
                break;
            case "Other":
                holder.Category.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_other2, 0);
                break;
        }

        return convertView;
    }

    public static class viewHolder {
        TextView Title;
        TextView Date;
        TextView Amount;
        TextView Category;
    }


}