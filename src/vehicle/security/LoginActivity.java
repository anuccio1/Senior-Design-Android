package vehicle.security;

import vehicle.database.DatabaseAdapterAlerts;
import vehicle.database.DatabaseAdapterUser;
import vehicle.database.DatabaseAdapterVehicle;
import android.app.Activity;
import android.app.DialogFragment;
import android.app.FragmentTransaction;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Vibrator;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends Activity{

	private DatabaseAdapterUser dbHelper;
	private DatabaseAdapterAlerts dbHelperA;
	private EditText username;
	private EditText password;
	private Intent logintent; 
	private Intent regintent;
	private Vibrator vib;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
			if(dbHelper==null){
			dbHelper = new DatabaseAdapterUser(this);
			}
			dbHelper.open();
		

		setContentView(R.layout.loginview);

		Button log = (Button) findViewById(R.id.loggbutton);
		Button reg = (Button) findViewById(R.id.newregbutton);

		username = (EditText) findViewById(R.id.loglogin);
		password = (EditText) findViewById(R.id.logpassword);

		logintent = new Intent(this, MainScreenActivity.class);
		regintent = new Intent(this, RegisterActivity.class);


		Intent callPol = this.getIntent();
		if (callPol.hasExtra("Emergency")) {	

			try {
				String v = callPol.getStringExtra("vehicle");
				String timea = callPol.getStringExtra("time");
				vib = (Vibrator) getSystemService(LoginActivity.VIBRATOR_SERVICE);
				//dbHelper.close();
		        dbHelperA = new DatabaseAdapterAlerts(this);
		        dbHelperA.open();
		        dbHelperA.createAlert(v, timea);
				vib.vibrate(2000);
				DialogFragment newFragment = EmergencyCallFragment.newInstance();
				newFragment.show(getFragmentManager(), "dialog");

			}catch (Exception e) {	}
		}



		log.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Login(v);
				//startActivity(logintent);

			}
		});

		reg.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {

				startActivity(regintent);

			}
		});

	}

	private void Login(View v) {
		String un = username.getText().toString();
		String pw = password.getText().toString();

		Cursor curruser = dbHelper.fetchUser(un, pw);
		if (curruser.moveToFirst()) {

			if (curruser.getCount() > 0) {
				curruser.close();
				//Toast.makeText(getApplicationContext(), "TEST2", Toast.LENGTH_SHORT).show();
				logintent.putExtra("username", un);
				startActivity(logintent);
			}

		}
		else {
			Toast.makeText(getApplicationContext(), "Database Error", Toast.LENGTH_SHORT).show();
			return;
		}
	}







}
