package com.online_guide.diploma.audioguide.maps;

import android.app.Activity;
import android.app.Dialog;
import android.database.CursorIndexOutOfBoundsException;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.online_guide.diploma.audioguide.R;
import com.online_guide.diploma.audioguide.constants.BaseField;
import com.online_guide.diploma.audioguide.entities.Attraction;
import com.online_guide.diploma.audioguide.entities.City;
import com.online_guide.diploma.audioguide.entities.UserAttraction;
import com.online_guide.diploma.audioguide.reader.InfoFileReader;
import com.orm.query.Condition;
import com.orm.query.Select;

import java.util.List;

/**
 * Класс для работы с GoogleMaps: отрисовка карты, добавление маркеров, обработка события клика
 * по маркеру
 */
public class ViewGoogleMap {
    /**
    * @param activity активити, в котором будет отрисована карта
    *
    * @param mapFragment отвечает за создание и отображение карты
    */

    private Activity activity;

    private MapFragment mapFragment;

    private Select select;

    private LatLng coords;

    private String info;

    public ViewGoogleMap(Activity activity) {
        this.activity = activity;
    }

    public ViewGoogleMap() {
    }

    public MapFragment getMapFragment() {
        return mapFragment;
    }

    public void setMapFragment(MapFragment mapFragment) {
        this.mapFragment = mapFragment;
    }

    public Activity getActivity() {
        return activity;
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }

    /**
     * Отрисовывает маркер выбранного города
     * @param map - карта приложения
     * @param city - выбранный город
     */
    public void addMarker(GoogleMap map, City city) {
        LatLng coords = new LatLng(city.getCoordX(), city.getCoordY());
        map.setMyLocationEnabled(true);
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(coords, 12));

        map.addMarker(new MarkerOptions()
                .title("").position(coords));
    }

    /**
     * Добавляет на карту маркеры мест предоставляемых приложением
     *
     * @param map - карта приложения
     * @param attractions - список доступный мест
     */
    public void addMarker(GoogleMap map, List<Attraction> attractions) {

        for (int i = 0; i < attractions.size(); i++) {

            LatLng coords = new LatLng(attractions.get(i).getCoordX(), attractions.get(i).getCoordY());
            map.setMyLocationEnabled(true);
            map.moveCamera(CameraUpdateFactory.newLatLngZoom(coords, 12));

            map.addMarker(new MarkerOptions()
                    .title(attractions.get(i).getAttractionName())
                    .snippet(attractions.get(i).getAddress()).flat(true)
                    .icon(BitmapDescriptorFactory.fromResource(R.mipmap.icon_anchor))
                    .position(coords)).showInfoWindow();
        }

    }

    public void addUserMarker(GoogleMap map, List<UserAttraction> userAttractions) {
        for (int i = 0; i < userAttractions.size(); i++) {
            addMarker(map, userAttractions.get(i));
        }

    }

    public void createMapView() {
        mapFragment = (MapFragment) activity.getFragmentManager()
                .findFragmentById(R.id.mapView);
        mapFragment.getMapAsync((OnMapReadyCallback) activity);
    }

    public boolean onMarkerClick(final GoogleMap map) {
        map.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                marker.showInfoWindow();
                InfoFileReader infoFileReader = new InfoFileReader(activity.getApplicationContext());
                String infoFileName;

                try {
                    select = Select.from(Attraction.class).where(Condition.prop(BaseField.ATTRACTION_NAME).eq(marker.getTitle()));
                } catch (CursorIndexOutOfBoundsException ex) {

                    return false;
                }
                Attraction attraction = (Attraction) select.first();
                try {
                    infoFileName = attraction.getInfo();
                    info = infoFileReader.openFile(infoFileName);
                } catch (NullPointerException ex) {
                    select = Select.from(UserAttraction.class)
                            .where(Condition.prop(BaseField.ATTRACTION_NAME).eq(marker.getTitle()));
                    UserAttraction userAttraction = (UserAttraction) select.first();
                    info = userAttraction.getInfo();
                }

                TextView infoTextView = (TextView) activity.findViewById(R.id.infoTextView);
                infoTextView.setText("\n" + info + "\n");

                return true;
            }
        });

        return true;
    }

    public void createUserAttraction(final GoogleMap map, final int cityId) {

        map.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                coords = latLng;
                final Dialog dialog = new Dialog(activity);
                dialog.setTitle(R.string.new_my_attraction);
                dialog.setContentView(R.layout.new_attraction_layout);
                dialog.show();

                dialog.findViewById(R.id.new_attraction_button).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        boolean type = false;

                        CheckBox checkBox = (CheckBox) dialog.findViewById(R.id.publicCheckBox);
                        if (checkBox.isChecked()) {
                            type = true;
                        }

                        String nameEditText = ((EditText) dialog.findViewById(R.id.attractionNameEditText)).getText().toString();
                        String infoEditText = ((EditText) dialog.findViewById(R.id.attractionInfoEditText)).getText().toString();

                        UserAttraction userAttraction = new UserAttraction();
                        userAttraction.setCoordX(coords.latitude);
                        userAttraction.setCoordY(coords.longitude);
                        userAttraction.setName(nameEditText);
                        userAttraction.setInfo(infoEditText);
                        userAttraction.setType(type);
                        userAttraction.setCity_Id(cityId);

                        addMarker(map, userAttraction);

                        userAttraction.save();
                        dialog.cancel();
                    }
                });
            }
        });
    }

    /**
     * Добавляет маркер созданный пользователем на карту
     * @param map - карта приложения
     * @param userAttraction - пользовательское место для добавления на карту
     */
    private void addMarker(GoogleMap map, UserAttraction userAttraction) {
        LatLng coords = new LatLng(userAttraction.getCoordX(), userAttraction.getCoordY());
        map.setMyLocationEnabled(true);
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(coords, 12));

        map.addMarker(new MarkerOptions()
                .title(userAttraction.getName()).flat(true)
                .icon(BitmapDescriptorFactory.fromResource(R.mipmap.icon_user_anchor))
                .position(coords)).showInfoWindow();
    }
}
