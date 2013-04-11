package vehicle.security;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class MainScreenActivity extends Activity{
	TextView title;
	
	 public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.homescreen);
	        title = (TextView) findViewById(R.id.login_message);
	        Intent i = getIntent();
	        String usern = i.getStringExtra("username");
	        title.setText("Welcome "+usern);
	 }
	 
	 public void onResume() {
		 super.onResume();
		 
	 }
}
