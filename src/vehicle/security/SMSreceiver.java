package vehicle.security;

import vehicle.database.DatabaseAdapterAlerts;
import vehicle.database.DatabaseAdapterVehicle;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.telephony.SmsMessage;
import android.text.format.Time;
import android.view.Menu;
import android.widget.Toast;

public class SMSreceiver extends BroadcastReceiver {

	public static final String SMS_EXTRA_NAME = "pdus";
	private String FromNum = "";
	private DatabaseAdapterVehicle dbHelper;
	
	@Override
	public void onReceive(Context context, Intent intent) {
		Bundle extras = intent.getExtras();

		String messages = "";
        dbHelper = new DatabaseAdapterVehicle(context);
        dbHelper.open();
        
		if ( extras != null )
		{
			// Get received SMS array
			Object[] smsExtra = (Object[]) extras.get( SMS_EXTRA_NAME );

			for ( int i = 0; i < smsExtra.length; ++i )
			{
				SmsMessage sms = SmsMessage.createFromPdu((byte[])smsExtra[i]);

				FromNum = sms.getOriginatingAddress();
				messages = "Text From: " + FromNum;
			}

		}
		
		if (dbHelper.fetchVehicle(FromNum).moveToFirst()) {
			// Display SMS message
			Cursor c = dbHelper.fetchAllVehiclesByPhone(FromNum);
			c.moveToFirst();
			String car = c.getString(2);
			
			Toast.makeText( context, messages, Toast.LENGTH_SHORT ).show();
			Intent startIntent = new Intent(context, LoginActivity.class);
			startIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			startIntent.putExtra("Emergency", "true");
			startIntent.putExtra("vehicle", car);
			Time t = new Time();
			t.setToNow();
			startIntent.putExtra("time", t.toString());
			dbHelper.close();
			context.startActivity(startIntent);
		}
	}



}



