package at.sw2017.calculator;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.IntegerRes;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class Calculator extends Activity implements View.OnClickListener {


    private TextView numberView;
    private ArrayList<Button> numberButtons = new ArrayList<>();

    public enum State {
        ADD , SUB , MUL , DIV , INIT , NUM
    }

    State state = State.INIT;
    int firstNumber = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculator);
        printToast("Started application");
        initButtons();
        setUpNumberButtonListener();
    }

    @Override
    public void onClick(View v) {
        Button clickedButton = (Button) v;

        switch (clickedButton.getId()) {
            case R.id.buttonPlus:
                clearNumberView();
                state = State.ADD;
                break;
            case R.id.buttonMinus:
                clearNumberView();
                state = State.SUB;
                break;
            case R.id.buttonMulti:
                clearNumberView();
                state = State.MUL;
                break;
            case R.id.buttonDiv:
                clearNumberView();
                state = State.DIV;
                break;
            case R.id.buttonEqual:
                calculateResult();
                break;
            case R.id.buttonClear:
                clearTextView();
                break;
            default:
                String recentNumber = numberView.getText().toString();
                if(state == State.INIT) {
                    recentNumber = "";
                    state = State.NUM;
                }
                recentNumber += clickedButton.getText().toString();
                numberView.setText(recentNumber);
        }
    }

    public void setUpNumberButtonListener () {
        for (int i = 0; i <= 9; i++) {
            String buttonName = "button" + i;
            int id = getResources().getIdentifier(buttonName, "id",
                    R.class.getPackage().getName());
            Button button = (Button) findViewById(id);
            button.setOnClickListener(this);
            numberButtons.add(button);
        }
    }

    private void initButtons(){
        Button btnPlus, btnMinus, btnMulti, btnDiv, btnClear, btnEqual;

        numberView = (TextView) findViewById(R.id.numberView);

        btnPlus = (Button) findViewById(R.id.buttonPlus);
        btnPlus.setOnClickListener(Calculator.this);

        btnMinus = (Button) findViewById(R.id.buttonMinus);
        btnMinus.setOnClickListener(Calculator.this);

        btnMulti = (Button) findViewById(R.id.buttonMulti);
        btnMulti.setOnClickListener(Calculator.this);

        btnDiv = (Button) findViewById(R.id.buttonDiv);
        btnDiv.setOnClickListener(Calculator.this);

        btnClear = (Button) findViewById(R.id.buttonClear);
        btnClear.setOnClickListener(Calculator.this);

        btnEqual = (Button) findViewById(R.id.buttonEqual);
        btnEqual.setOnClickListener(Calculator.this);

    }

    private void clearTextView() {
        numberView.setText("0");
        firstNumber = 0;
        state = State.INIT;
    }

    private void calculateResult(){
        int secondNumber = 0;

        String tempString = numberView.getText().toString();
        if(!tempString.equals("")){
            secondNumber = Integer.valueOf(tempString);
        }

        int result;
        switch (state){
            case ADD:
                result = Calculations.doAddition(firstNumber, secondNumber);
                break;
            case SUB:
                result = Calculations.doSubstraction(firstNumber, secondNumber);
                break;
            case MUL:
                result = Calculations.doMultiplication(firstNumber, secondNumber);
                break;
            case DIV:
                result = Calculations.doDivision(firstNumber, secondNumber);
                break;
            default:
                result = secondNumber;
        }

        numberView.setText(Integer.toString(result));
    }

    private void clearNumberView(){
        String tempString = numberView.getText().toString();
        if(!tempString.equals("")){
            firstNumber = Integer.valueOf(tempString);
        }
        numberView.setText("");
    }

    private void printToast(String msg){
        Context context = getApplicationContext();
        CharSequence text = msg;
        int duration = Toast.LENGTH_SHORT;
        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
    }

}
