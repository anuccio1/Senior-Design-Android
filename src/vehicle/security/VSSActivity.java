package vehicle.security;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;


public class VSSActivity extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }
}