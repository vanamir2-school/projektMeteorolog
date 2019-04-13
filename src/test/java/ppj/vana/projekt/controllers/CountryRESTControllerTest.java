package ppj.vana.projekt.controllers;

import com.google.gson.GsonBuilder;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.internal.matchers.ArrayEquals;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;
import ppj.vana.projekt.Main;
import ppj.vana.projekt.data.Country;
import ppj.vana.projekt.server.ServerAPI;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static org.assertj.core.api.BDDAssertions.then;
import static org.junit.Assert.assertEquals;

import java.util.Map;


// https://www.baeldung.com/spring-boot-testing


@RunWith(SpringRunner.class) // jinak se nespustí server -- mozna nechat
//@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Main.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles({"test"})
@TestPropertySource(locations = "classpath:app_test.properties")
public class CountryRESTControllerTest {

    ServerAPI serverAPI;
    @LocalServerPort
    private int port;
    @Autowired
    private TestRestTemplate testRestTemplate;
    private ConfigurableApplicationContext ctx;

    private GsonConverterFactory gsonConverterFactory = GsonConverterFactory.create(new GsonBuilder().setLenient().create());
    private String URL;
    private Retrofit retrofit;

    @Before
    public void init() {
        SpringApplication app = new SpringApplication(Main.class);
        String[] str = new String[1];
        str[0] = "Ahoj";
        ctx = app.run(str);
        URL = "http://localhost:" + this.port;
        retrofit = new Retrofit.Builder().baseUrl(URL).addConverterFactory(gsonConverterFactory).build();
        serverAPI = retrofit.create(ServerAPI.class);
    }

    @Test
    public void shouldReturn200WhenSendingRequestToController() throws Exception {
        @SuppressWarnings("rawtypes")
        ResponseEntity<Map> entity = this.testRestTemplate.getForEntity(
                "http://localhost:" + this.port + "/hello-world", Map.class);

        then(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
    }


    @Test
    // nahodit do DB nejaká data a zkusit všechny requesty pomocí Retrofit řešení
    public void testOperations() {
        System.out.println("START");
        Country country = new Country("Káhira22");
        Call<ResponseEntity> call = serverAPI.addCountry(country);
        call.enqueue(new Callback<ResponseEntity>() {
            @Override
            public void onResponse(Call<ResponseEntity> call, Response<ResponseEntity> response) {
                if (response.isSuccessful()) {
                    System.out.println("FLO LFO PO");
                    ResponseEntity responseEntity = response.body();
                    System.out.println(response.raw().code());
                    assertEquals("Test return CODE - should be OK", response.raw().code(), 200);
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

    @After
    public void finish() {
        ctx.close();
    }

}
