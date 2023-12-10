package dev.puzzler995.songlinkbot.site;

import dev.puzzler995.songlinkbot.client.SongLinkClient;
import java.awt.Color;
import java.util.regex.MatchResult;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import net.dv8tion.jda.api.utils.messages.MessageCreateBuilder;
import net.dv8tion.jda.api.utils.messages.MessageCreateData;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import static dev.puzzler995.songlinkbot.util.Constants.NO_MATCH_FOUND_MESSAGE;

@Slf4j
@Component
public class Spotify extends AbstractBaseSite {
  @Getter private final Color color = Color.GREEN;
  @Getter private final String identifier = "Spotify";

  @Getter
  private final String pattern =
      "https:\\/\\/open\\.spotify\\.com\\/(?<type>.*)\\/(?<id>[a-zA-Z0-9]+)";

  private final SongLinkClient songLinkClient;

  public Spotify(SongLinkClient songLinkClient) {
    this.songLinkClient = songLinkClient;
  }

  @Override
  public MessageCreateData process(MatchResult match) {
    var id = match.group("id");
    var type = match.group("type");
    String entityKey;
    if (StringUtils.equalsIgnoreCase(type, "track")) {
      entityKey = "SPOTIFY_SONG::" + id;
      type = "song";
    } else if (StringUtils.equalsIgnoreCase(type, "album")) {
      entityKey = "SPOTIFY_ALBUM::" + id;
    } else {
      log.error("Unknown type: {}", type);
      return null;
    }
    var songResponse = songLinkClient.getSong("spotify", type, id).block();
    if (songResponse == null) {
      log.error("SongLink is null");
      return new MessageCreateBuilder().setContent(NO_MATCH_FOUND_MESSAGE).build();
    }
    return this.buildMessage(songResponse, entityKey);
  }
}
