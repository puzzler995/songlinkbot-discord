package dev.puzzler995.songlinkbot.site;

import dev.puzzler995.songlinkbot.client.SongLinkClient.SongResponse;
import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.MatchResult;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.utils.messages.MessageCreateBuilder;
import net.dv8tion.jda.api.utils.messages.MessageCreateData;

@Getter
@Slf4j
public abstract class AbstractBaseSite {
  private final String identifier = "Base";

  private final String pattern = "base";

  private final Color color = Color.YELLOW;

  public MessageCreateData buildMessage(SongResponse songResponse, String entityKey) {
    MessageCreateBuilder messageBuilder = new MessageCreateBuilder();
    var song = songResponse.entitiesByUniqueId().getOrDefault(entityKey, null);
    if (song == null) {
      log.error("Song is null");
      return messageBuilder.build();
    }
    messageBuilder.setContent(
        "[**"
            + song.title()
            + "** by **"
            + song.artistName()
            + "**]("
            + songResponse.pageUrl()
            + ")");
    var embed =
        new EmbedBuilder()
            .setTitle(song.title() + " by " + song.artistName())
            .setThumbnail(song.thumbnailUrl())
            .setUrl(songResponse.pageUrl())
            .build();
    messageBuilder.setEmbeds(embed);
    return messageBuilder.build();
  }

  public List<MatchResult> match(String message) {
    List<MatchResult> matchResults = new ArrayList<>();
    Pattern p = Pattern.compile(getPattern(), Pattern.CASE_INSENSITIVE | Pattern.MULTILINE);
    Matcher matcher = p.matcher(message);

    while (matcher.find()) {
      matchResults.add(matcher.toMatchResult());
    }
    return matchResults;
  }

  public abstract MessageCreateData process(MatchResult match);
}
