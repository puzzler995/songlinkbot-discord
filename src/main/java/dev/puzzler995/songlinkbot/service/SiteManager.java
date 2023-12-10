package dev.puzzler995.songlinkbot.service;

import dev.puzzler995.songlinkbot.props.BotProps;
import dev.puzzler995.songlinkbot.site.AbstractBaseSite;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.MatchResult;
import lombok.extern.slf4j.Slf4j;
import net.dv8tion.jda.api.utils.messages.MessageCreateBuilder;
import net.dv8tion.jda.api.utils.messages.MessageCreateData;
import org.springframework.stereotype.Service;

import static dev.puzzler995.songlinkbot.util.Constants.NO_MATCH_FOUND_MESSAGE;

@Service
@Slf4j
public class SiteManager {
  private final BotProps botProps;
  private final List<AbstractBaseSite> siteList;

  public SiteManager(BotProps botProps, List<AbstractBaseSite> siteList) {
    this.botProps = botProps;
    this.siteList = new ArrayList<>(siteList);
  }

  public List<MessageCreateData> match(String message) {
    var results = new ArrayList<MessageCreateData>();
    for (AbstractBaseSite site : siteList) {
      if (botProps.getDisabledSites().contains(site.getIdentifier())) {
        continue;
      }
      var matches = site.match(message);
      for (MatchResult match : matches) {
        results.add(site.process(match));
      }
    }
    if (results.isEmpty()) {
      results.add(new MessageCreateBuilder().setContent(NO_MATCH_FOUND_MESSAGE).build());
    }
    return results;
  }
}
