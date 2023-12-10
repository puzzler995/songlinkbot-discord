package dev.puzzler995.songlinkbot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.test.context.TestConfiguration;

@TestConfiguration(proxyBeanMethods = false)
public class TestSongLinkBotApplication {

  public static void main(String[] args) {
    SpringApplication.from(SongLinkBotApplication::main).with(TestSongLinkBotApplication.class).run(args);
  }

}
