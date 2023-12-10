package dev.puzzler995.songlinkbot.util;

import dev.puzzler995.songlinkbot.props.BotProps;
import jakarta.annotation.PostConstruct;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.sharding.ShardManager;
import org.springframework.stereotype.Component;

@Component
public class CommandRegistrar {
  private final BotProps botProps;
  private final ShardManager shardManager;

  public CommandRegistrar(BotProps botProps, ShardManager shardManager) {
    this.botProps = botProps;
    this.shardManager = shardManager;
  }

  @PostConstruct
  private void registerGlobalCommands() {
    shardManager
        .getShards()
        .forEach(
            (JDA jda) -> {
              if (Boolean.TRUE.equals(botProps.getEnableCommands().getSlash())) {
                jda.updateCommands().addCommands(Constants.SLASH_COMMAND).queue();
              }
              if (Boolean.TRUE.equals(botProps.getEnableCommands().getContextMenu())) {
                jda.updateCommands().addCommands(Constants.CONTEXT_MENU_COMMAND).queue();
              }
            });
  }
}
