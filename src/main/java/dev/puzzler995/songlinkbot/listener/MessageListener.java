package dev.puzzler995.songlinkbot.listener;

import dev.puzzler995.songlinkbot.props.BotProps;
import dev.puzzler995.songlinkbot.service.SiteManager;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.utils.messages.MessageCreateData;
import org.springframework.stereotype.Component;

import static dev.puzzler995.songlinkbot.util.Constants.NO_MATCH_FOUND_MESSAGE;

@Component
public class MessageListener extends ListenerAdapter {
  private final BotProps botProps;
  private final SiteManager siteManager;

  public MessageListener(BotProps botProps, SiteManager siteManager) {
    super();
    this.siteManager = siteManager;
    this.botProps = botProps;
  }

  @Override
  public void onMessageReceived(MessageReceivedEvent event) {
    if (event.getAuthor().isBot()
        || Boolean.TRUE.equals(!botProps.getEnableCommands().getMessages())) {
      return;
    }
    Message message = event.getMessage();
    var response = siteManager.match(message.getContentRaw());
    for (MessageCreateData m : response) {
      if (!m.getContent().equals(NO_MATCH_FOUND_MESSAGE)) {
        message.reply(m).queue();
      }
    }
  }
}
