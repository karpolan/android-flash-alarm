package com.karpolan.android.flashalarm;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
public class MainActivity extends AppCompatActivity {
    private AudioPlayer audioPlayer;
    private FlashController flashController;

    private ImageView ivSwitch; // "Switch" button
    private Boolean isSwitchOn; // State for "Switch" button

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        audioPlayer = new AudioPlayer();
        flashController = new FlashController(getApplicationContext());

        // Initialize "Switch" button
        ivSwitch = (ImageView) findViewById(R.id.imageView);
        isSwitchOn = false;
        ivSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    if (isSwitchOn) {
                        turnOff();
                        isSwitchOn = false;
                    } else {
                        turnOn();
                        isSwitchOn = true;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        // Show warning message if there is no  Flash Light
        if (!flashController.flashExists) {
            AlertDialog alert = new AlertDialog.Builder(MainActivity.this).create();
            alert.setTitle(getString(R.string.app_name));
            alert.setMessage(getString(R.string.msg_no_flash));
            alert.setButton(DialogInterface.BUTTON_POSITIVE, getString(R.string.btn_ok), new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    // finish();
                }
            });
            alert.show();
        }
    }

    @Override
    protected void onDestroy() {
        turnOff(); // Turn off the Alarm sound and the Flash light
        super.onDestroy();
    }

    /**
     * Turns ON the Flash and start paying the Sound
     */
    public void turnOn() {
        ivSwitch.setImageResource(R.drawable.btn_off); // Green button
        audioPlayer.play(MainActivity.this, R.raw.alarm, true);
        flashController.turnOn();
    }

    /**
     * Turns OFF the Flash and stop paying the Sound
     */
    public void turnOff() {
        ivSwitch.setImageResource(R.drawable.btn_on); // Red button
        audioPlayer.stop();
        flashController.turnOff();
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (isSwitchOn) {
            turnOff();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (isSwitchOn) {
            turnOff();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (isSwitchOn) {
            turnOn();
        }
    }

}
