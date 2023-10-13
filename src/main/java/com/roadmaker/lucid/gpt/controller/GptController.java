package com.roadmaker.lucid.gpt.controller;

import com.roadmaker.lucid.gpt.service.GptService;
import com.theokanning.openai.service.OpenAiService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/gpt")
public class GptController {
    private final GptService gptService;


     }


