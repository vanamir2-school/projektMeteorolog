package ppj.vana.projekt.controllers;

import com.google.gson.GsonBuilder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import ppj.vana.projekt.Main;
import ppj.vana.projekt.data.Country;
import ppj.vana.projekt.server.ServerAPI;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


// https://www.baeldung.com/spring-boot-testing

@RunWith(SpringRunner.class) // jinak se nespustí server
@SpringBootTest(classes = Main.class)
@ActiveProfiles({"test"})
@TestPropertySource(locations = "classpath:app_test.properties")
public class CountryRESTControllerTest {

    private final String URL = "http://localhost:8080";

    private GsonConverterFactory gsonConverterFactory = GsonConverterFactory.create(new GsonBuilder().setLenient().create());

    private Retrofit retrofit = new Retrofit.Builder().baseUrl(URL).addConverterFactory(gsonConverterFactory).build();

    ServerAPI serverAPI = retrofit.create(ServerAPI.class);

    @Test
    // nahodit do DB nejaká data a zkusit všechny requesty pomocí Retrofit řešení
    public void testOperations() {
        System.out.println("START");
        Country country = new Country("Káhira20");
        Call<ResponseEntity> call = serverAPI.addCountry(country);
        call.enqueue(new Callback<ResponseEntity>() {
            @Override
            public void onResponse(Call<ResponseEntity> call, Response<ResponseEntity> response) {
                if (response.isSuccessful()) {
                    System.out.println("FLO LFO PO");
                    ResponseEntity responseEntity = response.body();
                    System.out.println(response.raw().code());
                    if (responseEntity != null && responseEntity.getBody() != null)
                        System.out.println(responseEntity.getBody().toString());
                    System.out.println("FLO LFO PO - 2 ");
                } else {
                    System.out.println("FAIL - RESPONSE WAS NOT SUCCESFULL");
                    ResponseEntity responseEntity = response.body();
                    if (responseEntity != null && responseEntity.getBody() != null)
                        System.out.println(responseEntity.getBody().toString());
                }

            }

            @Override
            public void onFailure(Call<ResponseEntity> call, Throwable t) {
                System.out.println("FAIL - FAILURE");
                t.printStackTrace();
            }
        });

    }


}
