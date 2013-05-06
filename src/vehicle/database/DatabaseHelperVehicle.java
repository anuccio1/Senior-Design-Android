package vehicle.database;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseHelperVehicle extends SQLiteOpenHelper {
	// The database name and version
	private static final String DB_NAME = "vehicle3";
	private static final int DB_VERSION = 2;
	// The database user table
	private static final String DB_TABLE = "create table vehicle3 (id integer primary key autoincrement, "
											+ "username text not null,vehicle text not null, phone text not null);";
	
	public DatabaseHelperVehicle(Context context) {
		super(context, DB_NAME, null, DB_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase database) {
		database.execSQL(DB_TABLE);
	}
	
	@Override
	public void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion) {
		Log.w(DatabaseHelperVehicle.class.getName(),
				"Upgrading databse from version" + oldVersion + "to " 
				+ newVersion + ", which will destroy all old data");
		database.execSQL("DROP TABLE IF EXISTS user");
		onCreate(database);
	}
}
