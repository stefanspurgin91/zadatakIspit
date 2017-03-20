package com.example.androiddevelopment.zadatakispit.activity;

import android.app.Dialog;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
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
import com.example.androiddevelopment.zadatakispit.dilog.AboutDialog;
import com.j256.ormlite.android.apptools.OpenHelperManager;

import java.util.List;

public class IspitListActivity extends AppCompatActivity {

    private IspitORMLightHelper databaseHelper;
    private SharedPreferences prefs;

    public static String KONTAKT_KEY = "KONTAKT_KEY";
    public static String NOTIF_TOAST = "notif_toast";
    public static String NOTIF_STATUS = "notif_statis";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        if(toolbar != null) {
            setSupportActionBar(toolbar);
        }

        prefs = PreferenceManager.getDefaultSharedPreferences(this);

        final ListView listView = (ListView) findViewById(R.id.ispit_kontakt_list);

        try {
            List<Kontakt> list = getDatabaseHelper().getKontaktDao().queryForAll();

            ListAdapter adapter = new ArrayAdapter<>(IspitListActivity.this, R.layout.list_item, list);
            listView.setAdapter(adapter);

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                   Kontakt p = (Kontakt) listView.getItemAtPosition(position);

                    Intent intent = new Intent(IspitListActivity.this, IspitDetailActivity.class);
                    intent.putExtra(KONTAKT_KEY, p.getmId());
                    startActivity(intent);
                }
            });

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.list_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    protected void onResume() {
        super.onResume();

        refresh();
    }

    private void refresh() {
        ListView listview = (ListView) findViewById(R.id.ispit_kontakt_list);

        if (listview != null){
            ArrayAdapter<Kontakt> adapter = (ArrayAdapter<Kontakt>) listview.getAdapter();

            if(adapter!= null)
            {
                try {
                    adapter.clear();
                    List<Kontakt> list = getDatabaseHelper().getKontaktDao().queryForAll();

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

        mNotificationManager.notify(1, mBuilder.build());
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case R.id.ispit_add_new_kontakt:

                final Dialog dialog = new Dialog(this);
                dialog.setContentView(R.layout.ispit_add_kontakt_layout);

                Button add = (Button) dialog.findViewById(R.id.add_kontakt);
                add.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        EditText name = (EditText) dialog.findViewById(R.id.kontakt_name);
                        EditText surname = (EditText) dialog.findViewById(R.id.kontakt_biography);
                        EditText address = (EditText) dialog.findViewById(R.id.kontakt_address;
                        EditText broj_telefona = (EditText) dialog.findViewById(R.id.kontakt_broj_telefona);

                        Kontakt a = new Kontakt();
                        a.setmName(name.getText().toString());
                        a.setmSurname(surname.getText().toString());
                        a.setmAddress(address.getText().toString());
                        a.setmbroj_telefona(broj_telefona.toString());

                        try {
                            getDatabaseHelper().getKontaktDao().create(a);


                            boolean toast = prefs.getBoolean(NOTIF_TOAST, false);
                            boolean status = prefs.getBoolean(NOTIF_STATUS, false);

                            if (toast){
                                Toast.makeText(IspitListActivity.this, "Added new kontakt", Toast.LENGTH_SHORT).show();
                            }

                            if (status){
                                showStatusMesage("Added new kontakt");
                            }


                            refresh();

                        } catch (SQLException e) {
                            e.printStackTrace();
                        }

                        dialog.dismiss();

                    }
                });

                dialog.show();

                break;
            case R.id.ispit_about:

                AlertDialog alertDialog = new AboutDialog(this).prepareDialog();
                alertDialog.show();
                break;
            case R.id.ispit_preferences:
                startActivity(new Intent(IspitListActivity.this, IspitListActivityPrefererences.class));
                break;
        }

        return super.onOptionsItemSelected(item);
    }


    public IspitORMLightHelper getDatabaseHelper() {
        if (databaseHelper == null) {
            databaseHelper = OpenHelperManager.getHelper(this, IspitORMLightHelper.class);
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