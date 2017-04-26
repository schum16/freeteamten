package at.sw2017.pocketdiary;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static android.content.Context.MODE_PRIVATE;


/**
 * Created by marku on 13.04.2017.
 */

public class DBUserSettings extends SQLiteOpenHelper{

    SQLiteDatabase db = this.getWritableDatabase();

    public DBUserSettings(Context context) {
        super(context, "pocketdiary.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }

    public void insert(String username, String picture, int pin_number, int is_pin_active){
        ContentValues content_val = new ContentValues();
        content_val.put("USERNAME", username);
        content_val.put("PICTURE", picture);
        content_val.put("PIN_VALUE", pin_number);
        content_val.put("IS_PIN_ACTIVE", is_pin_active);
        db.insert("USER_SETTINGS", null, content_val);
    }

    public Cursor getData(){
        Cursor results = db.rawQuery("SELECT * FROM USER_SETTINGS", null);
        return results;
    }
}
