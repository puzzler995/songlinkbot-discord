package dev.puzzler995.songlinkbot.props;

import java.util.Collections;
import java.util.List;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "settings.bot")
@Getter
@Setter
public class BotProps {

  private String activityMessage = "all the streaming services!";
  private Boolean defaultSendMatchedMessage;
  private List<String> disabledSites = Collections.emptyList();
  private EnableCommands enableCommands;

  @Configuration
  @Getter
  @Setter
  public static class EnableCommands {
    private Boolean contextMenu;
    private Boolean messages;
    private Boolean slash;
  }
}
