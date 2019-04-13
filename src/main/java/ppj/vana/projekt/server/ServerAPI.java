package ppj.vana.projekt.server;

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
    String C_NAME_PATH = "/{countryName}";
    String C_ALL_PATH = "/all";
    String C_NAME = "countryName";

    @GET(COUNTRY_BASE_PATH + C_ALL_PATH)
    Call<List<Country>> getCountries();

    @GET(COUNTRY_BASE_PATH + C_NAME_PATH)
    Call<Country> getCountryByID(@Path(C_NAME) String countryName);

    @DELETE(COUNTRY_BASE_PATH + C_NAME_PATH)
    Call<Void> deleteCountry(@Path(C_NAME) String countryName);

    @PUT(ServerAPI.COUNTRY_BASE_PATH)
    Call<Void> addCountry(@Body Country country);
    // -------------------------------------------------------------- COUNTRY API - end
}
