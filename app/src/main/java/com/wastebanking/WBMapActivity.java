package com.wastebanking;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RelativeLayout;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStates;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.location.places.GeoDataClient;
import com.google.android.gms.location.places.PlaceDetectionClient;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.wastebanking.Activities.WBContactCollectinCenter;
import com.wastebanking.Activities.WBContactTruck;
import com.wastebanking.Activities.WBTrashCalculator;
import com.wastebanking.Helpers.NavigationHelper;
import com.wastebanking.Models.WBWasteDisposalLocations;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class WBMapActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,
        OnMapReadyCallback,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        GoogleMap.OnMarkerDragListener,
        GoogleMap.OnMapLongClickListener,
        View.OnClickListener {
    //Our Map
    private GoogleMap mMap;

    //To store longitude and latitude from map
    private double longitude;
    private double latitude;
    private NavigationHelper navigationHelper;
    private boolean mLocationPermissionGranted;
    //Buttons
    private Button btn_contact_collecting_center,btn_contact_truck;
    private ImageButton buttonSave;
    private ImageButton buttonCurrent,btn_currentloction;
    private ImageButton buttonView;
    private ArrayList<WBWasteDisposalLocations> wasteDisposalLocationsArr;
    private static final int MY_PERMISSION_ACCESS_COURSE_LOCATION= 11;
    final static int REQUEST_CHECK_SETTINGS =21;
    private static final int PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1;
    private View mapView;
    private static final String TAG="getlocation";
    // The entry points to the Places API.
    private GeoDataClient mGeoDataClient;
    private PlaceDetectionClient mPlaceDetectionClient;
    private FusedLocationProviderClient mFusedLocationProviderClient;
    private final LatLng mDefaultLocation = new LatLng(6.903810, 79.876590);
    private static final int DEFAULT_ZOOM = 13;
    private static final int SEARCH_ZOOM = 15;
    private Location mLastKnownLocation;
    private LatLng myLocation;
    private LatLng prevLocation;
    private String typeofCollector="";
    private boolean collectingCenter=false;
    private Marker myLocationMarker;
    private Marker currentMarker;

    //Google ApiClient
    private GoogleApiClient googleApiClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.wb_map_activity);

        initViews();


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        invalidateOptionsMenu();
        int status = GooglePlayServicesUtil.isGooglePlayServicesAvailable(getBaseContext());
        if (status != ConnectionResult.SUCCESS) {
            int requestCode = 10;
            Dialog dialog = GooglePlayServicesUtil.getErrorDialog(status, this, requestCode);
            dialog.show();
        } else {
            // Construct a GeoDataClient.
            mGeoDataClient = Places.getGeoDataClient(this, null);

            // Construct a PlaceDetectionClient.
            mPlaceDetectionClient = Places.getPlaceDetectionClient(this, null);

            // Construct a FusedLocationProviderClient.
            mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

            // Obtain the SupportMapFragment and get notified when the map is ready to be used.
            SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                    .findFragmentById(R.id.map);
            mapFragment.getMapAsync(this);
            mapView = mapFragment.getView();
            myLocation = mDefaultLocation;
        }
//        Initializing googleapi client
//        googleApiClient = new GoogleApiClient.Builder(this)
//                .addConnectionCallbacks(this)
//                .addOnConnectionFailedListener(this)
//                .addApi(LocationServices.API)
//                .build();

        //Initializing views and adding onclick listeners
//        buttonSave = (ImageButton) findViewById(R.id.buttonSave);
//        btn_currentloction = (ImageButton) findViewById(R.id.btn_currentloction);
//        buttonView = (ImageButton) findViewById(R.id.buttonView);
//        buttonSave.setOnClickListener(this);
//        btn_currentloction.setOnClickListener(this);
//        buttonView.setOnClickListener(this);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        markLocationsOnMap();
    }
