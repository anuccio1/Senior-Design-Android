package vehicle.security;

import vehicle.database.DatabaseAdapterUser;
import vehicle.database.DatabaseAdapterVehicle;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class MainScreenActivity extends Activity{
	private TextView title;
	private Button addbutton;
	private Button viewcarbutton;
	private Intent addintent;
	private Intent alertintent;
	private Spinner carspinner;
	private DatabaseAdapterVehicle dbHelperV;
	private String usern;
	
	 public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.homescreen);
	        
	        
	        title = (TextView) findViewById(R.id.login_message);
	        addbutton = (Button) findViewById(R.id.addbutton);
	        viewcarbutton = (Button) findViewById(R.id.viewcarbutton);
	        carspinner = (Spinner) findViewById(R.id.carSpinner);
	       
	        
	        addintent = new Intent(this, AddVehicleActivity.class);
	        alertintent = new Intent(this, ViewAlertsActivity.class);
	        
	        Intent i = getIntent();
	        usern = i.getStringExtra("username");
	        addintent.putExtra("un", usern);
	        title.setText("Welcome "+usern);
	        
	        dbHelperV = new DatabaseAdapterVehicle(this);
	        dbHelperV.open();
	        FillCarSpinner();
	        
			addbutton.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					startActivity(addintent);

				}
			});
			
			viewcarbutton.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					TextView textView = (TextView)carspinner.getSelectedView();
					String vehic = textView.getText().toString();
					alertintent.putExtra("veh", vehic);
					dbHelperV.close();
					startActivity(alertintent);

				}
			});
	        
	        
	 }
	 
	 private void FillCarSpinner(){
		 
		 try {
		 Cursor c = dbHelperV.fetchAllVehicles(usern);
		 
		 startManagingCursor(c);
		  
		 // create an array to specify which fields we want to display
		 String[] from = new String[]{DatabaseAdapterVehicle.COL_VEHICLE};
		 // create an array of the display item we want to bind our data to
		 int[] to = new int[]{android.R.id.text1};
		 // create simple cursor adapter
		 SimpleCursorAdapter adapter =
		   new SimpleCursorAdapter(this, android.R.layout.simple_spinner_item, c, from, to );
		 adapter.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item );
		 // get reference to our spinner
		 
		 
		 carspinner.setAdapter(adapter);	
		 }catch(NullPointerException e){Toast.makeText(getApplicationContext(), e.toString(),  
					Toast.LENGTH_SHORT).show(); }
		 }

	public void onResume() {
		 super.onResume();
		 dbHelperV.open();
		 
	 }
}
