package ppj.vana.projekt.server;

import ppj.vana.projekt.data.City;
import ppj.vana.projekt.data.Country;
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

    // -------------------------------------------------------------- COUNTRY API - start
    String COUNTRY_BASE_PATH = "/country";
    String COUNTRY_NAME_PATH = "/{countryName}";
    String COUNTRY_ALL_PATH = "/all";
    String COUNTRY_NAME = "countryName";
    // -------------------------------------------------------------- CITY API - start
    String CITY_BASE_PATH = "/city";
    String CITY_NAME_PATH = "/{cityName}";
    String CITY_ALL_PATH = "/all";

    @PUT(ServerAPI.COUNTRY_BASE_PATH)
    Call<Void> addCountry(@Body Country country);

    // -------------------------------------------------------------- COUNTRY API - end
    String CITY_NAME = "cityName";

    @GET(COUNTRY_BASE_PATH + COUNTRY_ALL_PATH)
    Call<List<Country>> getCountries();

    @GET(COUNTRY_BASE_PATH + COUNTRY_NAME_PATH)
    Call<Country> getCountryByID(@Path(COUNTRY_NAME) String countryName);

    @DELETE(COUNTRY_BASE_PATH + COUNTRY_NAME_PATH)
    Call<Void> deleteCountry(@Path(COUNTRY_NAME) String countryName);

    @GET(CITY_BASE_PATH + CITY_ALL_PATH)
    Call<List<City>> getCities();

    @GET(CITY_BASE_PATH + CITY_NAME_PATH)
    Call<City> getCityByID(@Path(CITY_NAME) String cityName);

    @DELETE(CITY_BASE_PATH + CITY_NAME_PATH)
    Call<Void> deleteCity(@Path(CITY_NAME) String cityName);

    @PUT(ServerAPI.CITY_BASE_PATH)
    Call<Void> addCity(@Body City city);
    // -------------------------------------------------------------- CITY API - end
}
