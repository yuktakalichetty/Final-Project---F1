// =====================================================
// FORMULA 1 STATS APP
// PART 2 - SQLITE DATABASE + BASIC UI STRUCTURE
// Android Studio Java Version
// =====================================================


// =====================================================
// DATABASE HELPER
// Handles saving and loading data using SQLite
// =====================================================

package com.example.f1statsapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class DatabaseHelper extends SQLiteOpenHelper {

// Database Info
private static final String DATABASE_NAME = "F1Stats.db";
private static final int DATABASE_VERSION = 1;

// Driver Table
private static final String TABLE_DRIVERS = "drivers";

private static final String DRIVER_ID = "id";
private static final String DRIVER_NAME = "name";
private static final String DRIVER_TEAM = "team";

// Race Table
private static final String TABLE_RACES = "races";

private static final String RACE_ID = "id";
private static final String RACE_NAME = "race_name";
private static final String RACE_DATE = "race_date";
private static final String DRIVER_REF = "driver_name";

// Lap Table
private static final String TABLE_LAPS = "laps";

private static final String LAP_ID = "id";
private static final String LAP_NUMBER = "lap_number";
private static final String LAP_TIME = "lap_time";
private static final String TIRE_TYPE = "tire_type";
private static final String RACE_REF = "race_id";


public DatabaseHelper(Context context) {
super(context, DATABASE_NAME, null, DATABASE_VERSION);
}

// =================================================
// CREATE TABLES
// =================================================

@Override
public void onCreate(SQLiteDatabase db) {

String createDriversTable =
"CREATE TABLE " + TABLE_DRIVERS + "("
+ DRIVER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
+ DRIVER_NAME + " TEXT,"
+ DRIVER_TEAM + " TEXT"
+ ")";

String createRacesTable =
"CREATE TABLE " + TABLE_RACES + "("
+ RACE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
+ RACE_NAME + " TEXT,"
+ RACE_DATE + " TEXT,"
+ DRIVER_REF + " TEXT"
+ ")";

String createLapsTable =
"CREATE TABLE " + TABLE_LAPS + "("
+ LAP_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
+ LAP_NUMBER + " INTEGER,"
+ LAP_TIME + " REAL,"
+ TIRE_TYPE + " TEXT,"
+ RACE_REF + " INTEGER"
+ ")";

db.execSQL(createDriversTable);
db.execSQL(createRacesTable);
db.execSQL(createLapsTable);
}

// =================================================
// UPGRADE DATABASE
// =================================================

@Override
public void onUpgrade(SQLiteDatabase db,
int oldVersion,
int newVersion) {

db.execSQL("DROP TABLE IF EXISTS " + TABLE_DRIVERS);
db.execSQL("DROP TABLE IF EXISTS " + TABLE_RACES);
db.execSQL("DROP TABLE IF EXISTS " + TABLE_LAPS);

onCreate(db);
}

// =================================================
// ADD DRIVER
// =================================================

public void addDriver(String name, String team) {

SQLiteDatabase db = this.getWritableDatabase();

ContentValues values = new ContentValues();

values.put(DRIVER_NAME, name);
values.put(DRIVER_TEAM, team);

db.insert(TABLE_DRIVERS, null, values);

db.close();
}

// =================================================
// GET ALL DRIVERS
// =================================================

public ArrayList<String> getDrivers() {

ArrayList<String> drivers = new ArrayList<>();

SQLiteDatabase db = this.getReadableDatabase();

Cursor cursor =
db.rawQuery("SELECT * FROM " + TABLE_DRIVERS,
null);

if (cursor.moveToFirst()) {

do {

String driver =
cursor.getString(1) + " - "
+ cursor.getString(2);

drivers.add(driver);

} while (cursor.moveToNext());
}

cursor.close();

return drivers;
}

// =================================================
// ADD RACE
// =================================================

public long addRace(String raceName,
String date,
String driverName) {

SQLiteDatabase db = this.getWritableDatabase();

ContentValues values = new ContentValues();

values.put(RACE_NAME, raceName);
values.put(RACE_DATE, date);
values.put(DRIVER_REF, driverName);

long raceId =
db.insert(TABLE_RACES, null, values);

db.close();

return raceId;
}

// =================================================
// ADD LAP
// =================================================

public void addLap(int lapNumber,
double lapTime,
String tireType,
long raceId) {

SQLiteDatabase db = this.getWritableDatabase();

ContentValues values = new ContentValues();

values.put(LAP_NUMBER, lapNumber);
values.put(LAP_TIME, lapTime);
values.put(TIRE_TYPE, tireType);
values.put(RACE_REF, raceId);

db.insert(TABLE_LAPS, null, values);

db.close();
}
}



// =====================================================
// MAIN ACTIVITY
// Home Screen
// =====================================================

package com.example.f1statsapp;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

EditText nameInput;
EditText teamInput;

Button addButton;

ListView driverList;

DatabaseHelper db;

ArrayAdapter<String> adapter;

ArrayList<String> drivers;

@Override
protected void onCreate(Bundle savedInstanceState) {

super.onCreate(savedInstanceState);

setContentView(R.layout.activity_main);

// Connect UI
nameInput = findViewById(R.id.nameInput);
teamInput = findViewById(R.id.teamInput);

addButton = findViewById(R.id.addButton);

driverList = findViewById(R.id.driverList);

db = new DatabaseHelper(this);

// Load existing drivers
loadDrivers();

// Add Button
addButton.setOnClickListener(view -> {

String name =
nameInput.getText().toString();

String team =
teamInput.getText().toString();

if (!name.isEmpty() &&
!team.isEmpty()) {

db.addDriver(name, team);

loadDrivers();

nameInput.setText("");
teamInput.setText("");
}
});
}

// ============================================
// LOAD DRIVERS INTO LISTVIEW
// ============================================

private void loadDrivers() {

drivers = db.getDrivers();

adapter = new ArrayAdapter<>(
this,
android.R.layout.simple_list_item_1,
drivers
);

driverList.setAdapter(adapter);
}
}