public void initViews(){
    btn_contact_collecting_center=(Button)findViewById(R.id.btn_contact_collecting_center);
    btn_contact_collecting_center.setOnClickListener(this);
    btn_contact_truck=(Button)findViewById(R.id.btn_contact_truck);
    btn_contact_truck.setOnClickListener(this);
}

    public void markLocationsOnMap(){
//        public ArrayList<MyLocations> loadJSONFromAsset() {
//            ArrayList<MyLocations> locList = new ArrayList<>();
        wasteDisposalLocationsArr=new ArrayList<>();
            String json = null;
        Log.d("disposelocations","marklocations");
        InputStream is = getResources().openRawResource(R.raw.collecting_centers);
//        Writer writer=new StringWriter();
//        char[] buffer=new char[1024];

            try {
                int size = is.available();
                byte[] buffer = new byte[size];
                is.read(buffer);
                is.close();
                json = new String(buffer, "UTF-8");

//                Reader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
//                int n;
//                while ((n = reader.read(buffer)) != -1) {
//                    writer.write(buffer, 0, n);
//            }is.close();}
            }
            catch (IOException ex) {
                ex.printStackTrace();
            }
            try {
                JSONObject obj = new JSONObject(json);
                JSONArray m_jArry = obj.getJSONArray("locations");
                Log.d("disposelocations",""+m_jArry.toString());

                for (int i = 0; i < m_jArry.length(); i++) {
                    JSONObject jo_inside = m_jArry.getJSONObject(i);
                    WBWasteDisposalLocations wastedisposeLocations = new WBWasteDisposalLocations();
                    wastedisposeLocations.setLat(jo_inside.getDouble("lat"));
                    wastedisposeLocations.setLongitude(jo_inside.getDouble("lang"));
                    wastedisposeLocations.setCenterId(jo_inside.getString("id"));
                    wastedisposeLocations.setAddress(jo_inside.getString("address"));
                    wastedisposeLocations.setName(jo_inside.getString("name"));
                    wastedisposeLocations.setContact(jo_inside.getString("contact"));
                    wastedisposeLocations.setType(jo_inside.getString("type"));
                    JSONArray jwasteTypes=jo_inside.getJSONArray("acceptedWaste");
                    JSONArray jpriceList=jo_inside.optJSONArray("priceList");
                    ArrayList<String> stringArray = new ArrayList<String>();
                    ArrayList<String>stringPriceList=new ArrayList<String>();
                    for (int j=0; j<jwasteTypes.length();j++){

                        try {
                            String wasteTypes = jwasteTypes.getString(j);
                            stringArray.add(wasteTypes);
                            wastedisposeLocations.setAcceptedWaste(stringArray);
                            Log.d("disposelocations","1 "+stringArray.toString());
                        }
                        catch (JSONException e) {
                            e.printStackTrace();
                            Log.d("disposelocations","1 "+e);
                        }
                    }
                    for (int k=0; k<jpriceList.length();k++){

                        try {
                            String priceList = jpriceList.getString(k);
                            stringPriceList.add(priceList);
                            wastedisposeLocations.setPriceList(stringPriceList);
                            Log.d("priceList","1 "+stringPriceList.toString());
                        }
                        catch (JSONException e) {
                            e.printStackTrace();
                            Log.d("priceList","1 "+e);
                        }
                    }

                    //Add your values in your `ArrayList` as below:
                    wasteDisposalLocationsArr.add(wastedisposeLocations);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

        addMarkers();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
//        startDemo();

        navigationHelper = new NavigationHelper(mMap, getBaseContext());
        // Do other setup activities here too, as described elsewhere in this tutorial. getLocationPermission();


        LatLng latLng = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(latLng).draggable(true));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        mMap.setOnMarkerDragListener(this);
        mMap.setOnMapLongClickListener(this);

        getLocationPermission();
        // Turn on the My Location layer and the related control on the map.
        //updateLocationUI();

        getDeviceLocation();

        //   showCurrentPlace();

        settingsrequest();
        // Get the current location of the device and set the position of the map.


//        googleMap.setMapStyle(
//                MapStyleOptions.loadRawResourceStyle(
//                        this, R.raw.map_style_json));


        final Activity activity = this;
        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                if (marker.getSnippet() != null && (!marker.getSnippet().equals(""))){
                    String type=marker.getTitle().substring(0,2);
                  if (type.equals("CC")) {
                        Log.d("contact",""+type);
                        btn_contact_truck.setVisibility(View.GONE);
                        btn_contact_collecting_center.setVisibility(View.VISIBLE);
                      currentMarker = marker;
//                        collectingCenter=false;
                    }

                    else if (type.equals("CT")){
                        Log.d("contact",""+type);
                        btn_contact_collecting_center.setVisibility(View.GONE);
                        btn_contact_truck.setVisibility(View.VISIBLE);
                      currentMarker = marker;
//                        btn_contact_collecting_center.setText("Contact Collecting Center");
//
////                        collectingCenter=true;
                    }
                }
                return false;
            }
        });


        mMap.getUiSettings().setMyLocationButtonEnabled(true);

        View locationButton = ((View) mapView.findViewById(Integer.parseInt("1")).getParent()).findViewById(Integer.parseInt("2"));
        RelativeLayout.LayoutParams rlp = (RelativeLayout.LayoutParams) locationButton.getLayoutParams();
