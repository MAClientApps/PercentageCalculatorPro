package com.calculator.percentagcalculatorpro;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.cal.percalculatorpro.databinding.ActivityMainBinding;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private ActivityMainBinding binding;
    private static final int ADDITION = 0;
    private static final int SUBTRACTION = 1;

    private int method = 0;
    private int clickCountAd = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.totalAmountField.requestFocus();
        binding.totalAmountField.setShowSoftInputOnFocus(false);
        binding.percentageField.setShowSoftInputOnFocus(false);

        binding.button0.setOnClickListener(this);
        binding.button1.setOnClickListener(this);
        binding.button2.setOnClickListener(this);
        binding.button3.setOnClickListener(this);
        binding.button4.setOnClickListener(this);
        binding.button5.setOnClickListener(this);
        binding.button6.setOnClickListener(this);
        binding.button7.setOnClickListener(this);
        binding.button8.setOnClickListener(this);
        binding.button9.setOnClickListener(this);
        binding.buttonDEL.setOnClickListener(this);
        binding.buttonDot.setOnClickListener(this);
        binding.buttonAC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.totalAmountField.setText("");
                binding.percentageField.setText("");
                binding.result.setText("");
            }
        });

        binding.calculationMethod.setOnClickListener(view -> {
            if (method == ADDITION) {
                method = SUBTRACTION;
                binding.calculationMethod.setText("Subtraction");
            } else {
                method = ADDITION;
                binding.calculationMethod.setText("Addition");
            }
            showInterstitialAdIfAvailable();
        });

        binding.buttonEnter.setOnClickListener(view -> {
            if (binding.totalAmountField.getText() != null && !binding.totalAmountField.getText().toString().isEmpty()) {
                if (binding.percentageField.getText() != null && !binding.percentageField.getText().toString().isEmpty()) {
                    double amount = Double.parseDouble(binding.totalAmountField.getText().toString());
                    double percentage = Double.parseDouble(binding.percentageField.getText().toString());
                    if (method == ADDITION) {
                        binding.result.setText(String.valueOf(addPercentageToAmount(amount, percentage)));
                    } else {
                        binding.result.setText(String.valueOf(subtractPercentageFromAmount(amount, percentage)));
                    }
                } else {
                    binding.percentageField.setError("Required field");
                }
            } else {
                binding.totalAmountLayout.setError("Required field");
            }
            showInterstitialAdIfAvailable();
        });

        binding.buttonDEL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (binding.totalAmountField.hasFocus()) {
                    if (binding.totalAmountField.getText() != null) {
                        binding.totalAmountField.setText(removeLastChar(binding.totalAmountField.getText().toString()));
                        binding.totalAmountField.setSelection(binding.totalAmountField.getText().length());
                    }
                } else if (binding.percentageField.hasFocus()) {
                    if (binding.percentageField.getText() != null) {
                        binding.percentageField.setText(removeLastChar(binding.percentageField.getText().toString()));
                        binding.percentageField.setSelection(binding.percentageField.getText().length());
                    }
                }
            }
        });
    }

    private void showInterstitialAdIfAvailable() {
        clickCountAd++;
//        if (mInterstitialAd != null && clickCountAd >= 3) {
//            mInterstitialAd.show(MainActivity.this);
//            clickCountAd = 0;
//        }

    }

    private String removeLastChar(String str) {
        return removeChars(str, 1);
    }

    private String removeChars(String str, int numberOfCharactersToRemove) {
        if (str != null && !str.trim().isEmpty()) {
            return str.substring(0, str.length() - numberOfCharactersToRemove);
        }
        return "";
    }

    private double addPercentageToAmount(double amount, double percentage) {
        double increase = amount * (percentage / 100);
        return amount + increase;
    }

    public static double subtractPercentageFromAmount(double amount, double percentage) {
        double decrease = amount * (percentage / 100);
        return amount - decrease;
    }

    @Override
    public void onClick(View view) {
        if (view instanceof Button) {
            String text = ((TextView) view).getText().toString();
            if (isInteger(text) || text.equalsIgnoreCase(".")) {
                if (binding.totalAmountField.hasFocus()) {
                    if (binding.totalAmountField.getText() != null && !binding.totalAmountField.getText().toString().isEmpty()) {
                        binding.totalAmountField.setText(binding.totalAmountField.getText().append(text));
                    } else {
                        if (text.equalsIgnoreCase(".")) {
                            binding.totalAmountField.setText("0" + text);
                        } else {
                            binding.totalAmountField.setText(text);
                        }
                    }

                    binding.totalAmountField.setSelection(binding.totalAmountField.getText().length());

                } else if (binding.percentageField.hasFocus()) {
                    if (binding.percentageField.getText() != null && !binding.percentageField.getText().toString().isEmpty()) {
                        binding.percentageField.setText(binding.percentageField.getText().append(text));
                    } else {
                        if (text.equalsIgnoreCase(".")) {
                            binding.percentageField.setText("0" + text);
                        } else {
                            binding.percentageField.setText(text);
                        }


                    }
                    binding.percentageField.setSelection(binding.percentageField.getText().length());
                }
            } else if (text.equalsIgnoreCase("")) {

            }
        }
    }

    public static boolean isInteger(String str) {
        if (str == null) {
            return false;
        }
        int length = str.length();
        if (length == 0) {
            return false;
        }
        int i = 0;
        if (str.charAt(0) == '-') {
            if (length == 1) {
                return false;
            }
            i = 1;
        }
        for (; i < length; i++) {
            char c = str.charAt(i);
            if (c < '0' || c > '9') {
                return false;
            }
        }
        return true;
    }
}



