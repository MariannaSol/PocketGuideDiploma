package com.online_guide.diploma.audioguide.constants;

/**
 * Набор констант для работы с БД
 */
public interface BaseField {

    /**
     *
     *@param DATABASE_NAME - имя базы данных
     *
     *@param VERSION - версия БД
     *
     *@param TABLE_NAME_CITY - название таблицы City
     *
     *@param TABLE_NAME_REGION - название таблицы Region
     *
     *@param CITY_ID - название колонки для таблицы City, содержит информацию о ID города
     *
     *@param CITY_NAME - название колоноки для таблицы City, содержит информацию о название города
     *
     *@param REGION_ID - название колоноки для таблицы Region, содержит id региона
     *
     *@param REGION_NAME - название колоноки для таблицы Region, содержит имя области
     *
     *@param _ID - id сохраненного объекта
     *
     *@param COORD_X, COORD_Y  - координаты широты и долготы города, или достопримечательности
     *
     * @param INFO - названия колонок для таблиц City, Attraction. Содержит информацию о городе, или достопримечательносте
     *
     * @param ATTRACTION_ADDRESS - адрес Attraction
     *
     */

    String DATABASE_NAME = "guidedatabase";

    int VERSION = 1;

    String CITY_ID = "CITY_ID";

    String ATTRACTION_NAME = "ATTRACTION_NAME";

    String REGION_ID = "REGION_ID";

    String ID = "ID";

    String CITY_NAME = "name";

    String REGION_NAME = "REGION_NAME";

    String INFO = "INFO";

    String COORD_X = "COORD_X";

    String COORD_Y = "COORD_Y";

    String ATTRACTION_ADDRESS = "ADDRESS";

    String PHONE_ID = "phoneId";

    String USER_ID = "userId";

    String USER_DEVICE_ID = "USER_DEVICE_ID";

}


