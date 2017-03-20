package com.example.androiddevelopment.zadatakispit.activity;

import android.app.Dialog;
import android.app.NotificationManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.NotificationCompat;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.androiddevelopment.zadatakispit.R;
import com.example.androiddevelopment.zadatakispit.db.IspitORMLightHelper;
import com.example.androiddevelopment.zadatakispit.db.model.Kontakt;
import com.j256.ormlite.android.apptools.OpenHelperManager;

import java.sql.SQLException;
import java.util.List;

import static com.example.androiddevelopment.zadatakispit.activity.IspitDetailActivity.NOTIF_TOAST;
import static com.example.androiddevelopment.zadatakispit.activity.IspitDetailActivity.NOTIF_STATUS;

public class IspitDetailActivity extends AppCompatActivity {

    private IspitORMLightHelper databaseHelper;
    private SharedPreferences prefs;
    private Kontakt a;

    private EditText name;
    private EditText surname;
    private EditText address;
    private int broj_telefona;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        if(toolbar != null) {
            setSupportActionBar(toolbar);
        }

        prefs = PreferenceManager.getDefaultSharedPreferences(this);
        int key = getIntent().getExtras().getInt(IspitDetailActivity.KONTAKT_KEY);

        try {
            a = getDatabaseHelper().getKontaktDao().queryForId(key);

            name = (EditText) findViewById(R.id.kontakt_name);
            surname = (EditText) findViewById(R.id.kontakt_surname);
            address = (EditText) findViewById(R.id.kontakt_address);
            broj_telefona = (int) findViewById(R.id.kontakt_broj_telefona);

            name.setText(a.getmName());
            surname.setText(a.getmsurname());
            address.setText(a.getmaddress());
            broj_telefona.(a.getmbroj_telefona());
        } catch (SQLException e) {
            e.printStackTrace();
        }

        final ListView listView = (ListView) findViewById(R.id.ispit_kontakt_broj_telefona);

        try {
            List<Kontakt> list = getDatabaseHelper().getKontaktDao().queryBuilder()
                    .where()
                    .eq(Kontakt.FIELD_NAME_USER, a.getmId())
                    .query();

            ListAdapter adapter = new ArrayAdapter<>(this, R.layout.list_item, list);
            listView.setAdapter(adapter);

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Kontakt m = (Kontakt) listView.getItemAtPosition(position);
                }
            });

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void refresh() {
        ListView listview = (ListView) findViewById(R.id.ispit_kontak_broj_telefona);

        if (listview != null){
            ArrayAdapter<Kontakt> adapter = (ArrayAdapter<Kontakt>) listview.getAdapter();

            if(adapter!= null)
            {
                try {
                    adapter.clear();
                    List<Kontakt> list = getDatabaseHelper().getKontaktDao().queryBuilder()
                            .where()
                            .eq(Kontakt.FIELD_NAME_USER, a.getmId())
                            .query();

                    adapter.addAll(list);

                    adapter.notifyDataSetChanged();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void showStatusMesage(String message){
        NotificationManager mNotificationManager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this);
        mBuilder.setSmallIcon(R.drawable.ic_launcher);
        mBuilder.setContentTitle("zadatakIspit");
        mBuilder.setContentText(message);

        Bitmap bm = BitmapFactory.decodeResource(getResources(), R.drawable.ic_action_add);

        mBuilder.setLargeIcon(bm);
        // notificationID allows you to update the notification later on.
        mNotificationManager.notify(1, mBuilder.build());
    }

    private void showMessage(String message){
        //provera podesenja
        boolean toast = prefs.getBoolean(NOTIF_TOAST, false);
        boolean status = prefs.getBoolean(NOTIF_STATUS, false);

        if (toast){
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
        }

        if (status){
            showStatusMesage(message);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.detail_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.ispit_add_kontakt:

                final Dialog dialog = new Dialog(this);
                dialog.setContentView(R.layout.ispit_add_kontakt);

                Button add = (Button) dialog.findViewById(R.id.add_kontakt);
                add.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        EditText name = (EditText) dialog.findViewById(R.id.kontakt_name);
                        EditText surname = (EditText) dialog.findViewById(R.id.kontakt_surname);
                        EditText address = (EditText) dialog.findViewById(R.id.kontakt_address);

                        Kontakt m = new Kontakt();
                        m.setmName(name.getText().toString());
                        m.setmSurname(surname.getText().toString());
                        m.setmAddress(address.getText().toString());
                        m.setmKontakt(a);

                        try {
                            getDatabaseHelper().getKontaktDao().create(m);
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }

                        refresh();

                        showMessage("New kontakt added");

                        dialog.dismiss();
                    }
                });

                dialog.show();

                break;
            case R.id.ispit_edit:

                a.setmName(name.getText().toString());
                a.setmSurname(surname.getText().toString());
                a.setmAddress(address.getText().toString());

                try {
                    getDatabaseHelper().getKontaktDao().update(a);

                    showMessage("Kontakt detail updated");

                } catch (SQLException e) {
                    e.printStackTrace();
                }

                break;
            case R.id.ispit_remove:
                try {
                    getDatabaseHelper().getKontaktDao().delete(a);

                    showMessage("Kontakt deleted");

                    finish();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                break;
        }

        return super.onOptionsItemSelected(item);
    }


    public IspitDetailActivityORMLightHelper getDatabaseHelper() {
        if (databaseHelper == null) {
            databaseHelper = OpenHelperManager.getHelper(this, IspitDetailActivityORMLightHelper.class);
        }
        return databaseHelper;
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();


        if (databaseHelper != null) {
            OpenHelperManager.releaseHelper();
            databaseHelper = null;
        }
    }
}
