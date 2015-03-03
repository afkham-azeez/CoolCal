package lk.vega.calculator;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import java.text.DecimalFormat;

public class MainActivity extends Activity implements View.OnLongClickListener, View.OnClickListener {

    //TODO: image button with images changing on click
    //TODO: Make text field editable and make number pad/keypad appear ???
    //TODO: Popup settings

    private TextView mCalculatorDisplay;
    private Boolean userIsInTheMiddleOfTypingANumber = false;
    private CalculatorBrain mCalculatorBrain;
    private static final String DIGITS = "0123456789.";

    private DecimalFormat df = new DecimalFormat("@###########");

    @SuppressLint("NewApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        // hide the window title.
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        // hide the status bar and other OS-level chrome
//        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mCalculatorBrain = new CalculatorBrain(this);
        mCalculatorDisplay = (TextView) findViewById(R.id.textView1);

        df.setMinimumFractionDigits(0);
        df.setMinimumIntegerDigits(1);
        df.setMaximumIntegerDigits(8);

        setButtonConfigurations(findViewById(R.id.button0));
        setButtonConfigurations(findViewById(R.id.button1));
        setButtonConfigurations(findViewById(R.id.button2));
        setButtonConfigurations(findViewById(R.id.button3));
        setButtonConfigurations(findViewById(R.id.button4));
        setButtonConfigurations(findViewById(R.id.button5));
        setButtonConfigurations(findViewById(R.id.button6));
        setButtonConfigurations(findViewById(R.id.button7));
        setButtonConfigurations(findViewById(R.id.button8));
        setButtonConfigurations(findViewById(R.id.button9));

        setButtonConfigurations(findViewById(R.id.buttonAdd));
        setButtonConfigurations(findViewById(R.id.buttonSubtract));
        setButtonConfigurations(findViewById(R.id.buttonMultiply));
        setButtonConfigurations(findViewById(R.id.buttonDivide));
        setButtonConfigurations(findViewById(R.id.buttonToggleSign));
        setButtonConfigurations(findViewById(R.id.buttonDecimalPoint));
        setButtonConfigurations(findViewById(R.id.buttonEquals));
        setButtonConfigurations(findViewById(R.id.buttonClear));
        setButtonConfigurations(findViewById(R.id.buttonClearMemory));
        setButtonConfigurations(findViewById(R.id.buttonAddToMemory));
        setButtonConfigurations(findViewById(R.id.buttonSubtractFromMemory));
        setButtonConfigurations(findViewById(R.id.buttonRecallMemory));

        // The following buttons only exist in layout-land (Landscape mode) and require extra attention.
        // The messier option is to place the buttons in the regular layout too and set android:visibility="invisible".
        if (findViewById(R.id.buttonSquareRoot) != null) {
            setButtonConfigurations(findViewById(R.id.buttonSquareRoot));
        }
        if (findViewById(R.id.buttonSquared) != null) {
            setButtonConfigurations(findViewById(R.id.buttonSquared));
        }
        if (findViewById(R.id.buttonInvert) != null) {
            setButtonConfigurations(findViewById(R.id.buttonInvert));
        }
        if (findViewById(R.id.buttonSine) != null) {
            setButtonConfigurations(findViewById(R.id.buttonSine));
        }
        if (findViewById(R.id.buttonCosine) != null) {
            setButtonConfigurations(findViewById(R.id.buttonCosine));
        }
        if (findViewById(R.id.buttonTangent) != null) {
            setButtonConfigurations(findViewById(R.id.buttonTangent));
        }
    }

    private void setButtonConfigurations(View v){
        v.setOnLongClickListener(this);
        v.setBackgroundResource(R.drawable.button_style);
        v.setOnLongClickListener(this);
        v.setOnClickListener(this);
    }

    public void onClick(View v) {

        final Button btn = (Button) v;
        String buttonPressed = btn.getText().toString();

        if (DIGITS.contains(buttonPressed)) {

            // digit was pressed
            if (userIsInTheMiddleOfTypingANumber) {

                if (buttonPressed.equals(".") && mCalculatorDisplay.getText().toString().contains(".")) {
                    // ERROR PREVENTION
                    // Eliminate entering multiple decimals
                } else {
                    mCalculatorDisplay.append(buttonPressed);
                }

            } else {

                if (buttonPressed.equals(".")) {
                    // ERROR PREVENTION
                    // This will avoid error if only the decimal is hit before an operator, by placing a leading zero
                    // before the decimal
                    mCalculatorDisplay.setText(0 + buttonPressed);
                } else {
                    mCalculatorDisplay.setText(buttonPressed);
                }

                userIsInTheMiddleOfTypingANumber = true;
            }

        } else {
            // operation was pressed
            if (userIsInTheMiddleOfTypingANumber) {

                mCalculatorBrain.setOperand(Double.parseDouble(mCalculatorDisplay.getText().toString()));
                userIsInTheMiddleOfTypingANumber = false;
            }

            mCalculatorBrain.performOperation(buttonPressed);
            mCalculatorDisplay.setText(df.format(mCalculatorBrain.getResult()));
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        // Save variables on screen orientation change
        outState.putDouble("OPERAND", mCalculatorBrain.getResult());
        outState.putDouble("MEMORY", mCalculatorBrain.getMemory());
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        // Restore variables on screen orientation change
        mCalculatorBrain.setOperand(savedInstanceState.getDouble("OPERAND"));
        mCalculatorBrain.setMemory(savedInstanceState.getDouble("MEMORY"));
        mCalculatorDisplay.setText(df.format(mCalculatorBrain.getResult()));
    }

    @Override
    public boolean onLongClick(View v) {
        View view = findViewById(v.getId());
        if (view.isEnabled()) {
            view.setEnabled(false);
        } else {
            view.setEnabled(true);
        }
        ButtonDisabledNotification.notify(v.getContext(), "CoolCalc button disabled", 10);
        return true;
    }
}
