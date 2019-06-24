package com.online_guide.diploma.audioguide;


import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.Toast;

import com.online_guide.diploma.audioguide.auth.Auth;
import com.online_guide.diploma.audioguide.db.sqLite.DatabaseHelper;
import com.online_guide.diploma.audioguide.entities.City;
import com.online_guide.diploma.audioguide.entities.Region;
import com.online_guide.diploma.audioguide.entities.User;
import com.online_guide.diploma.audioguide.view.adapters.ExpandableListAdapter;
import com.orm.query.Condition;
import com.orm.query.Select;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static com.online_guide.diploma.audioguide.constants.BaseField.*;

public class MenuActivity extends AppCompatActivity {

    List<City> cities;

    DatabaseHelper sqlHelper;

    Dialog dialog;

    String deviceId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_layout);


        final ExpandableListView listView = (ExpandableListView)findViewById(R.id.expandableListView);

        sqlHelper = new DatabaseHelper(getApplicationContext());
        // создаем базу данных
        sqlHelper.create_db();

        try {
            sqlHelper.open();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        sqlHelper.close();

        deviceId = new Auth(getApplicationContext()).getDevicedId();
        authDialog();

/*=========================================================================================================================*/
// Идентификация пользователя

// Чтение из базы

            List<Region> regions = Region.listAll(Region.class);

//        //Создаем набор данных для адаптера
            ArrayList<ArrayList<String>> groups = new ArrayList<>();
            ArrayList<String> children = new ArrayList<>();

            cities = new ArrayList<>();

            final List<String> listRegionNames = new ArrayList<>();

            int regionCount = regions.size();

            int j;
            for (j = 0; j < regionCount; j++) {
                cities = City.find(City.class, REGION_ID + " = ?", String.valueOf(regions.get(j).getID()));

                for (int k = 0; k < cities.size(); k++) {
                    children.add(cities.get(k).getCityName());
                }

                listRegionNames.add(regions.get(j).getName());

                groups.add(children);
                children = new ArrayList<>();
                cities = new ArrayList<>();
            }

            final ExpandableListAdapter adapter = new ExpandableListAdapter(getApplicationContext(), groups, listRegionNames);
            listView.setAdapter(adapter);


            listView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {

                @Override
                public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                    String cityName = adapter.getChild(groupPosition, childPosition).toString();
                    System.out.println(cityName);

                    Select selectCity = Select.from(City.class).where(Condition.prop(CITY_NAME).eq(cityName));
                    City findCity = (City) selectCity.first();

                    Intent intent = new Intent(MenuActivity.this, MapActivity.class);
                    intent.putExtra(CITY_NAME, cityName);
                    intent.putExtra(COORD_X, findCity.getCoordX());
                    intent.putExtra(COORD_Y, findCity.getCoordY());
                    intent.putExtra(INFO, findCity.getInfo());
                    intent.putExtra(CITY_ID, findCity.getId());
                    intent.putExtra(USER_DEVICE_ID, deviceId);
                    startActivity(intent);
                    return true;
                }
            });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.list_attraction) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * При старте приложения поверяет есть ли в БД Emai устройства:
     * если нет - запуск диалога авторизации с просьбой указать имя пользователя,
     * иначе диалог не создается
     */
    private void authDialog() {
        dialog = new Dialog(MenuActivity.this);

        if(User.find(User.class, PHONE_ID + " = ?", deviceId).size() == 0) {
            dialog = new Dialog(MenuActivity.this);
            dialog.setTitle("Вход");
            dialog.setContentView(R.layout.auth_dialog);
            dialog.show();

            dialog.findViewById(R.id.nextButton).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String username = ((EditText) dialog.findViewById(R.id.usernameEditText)).getText().toString();
                        new User(deviceId, username).save();
                        dialog.cancel();
                }
            });
        }
    }

}
