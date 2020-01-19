package arnes.respati.mywallet.AddIncomeActivityClasses;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.constraint.Constraints;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.util.Calendar;

import arnes.respati.mywallet.AddExpenditureActivityClasses.AddExpenditureActivity;
import arnes.respati.mywallet.AddExpenditureActivityClasses.AddExpenditureController;
import arnes.respati.mywallet.MainActivityClasses.MainActivity;
import arnes.respati.mywallet.R;

public class AddIncomeActivity extends AppCompatActivity {

    private AddIncomeController addIncomeController;

    private EditText inputTitle, inputAmount;
    private RadioGroup rbtn_group;
    private Button btn_save, btn_selectDate;

    private String selected_date = new String();
    private DatePickerDialog.OnDateSetListener selectDateListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_income);

        addIncomeController = new AddIncomeController(this);

        initializeComponents();
        registerListeners();
    }

    public void initializeComponents (){

        inputTitle = (EditText) findViewById(R.id.inputTitleInc);
        inputAmount = (EditText) findViewById(R.id.inputAmountInc);

        rbtn_group = (RadioGroup) findViewById(R.id.rbtn_groupInc);

        btn_save = (Button) findViewById(R.id.btn_saveIncome);
        btn_selectDate = (Button) findViewById(R.id.buttonInputDateInc);
    }

    public void registerListeners (){

        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String title = inputTitle.getText().toString();
                String amount = inputAmount.getText().toString();

                if (title.isEmpty() || amount.isEmpty() ) {
                    Toast.makeText(AddIncomeActivity.this, "All fields must not be empty!", Toast.LENGTH_LONG).show();
                }

                else {

                    String date = selected_date;

                    // get selected radio button from radioGroup
                    int selectedId = rbtn_group.getCheckedRadioButtonId();

                    // find the radiobutton by returned id
                    RadioButton selectedRbtn = (RadioButton) findViewById(selectedId);
                    String category = selectedRbtn.getText().toString();

                    addIncomeController.insertdata(title, date, amount, category);

                    Toast.makeText(getApplicationContext(), "Saved successfully", Toast.LENGTH_LONG).show();

                    Intent intent = new Intent(AddIncomeActivity.this, MainActivity.class);
                    AddIncomeActivity.this.startActivity(intent);
                }
            }
        });

        btn_selectDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btn_selectDate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Calendar cal = Calendar.getInstance();
                        int year = cal.get(Calendar.YEAR);
                        int month = cal.get(Calendar.MONTH);
                        int day = cal.get(Calendar.DAY_OF_MONTH);

                        DatePickerDialog datePickerDialog = new DatePickerDialog(
                                AddIncomeActivity.this,
                                android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                                selectDateListener, year, month, day);

                        datePickerDialog.getWindow().setBackgroundDrawable( new ColorDrawable(Color.TRANSPARENT));
                        datePickerDialog.show();
                    }
                });
            }
        });
        if (!selected_date.equals(btn_selectDate.getText().toString())) {
            selectDateListener = new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                    Log.d(Constraints.TAG, "onDateSet: date: " + year + "-" + month + "-" + day);

                    btn_selectDate = (Button) findViewById(R.id.buttonInputDateInc);

                    if ((month + 1) < 10 && day < 10) {
                        selected_date = Integer.toString(year) + "-0" + Integer.toString(month + 1) + "-0" + Integer.toString(day);
                    }

                    else if ((month + 1) < 10) {
                        selected_date = Integer.toString(year) + "-0" + Integer.toString(month + 1) + "-" + Integer.toString(day);
                    }

                    else if (day < 10) {
                        selected_date = Integer.toString(year) + "-" + Integer.toString(month + 1) + "-0" + Integer.toString(day);
                    }

                    else {selected_date = Integer.toString(year) + "-" + Integer.toString(month + 1) + "-" + Integer.toString(day); }

                    btn_selectDate.setText(selected_date);
                    addIncomeController = new AddIncomeController(AddIncomeActivity.this);
                }
            };
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        String title = inputTitle.getText().toString();
        String date = btn_selectDate.getText().toString();
        String amount = inputAmount.getText().toString();
        int category = rbtn_group.getCheckedRadioButtonId();;

        super.onSaveInstanceState(outState);
        outState.putString("inc_title", title);
        outState.putString("inc_date", date);
        outState.putString("inc_amount", amount);
        outState.putInt("inc_category", category);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        inputTitle.setText(savedInstanceState.getString("inc_title"));
        btn_selectDate.setText(savedInstanceState.getString("inc_date"));
        inputAmount.setText(savedInstanceState.getString("inc_amount"));
        rbtn_group.check(savedInstanceState.getInt("inc_category"));
    }

}
