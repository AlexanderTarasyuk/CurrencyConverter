package com.example.alextarasyuk.currencyconverter;

import android.content.Context;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.example.alextarasyuk.currencyconverter.api.CurrencyConverterService;
import com.example.alextarasyuk.currencyconverter.api.CurrencyConverterServiceImpl;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Locale;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private Button mButton0, mButton1, mButton2, mButton3, mButton4, mButton5, mButton6, mButton7, mButton8, mButton9;
    private Button mButtonDot;
    private Button mButtonCleanTextView;
    private Button mButtonStardConvert;
    private Button mButtonValueFromConverted;
    private Button mButtonToConvert;
    private Button mButtonDelete;
    private TextView mTextView;
    private Double mExtractedDoubleValue = 0.0;
    private Double mConvertedDoubleValue = 0.0;
    private String mString = "";

    private CurrencyConverterService mCurrencyConverterService;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setupViews();
        setupListeners();
        mCurrencyConverterService = new CurrencyConverterServiceImpl().getCurrencyConverterService();
    }

    private void setupListeners() {

        mButtonCleanTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mTextView.setText("");
                mTextView.setHint("0.000");
            }
        });

        mButtonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!mButtonValueFromConverted.getText().toString().isEmpty()) {
                    String toset = mTextView.getText().toString().trim();
                    mButtonValueFromConverted.setText(toset);
                } else {
                    mTextView.setText("0.000");
                }
            }
        });

        mButtonValueFromConverted.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popupMenu = new PopupMenu(MainActivity.this, mButtonValueFromConverted);
                popupMenu.getMenuInflater().inflate(R.menu.menu, popupMenu.getMenu());
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        mButtonValueFromConverted.setText(item.getTitle().toString().substring(item.getTitle().toString().length() - 3));
                        return false;
                    }
                });
                popupMenu.show();
            }
        });

        mButtonToConvert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenu popupMenu = new PopupMenu(MainActivity.this, mButtonToConvert);
                popupMenu.getMenuInflater().inflate(R.menu.menu, popupMenu.getMenu());
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        mButtonToConvert.setText(menuItem.getTitle().toString().substring(menuItem.getTitle().toString().length() - 3));
                        return false;
                    }
                });
                popupMenu.show();

            }
        });

        mButtonStardConvert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                convert();
            }
        });


    }

    private void convert() {

        final String fromtext = mButtonValueFromConverted.getText().toString();
        final String totext = mButtonToConvert.getText().toString();

        if (fromtext.equals(totext)) {
            Toast.makeText(MainActivity.this, "From and to Values are Same", Toast.LENGTH_SHORT).show();
            return;
        } else if (mTextView.getText().toString().isEmpty()) {
            Toast.makeText(MainActivity.this, "Please Enter a Value", Toast.LENGTH_SHORT).show();
            return;

        }

        if (!isConnected(this)) {
            Toast.makeText(MainActivity.this, "No Internet Connection", Toast.LENGTH_LONG).show();
        } else {
            mCurrencyConverterService.getCurrency(fromtext).enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    try {
                        String stringJson = response.body().string();

                        JSONObject converResult = (JSONObject) new JSONObject(stringJson).get("rates");
                        mString = converResult.getString(totext);
                        mExtractedDoubleValue = Double.parseDouble(mString);

                        String fromvals = mTextView.getText().toString();
                        if (fromvals.equals(".")) fromvals = "0.0";
                        Double fromval = Double.parseDouble(fromvals);
                        mConvertedDoubleValue = mExtractedDoubleValue * fromval;

                        mTextView.setText("");
                        mTextView.setHintTextColor(Color.parseColor("#5c007a"));
                        if ((String.format(Locale.getDefault(), "%.4f", mConvertedDoubleValue).length()) > 13) {
                            mTextView.setTextSize(28f);
                        }
                        mTextView.setHint("= " + String.format(Locale.getDefault(), "%.4f", mConvertedDoubleValue));
                    } catch (IOException e) {
                        e.printStackTrace();

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    Toast.makeText(MainActivity.this, "Mistake", Toast.LENGTH_LONG).show();

                }
            });

        }


    }

    public static boolean isConnected(Context context) {
        ConnectivityManager cm =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnectedOrConnecting()) {
            return true;
        }
        return false;
    }

    public void input(View view) {
        Button btn = (Button) view;
        String currentText = mTextView.getText().toString();

        if (currentText.indexOf('.') == -1) {
            mTextView.append(btn.getText().toString());
        } else if (currentText.indexOf('.') >= 0) {
            if (btn.getText().toString().equals(".")) {
                Toast.makeText(MainActivity.this, "Multiple Decimal Points Cannot Exist", Toast.LENGTH_SHORT).show();
            } else
                mTextView.append(btn.getText().toString());
        }
    }

    private void setupViews() {

        mButton0 = (Button) findViewById(R.id.button0);
        mButton1 = (Button) findViewById(R.id.button1);
        mButton2 = (Button) findViewById(R.id.button2);
        mButton3 = (Button) findViewById(R.id.button3);
        mButton4 = (Button) findViewById(R.id.button4);
        mButton5 = (Button) findViewById(R.id.button5);
        mButton6 = (Button) findViewById(R.id.button6);
        mButton7 = (Button) findViewById(R.id.button7);
        mButton8 = (Button) findViewById(R.id.button8);
        mButton9 = (Button) findViewById(R.id.button9);
        mButtonDot = (Button) findViewById(R.id.btn_dot);
        mButtonCleanTextView = (Button) findViewById(R.id.btn_clean_tv);
        mButtonStardConvert = (Button) findViewById(R.id.btn_convert);
        mButtonValueFromConverted = (Button) findViewById(R.id.buttonfrom);
        mButtonToConvert = (Button) findViewById(R.id.buttonto);
        mTextView = (TextView) findViewById(R.id.textViewfrom);
        mButtonDelete = (Button) findViewById(R.id.imageButton);
    }
}
