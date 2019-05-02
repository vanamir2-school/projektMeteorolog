package ppj.vana.projekt;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;
import ppj.vana.projekt.providers.SqlProvider;

/**
 * Extends SpringBootServletInitializer must be declared. otherwise TomCat server does not work.
 * To check (and log) which profile is active - add "ctx.getBean(Foo.class).printLog();".
 */
@SpringBootApplication
@EnableScheduling
@EnableJpaRepositories("ppj.vana.projekt")
public class Main extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }

    @Profile({"devel,prod"})
    @Bean(initMethod = "doProvision")
    public SqlProvider provisioner() {
        return new SqlProvider();
    }

}