package com.TallerMecanicoSpring.TM;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
public class TallerMecanicoApplication {

	public static void main(String[] args) {
		SpringApplication.run(TallerMecanicoApplication.class, args);
	}
        
//        @Bean
//        public WebMvcConfigurer corsCinfigurer(){
//          return new WebMvcConfigurer(){
//            @Override
//            public void addCorsMappings(CorsRegistry registry){
//              registry.addMapping("/**").allowedOrigins("http://loclhost:4200").allowedMethods("*").allowedHeaders("*");
//            }
//          };
//        }
        
        @Bean
    public CorsFilter corsFilter() {
            UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
            CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        config.addAllowedOrigin("http://localhost:4200"); // Reemplaza con la URL de tu aplicación Angular
        config.addAllowedHeader("*");
        config.addAllowedMethod("*");
        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);
    }
}
