package com.example.hangouts;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;

public class BaseActivity extends AppCompatActivity {

    private SwitchCompat switchColor;
    private ActionBar actionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // init action bar
        actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle(R.string.contact);
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowHomeEnabled(true);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.header_menu, menu);

        MenuItem item = menu.findItem(R.id.toggle_color);

        View view = item.getActionView();

        switchColor = view.findViewById(R.id.switchColor);

        switchColor.setOnCheckedChangeListener((buttonView, isChecked) -> {

            if (actionBar == null)
                return;

            if (isChecked)
                actionBar.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.colorPrimary, null)));
            else
                actionBar.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.colorSecondary, null)));

        });

        return true;
    }

    // handle back button in action bar
    @Override
    public boolean onSupportNavigateUp() {
        finish(); // close Activity
        return true;
    }
}
