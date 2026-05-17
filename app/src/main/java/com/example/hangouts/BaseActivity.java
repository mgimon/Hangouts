package com.example.hangouts;

import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;
import androidx.core.content.ContextCompat;

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

        SharedPreferences prefs = getSharedPreferences("settings", MODE_PRIVATE);
        int color = prefs.getInt("actionbar_color", -1);

        if (color != -1 && actionBar != null) {
            actionBar.setBackgroundDrawable(new ColorDrawable(color));
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.action_change_color) {

            SharedPreferences prefs = getSharedPreferences("settings", MODE_PRIVATE);
            SharedPreferences.Editor editor = prefs.edit();


            if (actionBar != null) {

                new androidx.appcompat.app.AlertDialog.Builder(BaseActivity.this)
                        .setTitle(R.string.header)
                        .setSingleChoiceItems(new String[]{getString(R.string.light), getString(R.string.dark)}, -1, (dialog, which) -> {
                            if (which == 0) {
                                int color = ContextCompat.getColor(this, R.color.colorSecondary);
                                actionBar.setBackgroundDrawable(new ColorDrawable(color));
                                editor.putInt("actionbar_color", color);
                            } else if (which == 1) {
                                int color = ContextCompat.getColor(this, R.color.colorPrimary);
                                actionBar.setBackgroundDrawable(new ColorDrawable(color));
                                editor.putInt("actionbar_color", color);
                            }
                            editor.apply();
                        })
                        .setNegativeButton(R.string.save, (dialog, which) -> {
                            dialog.dismiss();
                        })
                        .show();

            }

            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    // handle back button in action bar
    @Override
    public boolean onSupportNavigateUp() {
        finish(); // close Activity
        return true;
    }
}
