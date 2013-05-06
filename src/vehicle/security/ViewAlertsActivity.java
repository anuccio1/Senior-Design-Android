package vehicle.security;


import vehicle.database.DatabaseAdapterAlerts;
import vehicle.database.DatabaseAdapterVehicle;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;


public class ViewAlertsActivity extends Activity {
    /** Called when the activity is first created. */
	
	private TextView title;
	private DatabaseAdapterAlerts dbHelperA;
	private ListView list;
	private String vehicle;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.alerthistory);
        title = (TextView) findViewById(R.id.alertList);
        list =  (ListView) findViewById(R.id.alertlistview);
        list.bringToFront();
        Intent i = getIntent();
        vehicle = i.getStringExtra("veh");
        title.setText("Alerts for Vehicle "+vehicle);
        
        dbHelperA = new DatabaseAdapterAlerts(this);
        dbHelperA.open();
        fillList();

       
    }

	private void fillList() {
		try {
	        Cursor cursor = dbHelperA.fetchAllAlerts(vehicle);
	        startManagingCursor(cursor);
	        String[] fields = new String[]{DatabaseAdapterAlerts.COL_ALERTTIME};
	        int[] to = new int[]{android.R.id.text1};
	        
	        SimpleCursorAdapter adapter = new SimpleCursorAdapter(this,
	                android.R.layout.simple_list_item_1, cursor, fields,to);
	        list.setAdapter(adapter);
			 }catch(NullPointerException e){Toast.makeText(getApplicationContext(), e.toString(),  
						Toast.LENGTH_SHORT).show(); }
		
	}
	
	public void onResume() {
		 super.onResume();
		 
	 }
	
	
}