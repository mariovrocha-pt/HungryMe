package pt.ismai.hungryme.ui;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.SwitchCompat;
import android.view.MenuItem;
import android.widget.CompoundButton;

import pt.ismai.hungryme.R;
import pt.ismai.hungryme.ui.UI.BaseActivity;

import java.util.Calendar;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

public class SettingsActivity extends BaseActivity implements CompoundButton.OnCheckedChangeListener {

    SwitchCompat switchCompatRed, switchCompatGreen, switchCompatBlue;
    private TextView tvDisplayTime,tvDisplayTime2;
    private TimePicker timePicker1, timePicker2;
    private Button btnChangeTime, btnChangeTime2;
    private int hour, hour2;
    private int minute, minute2;
    public static final String PREFS = "userPrefs";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        Utils.onActivityCreateSetTheme(this);
        setContentView(R.layout.activity_settings);
        setupToolbar();

        switchCompatRed = (SwitchCompat) findViewById(R.id.switch_red);
        switchCompatRed.setSwitchPadding(40);
        switchCompatRed.setOnCheckedChangeListener(this);

        switchCompatGreen = (SwitchCompat) findViewById(R.id.switch_green);
        switchCompatGreen.setSwitchPadding(40);
        switchCompatGreen.setOnCheckedChangeListener(this);

        switchCompatBlue = (SwitchCompat) findViewById(R.id.switch_blue);
        switchCompatBlue.setSwitchPadding(40);
        switchCompatBlue.setOnCheckedChangeListener(this);

        setCurrentTimeOnView();
        addListenerOnButton();

        setCurrentTimeOnView2();
        addListenerOnButton2();
    }

    public void setCurrentTimeOnView2(){
        tvDisplayTime2 = (TextView) findViewById(R.id.tvTime2);
        timePicker2 = (TimePicker) findViewById(R.id.timePickerdinner);

        final Calendar c = Calendar.getInstance();
        hour2 = c.get(Calendar.HOUR_OF_DAY);
        minute2 = c.get(Calendar.MINUTE);

        tvDisplayTime2.setText(new StringBuilder().append(pad(hour2)).append(":").append(pad(minute2)));

        SharedPreferences prefs = getPreferences(MODE_PRIVATE);

        timePicker2.setIs24HourView(true);
        timePicker2.setCurrentHour(prefs.getInt("hour2", 1));
        timePicker2.setCurrentMinute(prefs.getInt("minute2", 01));
    }

    public void addListenerOnButton2(){
        btnChangeTime2 = (Button) findViewById(R.id.buttondinner);
        btnChangeTime2.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences.Editor editor = getPreferences(MODE_PRIVATE).edit();
                editor.putInt("hour2", timePicker2.getCurrentHour());
                editor.putInt("minute2", timePicker2.getCurrentMinute());
                editor.apply();
                Toast.makeText(getBaseContext(), "Submitted Dinner Time", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void setCurrentTimeOnView() {
        tvDisplayTime = (TextView) findViewById(R.id.tvTime);
        timePicker1 = (TimePicker) findViewById(R.id.timePickerlunch);
        final Calendar c = Calendar.getInstance();
        hour = c.get(Calendar.HOUR_OF_DAY);
        minute = c.get(Calendar.MINUTE);

        tvDisplayTime.setText(new StringBuilder().append(pad(hour)).append(":").append(pad(minute)));

        SharedPreferences prefs = getPreferences(MODE_PRIVATE);
        timePicker1.setIs24HourView(true);
        timePicker1.setCurrentHour(prefs.getInt("hour", 1));
        timePicker1.setCurrentMinute(prefs.getInt("minute", 01));
    }

    public void addListenerOnButton() {
        btnChangeTime = (Button) findViewById(R.id.buttonlunch);
        btnChangeTime.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = getPreferences(MODE_PRIVATE).edit();
                editor.putInt("hour", timePicker1.getCurrentHour());
                editor.putInt("minute", timePicker1.getCurrentMinute());
                editor.commit();
                Toast.makeText(getBaseContext(), "Submitted Lunch Time", Toast.LENGTH_SHORT).show();

                Calendar calendar = Calendar.getInstance();
                calendar.set(calendar.HOUR_OF_DAY,getPreferences(MODE_PRIVATE).getInt("hour", 1));
                calendar.set(calendar.MINUTE,getPreferences(MODE_PRIVATE).getInt("minute", 1));

                Intent intent = new Intent(getApplicationContext(),NotificationReceiver.class);
                PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(),100,intent,PendingIntent.FLAG_UPDATE_CURRENT);

                AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
                alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(),AlarmManager.INTERVAL_DAY,pendingIntent);
            }
        });
    }

    private static String pad(int c) {
        if (c >= 10) {
            return String.valueOf(c);
        }
        else {
            return "0" + String.valueOf(c);
        }
    }

    private void setupToolbar() {
        final ActionBar ab = getActionBarToolbar();
        ab.setHomeAsUpIndicator(R.drawable.ic_menu);
        ab.setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                openDrawer();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected int getSelfNavDrawerItem() {
        return R.id.nav_settings;
    }

    @Override
    public boolean providesActivityToolbar() {
        return true;
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        switch (buttonView.getId()) {
            case R.id.switch_red:
                Utils.changeToTheme(this, Utils.THEME_RED);
                break;

            case R.id.switch_green:
                Utils.changeToTheme(this, Utils.THEME_GREEN);
                break;

            case R.id.switch_blue:
                Utils.changeToTheme(this, Utils.THEME_BLUE);
                break;

        }
    }
}
