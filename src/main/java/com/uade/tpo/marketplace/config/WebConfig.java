package com.uade.tpo.marketplace.config;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // Este método le dice a Spring dónde buscar los archivos de imagen
        registry.addResourceHandler("/uploads/**")
                .addResourceLocations("file:uploads/"); 
        // "file:" indica que es una carpeta local, no dentro de src/main/resources
    }
}
