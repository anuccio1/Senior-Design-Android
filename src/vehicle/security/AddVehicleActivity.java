package vehicle.security;

import vehicle.database.DatabaseAdapterAlerts;
import vehicle.database.DatabaseAdapterUser;
import vehicle.database.DatabaseAdapterVehicle;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AddVehicleActivity extends Activity {

	private EditText vehicle;
	private String username;
	private String vehiclename;
	private DatabaseAdapterVehicle dbHelper;
	private EditText vname;
	private EditText phone;
	private String alert;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		Intent i = getIntent();
		username = i.getStringExtra("un");
		alert = Long.toString(System.nanoTime());

		dbHelper = new DatabaseAdapterVehicle(this);
		dbHelper.open();
		setContentView(R.layout.newvehicle);

		Button addvehicle = (Button) findViewById(R.id.regIDbutton);
		vname = (EditText) findViewById(R.id.vname);
		phone = (EditText) findViewById(R.id.phonenum);

		addvehicle.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {

				addv(v);

			}
		});


	}

	private void addv(View v) {
		String vehic = vname.getText().toString();
		String pn = phone.getText().toString();

		Cursor curruser = dbHelper.fetchVehicle(username, vehic, pn );


		curruser = dbHelper.fetchVehicle(username, vehic,pn);  
		if (curruser == null) {  
			Toast.makeText(getApplicationContext(), "Database query error",  
					Toast.LENGTH_SHORT).show();  
			return;  
		} else {  
			startManagingCursor(curruser);  

			if (curruser.getCount() > 0) {  
				Toast.makeText(getApplicationContext(), "The vehicle is already registered!",  
						Toast.LENGTH_SHORT).show();  
				stopManagingCursor(curruser);  
				curruser.close();  
				return;  
			}  
			stopManagingCursor(curruser);  
			curruser.close(); 
			curruser = dbHelper.fetchVehicle(username, vehic,pn);  

			if (curruser == null) {  
				Toast.makeText(getApplicationContext(), "Database query error",  
						Toast.LENGTH_SHORT).show();  
				return;  
			} else {  
				startManagingCursor(curruser);  

				if (curruser.moveToFirst()) {
					Toast.makeText(getApplicationContext(), "The vehicle is already registeredd",  
							Toast.LENGTH_SHORT).show();  
					stopManagingCursor(curruser);  
					curruser.close();  
					return;  
				}  
				stopManagingCursor(curruser);  
				curruser.close();  
			} 
			//Create the new username.  
			long id = dbHelper.createVehicle(username, vehic,pn);  
			if (id > 0) {  
				Toast.makeText(getApplicationContext(), "Your vehicle was created",  
						Toast.LENGTH_SHORT).show();  

				Intent i = new Intent(v.getContext(), MainScreenActivity.class); 
				i.putExtra("username", username);
				startActivity(i);  

				finish();  
			} else {  
				Toast.makeText(getApplicationContext(), "Failed to create new vehicle " + id,  
						Toast.LENGTH_SHORT).show();  
			}  
		}
	}   




}
