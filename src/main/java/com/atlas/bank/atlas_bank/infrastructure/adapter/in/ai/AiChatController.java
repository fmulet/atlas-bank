package com.atlas.bank.atlas_bank.infrastructure.adapter.in.ai;

import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * AiChatController
 */
@RestController
@RequestMapping("/api/v1/ai")
@RequiredArgsConstructor
public class AiChatController {

  private final ChatClient chatClient;

  @PostMapping("/chat")
  public String chat(@RequestBody String message) {
    return chatClient.prompt().user(message).call().content();
  }
}
