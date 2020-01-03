package com.diploma.backend.config;

import com.diploma.backend.model.converters.EnumFromStringConverterFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.format.datetime.DateFormatter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class WebConfig implements WebMvcConfigurer {

    @Value("${date.pattern}")
    private String datePattern;
    private final EnumFromStringConverterFactory enumFromStringConverterFactory;

    @Override
    public void addFormatters(FormatterRegistry registry) {
        DateFormatter dateFormatter = new DateFormatter();
        dateFormatter.setPattern(datePattern);
        registry.addFormatter(dateFormatter);

        registry.addConverterFactory(enumFromStringConverterFactory);
    }

}
