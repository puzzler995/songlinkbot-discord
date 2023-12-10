package dev.puzzler995.songlinkbot.util;

import java.io.IOException;
import java.util.Objects;
import java.util.Properties;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.config.YamlPropertiesFactoryBean;
import org.springframework.core.env.PropertiesPropertySource;
import org.springframework.core.env.PropertySource;
import org.springframework.core.io.support.EncodedResource;
import org.springframework.core.io.support.PropertySourceFactory;

public class MultipleYamlPropertySourceFactory implements PropertySourceFactory {
  @NotNull
  @Override
  public PropertySource<?> createPropertySource(String name, EncodedResource encodedResource)
      throws IOException {
    YamlPropertiesFactoryBean factory = new YamlPropertiesFactoryBean();
    factory.setResources(encodedResource.getResource());
    Properties properties = factory.getObject();
    return new PropertiesPropertySource(
        Objects.requireNonNull(encodedResource.getResource().getFilename()),
        Objects.requireNonNull(properties));
  }
}
