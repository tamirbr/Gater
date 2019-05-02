package online.gater.config;

import java.util.concurrent.TimeUnit;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;


@Configuration
@EnableConfigurationProperties
@PropertySource("classpath:app.properties")
public class AppConfig implements WebMvcConfigurer {

    @Bean
    public InternalResourceViewResolver viewResolver(){
        InternalResourceViewResolver internalResourceViewResolver = new InternalResourceViewResolver();
        internalResourceViewResolver.setPrefix("/");
        internalResourceViewResolver.setSuffix(".html");
        return  internalResourceViewResolver;
    }

    @Override
    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
        configurer.enable();
    }

    @Override
    public void addResourceHandlers(final ResourceHandlerRegistry registry) {
    	int cacheDuration = (int) TimeUnit.SECONDS.convert(7, TimeUnit.DAYS);
		registry
	        .addResourceHandler("/resources/**")
	        .addResourceLocations("/resources/")
	        .setCachePeriod(cacheDuration);
        
        registry.addResourceHandler("/static/**")
	        .addResourceLocations("classpath:/static/", "classpath:/static/**")
	        .setCachePeriod(cacheDuration);
        
        registry.addResourceHandler("/materialize/**")
	        .addResourceLocations("classpath:/materialize/**")
	        .setCachePeriod(cacheDuration);
        
        registry.addResourceHandler("/materialize/**")
		.addResourceLocations("classpath:/static/materialize/");
    }
    
}
