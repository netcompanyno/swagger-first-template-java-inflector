package no.nc.config;


import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.joda.JodaModule;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import io.swagger.inflector.SwaggerInflector;
import io.swagger.util.Json;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class InflectorConfig {

    @Bean
    @ConfigurationProperties(prefix = "swagger-inflector") //Using Spring to populate the inflector Configuration object
    public io.swagger.inflector.config.Configuration inflectorConfiguration(ApplicationContext applicationContext) {

        //Swagger Inflector (sadly) uses it's own ObjectMapper singleton. Let's configure it.
        Json.mapper().registerModule(new JavaTimeModule());
        Json.mapper().registerModule(new JodaModule());
        Json.mapper().registerModule(new Jdk8Module());

        io.swagger.inflector.config.Configuration configuration = io.swagger.inflector.config.Configuration.defaultConfiguration();
        configuration.setControllerFactory((cls, operation) -> applicationContext.getBean(cls));

        return configuration;
    }

    @Bean
    public SwaggerInflector swaggerInflector(io.swagger.inflector.config.Configuration configuration) {
        return new SwaggerInflector(configuration);
    }
}
