package dev.puzzler995.songlinkbot;

import dev.puzzler995.songlinkbot.props.BotProps;
import dev.puzzler995.songlinkbot.util.Constants;
import dev.puzzler995.songlinkbot.util.MultipleYamlPropertySourceFactory;
import java.util.List;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.sharding.DefaultShardManagerBuilder;
import net.dv8tion.jda.api.sharding.ShardManager;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource(
    value = "classpath:application-secrets.yml",
    factory = MultipleYamlPropertySourceFactory.class)
public class BeanConfig {
  @Value("${settings.bot.discord-token}")
  private String discordToken;

  @Bean
  public <T extends ListenerAdapter> ShardManager api(BotProps botProps, List<T> eventListeners) {
    var api =
        DefaultShardManagerBuilder.createLight(discordToken)
            .enableIntents(Constants.REQUIRED_INTENTS)
            .setActivity(Activity.listening(botProps.getActivityMessage()));
    for (var listener : eventListeners) {
      api.addEventListeners(listener);
    }
    return api.build();
  }
}
