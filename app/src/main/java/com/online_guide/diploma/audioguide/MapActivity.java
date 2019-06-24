package com.online_guide.diploma.audioguide;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.online_guide.diploma.audioguide.entities.Attraction;
import com.online_guide.diploma.audioguide.entities.City;
import com.online_guide.diploma.audioguide.entities.UserAttraction;
import com.online_guide.diploma.audioguide.maps.ViewGoogleMap;
import com.online_guide.diploma.audioguide.reader.InfoFileReader;

import java.util.ArrayList;
import java.util.List;

import static com.online_guide.diploma.audioguide.constants.BaseField.*;

public class MapActivity extends AppCompatActivity implements OnMapReadyCallback {

    final String LOG_TAG = "myLogs";

    ViewGoogleMap viewGoogleMap;

    City city;

    String userDeviceId;

    String cityInfo;

    String infoFileName;

    List<Attraction> attractions;

    List<UserAttraction> userAttractions;

    TextView infoTextView;

    InfoFileReader infoFileReader;

    GoogleMap map;

    SlidingMenu menu;

    SlidingMenu infoBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.google_map_layout);

        initCity();

        infoBar = new SlidingMenu(this);
        infoBar.setMode(SlidingMenu.RIGHT);
        infoBar.setFadeDegree(0.1f);
        infoBar.attachToActivity(this, SlidingMenu.SLIDING_WINDOW);
        infoBar.setMenu(R.layout.information_fragment);
        infoBar.setBehindWidth(400);

        viewGoogleMap = new ViewGoogleMap(this);

        String title = city.getCityName();
        infoFileName = city.getInfo();

        TextView titleTextView = (TextView) findViewById(R.id.titleTextView);

        infoTextView = (TextView) infoBar.findViewById(R.id.infoTextView);
        infoTextView.setMovementMethod(new ScrollingMovementMethod());

        infoFileReader = new InfoFileReader(getApplicationContext());

        cityInfo =  infoFileReader.openFile(infoFileName);

        titleTextView.setText(title);
        infoTextView.setText(cityInfo + "\n");

        attractions = new ArrayList<>();
        attractions = Attraction.find(Attraction.class, CITY_ID + " = ?", String.valueOf(city.getID()));

        userAttractions = UserAttraction.find(UserAttraction.class, CITY_ID + " = ?", String.valueOf(city.getID()));

        menu = new SlidingMenu(this);
        menu.setMode(SlidingMenu.LEFT);
        menu.setFadeDegree(0.1f);
        menu.attachToActivity(this, SlidingMenu.SLIDING_WINDOW);
        menu.setMenu(R.layout.slidemenu);
        menu.setBehindWidth(400);

        ListView attractionsListView = (ListView) findViewById(R.id.sidemenu);

        attractionsListView.setAdapter(new ArrayAdapter<>(
                this,
                R.layout.slidemenu_item,
                R.id.text,
                attractions));

        attractionsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                LatLng coords = new LatLng(attractions.get(position).getCoordX(), attractions.get(position).getCoordY());
                map.moveCamera(CameraUpdateFactory.newLatLngZoom(coords, 18));

                menuToggle(menu);
            }
        });

        infoBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                menuToggle(infoBar);
            }
        });

        viewGoogleMap.createMapView();

    }

    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        cityInfo = savedInstanceState.getString("field_info");
        Log.d(LOG_TAG, "onRestoreInstanceState");
    }

    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("field_info", cityInfo);
        Log.d(LOG_TAG, "onSaveInstanceState");
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                menuToggle(menu);
                menuToggle(infoBar);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void menuToggle(SlidingMenu slide){
        if(slide.isMenuShowing())
            slide.showContent();
        else
            slide.showMenu();
    }

    @Override
    public void onMapReady(GoogleMap map) {
        this.map = map;

        viewGoogleMap.addMarker(map, city);

        viewGoogleMap.addMarker(map, attractions);

        viewGoogleMap.onMarkerClick(map);

        viewGoogleMap.addUserMarker(map, userAttractions);

        viewGoogleMap.createUserAttraction(map, (int) city.getID());

    }

    // Создание объекта City из данных полученных от MenuActivity
    private void initCity() {
        Intent intent = getIntent();

        String cityName = intent.getStringExtra(CITY_NAME);
        long cityId = intent.getLongExtra(CITY_ID, 0);
        String info = intent.getStringExtra(INFO);
        Double coordX = intent.getDoubleExtra(COORD_X, 0);
        Double coordY = intent.getDoubleExtra(COORD_Y, 0);

        city = new City(cityName, null, coordX, coordY, info);
        city.setId(cityId);

        userDeviceId = intent.getStringExtra(USER_DEVICE_ID);
    }

}
