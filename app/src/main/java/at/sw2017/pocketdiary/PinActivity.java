package at.sw2017.pocketdiary;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by marku on 13.04.2017.
 */

public class PinActivity extends Activity implements View.OnClickListener{

    Button login_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pin);
        login_button = (Button) findViewById(R.id.loginButton);
        login_button.setOnClickListener(PinActivity.this);
    }

    @Override
    public void onClick(View v) {
        DBUserSettings db_usersettings = new DBUserSettings(this);
        Cursor results = db_usersettings.getData();
        if(results.getCount() == 0) {
            log("No data in Pin Activity onClick.");
        }

        String pin_num = "";
        while(results.moveToNext()){
            pin_num += results.getString(3);
        }

        EditText pin_input = (EditText) findViewById(R.id.passwordField);
        String pin_input_value = pin_input.getText().toString();

        if(pin_input_value.equals(pin_num)){
            log("Pins match.");
            Intent i = new Intent(PinActivity.this, OverviewActivity.class);
            startActivity(i);
        }
        else {
            printToast("Pin is wrong. Try again.");
        }

    }

    public void printToast(String msg){
        Context context = getApplicationContext();
        CharSequence text = msg;
        int duration = Toast.LENGTH_SHORT;

        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
    }

    public void log(String msg) {
        Log.d("Verbose", msg);
    }
}
