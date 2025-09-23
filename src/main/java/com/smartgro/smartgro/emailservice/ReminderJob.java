package com.smartgro.smartgro.emailservice;

import com.smartgro.smartgro.repository.UserRepository;
import com.smartgro.smartgro.service.SuggestionService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ReminderJob {
    private final SuggestionService suggestionService;
    private final UserRepository userRepository;
    private final MailService mailService;

    public ReminderJob(SuggestionService suggestionService, UserRepository userRepository, MailService mailService) {
        this.suggestionService = suggestionService;
        this.userRepository = userRepository;
        this.mailService = mailService;
    }

    // 09:30 every day Asia/Kolkata
    @Scheduled(cron = "0 30 9 * * *", zone = "Asia/Kolkata")
    public void sendDailySuggestions() {
        var users = userRepository.findAll();
        for (var u : users) {
            var suggestions = suggestionService.suggestForUser(u.getId(), 10);
            if (!suggestions.isEmpty()) {
                mailService.sendSuggestionEmail(u.getEmail(), suggestions);
            }
        }
    }
}
