package ppj.vana.projekt.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import ppj.vana.projekt.Foo;

import java.util.Arrays;

// Configuration class for test purpores. It creates contructor by active profile and set note by profil note in .config.

// @Configuration Indicates that a class declares one or more @Bean methods and may be processed by the Spring container
// to generate bean definitions and service requests for those beans at runtime

// @Component Indicates that an annotated class is a "component". Such classes are considered as candidates for
// auto-detection when using annotation-based configuration and classpath scanning.

@Configuration
@ConfigurationProperties(prefix = "app")
public class FooConfiguration {

    private final Environment environment;

    private String note;

    public FooConfiguration(Environment environment) {
        this.environment = environment;
    }

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

    @PropertySource("classpath:app_prod.properties")
    @Profile({"prod"})
    public class ProdPropertiesLoaderConfiguration {
        @Bean
        public Foo Foo() {
            return new Foo("Prod - Profil: " + String.join(",", Arrays.asList(environment.getActiveProfiles())) + " - " + note);
        }
    }

}
