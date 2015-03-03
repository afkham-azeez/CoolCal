package lk.vega.calculator;

/**
 * Created by azeez on 3/3/15.
 */
public class CalculatorBrain {

    // 3 + 6 = 9
    // 3 & 6 are called the operand.
    // The + is called the operator.
    // 9 is the result of the operation.
    private double mOperand;
    private double mWaitingOperand;
    private String mWaitingOperator;
    private double mCalculatorMemory;

    // operator types
    public final String add;
    private final String subtract;
    private final String multiply;
    private final String divide;

    private final String clear;
    private final String clearMemory;
    private final String addToMemory;
    private final String subtractFromMemory;
    private final String recallMemory;
    private final String squareRoot;
    private final String squared;
    private final String invert;
    private final String toggleSign;
    private final String sine;
    private final String cosine;
    private final String tangent;

    // private String EQUALS = "=";

    // constructor
    public CalculatorBrain(MainActivity mainActivity) {
        // initialize variables upon start
        mOperand = 0;
        mWaitingOperand = 0;
        mWaitingOperator = "";
        mCalculatorMemory = 0;

        add = mainActivity.getString(R.string.buttonAdd);
        subtract = mainActivity.getString(R.string.buttonSubtract);
        multiply = mainActivity.getString(R.string.buttonMultiply);
        divide = mainActivity.getString(R.string.buttonDivide);

        clear = mainActivity.getString(R.string.buttonClear);
        clearMemory = mainActivity.getString(R.string.buttonClearMemory);
        addToMemory = mainActivity.getString(R.string.buttonAddToMemory);
        subtractFromMemory = mainActivity.getString(R.string.buttonSubtractFromMemory);
        recallMemory = mainActivity.getString(R.string.buttonRecallMemory);
        squareRoot = mainActivity.getString(R.string.buttonSquareRoot);
        squared = mainActivity.getString(R.string.buttonSquared);
        invert = mainActivity.getString(R.string.buttonInvert);
        toggleSign = mainActivity.getString(R.string.buttonToggleSign);

        sine = mainActivity.getString(R.string.buttonSine);
        cosine = mainActivity.getString(R.string.buttonCosine);
        tangent = mainActivity.getString(R.string.buttonTangent);
    }

    public void setOperand(double operand) {
        mOperand = operand;
    }

    public double getResult() {
        return mOperand;
    }

    // used on screen orientation change
    public void setMemory(double calculatorMemory) {
        mCalculatorMemory = calculatorMemory;
    }

    // used on screen orientation change
    public double getMemory() {
        return mCalculatorMemory;
    }

    public String toString() {
        return Double.toString(mOperand);
    }

    protected double performOperation(String operator) {
        if (operator.equals(clear)) {
            mOperand = 0;
            mWaitingOperator = "";
            mWaitingOperand = 0;
            // mCalculatorMemory = 0;

        } else if (operator.equals(clearMemory)) {
            mCalculatorMemory = 0;

        } else if (operator.equals(addToMemory)) {
            mCalculatorMemory = mCalculatorMemory + mOperand;

        } else if (operator.equals(subtractFromMemory)) {
            mCalculatorMemory = mCalculatorMemory - mOperand;

        } else if (operator.equals(recallMemory)) {
            mOperand = mCalculatorMemory;

        } else if (operator.equals(squareRoot)) {
            mOperand = Math.sqrt(mOperand);

        } else if (operator.equals(squared)) {
            mOperand = mOperand * mOperand;

        } else if (operator.equals(invert)) {
            if (mOperand != 0) {
                mOperand = 1 / mOperand;
            }

        } else if (operator.equals(toggleSign)) {
            mOperand = -mOperand;

        } else if (operator.equals(sine)) {
            mOperand = Math.sin(Math.toRadians(mOperand)); // Math.toRadians(mOperand) converts result to degrees

        } else if (operator.equals(cosine)) {
            mOperand = Math.cos(Math.toRadians(mOperand)); // Math.toRadians(mOperand) converts result to degrees

        } else if (operator.equals(tangent)) {
            mOperand = Math.tan(Math.toRadians(mOperand)); // Math.toRadians(mOperand) converts result to degrees

        } else {
            performWaitingOperation();
            mWaitingOperator = operator;
            mWaitingOperand = mOperand;

        }

        return mOperand;
    }

    protected void performWaitingOperation() {
        if (mWaitingOperator.equals(add)) {
            mOperand = mWaitingOperand + mOperand;

        } else if (mWaitingOperator.equals(subtract)) {
            mOperand = mWaitingOperand - mOperand;

        } else if (mWaitingOperator.equals(multiply)) {
            mOperand = mWaitingOperand * mOperand;

        } else if (mWaitingOperator.equals(divide)) {
            if (mOperand != 0) {
                mOperand = mWaitingOperand / mOperand;
            }

        }
    }
}
