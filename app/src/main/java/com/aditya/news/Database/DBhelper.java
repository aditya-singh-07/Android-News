package com.aditya.news.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DBhelper extends SQLiteOpenHelper {
    final static String DBNAME = "news.db";
    final static int DBVERSION = 1;
    static SQLiteDatabase sqllitedatabase;
    String CREATE_TABLE = "CREATE TABLE News(id integer primary key autoincrement," +
            " title text not null," +
            "headline text not null," +
            "time text not null," +
            "image text not null," +
            "url text not null)";

    public DBhelper(@Nullable Context context) {
        super(context, DBNAME, null, DBVERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
//        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP table if exists News");
        onCreate(db);
    }

//    public ArrayList<OrderModels> getorders(){
//        SQLiteDatabase database=this.getWritableDatabase();
//        ArrayList<OrderModels> orderlist=new ArrayList<OrderModels>();
//        String order_sql="SELECT id,f_name,f_image,f_price FROM Foods";
//        Cursor cursor=database.rawQuery(order_sql,null);
//        if(cursor.moveToNext()){
//            while(cursor.moveToNext()){
//                OrderModels order=new OrderModels();
//                order.setOrdernumber(cursor.getInt(0) + "");
//                order.setOrdername(cursor.getString(1));
//                order.setOrderimage(cursor.getInt(2));
//                order.setPrice(cursor.getInt(3) + "");
//                orderlist.add(order);
//            }
//        }
//        cursor.close();
//        database.close();
//        return  orderlist;
//    }


}
