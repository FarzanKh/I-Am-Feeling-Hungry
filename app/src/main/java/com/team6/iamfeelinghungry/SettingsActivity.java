package com.team6.iamfeelinghungry;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.Switch;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.google.firebase.auth.FirebaseAuth;

public class SettingsActivity extends AppCompatActivity {

    private Button logout;

    public static final String NOTIFICATION_CHANNEL_ID = "10001" ;
    private final static String default_notification_channel_id = "default" ;


    // Radio Button Boolean Values
    public static boolean rad1 = false;
    public static boolean rad2 = false;
    public static boolean rad3 = false;

    //Cancel Notifications
    public static boolean notifCheck = true;

    public static AlarmManager alarmManager = null;
    public static PendingIntent pendingIntent = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



        // remove bar
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();

        setContentView(R.layout.activity_settings);

        logout=findViewById(R.id.logout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent intent=new Intent(SettingsActivity.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        Button btn = findViewById(R.id.button);

        // Radio Button Values
        RadioButton radBtn1 = findViewById(R.id.radia_id1);
        RadioButton radBtn2 = findViewById(R.id.radia_id2);
        RadioButton radBtn3 = findViewById(R.id.radia_id3);

        //Handles Switch Toggle to keep Notification Events On or Off
        Switch switchBtn = (Switch) findViewById(R.id.switch1);

        switchBtn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    // The toggle is enabled, Disable Radio Buttons
                    radBtn1.setClickable(false);
                    radBtn2.setClickable(false);
                    radBtn3.setClickable(false);
                    btn.setClickable(false);
                    switchBtn.setText("Notification Status: Disabled");
                    notifCheck = false;

                    if (!notifCheck && alarmManager != null && pendingIntent != null) {
                        alarmManager.cancel(pendingIntent);
                    }

                } else {
                    // The toggle is disabled, Enable Radio Buttons
                    radBtn1.setClickable(true);
                    radBtn2.setClickable(true);
                    radBtn3.setClickable(true);
                    btn.setClickable(true);
                    switchBtn.setText("Notification Status: Enabled");
                    notifCheck = true;

                }
            }
        });

    }


    public void onRadioButtonClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.radia_id1:
                if (checked)
                    rad1 = true;
                    break;
            case R.id.radia_id2:
                if (checked)
                    rad2 = true;
                    break;
            case R.id.radia_id3:
                if (checked)
                    rad3 = true;
                    break;
            default:
                rad1 = false;
                rad2 = false;
                rad3 = false;
                break;
        }
    }

    public void sendToast(CharSequence text) {
        Context context = getApplicationContext();
        int duration = Toast.LENGTH_SHORT;
        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
    }


    /** Called when the user touches the save button */
    public void saveNotification(View view)
    {
        // Do something in response to button click
        if(rad1) {
            sendToast("Notification set for Every 10 secs!");
            scheduleNotification(getNotification( "Every 10 secs" ) , 5000 );
        } else if (rad2) {
            sendToast("Notification set for 3 Days a Week!");
            int num = 86400000 * 3;
            scheduleNotification(getNotification( "In 3 days" ) , num );
        } else if(rad3) {
            sendToast("Notification set for Once a Week!");
            int num = 86400000 * 7;
            scheduleNotification(getNotification( "In a week" ) , num );
        }
        rad1 = false;
        rad2 = false;
        rad3 = false;
    }


    private void scheduleNotification (Notification notification , int delay) {
        Intent notificationIntent = new Intent( this, NotificationPublisher. class ) ;
        notificationIntent.putExtra(NotificationPublisher.NOTIFICATION_ID , 1 ) ;
        notificationIntent.putExtra(NotificationPublisher.NOTIFICATION , notification) ;

        pendingIntent = PendingIntent. getBroadcast ( this, 0 , notificationIntent , PendingIntent.FLAG_UPDATE_CURRENT ) ;
        long futureInMillis = SystemClock.elapsedRealtime() + delay ;
        alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE ) ;
        assert alarmManager != null;
        alarmManager.setInexactRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP, futureInMillis, delay, pendingIntent); ;
    }

    private Notification getNotification (String content) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder( this, default_notification_channel_id ) ;
        builder.setContentTitle( "Scheduled Notification: " + content ) ;
        builder.setContentText("Please complete your quiz" ) ;
        builder.setSmallIcon(R.drawable.hungry ) ;
        builder.setAutoCancel( true ) ;
        builder.setChannelId( NOTIFICATION_CHANNEL_ID ) ;
        return builder.build();
    }


}