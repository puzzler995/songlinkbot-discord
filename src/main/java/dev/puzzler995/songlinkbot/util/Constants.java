package dev.puzzler995.songlinkbot.util;

import java.util.List;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.requests.GatewayIntent;

public final class Constants {

  public static final String CONTEXT_MENU_COMMAND_NAME = "Get Song.Link";
  public static final CommandData CONTEXT_MENU_COMMAND =
      Commands.message(CONTEXT_MENU_COMMAND_NAME);
  public static final String NO_MATCH_FOUND_MESSAGE = "No Matches Found!";
  public static final List<GatewayIntent> REQUIRED_INTENTS =
      List.of(GatewayIntent.GUILD_MESSAGES, GatewayIntent.MESSAGE_CONTENT);
  public static final List<Permission> REQUIRED_PERMISSIONS =
      List.of(
          Permission.MESSAGE_HISTORY,
          Permission.MESSAGE_SEND,
          Permission.MESSAGE_EMBED_LINKS,
          Permission.MESSAGE_ATTACH_FILES,
          Permission.MESSAGE_SEND_IN_THREADS);
  public static final String SLASH_COMMAND_DESCRIPTION = "Finds a link on the song.link service";
  public static final String SLASH_COMMAND_NAME = "songlink";
  public static final String SLASH_COMMAND_PARAMETER_DESCRIPTION =
      "The Streaming url to search for";
  public static final String URL = "url";
  public static final CommandData SLASH_COMMAND =
      Commands.slash(SLASH_COMMAND_NAME, SLASH_COMMAND_DESCRIPTION)
          .addOption(OptionType.STRING, URL, SLASH_COMMAND_PARAMETER_DESCRIPTION);

  private Constants() {}
}
