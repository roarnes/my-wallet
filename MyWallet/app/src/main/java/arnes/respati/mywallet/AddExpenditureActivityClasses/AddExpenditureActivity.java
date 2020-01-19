package arnes.respati.mywallet.AddExpenditureActivityClasses;

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
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

import arnes.respati.mywallet.AddIncomeActivityClasses.AddIncomeActivity;
import arnes.respati.mywallet.MainActivityClasses.MainActivity;
import arnes.respati.mywallet.MainActivityClasses.OverviewFragment;
import arnes.respati.mywallet.MainActivityClasses.OverviewFragmentController;
import arnes.respati.mywallet.R;

public class AddExpenditureActivity extends AppCompatActivity {

    private AddExpenditureController addExpenditureController;

    private EditText inputTitle, inputAmount;
    private RadioGroup rbtn_group;
    private Button btn_save, btn_selectDate;

    private String selected_date = new String();
    private DatePickerDialog.OnDateSetListener selectDateListener;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_expenditure);

        addExpenditureController = new AddExpenditureController(this);

        initializeComponents();
        registerListeners();

        if (savedInstanceState != null){
            inputTitle.setText(savedInstanceState.getString("exp_title"));
            btn_selectDate.setText(savedInstanceState.getString("exp_date"));
            inputAmount.setText(savedInstanceState.getString("exp_amount"));
            rbtn_group.check(savedInstanceState.getInt("exp_category"));
        }

        else {
            btn_selectDate.setText(R.string.selectdate);
            selected_date = btn_selectDate.getText().toString();
        }
    }

    public void initializeComponents (){

        inputTitle = (EditText) findViewById(R.id.inputTitleExp);
        inputAmount = (EditText) findViewById(R.id.inputAmountExp);

        rbtn_group = (RadioGroup) findViewById(R.id.rbtn_groupExp);

        btn_save = (Button) findViewById(R.id.btn_saveExpenditure);
        btn_selectDate = (Button) findViewById(R.id.buttonInputDateExp);
    }

    public void registerListeners (){
        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String title = inputTitle.getText().toString();
                String amount = inputAmount.getText().toString();

                if (title.isEmpty() || amount.isEmpty() ) {
                    Toast.makeText(AddExpenditureActivity.this, "All fields must not be empty!", Toast.LENGTH_LONG).show();
                }

                else {
                    String date = selected_date;

                    // get selected radio button from radioGroup
                    int selectedId = rbtn_group.getCheckedRadioButtonId();

                    // find the radiobutton by returned id
                    RadioButton selectedRbtn = (RadioButton) findViewById(selectedId);
                    String category = selectedRbtn.getText().toString();

                    addExpenditureController.insertdata(title, date, amount, category);

                    Toast.makeText(getApplicationContext(), "Saved successfully", Toast.LENGTH_LONG).show();

                    Intent intent = new Intent(AddExpenditureActivity.this, MainActivity.class);
                    AddExpenditureActivity.this.startActivity(intent);
                }
            }
        });

        btn_selectDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        AddExpenditureActivity.this,
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        selectDateListener, year, month, day);

                datePickerDialog.getWindow().setBackgroundDrawable( new ColorDrawable(Color.TRANSPARENT));
                datePickerDialog.show();
            }
        });

        if (!selected_date.equals(btn_selectDate.getText().toString())) {
            selectDateListener = new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                    Log.d(Constraints.TAG, "onDateSet: date: " + year + "-" + month + "-" + day);

                    btn_selectDate = (Button) findViewById(R.id.buttonInputDateExp);

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
                    addExpenditureController = new AddExpenditureController(AddExpenditureActivity.this);
                }
            };
        }
    }

    protected void onSaveInstanceState(Bundle outState) {
        String title = inputTitle.getText().toString();
        String date = btn_selectDate.getText().toString();
        String amount = inputAmount.getText().toString();
        int category = rbtn_group.getCheckedRadioButtonId();;

        super.onSaveInstanceState(outState);
        outState.putString("exp_title", title);
        outState.putString("exp_date", date);
        outState.putString("exp_amount", amount);
        outState.putInt("exp_category", category);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        inputTitle.setText(savedInstanceState.getString("exp_title"));
        btn_selectDate.setText(savedInstanceState.getString("exp_date"));
        inputAmount.setText(savedInstanceState.getString("exp_amount"));
        rbtn_group.check(savedInstanceState.getInt("exp_category"));
    }

}
