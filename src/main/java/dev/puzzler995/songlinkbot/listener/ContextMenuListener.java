package dev.puzzler995.songlinkbot.listener;

import dev.puzzler995.songlinkbot.service.SiteManager;
import net.dv8tion.jda.api.events.interaction.command.MessageContextInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.utils.messages.MessageCreateData;
import org.springframework.stereotype.Component;

import static dev.puzzler995.songlinkbot.util.Constants.CONTEXT_MENU_COMMAND_NAME;

@Component
public class ContextMenuListener extends ListenerAdapter {
  private final SiteManager siteManager;

  public ContextMenuListener(SiteManager siteManager) {
    super();
    this.siteManager = siteManager;
  }
  @Override
  public void onMessageContextInteraction(MessageContextInteractionEvent event) {
    if (event.getName().equals(CONTEXT_MENU_COMMAND_NAME)) {
      event.deferReply().queue();
      var response = siteManager.match(event.getTarget().getContentRaw());
      for (MessageCreateData message : response) {
        event.getHook().sendMessage(message).queue();
      }
    }
  }
}
