package at.sw2017.pocketdiary;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import java.io.File;

public class MainActivity extends AppCompatActivity {

    DatabaseHelper pd_db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        checkAndDelIfDBexists();
        pd_db = new DatabaseHelper(this);
        DBUserSettings db_usersettings = new DBUserSettings(this);
        db_usersettings.insert("Markus", "Bild", 1234, 1);
        checkIfPinIsActive(db_usersettings);

    }

    public void checkAndDelIfDBexists(){
        File dbtest =new File("/data/data/at.sw2017.pocketdiary/databases/pocketdiary.db");
        File dbtestjr =new File("/data/data/at.sw2017.pocketdiary/databases/pocketdiary.db-journal");
        if(dbtest.exists())
        {
            printToast("db exists");
            dbtest.delete();
            dbtestjr.delete();
        }
        else
        {
            printToast("db doesnt exist");
        }
    }

    public void printToast(String msg){
        Context context = getApplicationContext();
        CharSequence text = msg;
        int duration = Toast.LENGTH_SHORT;

        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
    }

    public void checkIfPinIsActive(DBUserSettings db_usersettings){
        Cursor results = db_usersettings.getData();
        if(results.getCount() == 0){
            printToast("No data availabe");
        }
        String buffer = "";
        while(results.moveToNext()){
            buffer += results.getString(4);
        }
        if(buffer.equals("1")){
            Intent i = new Intent(MainActivity.this, PinActivity.class);
            startActivity(i);
        }
        else if(buffer.equals("0")){
            Intent i = new Intent(MainActivity.this, OverviewActivity.class);
            startActivity(i);
        }
    }
}
