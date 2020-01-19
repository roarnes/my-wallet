package arnes.respati.mywallet.MainActivityClasses;

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

public class BetweenDatesAdapter extends BaseAdapter {
    private Context mContext;
    private MyWallet_DBHelper incomeDBHelper;
    SQLiteDatabase db;

    private ArrayList<String> Title = new ArrayList<String>();
    private ArrayList<String> Date = new ArrayList<String>();
    private ArrayList<String> Amount = new ArrayList<String>();
    private ArrayList<String> Category = new ArrayList<String>();

    public BetweenDatesAdapter(Context context, ArrayList <String> Title, ArrayList<String> Date,
                               ArrayList <String> Amount, ArrayList <String> Category){
        this.mContext = context;
        this.Title = Title;
        this.Date = Date;
        this.Amount = Amount;
        this.Category =Category;
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
            convertView = layoutInflater.inflate(R.layout.between_dates_listview, null);
            holder = new viewHolder();
            holder.Title = (TextView) convertView.findViewById(R.id.tvTitle2);
            holder.Date = (TextView) convertView.findViewById(R.id.tvDate2);
            holder.Amount = (TextView) convertView.findViewById(R.id.tvAmount2);
            holder.Category = (TextView) convertView.findViewById(R.id.tvCategory2);
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
