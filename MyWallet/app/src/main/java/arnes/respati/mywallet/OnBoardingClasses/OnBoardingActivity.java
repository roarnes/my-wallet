package arnes.respati.mywallet.OnBoardingClasses;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.mynameismidori.currencypicker.CurrencyPicker;
import com.mynameismidori.currencypicker.CurrencyPickerListener;

import arnes.respati.mywallet.MainActivityClasses.MainActivity;
import arnes.respati.mywallet.R;

public class OnBoardingActivity extends AppCompatActivity {

    private EditText inputName;
    private Button btnFinish;
    private Button btnCurrency;

    private String currency;

    private SharedPreferences username, Currency;
    private SharedPreferences.Editor usernameEditor, CurrencyEditor;
    private static final String PREF_NAME = "names";
    private static final String PREF_CURRENCY = "curr";
    private static final String KEY_NAME = "name";
    private static final String KEY_CURRENCY = "country name";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_on_boarding);
        initializeComponents();
        registerListeners();
    }

    private void initializeComponents() {
        username = getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        usernameEditor = username.edit();

        Currency = getSharedPreferences(PREF_CURRENCY, Context.MODE_PRIVATE);
        CurrencyEditor = Currency.edit();

        inputName = (EditText) findViewById(R.id.inputName);

        btnCurrency = (Button) findViewById(R.id.chooseCurrency);
        btnFinish = (Button) findViewById(R.id.btnFinish);
    }

    private void registerListeners () {
        btnFinish.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                String name = inputName.getText().toString();
                if (name.isEmpty()) {
                    Toast.makeText(OnBoardingActivity.this, "Name field can not be empty!", Toast.LENGTH_LONG).show();
                }
                else {
                    saveUserName();
                    getSharedPreferences("PREFERENCE", MODE_PRIVATE).edit()
                            .putBoolean("isFirstRun", false).commit();
                    Intent intent = new Intent(OnBoardingActivity.this, MainActivity.class);
                    startActivity(intent);
                }
            }
        });
        btnCurrency.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CurrencyPicker picker = CurrencyPicker.newInstance("Select Currency");  // dialog title

                picker.show(getSupportFragmentManager(), "CURRENCY_PICKER");

                picker.setListener(new CurrencyPickerListener() {
                    @Override
                    public void onSelectCurrency(String name, String code, String symbol, int flagDrawableResID) {
                        // Implement your code here
                        currency = name;
//                        Toast.makeText(OnBoardingActivity.this, name + " " + code + " " + symbol, Toast.LENGTH_LONG).show();
                        saveCurrency();
                    }
                });

            }

        });
    }

    public void saveUserName(){
        String name = inputName.getText().toString();
        usernameEditor.putString(KEY_NAME, name);
        usernameEditor.commit();
    }

    public void saveCurrency(){
        CurrencyEditor.putString(KEY_CURRENCY, currency);
        CurrencyEditor.commit();
        btnCurrency.setText(currency);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        String name = inputName.getText().toString();

        super.onSaveInstanceState(outState);
        outState.putString("onboard_name", name);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        inputName.setText(savedInstanceState.getString("onboard_name"));
    }
}
