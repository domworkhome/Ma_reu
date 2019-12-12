package com.lamzone.mareu.controllers.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.lamzone.mareu.R;
import com.lamzone.mareu.views.dialogs.FilterDialog;
import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements FilterDialog.filterByRoom {

    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setSupportActionBar(mToolbar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_activity_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.filter_room: {
                filterByRoomButton();
                return true;
            }
            case R.id.filter_date: {
                //filterByDate;
                Toast.makeText(this, "Filtre par date", Toast.LENGTH_SHORT).show();
                return true;
            }
            case R.id.no_filter: {
                Toast.makeText(this, "Pas de filtre", Toast.LENGTH_SHORT).show();
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    private void filterByRoomButton(){
        FilterDialog filterDialog = new FilterDialog();
        filterDialog.show(getSupportFragmentManager().beginTransaction(),"addmeetingdialog");
    }

    @Override
    public void onRoomFilterButtonClick() {

    }
}