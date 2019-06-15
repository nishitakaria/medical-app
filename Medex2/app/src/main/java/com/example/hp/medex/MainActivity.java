package com.example.hp.medex;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.maps.GoogleMap;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    ImageView amb,panic,sos,maps,firstaid;
    SharedPreferences sf;
    public String result,number1,number2,number3;
    private GoogleMap mMap;
    private GoogleApiClient mGoogleApiClient;
    private Location mCurrentLocation;
    double latitude, longitude;
    private static final int REQUEST_PERMISSIONS = 100;
    private static final String PERMISSIONS_REQUIRED[] = new String[]{
            Manifest.permission.READ_PHONE_STATE,
            Manifest.permission.ACCESS_FINE_LOCATION
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        amb = findViewById(R.id.ambulance);
        amb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent callIntent1 = new Intent(MainActivity.this,ambbooking.class);
                startActivity(callIntent1);
            }
        });

        panic = findViewById(R.id.imageView2);
        panic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent callIntent1 = new Intent(Intent.ACTION_CALL);
                callIntent1.setData(Uri.parse("tel:112"));
                startActivity(callIntent1);
            }
        });

        sos = findViewById(R.id.sos);
        sos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sf=getSharedPreferences("numbers",MODE_PRIVATE);
                SharedPreferences.Editor editor = sf.edit();
                number1 = sf.getString("n1","");
                number2 = sf.getString("n2","");
                number3 = sf.getString("n3","");
                LocationManager locationManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);

                getSystemService(Context.LOCATION_SERVICE);
                mCurrentLocation=locationManager.getLastKnownLocation(locationManager.NETWORK_PROVIDER);
                latitude=mCurrentLocation.getLatitude();
                longitude=mCurrentLocation.getLongitude();

                Geocoder geocoder = new Geocoder(getApplicationContext(), Locale.getDefault());
                result = null;
                try {
                    List<Address> addressList = geocoder.getFromLocation(latitude, longitude, 1);
                    if (addressList != null && addressList.size() > 0) {
                        Address address = addressList.get(0);
                        StringBuilder sb = new StringBuilder();
                        for (int i = 0; i < address.getMaxAddressLineIndex(); i++) {
                            sb.append(address.getAddressLine(i)).append("\n");
                        }
                        sb.append(address.getLocality()).append("\n");
                        sb.append(address.getPostalCode()).append("\n");
                        sb.append(address.getCountryName());
                        result = sb.toString();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    checkPermissions();
                    Toast.makeText(MainActivity.this, result, Toast.LENGTH_LONG).show();
                }
            }
        });

        maps = findViewById(R.id.location);
        maps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(MainActivity.this, MapsActivity.class);
                startActivity(i);
            }
        });

        firstaid=findViewById(R.id.firstaid);
        firstaid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(MainActivity.this,FirstAid.class);
                startActivity(i);
            }
        });
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.add_contacts) {
            Intent i=new Intent(this, Sos.class);
            startActivity(i);
        } else if (id == R.id.home) {

        } else if (id == R.id.videos) {
            Intent i=new Intent(MainActivity.this,FirstAid.class);
            startActivity(i);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    private boolean checkPermission(String permissions[]) {
        for (String permission : permissions) {
            if (ActivityCompat.checkSelfPermission(getApplicationContext(), permission) != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }
    private void checkPermissions() {
        boolean permissionsGranted = checkPermission(PERMISSIONS_REQUIRED);
        if (permissionsGranted) {
            Toast.makeText(this, "You've granted all required permissions!", Toast.LENGTH_SHORT).show();
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage("+91"+number1, null, "SOS received. User is at "+result, null, null);
            smsManager.sendTextMessage("+91"+number2, null, "SOS received. User is at "+result, null, null);
            smsManager.sendTextMessage("+91"+number3, null, "SOS received. User is at "+result, null, null);
            Toast.makeText(getApplicationContext(), "Alerts sent", Toast.LENGTH_LONG).show();
        } else {
            boolean showRationale = true;
            for (String permission: PERMISSIONS_REQUIRED) {
                showRationale = ActivityCompat.shouldShowRequestPermissionRationale(this, permission);
                if (!showRationale) {
                    break;
                }
            }

            String dialogMsg = showRationale ? "We need some permissions to run this APP!" : "You've declined the required permissions, please grant them from your phone settings";

            new AlertDialog.Builder(this)
                    .setTitle("Permissions Required")
                    .setMessage(dialogMsg)
                    .setCancelable(false)
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            ActivityCompat.requestPermissions(MainActivity.this, PERMISSIONS_REQUIRED, REQUEST_PERMISSIONS);
                        }
                    }).create().show();
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        Log.d("MainActivity", "requestCode: " + requestCode);
        Log.d("MainActivity", "Permissions:" + Arrays.toString(permissions));
        Log.d("MainActivity", "grantResults: " + Arrays.toString(grantResults));

        if (requestCode == REQUEST_PERMISSIONS) {
            boolean hasGrantedPermissions = true;
            for (int i=0; i<grantResults.length; i++) {
                if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                    hasGrantedPermissions = false;
                    break;
                }
            }

            if (!hasGrantedPermissions) {
                finish();
            }

        } else {
            finish();
        }
    }
}
