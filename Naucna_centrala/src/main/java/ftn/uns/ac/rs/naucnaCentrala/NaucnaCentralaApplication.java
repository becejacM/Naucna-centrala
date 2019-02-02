package ftn.uns.ac.rs.naucnaCentrala;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import javax.sql.DataSource;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import ftn.uns.ac.rs.naucnaCentrala.elasticSearch.model.StorageProperties;
import ftn.uns.ac.rs.naucnaCentrala.elasticSearch.service.ESPaperService;


@Configuration
@EnableWebMvc
@SpringBootApplication
@EnableConfigurationProperties(StorageProperties.class)
public class NaucnaCentralaApplication {

	@Autowired
    private ElasticsearchTemplate elasticsearchTemplate;

	public static void main(String[] args) {
		SpringApplication.run(NaucnaCentralaApplication.class, args);
	}

	@Bean
	public FilterRegistrationBean corsFilter() {
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		CorsConfiguration config = new CorsConfiguration();
		config.setAllowCredentials(true);
		config.addAllowedOrigin("*");
		config.addAllowedHeader("*");
		config.addAllowedMethod("*");
		source.registerCorsConfiguration("/**", config);
		FilterRegistrationBean bean = new FilterRegistrationBean(new CorsFilter(source));
		bean.setOrder(0);
		return bean;
	}
	
	@Bean
    CommandLineRunner init(ESPaperService eBookService) {
        return (args) -> {
            eBookService.init();
            if (!elasticsearchTemplate.indexExists("naucnacentrala6")) {
                createIndex();
                eBookService.initializeIndexFiles();
            }
            	
        };
    }

    private void createIndex() throws IOException {
        String indexSettings = new String (Files.readAllBytes(Paths.get("src/main/resources/indexsettings.json")));
        elasticsearchTemplate.createIndex("naucnacentrala6", indexSettings);

        String mappingSettings = new String (Files.readAllBytes(Paths.get("src/main/resources/mapping.json")));
        elasticsearchTemplate.putMapping("naucnacentrala6", "paperindexunit", mappingSettings);
    }
}

