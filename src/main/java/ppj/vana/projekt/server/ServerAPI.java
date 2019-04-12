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

    public static final String COUNTRY_NAME = "{countryName}";
    public static final String COUNTRY_PATH = "/country";
    public static final String COUNTRY_PATH_ALL = "/country/all";
    public static final String COUNTRY_PATH_NAME = "/country/" + COUNTRY_NAME;

    @GET(COUNTRY_PATH_ALL)
    public Call<ResponseEntity<List<Country>>> getCountries();

    @GET(COUNTRY_PATH_NAME)
    public Country getCountryByID(@Path(COUNTRY_NAME) String countryName);

    @DELETE(COUNTRY_PATH_NAME)
    public ResponseEntity deleteCountry(@Path(COUNTRY_NAME) String countryName);


    // TODO - tahle jedina metoda funguje, ostatní je třeba otestovat - například v Mainu, aby vše bylo OK
    @PUT(ServerAPI.COUNTRY_PATH)
    public Call<ResponseEntity> addCountry(@Body Country country);
}
