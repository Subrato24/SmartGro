package com.smartgro.smartgro.controller;

import com.smartgro.smartgro.customexceptions.userNotFound;
import com.smartgro.smartgro.dto.SuggestionDTO;
import com.smartgro.smartgro.dto.UserDto;
import com.smartgro.smartgro.emailservice.MailService;
import com.smartgro.smartgro.repository.UserRepository;
import com.smartgro.smartgro.service.SuggestionService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/suggestions")
@RequiredArgsConstructor
public class SuggestionController {
    private final SuggestionService suggestionService;
    private final UserRepository userRepo;

    public SuggestionController(SuggestionService suggestionService, UserRepository userRepo) {
        this.suggestionService = suggestionService;
        this.userRepo = userRepo;
    }

    @GetMapping("/mine")
    public List<SuggestionDTO> mySuggestions(@RequestBody UserDto user) {
        Long userId = userRepo.findByEmail(user.getEmail()).orElseThrow(()-> new userNotFound(user.getEmail())).getId();
        return suggestionService.suggestForUser(userId, 10);
    }

    @PostMapping("/send-now")
    public void sendNow(@AuthenticationPrincipal(expression = "username") String email,
                        MailService mailService) {
        var user = userRepo.findByEmail(email).orElseThrow();
        var suggestions = suggestionService.suggestForUser(user.getId(), 10);
        if (!suggestions.isEmpty()) {
            mailService.sendSuggestionEmail(user.getEmail(), suggestions);
        }
    }
}
