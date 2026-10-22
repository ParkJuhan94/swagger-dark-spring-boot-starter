package com.github.parkjuhan94.swaggerdark;

import java.io.IOException;
import java.io.InputStream;
import java.io.UncheckedIOException;
import java.nio.charset.StandardCharsets;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ClassPathResource;

@AutoConfiguration
@ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.SERVLET)
@ConditionalOnClass(name = "org.springdoc.core.configuration.SpringDocConfiguration")
@ConditionalOnProperty(prefix = "swagger-dark", name = "enabled", havingValue = "true", matchIfMissing = true)
@EnableConfigurationProperties(SwaggerDarkProperties.class)
public class SwaggerDarkAutoConfiguration {

    @Bean
    public FilterRegistrationBean<SwaggerDarkFilter> swaggerDarkFilter(SwaggerDarkProperties properties) {
        SwaggerDarkFilter filter = new SwaggerDarkFilter(properties.getIndexPath(), loadCss());
        FilterRegistrationBean<SwaggerDarkFilter> registration = new FilterRegistrationBean<>(filter);
        registration.addUrlPatterns("/*");
        registration.setName("swaggerDarkFilter");
        return registration;
    }

    private static String loadCss() {
        try (InputStream in = new ClassPathResource("swagger-dark.css", SwaggerDarkAutoConfiguration.class.getClassLoader()).getInputStream()) {
            return new String(in.readAllBytes(), StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new UncheckedIOException("swagger-dark.css를 읽을 수 없습니다", e);
        }
    }
}
