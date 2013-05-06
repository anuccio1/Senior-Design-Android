package vehicle.database;

 import android.content.ContentValues;  
 import android.content.Context;  
 import android.database.Cursor;  
 import android.database.SQLException;  
import android.database.sqlite.SQLiteDatabase;  

 public class DatabaseAdapterAlerts {  
      //Table name  
      private static final String LOGIN_TABLE = "alert3";  
      //Table unique id  
      public static final String COL_ID = "id";  
      //Table username and password columns   
      public static final String COL_VEHICLE = "vehicle";
      public static final String COL_ALERTTIME = "alert";
   
      private Context context;  
      private SQLiteDatabase database;  
      private DatabaseHelperAlerts dbHelper;  
   
      public DatabaseAdapterAlerts(Context context) {  
           this.context = context;  
      }  
   

      public DatabaseAdapterAlerts open() throws SQLException {  
           dbHelper = new DatabaseHelperAlerts(context);  
           database = dbHelper.getWritableDatabase();  
           return this;  
      }  

      public void close() {  
           dbHelper.close();  
      }  
   
      public long createAlert( String vehicle,String alert) {  
           ContentValues initialValues = createAlertTableContentValues(vehicle, alert);  
           return database.insert(LOGIN_TABLE, null, initialValues);  
      }  
   
      public boolean deleteAlert(long rowId) {  
           return database.delete(LOGIN_TABLE, COL_ID + "=" + rowId, null) > 0;  
      }  
   
      public boolean updateAlertTable(long rowId, String vehicle, String alert) {  
           ContentValues updateValues = createAlertTableContentValues(vehicle, alert);  
           return database.update(LOGIN_TABLE, updateValues, COL_ID + "=" + rowId, null) > 0;  
      }  

      public Cursor fetchAllAlerts(String vehicle) {  
    	  String selectQuery = "SELECT "+ LOGIN_TABLE +".*, alert as _id FROM " + LOGIN_TABLE + " WHERE vehicle = '"+vehicle+"'";
          return database.rawQuery(selectQuery,null);  

      }  

      public Cursor fetchAlert(String vehicle, String alert) {  
           Cursor myCursor = database.query(LOGIN_TABLE,   
                     new String[] { COL_ID, COL_VEHICLE, COL_ALERTTIME },   
                     COL_VEHICLE + "='" + vehicle + "' AND " + COL_ALERTTIME + "='" + alert + "'", null, null, null, null);  
   
           if (myCursor != null) {  
                myCursor.moveToFirst();  
           }  
           return myCursor;  
      }  
      
      public Cursor fetchAlert(String vehicle) {  
          Cursor myCursor = database.query(LOGIN_TABLE,   
                    new String[] { COL_ID, COL_VEHICLE },   
                    COL_VEHICLE + "='" + vehicle + "'", null, null, null, null);  
  
          if (myCursor != null) {  
               myCursor.moveToFirst();  
          }  
          return myCursor;  
     }  
   
   
      private ContentValues createAlertTableContentValues(String vehicle, String alert) {  
           ContentValues values = new ContentValues();  
           values.put(COL_VEHICLE, vehicle);
           values.put(COL_ALERTTIME, alert);
           return values;  
      }  
 }  