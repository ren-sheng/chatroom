package org.example.chatroom.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.text.SimpleDateFormat;
import java.util.Date;

@Configuration
public class DateConverterConfiguration implements WebMvcConfigurer {

    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter(String.class, Date.class, source -> {
            try {
                return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(source);
            } catch (Exception e) {
                return null;
            }
        });
    }
}