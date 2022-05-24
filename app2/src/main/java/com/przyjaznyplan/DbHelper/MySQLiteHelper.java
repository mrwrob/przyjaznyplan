/*
 * Copyright (c) 2016. Wydział Elektroniki, Telekomunikacji i Informatyki, Politechnika Gdańska
 * This program is free software; you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation; either version 3 of the License, or   (at your option) any later version.
 *
 * Copy of GNU General Public License is available at http://www.gnu.org/licenses/gpl-3.0.html
 */
package com.przyjaznyplan.DbHelper;

import static android.os.Environment.DIRECTORY_DOWNLOADS;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;
import android.util.Log;


import com.przyjaznyplan.factories.FactoryDataBaseSQL;
import com.przyjaznyplan.factories.InsertSqlFactory;

//Funckje do tworzenia, upgrade bazy i insertow fejkowych danych
public class MySQLiteHelper  {


    private static final String DATABASE_NAME =   "commments2.db";

    private static final int DATABASE_VERSION = 70;
    private static SQLiteDatabase db;
    private static Context context = null;

    public static synchronized SQLiteDatabase getDb(){
        boolean isAvailable= false;
        boolean isWritable= false;
        boolean isReadable= false;
        String state = Environment.getExternalStorageState();

        if(Environment.MEDIA_MOUNTED.equals(state)) {
            // Operation possible - Read and Write
            isAvailable= true;
            isWritable= true;
            isReadable= true;
        } else if (Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
            // Operation possible - Read Only
            isAvailable= true;
            isWritable= false;
            isReadable= true;
        } else {
            // SD card not available
            isAvailable = false;
            isWritable= false;
            isReadable= false;
        }
        if(db==null){
            try {
//                db = SQLiteDatabase.openOrCreateDatabase(DATABASE_NAME, null);
//                db = SQLiteDatabase.openOrCreateDatabase(Environment.getExternalStorageDirectory().toString().concat("/").concat(DATABASE_NAME), null);
//                String dir = context.getApplicationInfo().dataDir;
                String dir = Environment.getExternalStoragePublicDirectory(DIRECTORY_DOWNLOADS).toString();
                db = SQLiteDatabase.openOrCreateDatabase(dir.concat("/").concat(DATABASE_NAME), null);
                boolean b =db.isOpen();
                int v = db.getVersion();
                if(db.getVersion()!=DATABASE_VERSION){
                    onUpgrade(db,db.getVersion(),DATABASE_VERSION);
                    insertInitValues(db);
                    db.setVersion(DATABASE_VERSION);
                }
            }catch (Exception e){
                System.out.println(e.getMessage());
            }
                return db;
        }else{
            return db;
        }
    }

    public MySQLiteHelper(Context context) {
        this.context = context;
    }

    //@Override
    public void onCreate(SQLiteDatabase database) {

            new FactoryDataBaseSQL().DropDatabaseSql(database);
            new FactoryDataBaseSQL().CreateDatabaseSQL(database);
            insertInitValues(database);
            this.db = database;

    }

    //@Override
    public static synchronized void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(MySQLiteHelper.class.getName(),
                "Upgrading database from version " + oldVersion + " to "
                        + newVersion + ", which will destroy all old data"
        );

        new FactoryDataBaseSQL().DropDatabaseSql(db);
        new FactoryDataBaseSQL().CreateDatabaseSQL(db);
    }



    public static synchronized  void insertInitValues(SQLiteDatabase database) {



        new InsertSqlFactory().execInsert(database);

        //przyklad aktywnosci o schemie csv
        /*CsvConverter csv = new CsvConverter(database);
        try {
             csv.open("IWRD_AKTYWNOSCI/CSV ACTIVITY/schemat.csv");
            csv.CreateSlides();
        } catch (Exception e) {
            Log.i("CSV_CONVERTER EXCEPTION", e.getMessage());
        }
*/

    }

}