// position on right bottom
        //rlp.addRule(RelativeLayout.ALIGN_PARENT_TOP, 0);
        //  rlp.addRule(RelativeLayout.ALIGN_PARENT_TOP, RelativeLayout.TRUE);
        rlp.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        //  rlp.setMargins(0,  0, 0, 0);;
    }



    private void getLocationPermission() {
    /*
     * Request location permission, so that we can get the location of the
     * device. The result of the permission request is handled by a callback,
     * onRequestPermissionsResult.
     */
        if (ContextCompat.checkSelfPermission(this.getApplicationContext(),
                android.Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            mLocationPermissionGranted = true;
            Log.d("location", "location permission granted");
            mMap.setMyLocationEnabled(true);
            mMap.getUiSettings().setMyLocationButtonEnabled(true);

        } else {
            ActivityCompat.requestPermissions(this,
                    new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                    PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
            Log.d("location", "location permission not granted");
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String permissions[],
                                           @NonNull int[] grantResults) {
        mLocationPermissionGranted = false;
        switch (requestCode) {
            case PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    mLocationPermissionGranted = true;
                    getDeviceLocation();
                }
                else{
                    ActivityCompat.requestPermissions(this,
                            new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                            PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
                }
            }
        }
        //updateLocationUI();
    }
    private void updateDeviceLocation() {
        Log.d(TAG, "updating_device_location");
        try {
            if (mLocationPermissionGranted) {

                Task locationResult = mFusedLocationProviderClient.getLastLocation();
                locationResult.addOnCompleteListener(this, new OnCompleteListener() {
                    @Override
                    public void onComplete(@NonNull Task task) {
                        if (task.isSuccessful() && task.getResult() != null) {
                            // Set the map's camera position to the current location of the device.
                            mLastKnownLocation = (Location) task.getResult();

                            //Marker myMarker = new Marker();

                            myLocation = new LatLng(mLastKnownLocation.getLatitude(),
                                    mLastKnownLocation.getLongitude());

//                            double dis = distance(prevLocation.latitude,prevLocation.longitude,myLocation.latitude,myLocation.longitude);
//                            Log.d(TAG, "device_location_updated: "+myLocation + " moved(m):"+dis);
//                            if(dis>1000){
//                                prevLocation = myLocation;
//                                fetchParkingSlots(myLocation.latitude,myLocation.longitude);
//                            }
                            myLocationMarker.setPosition(new LatLng(mLastKnownLocation.getLatitude(),
                                    mLastKnownLocation.getLongitude()));


                        } else {
                            // myLocation = mDefaultLocation;
                            Log.d(TAG, "Current location is null. location update failed. requesting location permission");



                        }
                    }
                });
            }
        } catch (SecurityException e) {
            Log.e("Exception: %s", e.getMessage());
        }
    }

    private void getDeviceLocation() {
    /*
     * Get the best and most recent location of the device, which may be null in rare
     * cases when a location is not available.
     */
        try {
            if (mLocationPermissionGranted) {

                Task locationResult = mFusedLocationProviderClient.getLastLocation();
                locationResult.addOnCompleteListener(this, new OnCompleteListener() {
                    @Override
                    public void onComplete(@NonNull Task task) {
                        if (task.isSuccessful() && task.getResult() != null) {
                            // Set the map's camera position to the current location of the device.
                            mLastKnownLocation = (Location) task.getResult();
                            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(
                                    new LatLng(mLastKnownLocation.getLatitude(),
                                            mLastKnownLocation.getLongitude()), DEFAULT_ZOOM));


                            //Marker myMarker = new Marker();
                            myLocation = new LatLng(mLastKnownLocation.getLatitude(),
                                    mLastKnownLocation.getLongitude());
                            myLocationMarker = mMap.addMarker(new MarkerOptions()
                                    .title("My Location")
                                    .anchor(0.5f, 0.5f)
                                    .position(new LatLng(mLastKnownLocation.getLatitude(),
                                            mLastKnownLocation.getLongitude()))
                                    .flat(false)
//                                    .icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_my_car))
                            );
//                            Location prevLoc = mMap.getMyLocation();
//                            Location newLoc = mMap.getMyLocation();
//                            float bearing = prevLoc.bearingTo(newLoc);
//                            myLocationMarker.setRotation(bearing);


                            new Timer().schedule(new TimerTask() {
                                @Override
                                public void run() {
                                    updateDeviceLocation();

                                }
                            }, 0, 2000);
                            if (ActivityCompat.checkSelfPermission(getBaseContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getBaseContext(), android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                                // TODO: Consider calling
                                //    ActivityCompat#requestPermissions
                                // here to request the missing permissions, and then overriding
                                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                                //                                          int[] grantResults)
                                // to handle the case where the user grants the permission. See the documentation
                                // for ActivityCompat#requestPermissions for more details.
                                return;
                            }
                            Log.d(TAG, "myLocationEnabled");
                            mMap.setMyLocationEnabled(true);
                            mMap.getUiSettings().setMyLocationButtonEnabled(true);

                        } else {
                            ActivityCompat.requestPermissions(WBMapActivity.this,
                                    new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                                    PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
                            myLocation = mDefaultLocation;
                            Log.d(TAG, "Current location is null. Using defaults.");
                            Log.e(TAG, "Exception: %s", task.getException());
                            mMap.addMarker(new MarkerOptions()
                                    .title("My Location")
                                    .anchor(0.5f,0.5f)
                                    .position(mDefaultLocation)
                                    .flat(false)
//                                    .icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_my_car))
                            );
                            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(mDefaultLocation, DEFAULT_ZOOM));

                            // mMap.getUiSettings().setMyLocationButtonEnabled(false);
                        }
                        prevLocation = myLocation;
                        markLocationsOnMap();
//                        fetchParkingSlots(myLocation.latitude,myLocation.longitude);
                    }
                });
            }
        } catch(SecurityException e)  {
            Log.e("Exception: %s", e.getMessage()+"");
        }
    }

    public void settingsrequest()
    {
        GoogleApiClient googleApiClient = new GoogleApiClient.Builder(this)
                .addApi(LocationServices.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this).build();
        googleApiClient.connect();

        LocationRequest locationRequest = LocationRequest.create();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setInterval(30 * 1000);
        locationRequest.setFastestInterval(5 * 1000);
        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
                .addLocationRequest(locationRequest);
        builder.setAlwaysShow(true); //this is the key ingredient

        PendingResult<LocationSettingsResult> result =
                LocationServices.SettingsApi.checkLocationSettings(googleApiClient, builder.build());
        result.setResultCallback(new ResultCallback<LocationSettingsResult>() {
            @Override
            public void onResult(LocationSettingsResult result) {
                final Status status = result.getStatus();
                final LocationSettingsStates state = result.getLocationSettingsStates();
                switch (status.getStatusCode()) {
                    case LocationSettingsStatusCodes.SUCCESS:
                        // All location settings are satisfied. The client can initialize location
                        // requests here.

                        break;
                    case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                        // Location settings are not satisfied. But could be fixed by showing the user
                        // a dialog.
                        try {
                            // Show the dialog by calling startResolutionForResult(),
                            // and check the result in onActivityResult().
                            status.startResolutionForResult(WBMapActivity.this, REQUEST_CHECK_SETTINGS);
                        } catch (IntentSender.SendIntentException e) {
                            // Ignore the error.
                        }
                        break;
                    case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                        // Location settings are not satisfied. However, we have no way to fix the
                        // settings so we won't show the dialog.
                        break;
                }
            }
        });
    }
//    private void drawMarker(latLng point){
//
//    }

    @Override
    public void onConnected(Bundle bundle) {
//        getCurrentLocation();
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }


    @Override
    public void onMapLongClick(LatLng latLng) {
        //Clearing all the markers
        mMap.clear();

        //Adding a new marker to the current pressed position
        mMap.addMarker(new MarkerOptions()
                .position(latLng)
                .draggable(true));
    }

    @Override
    public void onMarkerDragStart(Marker marker) {

    }

    @Override
    public void onMarkerDrag(Marker marker) {

    }

    @Override
    public void onMarkerDragEnd(Marker marker) {
        //Getting the coordinates
        latitude = marker.getPosition().latitude;
        longitude = marker.getPosition().longitude;

        //Moving the map
//        moveMap();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_contact_collecting_center:
                for (int i = 0; i < wasteDisposalLocationsArr.size(); i++) {
                    if (wasteDisposalLocationsArr.get(i).type.equals("collectingCenter")){
                        if (currentMarker.getTitle().contains(wasteDisposalLocationsArr.get(i).centerId)) {
                            Intent intent1 = new Intent(this, WBContactCollectinCenter.class);
                            intent1.putExtra("centerId", wasteDisposalLocationsArr.get(i).centerId);
                            intent1.putExtra("name", wasteDisposalLocationsArr.get(i).name);
                            intent1.putExtra("address", wasteDisposalLocationsArr.get(i).address);
                            intent1.putExtra("contact", wasteDisposalLocationsArr.get(i).contact);
                            intent1.putExtra("acceptedWaste", wasteDisposalLocationsArr.get(i).acceptedWaste);
                            intent1.putExtra("priceList",wasteDisposalLocationsArr.get(i).priceList);
                            startActivity(intent1);
                            return;
                        }
                    }
                }
            case R.id.btn_contact_truck:
                for (int i = 0; i < wasteDisposalLocationsArr.size(); i++) {
                 if (wasteDisposalLocationsArr.get(i).type.equals("garbageTruck")) {
                     if (currentMarker.getTitle().contains(wasteDisposalLocationsArr.get(i).centerId)) {
                         Intent intent = new Intent(this, WBContactTruck.class);
                         intent.putExtra("centerId", wasteDisposalLocationsArr.get(i).centerId);
                         intent.putExtra("name", wasteDisposalLocationsArr.get(i).name);
                         intent.putExtra("address", wasteDisposalLocationsArr.get(i).address);
                         intent.putExtra("contact", wasteDisposalLocationsArr.get(i).contact);
                         intent.putExtra("acceptedWaste", wasteDisposalLocationsArr.get(i).acceptedWaste);
                         startActivity(intent);
                         return;
                     }
                 }
            }

        }
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
private void addMarkers(){
    for (int i = 0; i < wasteDisposalLocationsArr.size(); i++) {
        LatLng location = new LatLng(wasteDisposalLocationsArr.get(i).lat, wasteDisposalLocationsArr.get(i).longitude);
        Log.d("markLocations",""+location);
        MarkerOptions options=new MarkerOptions().title(wasteDisposalLocationsArr.get(i).centerId)
                .snippet("Type: "+wasteDisposalLocationsArr.get(i).type+"\nContact: "+wasteDisposalLocationsArr.get(i).contact+"\nAddress: "+wasteDisposalLocationsArr.get(i).address)
                .position(location);
//        MarkerOptions options=new MarkerOptions()
//                .position(location);

        if (wasteDisposalLocationsArr.get(i).type.equals("collectingCenter")){
            Log.d("markLocations","type"+wasteDisposalLocationsArr.get(i).type);
            options.icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_location));
            collectingCenter=true;
            typeofCollector="collectingCenter";

        }
        else if (wasteDisposalLocationsArr.get(i).type.equals("garbageTruck")){
            options.icon(BitmapDescriptorFactory.fromResource(R.drawable.garbage_truck));
            collectingCenter=false;
            typeofCollector="garbageTruck";
        }
        if (mMap!=null){
            mMap.addMarker(options);
        }

    }
}
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.wbmap, menu);
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

        if (id == R.id.nav_calculator) {
            Intent intent=new Intent(this, WBTrashCalculator.class);
            startActivity(intent);
        } else if (id == R.id.nav_history) {

        } else if (id == R.id.nav_points) {

        } else if (id == R.id.nav_edituser) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

}

