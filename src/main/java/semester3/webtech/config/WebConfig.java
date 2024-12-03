package semester3.webtech.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
@Configuration
@EnableWebMvc
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(final CorsRegistry registry) {
        registry
                .addMapping("/api/**")
                .allowedMethods("*")
                .allowedOrigins(
                        "http://localhost:5173",
                        "https://webtech-frontend-qfu2.onrender.com"
                );
    }
}
