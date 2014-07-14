package co.touchlab.testintent;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;
import java.util.TimeZone;


public class MyActivity extends Activity {

    private Spinner timeZoneSpinner;
    private EditText firstName;
    private EditText lastName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);

        timeZoneSpinner = (Spinner) findViewById(R.id.timeZone);
        firstName = (EditText) findViewById(R.id.first);
        lastName = (EditText) findViewById(R.id.last);

        //This should probably be in a background thread, but this is just a demo
        loadData();
    }

    private void loadData()
    {
        String userName = GetTheStuff.findUserName(this);

        String[] parts = TextUtils.split(userName, " ");

        //If exactly 2 parts, split.  Otherwise, don't.  Should just be a "display name" field instead of first/last.
        if(parts.length == 2)
        {
            firstName.setText(parts[0]);
            lastName.setText(parts[1]);
        }

        TimeZone timeZone = TimeZone.getDefault();
        String[] availableTZIds = TimeZone.getAvailableIDs();
        List<TimeZoneSpinnerItem> items = new ArrayList<TimeZoneSpinnerItem>();

        int count = 0;
        int selection = 0;
        for (String availableTZId : availableTZIds)
        {
            items.add(new TimeZoneSpinnerItem(availableTZId, TimeZone.getTimeZone(availableTZId).getDisplayName()));

            if(availableTZId.equals(timeZone.getID()))
            {
                selection = count;
            }
            count++;
        }

        ArrayAdapter<TimeZoneSpinnerItem> adapter = new ArrayAdapter<TimeZoneSpinnerItem>(this, android.R.layout.simple_spinner_item, items);
        timeZoneSpinner.setAdapter(adapter);
        timeZoneSpinner.setSelection(selection);

    }

    public static class TimeZoneSpinnerItem
    {
        public String code;
        public String name;

        public TimeZoneSpinnerItem(String code, String name)
        {
            this.code = code;
            this.name = name;
        }

        @Override
        public String toString()
        {
            return name;
        }
    }

}
