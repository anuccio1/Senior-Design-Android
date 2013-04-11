package vehicle.security;

import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.telephony.SmsMessage;
import android.view.Menu;
import android.widget.Toast;

public class SMSreceiver extends BroadcastReceiver {

	public static final String SMS_EXTRA_NAME = "pdus";
	private String FromNum = "";
	
	@Override
	public void onReceive(Context context, Intent intent) {
		Bundle extras = intent.getExtras();

		String messages = "";

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
		if (FromNum.equals("3193839800")) {
			// Display SMS message
			Toast.makeText( context, messages, Toast.LENGTH_SHORT ).show();
			Intent startIntent = new Intent(context, MainScreenActivity.class);
			startIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			context.startActivity(startIntent);
		}
	}



}



