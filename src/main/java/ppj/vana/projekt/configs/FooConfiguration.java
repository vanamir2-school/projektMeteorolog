package ppj.vana.projekt.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import ppj.vana.projekt.Foo;

@Configuration
public class FooConfiguration {

    @Configuration
    @PropertySource("classpath:app_devel.properties")
    @Profile("devel")
    public static class DevPropertiesLoaderConfiguration {
        @Bean
        public Foo Foo() {
            return new Foo("kachna-devel");
        }
    }

    @Configuration
    @PropertySource("classpath:app_test.properties")
    @Profile("test")
    public static class TestPropertiesLoaderConfiguration {
        @Bean
        public Foo Foo() {
            return new Foo("TEST");
        }
    }

    @Configuration
    @PropertySource("classpath:app_prod.properties")
    @Profile("prod")
    public static class ProdPropertiesLoaderConfiguration {
        @Bean
        public Foo Foo() {
            return new Foo("ovce-prod");
        }
    }

}
