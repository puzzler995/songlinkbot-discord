package dev.puzzler995.songlinkbot.props.site;

import dev.puzzler995.songlinkbot.util.MultipleYamlPropertySourceFactory;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@ConfigurationProperties(prefix = "settings.sites.songlink")
@PropertySource(
    value = "classpath:application-secrets.yml",
    factory = MultipleYamlPropertySourceFactory.class)
@Getter
@Setter
public class SongLinkProps {
  private String apiKey;
  private Boolean songIfSingle = true;
  private String userCountry = "US";
}
