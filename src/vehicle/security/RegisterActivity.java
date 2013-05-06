package vehicle.security;

import vehicle.database.DatabaseAdapterUser;
import vehicle.database.DatabaseHelperUser;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class RegisterActivity extends Activity {

	private EditText username;
	private EditText password;
	private EditText repassword;
	private DatabaseAdapterUser dbHelper;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		

		dbHelper = new DatabaseAdapterUser(this);
		dbHelper.open();
		setContentView(R.layout.registerlayout);
		
		Button register = (Button) findViewById(R.id.regbutton);
		
		username = (EditText) findViewById(R.id.reglogin);
		password = (EditText) findViewById(R.id.regpassword);
		repassword = (EditText) findViewById(R.id.repassword);



		register.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Register(v);
				
	}

	private void Register(View v) {
		String un = username.getText().toString();
		String pw = password.getText().toString();
		String repw = repassword.getText().toString();
		
		if (un.equals("") || pw.equals("")){  
			Toast.makeText(getApplicationContext(),   
					"Please ensure all fields have been completed.",  
					Toast.LENGTH_SHORT).show();  
			return;  
		}  

		//Check password match.   
		if (!pw.equals(repw)) {  
			Toast.makeText(getApplicationContext(),   
					"The password does not match.",  
					Toast.LENGTH_SHORT).show();  
			password.setText("");  
			repassword.setText("");  
			return;  
		}  
		
		Cursor curruser = dbHelper.fetchUser(un, pw);

		if (curruser == null) {  
			Toast.makeText(getApplicationContext(), "Database query error",  
					Toast.LENGTH_SHORT).show();  
		} else {  
			startManagingCursor(curruser);  

			//Check for duplicate usernames  
			if (curruser.getCount() > 0) {  
				Toast.makeText(getApplicationContext(), "The username is already registered!",  
						Toast.LENGTH_SHORT).show();  
				stopManagingCursor(curruser);  
				curruser.close();  
				return;  
			}  
			stopManagingCursor(curruser);  
			curruser.close();  
			curruser = dbHelper.fetchUser(un, pw);  
			if (curruser == null) {  
				Toast.makeText(getApplicationContext(), "Database query error",  
						Toast.LENGTH_SHORT).show();  
				return;  
			} else {  
				startManagingCursor(curruser);  

				if (curruser.moveToFirst()) { 
					Toast.makeText(getApplicationContext(), "The username is already registeredd",  
							Toast.LENGTH_SHORT).show();  
					stopManagingCursor(curruser);  
					curruser.close();  
					return;  
				}  
				stopManagingCursor(curruser);  
				curruser.close();  
			}  
			//Create the new username.  
			long id = dbHelper.createUser(un, pw);  
			if (id > 0) {  
				Toast.makeText(getApplicationContext(), "Your username was created",  
						Toast.LENGTH_SHORT).show();  

				Intent i = new Intent(v.getContext(), LoginActivity.class);  
				startActivity(i);  

				finish();  
			} else {  
				Toast.makeText(getApplicationContext(), "Failed to create new username",  
						Toast.LENGTH_SHORT).show();  
			}  
		}  

	}

});
		
	}
}
