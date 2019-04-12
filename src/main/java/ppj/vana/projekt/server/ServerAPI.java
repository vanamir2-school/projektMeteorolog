package ppj.vana.projekt.server;

import ppj.vana.projekt.data.Country;
import retrofit.http.*;

import java.util.List;

public interface ServerAPI {

    public static final String COUNTRY_PATH = "/country";

    @GET(COUNTRY_PATH)
    public List<Country> showCountries();
}
