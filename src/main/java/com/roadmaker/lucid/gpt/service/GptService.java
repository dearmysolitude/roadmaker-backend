package com.roadmaker.lucid.gpt.service;

import com.theokanning.openai.completion.chat.ChatCompletionRequest;
import com.theokanning.openai.completion.chat.ChatMessage;
import com.theokanning.openai.service.OpenAiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

@Service
public class GptService {
    @Autowired
    ThreadPoolTaskExecutor executor;

    private final OpenAiService service;
    private final String gptModel = "gpt-3.5-turbo";
    private static final Duration DURATION = Duration.ofSeconds(120);

    public GptService(@Value("${gpt.api-key}") String apiKey) {
        this.service = new OpenAiService(apiKey, DURATION);
    }

    private List<ChatMessage> getMessage(String content1, String content2) {
        List<ChatMessage> messages = new ArrayList<>();

        ChatMessage message1 = new ChatMessage("system", content1);
        ChatMessage message2 = new ChatMessage("user", content2);

        messages.add(message1);
        messages.add(message2);

        return messages;
    }

    public String getGptAnswer(String content1, String Content2) {
        ChatCompletionRequest completionRequest = ChatCompletionRequest.builder()
                .messages(getMessage(content1, Content2))
                .model(gptModel)
                .temperature(0.2)
                .build();

        return service.createChatCompletion(completionRequest).getChoices().get(0).getMessage().getContent();
    }

//    public List<RoadmapData>
}
