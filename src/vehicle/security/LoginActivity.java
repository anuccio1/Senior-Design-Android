package vehicle.security;

import vehicle.database.DatabaseAdapter;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends Activity{

	private DatabaseAdapter dbHelper;
	private EditText username;
	private EditText password;
	private Intent logintent; 
	private Intent regintent;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		dbHelper = new DatabaseAdapter(this);  
		dbHelper.open();
		setContentView(R.layout.loginview);

		Button log = (Button) findViewById(R.id.loggbutton);
		Button reg = (Button) findViewById(R.id.newregbutton);

		username = (EditText) findViewById(R.id.loglogin);
		password = (EditText) findViewById(R.id.logpassword);

		logintent = new Intent(this, MainScreenActivity.class);
		regintent = new Intent(this, RegisterActivity.class);

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
