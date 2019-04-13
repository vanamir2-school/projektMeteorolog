package ppj.vana.projekt.server;

import org.springframework.http.ResponseEntity;
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
    public static final String COUNTRY_BASE_PATH = "/country";
    public static final String C_NAME_PATH = "/{countryName}";
    public static final String C_ALL_PATH = "/all";
    public static final String C_NAME = "countryName";

    @GET(COUNTRY_BASE_PATH + C_ALL_PATH)
    public Call<ResponseEntity<List<Country>>> getCountries();

    @GET(COUNTRY_BASE_PATH + C_NAME_PATH)
    public Call<ResponseEntity<Country>> getCountryByID(@Path(C_NAME) String countryName);

    @DELETE(COUNTRY_BASE_PATH + C_NAME_PATH)
    public Call<ResponseEntity> deleteCountry(@Path(C_NAME) String countryName);

    @PUT(ServerAPI.COUNTRY_BASE_PATH)
    public Call<ResponseEntity> addCountry(@Body Country country);
    // -------------------------------------------------------------- COUNTRY API - end
}
