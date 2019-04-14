package ppj.vana.projekt.server;

import ppj.vana.projekt.data.City;
import ppj.vana.projekt.data.Country;
import ppj.vana.projekt.data.Measurement;
import retrofit2.Call;
import retrofit2.http.*;

import java.util.List;

//  PUT     = CREATE
//  POST    = UPDATE
//  DELETE  = DELETE
//  GET     = READ


/**
 * Interface odpovídající REST API. Rozhraní je anotováno pomocí retrofit2.
 * retrofit2 Tutorial: https://www.vogella.com/tutorials/Retrofit/article.html
 */
public interface ServerAPI {

    // -------------------------------------------------------------- COUNTRY API - PATHS
    String COUNTRY_BASE_PATH = "/country";
    String COUNTRY_NAME_PATH = "/{countryName}";
    String COUNTRY_ALL_PATH = "/all";
    String COUNTRY_NAME = "countryName";
    // -------------------------------------------------------------- CITY API - PATHS
    String CITY_BASE_PATH = "/city";
    String CITY_NAME_PATH = "/{cityName}";
    String CITY_ALL_PATH = "/all";
    String CITY_NAME = "cityName";

    // -------------------------------------------------------------- CITY API - PATHS
    String MEASUREMENT_BASE_PATH = "/measurement";
    String MEASUREMENT_NAME_PATH = "/{id}";
    String MEASUREMENT_NAME = "id";

    // -------------------------------------------------------------- COUNTRY API
    @PUT(ServerAPI.COUNTRY_BASE_PATH)
    Call<Void> addCountry(@Body Country country);

    @GET(COUNTRY_BASE_PATH + COUNTRY_ALL_PATH)
    Call<List<Country>> getCountries();

    @GET(COUNTRY_BASE_PATH + COUNTRY_NAME_PATH)
    Call<Country> getCountryByID(@Path(COUNTRY_NAME) String countryName);

    @DELETE(COUNTRY_BASE_PATH + COUNTRY_NAME_PATH)
    Call<Void> deleteCountry(@Path(COUNTRY_NAME) String countryName);

    // -------------------------------------------------------------- CITY API

    @GET(CITY_BASE_PATH + CITY_ALL_PATH)
    Call<List<City>> getCities();

    @GET(CITY_BASE_PATH + CITY_NAME_PATH)
    Call<City> getCityByID(@Path(CITY_NAME) String cityName);

    @DELETE(CITY_BASE_PATH + CITY_NAME_PATH)
    Call<Void> deleteCity(@Path(CITY_NAME) String cityName);

    @PUT(ServerAPI.CITY_BASE_PATH)
    Call<Void> addCity(@Body City city);

    @POST(ServerAPI.CITY_BASE_PATH + CITY_NAME_PATH)
    Call<Void> updateCity(@Path(CITY_NAME) String cityName, @Body City city);

    // -------------------------------------------------------------- MEASUREMENT API

    @GET(MEASUREMENT_BASE_PATH + MEASUREMENT_NAME_PATH)
    Call<Measurement> getMeasurementByID(@Path(MEASUREMENT_NAME) String id);

    @DELETE(MEASUREMENT_BASE_PATH + MEASUREMENT_NAME_PATH)
    Call<Void> deleteMeasurement(@Path(MEASUREMENT_NAME) String id);

    @PUT(ServerAPI.MEASUREMENT_BASE_PATH)
    Call<Void> addMeasurement(@Body Measurement measurement);

    @POST(ServerAPI.MEASUREMENT_BASE_PATH + MEASUREMENT_NAME_PATH)
    Call<Void> updateMeasurement(@Path(MEASUREMENT_NAME) String measurementName, @Body Measurement measurement);
}
