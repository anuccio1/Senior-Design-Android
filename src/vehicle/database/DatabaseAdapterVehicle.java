package vehicle.database;

 import android.content.ContentValues;  
 import android.content.Context;  
 import android.database.Cursor;  
 import android.database.SQLException;  
import android.database.sqlite.SQLiteDatabase;  

 public class DatabaseAdapterVehicle {  
      //Table name  
      private static final String LOGIN_TABLE = "vehicle3";  
      //Table unique id  
      public static final String COL_ID = "id";  
      //Table username and vehicle columns   
      public static final String COL_USERNAME = "username";
      public static final String COL_VEHICLE = "vehicle";
      public static final String COL_PHONE = "phone";
   
      private Context context;  
      private SQLiteDatabase database;  
      private DatabaseHelperVehicle dbHelper;  
   
      public DatabaseAdapterVehicle(Context context) {  
           this.context = context;  
      }  
   

      public DatabaseAdapterVehicle open() throws SQLException {  
           dbHelper = new DatabaseHelperVehicle(context);  
           database = dbHelper.getWritableDatabase();  
           return this;  
      }  

      public void close() {  
           dbHelper.close();  
      }  
   
      public long createVehicle(String username, String vehicle, String phone) {  
           ContentValues initialValues = createVehicleTableContentValues(username,vehicle, phone);  
           return database.insert(LOGIN_TABLE, null, initialValues);  
      }  
   
      public boolean deleteVehicle(long rowId) {  
           return database.delete(LOGIN_TABLE, COL_ID + "=" + rowId, null) > 0;  
      }  
   
      public boolean updateVehicleTable(long rowId, String username, String vehicle, String phone) {  
           ContentValues updateValues = createVehicleTableContentValues(username, vehicle, phone);  
           return database.update(LOGIN_TABLE, updateValues, COL_ID + "=" + rowId, null) > 0;  
      }  

      public Cursor fetchAllVehicles(String usern) {  
    	  String selectQuery = "SELECT "+ LOGIN_TABLE +".*, vehicle as _id FROM " + LOGIN_TABLE + " WHERE username = '"+usern+"'";
          return database.rawQuery(selectQuery,null);
      }  
      
      public Cursor fetchAllVehiclesByPhone(String phone) {  
    	  String selectQuery = "SELECT "+ LOGIN_TABLE +".*, vehicle as _id FROM " + LOGIN_TABLE + " WHERE phone = '"+phone+"'";
          return database.rawQuery(selectQuery,null);
      }  

      public Cursor fetchVehicle(String username, String vehicle, String phone) {  

           Cursor myCursor = database.query(LOGIN_TABLE,   
                   new String[] { COL_ID, COL_USERNAME, COL_VEHICLE, COL_PHONE },   
                   COL_USERNAME + "='" + username + "' AND " +   
                   COL_VEHICLE + "='" + vehicle + "' AND " +   
                           COL_PHONE + "='" + phone + "'", null, null, null, null);  
           
           if (myCursor != null) {  
                myCursor.moveToFirst();  
           }  
           return myCursor;  
      }  
      
      public Cursor fetchVehicle(String phone) {  

          Cursor myCursor = database.query(LOGIN_TABLE,   
                  new String[] { COL_ID, COL_PHONE },      
                          COL_PHONE + "='" + phone + "'", null, null, null, null);  
          
          if (myCursor != null) {  
               myCursor.moveToFirst();  
          }  
          return myCursor;  
     }  
   
   
      private ContentValues createVehicleTableContentValues(String username, String vehicle, String phone) {  
           ContentValues values = new ContentValues();  
           values.put(COL_USERNAME, username);
           values.put(COL_VEHICLE, vehicle);
           values.put(COL_PHONE, phone);
           return values;  
      }  
 }  