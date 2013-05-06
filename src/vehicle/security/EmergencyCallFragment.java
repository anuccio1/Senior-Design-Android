package vehicle.security;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Vibrator;
import android.util.Log;

public class EmergencyCallFragment extends DialogFragment {
	
	@Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage("Alert the Police?");
              builder.setPositiveButton("Call", new DialogInterface.OnClickListener() {
                   public void onClick(DialogInterface dialog, int id) {
           		    try{
        		        Intent callIntent = new Intent(Intent.ACTION_CALL);
        		        callIntent.setData(Uri.parse("tel:123456789"));
        		        startActivity(callIntent);
        		    } catch (ActivityNotFoundException activityException) {
        		         Log.e("helloandroid dialing example", "Call failed");
        		    } 
                   }
               });
              
              builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                   public void onClick(DialogInterface dialog, int id) {
                       
                   }
               });
        // Create the AlertDialog object and return it
        return builder.create();
    }
    
    static EmergencyCallFragment newInstance() {
        return new EmergencyCallFragment();
    }
}
