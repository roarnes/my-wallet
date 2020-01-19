package arnes.respati.mywallet.MainActivityClasses.ExpensesFragmentClasses;

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

public class ExpensesAdapter extends BaseAdapter {

    private Context context;
    MyWallet_DBHelper expensesDBHelper;
    SQLiteDatabase db;

    private ArrayList<String> Title = new ArrayList<String>();
    private ArrayList<String> Date = new ArrayList<String>();
    private ArrayList<String> Amount = new ArrayList<String>();
    private ArrayList<String> Category = new ArrayList<String>();
    private ArrayList<Integer> Code = new ArrayList<>();

    public ExpensesAdapter (Context context, ArrayList<Integer> Code, ArrayList <String> Title, ArrayList<String> Date,
                          ArrayList <String> Amount, ArrayList <String> Category){
        this.context = context;
        this.Title = Title;
        this.Date = Date;
        this.Amount = Amount;
        this.Category = Category;
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

        final viewHolder holder;
        expensesDBHelper = new MyWallet_DBHelper(context);
        LayoutInflater layoutInflater;

        if (convertView == null) {
            layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.table_expenditure, null);

            holder = new viewHolder();
            holder.Title = (TextView) convertView.findViewById(R.id.tvTitle1);
            holder.Date = (TextView) convertView.findViewById(R.id.tvDate1);
            holder.Amount = (TextView) convertView.findViewById(R.id.tvAmount1);
            holder.Category = (TextView) convertView.findViewById(R.id.tvCategory1);
            convertView.setTag(holder);
        }

        else {
            holder = (viewHolder) convertView.getTag();
        }

        holder.Title.setText(Title.get(position));
        holder.Date.setText(Date.get(position));
        holder.Amount.setText(Amount.get(position));
        holder.Category.setText(Category.get(position));

        switch (Category.get(position)) {
            case "Food":
                holder.Category.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_food, 0);
                break;
            case "Leisure":
                holder.Category.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_leisure, 0);
                break;
            case "Education":
                holder.Category.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_edu, 0);
                break;
            case "Travel":
                holder.Category.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_travel, 0);
                break;
            case "Accommodation":
                holder.Category.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_accom, 0);
                break;
            case "Grocery":
                holder.Category.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_grocery, 0);
                break;
            case "Fashion":
                holder.Category.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_fashion, 0);
                break;
            case "Entertainment":
                holder.Category.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_entertain, 0);
                break;
            case "Other":
                holder.Category.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_other, 0);
                break;
        }

        return convertView;
    }

    public class viewHolder {
        TextView Title;
        TextView Date;
        TextView Amount;
        TextView Category;
    }
}