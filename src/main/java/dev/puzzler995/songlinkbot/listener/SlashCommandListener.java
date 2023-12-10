package dev.puzzler995.songlinkbot.listener;

import dev.puzzler995.songlinkbot.service.SiteManager;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.springframework.stereotype.Service;

import java.util.Objects;

import static dev.puzzler995.songlinkbot.util.Constants.SLASH_COMMAND_NAME;
import static dev.puzzler995.songlinkbot.util.Constants.URL;

@Service
public class SlashCommandListener extends ListenerAdapter {
  private final SiteManager siteManager;
  public SlashCommandListener(SiteManager siteManager) {
    super();
    this.siteManager = siteManager;
  }
  @Override
  public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {
    if (event.getName().equals(SLASH_COMMAND_NAME)) {
      event.deferReply().queue();
      String url = Objects.requireNonNull(event.getOption(URL)).getAsString();
      var response = siteManager.match(url);
      event.getHook().sendMessage(response.getFirst()).queue();
    }
  }
}
