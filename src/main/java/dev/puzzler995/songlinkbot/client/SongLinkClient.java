package dev.puzzler995.songlinkbot.client;

import dev.puzzler995.songlinkbot.props.site.SongLinkProps;
import java.net.URI;
import java.util.List;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriTemplate;
import reactor.core.publisher.Mono;

@Slf4j
@Component
public class SongLinkClient {
  private static final String BASE_URL = "https://api.song.link/v1-alpha.1";
  private static final UriTemplate DETAILED_KEY_URI =
      new UriTemplate(
          BASE_URL
              + "/links?platform={platform}&type={type}&id={id}"
              + "&songIfSingle={songIfSingle}&userCountry={userCountry}&key={key}");
  private static final UriTemplate DETAILED_URI =
      new UriTemplate(
          BASE_URL
              + "/links?platform={platform}&type={type}&id={id}"
              + "&songIfSingle={songIfSingle}&userCountry={userCountry}");
  private static final UriTemplate URL_KEY_URI =
      new UriTemplate(
          BASE_URL
              + "/links?url={url}&songIfSingle={songIfSingle}&userCountry={userCountry}&key={key}");
  private static final UriTemplate URL_URI =
      new UriTemplate(
          BASE_URL + "/links?url={url}&songIfSingle={songIfSingle}&userCountry={userCountry}");

  private final WebClient client;
  private final SongLinkProps props;

  public SongLinkClient(WebClient.Builder builder, SongLinkProps props) {
    this.client = builder.baseUrl(BASE_URL).build();
    this.props = props;
  }

  private static URI buildDetailedKeyUri(
      String platform,
      String type,
      String id,
      Boolean songIfSingle,
      String userCountry,
      String key) {
    return DETAILED_KEY_URI.expand(platform, type, id, songIfSingle, userCountry, key);
  }

  private static URI buildDetailedUri(
      String platform, String type, String id, Boolean songIfSingle, String userCountry) {
    return DETAILED_URI.expand(platform, type, id, songIfSingle, userCountry);
  }

  private static URI buildUrlKeyUri(
      String url, Boolean songIfSingle, String userCountry, String key) {
    return URL_KEY_URI.expand(url, songIfSingle, userCountry, key);
  }

  private static URI buildUrlUri(String url, Boolean songIfSingle, String userCountry) {
    return URL_URI.expand(url, songIfSingle, userCountry);
  }

  @Cacheable("url")
  public Mono<SongResponse> getSong(String platform, String type, String id) {
    var apiKey = props.getApiKey();
    if (StringUtils.isNotEmpty(apiKey)) {
      return getSong(
          buildDetailedKeyUri(
              platform, type, id, props.getSongIfSingle(), props.getUserCountry(), apiKey));
    } else {
      return getSong(
          buildDetailedUri(platform, type, id, props.getSongIfSingle(), props.getUserCountry()));
    }
  }

  @Cacheable("url")
  public Mono<SongResponse> getSong(String url) {
    var apiKey = props.getApiKey();
    if (StringUtils.isNotEmpty(apiKey)) {
      return getSong(buildUrlKeyUri(url, props.getSongIfSingle(), props.getUserCountry(), apiKey));
    } else {
      return getSong(buildUrlUri(url, props.getSongIfSingle(), props.getUserCountry()));
    }
  }

  private Mono<SongResponse> getSong(URI uri) {
    return this.client
        .get()
        .uri(uri)
        .exchangeToMono(
            (ClientResponse clientResponse) -> {
              if (clientResponse.statusCode() == HttpStatus.NOT_FOUND) {
                return Mono.empty();
              } else {
                return clientResponse.bodyToMono(SongResponse.class);
              }
            });
  }

  public record PlatformLink(
      String country,
      String url,
      String entityUniqueId,
      String nativeAppUriMobile,
      String nativeAppUriDesktop) {}

  public record SongEntity(
      String id,
      String title,
      String artistName,
      String thumbnailUrl,
      Integer thumbnailWidth,
      Integer thumbnailHeight,
      String apiProvider,
      List<String> platforms) {}

  public record SongResponse(
      String entityUniqueId,
      String userCountry,
      String pageUrl,
      Map<String, PlatformLink> linksByPlatform,
      Map<String, SongEntity> entitiesByUniqueId) {}
}
