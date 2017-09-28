package a10593699.uvu.edu.cb_project7;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import database.ClientDBSchema.ClientTable;

public class DBHelper extends SQLiteOpenHelper {
    private static final int VERSION = 1;
    private static final String DATABASE_NAME = "mydatabase.db";

    public DBHelper(Context context){
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db){
        db.execSQL("create table " + ClientTable.NAME + "(" +
                ClientTable.Columns.ID + " INTEGER, " +
                ClientTable.Columns.C_NAME + " TEXT, " +
                ClientTable.Columns.PHONE + " TEXT, " +
                ClientTable.Columns.APT_DATE + " TEXT, " +
                ClientTable.Columns.TYPE + " TEXT, " +
                ClientTable.Columns.LIST + " TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
        db.execSQL("DROP TABLE IF EXISTS " + ClientTable.NAME);
        onCreate(db);
    }

    public static void initialize(SQLiteDatabase db, singClient stuff){
        final int entries = 15;
        String[] sqlSequence = new String[] {
                "INSERT INTO " + ClientTable.NAME + " VALUES (" + stuff.getID() + ",'John Doe','801-484-4844','04/15/2017','Meeting','April')",
                "INSERT INTO " + ClientTable.NAME + " VALUES (" + stuff.getID() + ",'Paul Jackson','801-225-7789','04/21/2017','Meeting','April')",
                "INSERT INTO " + ClientTable.NAME + " VALUES (" + stuff.getID() + ",'Ron Douglas','385-484-4866','04/28/2017','Lunch','April')",
                "INSERT INTO " + ClientTable.NAME + " VALUES (" + stuff.getID() + ",'Brian Burningham','801-224-7779','04/15/2017','Meeting','April')",
                "INSERT INTO " + ClientTable.NAME + " VALUES (" + stuff.getID() + ",'Bob Howard','224-456-4114','04/13/2018','Dinner','April')",
                "INSERT INTO " + ClientTable.NAME + " VALUES (" + stuff.getID() + ",'Ryan Vincent','801-207-9954','04/11/2017','Meeting','April')",
                "INSERT INTO " + ClientTable.NAME + " VALUES (" + stuff.getID() + ",'Nathan Brookes','801-385-9111','04/23/2017','Meeting','April')",
                "INSERT INTO " + ClientTable.NAME + " VALUES (" + stuff.getID() + ",'Lacy Mitchell','877-889-1123','04/05/2017','Meeting','April')",
                "INSERT INTO " + ClientTable.NAME + " VALUES (" + stuff.getID() + ",'Jane Doe','801-484-4845','04/15/2017','Meeting','April')",
                "INSERT INTO " + ClientTable.NAME + " VALUES (" + stuff.getID() + ",'Morgan Smith','699-458-8111','04/20/2019','Lunch','April')",
                "INSERT INTO " + ClientTable.NAME + " VALUES (" + stuff.getID() + ",'Brent Chase','801-785-5564','04/30/2017','Meeting','April')",
                "INSERT INTO " + ClientTable.NAME + " VALUES (" + stuff.getID() + ",'Jessica Carlson','485-592-8312','04/19/2018','Meeting','April')",
                "INSERT INTO " + ClientTable.NAME + " VALUES (" + stuff.getID() + ",'Holly Barry','801-442-6611','04/05/2017','Meeting','April')",
                "INSERT INTO " + ClientTable.NAME + " VALUES (" + stuff.getID() + ",'Hannah Wilkinson','679-552-9963','04/30/2017','Meeting','April')",
                "INSERT INTO " + ClientTable.NAME + " VALUES (" + stuff.getID() + ",'Brittney Orton','119-592-9753','05/10/2017','Meeting','May')",
                "INSERT INTO " + ClientTable.NAME + " VALUES (" + stuff.getID() + ",'Tyler Warden','336-782-9633','03/01/2018','Dinner','March')",
                "INSERT INTO " + ClientTable.NAME + " VALUES (" + stuff.getID() + ",'Justin Bateman','369-778-1364','04/11/2017','Dinner','April')"
        };

        for(int i = 0; i < entries; i++){
            db.execSQL(sqlSequence[i]);
        }

        stuff.addListName(0, "April");
        stuff.addListName(1, "May");
        stuff.addListName(2, "March");
        stuff.addListNumber();
        stuff.addListNumber();
        stuff.addListNumber();
    }
}
