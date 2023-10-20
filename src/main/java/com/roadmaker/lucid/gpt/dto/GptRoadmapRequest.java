package com.roadmaker.lucid.gpt.dto;

import com.theokanning.openai.completion.chat.ChatMessage;

import java.util.List;

public class GptRoadmapRequest {
    private List<ChatMessage> chatMessages;
    
    public List<ChatMessage> getChatMessages() { return chatMessages; }
    public void setChatMessages(List<ChatMessage> chatMessages) { this.chatMessages = chatMessages; }
}
