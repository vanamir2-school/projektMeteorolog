package ppj.vana.projekt.configs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import ppj.vana.projekt.Foo;

import java.util.Arrays;

// Testovaci konfiguracni trida ktera vytváří kontruktor dle profilu a stejně tak nastavuje field "note" dle profilu

// @Configuration Indicates that a class declares one or more @Bean methods and may be processed by the Spring container
// to generate bean definitions and service requests for those beans at runtime

// @Component Indicates that an annotated class is a "component". Such classes are considered as candidates for
// auto-detection when using annotation-based configuration and classpath scanning.

@Configuration
@ConfigurationProperties(prefix = "app")
public class FooConfiguration {

    @Autowired
    private Environment environment;
    private String note;

    public void setNote(String note) {
        this.note = note;
    }

    @PropertySource("classpath:app_devel.properties")
    @Profile({"devel"})
    public class DevPropertiesLoaderConfiguration {
        @Bean
        public Foo Foo() {
            return new Foo("Dev - Profil: " + String.join(",", Arrays.asList(environment.getActiveProfiles())) + " - " + note);
        }
    }

    @PropertySource("classpath:app_test.properties")
    @Profile({"test"})
    public class TestPropertiesLoaderConfiguration {
        @Bean
        public Foo Foo() {
            return new Foo("Test - Profil: " + String.join(",", Arrays.asList(environment.getActiveProfiles())) + " - " + note);
        }
    }

    @PropertySource("classpath:app_prod.properties")
    @Profile({"prod"})
    public class ProdPropertiesLoaderConfiguration {
        @Bean
        public Foo Foo() {
            return new Foo("Prod - Profil: " + String.join(",", Arrays.asList(environment.getActiveProfiles())) + " - " + note);
        }
    }

}
