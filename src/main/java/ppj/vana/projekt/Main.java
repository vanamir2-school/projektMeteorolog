package ppj.vana.projekt;

import com.google.gson.GsonBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.http.ResponseEntity;
import ppj.vana.projekt.data.Country;
import ppj.vana.projekt.provisioning.Provisioner;
import ppj.vana.projekt.server.ServerAPI;
import ppj.vana.projekt.service.MongoMeasurementService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.io.IOException;

@SpringBootApplication
@EnableJpaRepositories("ppj.vana.projekt.repositories")
public class Main {

    private static final Logger logger = LoggerFactory.getLogger(Main.class);

    @Autowired
    private MongoTemplate mongo;

    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(Main.class);
        ApplicationContext ctx = app.run(args);

        // docasna testovaci class
        ctx.getBean(Foo.class).makeSound();

        // REST TEST
        final String URL = "http://localhost:8080";
        GsonConverterFactory gsonConverterFactory = GsonConverterFactory.create(new GsonBuilder().setLenient().create());
        Retrofit retrofit = new Retrofit.Builder().baseUrl(URL).addConverterFactory(gsonConverterFactory).build();
        ServerAPI serverAPI = retrofit.create(ServerAPI.class);

        Country country = new Country("Káhira9");
        Call<ResponseEntity> call = serverAPI.addCountry(country);
      /*  Call<ResponseEntity> call = serverAPI.addCountry(country);
        try {
            Response<ResponseEntity> response = call.execute();
            System.out.println(response.body().getStatusCode().toString());

        } catch (IOException e) {
            e.printStackTrace();
        }*/
        // System.out.println(responseEntity.getStatusCode());
        call.enqueue(new Callback<ResponseEntity>() {
            @Override
            public void onResponse(Call<ResponseEntity> call, Response<ResponseEntity> response) {
                if (response.isSuccessful()) {
                    ResponseEntity responseEntity = response.body();
                    System.out.println(response.raw().code());
                    if (responseEntity != null && responseEntity.getBody() != null)
                        System.out.println(responseEntity.getBody().toString());
                    System.out.println("FLO LFO PO");
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

    @Bean
    public MongoMeasurementService mongoUserService() {
        return new MongoMeasurementService(mongo);
    }

    @Profile({"devel"})
    @Bean(initMethod = "doProvision")
    public Provisioner provisioner() {
        return new Provisioner();
    }

}