package com.diploma.backend.model.converters;

import com.fasterxml.jackson.annotation.JsonCreator;
import org.springframework.core.convert.converter.Converter;
import org.springframework.core.convert.converter.ConverterFactory;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Optional;

@Component
@SuppressWarnings("unchecked")
public class EnumFromStringConverterFactory implements ConverterFactory<String, Enum> {

    @Override
    public <T extends Enum> Converter<String, T> getConverter(Class<T> targetType) {
        return new EnumFromStringConverter(targetType);
    }

    private static class EnumFromStringConverter<T extends Enum> implements Converter<String, T> {

        private Class<T> enumType;

        EnumFromStringConverter(Class<T> enumType) {
            this.enumType = enumType;
        }

        public T convert(@Nullable String source) {

            Method method = Arrays.stream(this.enumType.getDeclaredMethods())
                    .filter(m -> m.isAnnotationPresent(JsonCreator.class))
                    .filter(m -> m.getReturnType().equals(enumType))
                    .findAny().orElse(null);

            if (method != null)
                return (T) Arrays.stream(this.enumType.getEnumConstants()).findAny()
                        .map(o -> ReflectionUtils.invokeMethod(method, o, source)).orElse(null);

            return (T) Enum.valueOf(this.enumType, Optional.ofNullable(source).map(String::trim).orElse(""));
        }
    }
}

