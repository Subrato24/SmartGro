package com.smartgro.smartgro.service;

import com.smartgro.smartgro.dto.ShoppingDateSummaryDto;
import com.smartgro.smartgro.entity.ShoppingDateSummary;
import com.smartgro.smartgro.entity.User;
import com.smartgro.smartgro.repository.ShoppingDateSummaryRepository;
import com.smartgro.smartgro.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class ShoppingDateSummaryService {
    private final ShoppingDateSummaryRepository repository;
    private final EmailService emailService;
    private final UserRepository userRepository;

    public ShoppingDateSummaryService(ShoppingDateSummaryRepository repository, EmailService emailService, UserRepository userRepository) {
        this.repository = repository;
        this.emailService = emailService;
        this.userRepository = userRepository;
    }

    public ShoppingDateSummary saveSummary(ShoppingDateSummaryDto dto) {
        ShoppingDateSummary summary = new ShoppingDateSummary();
        summary.setShoppingDate(LocalDate.parse(dto.getShoppingDate()));
        summary.setShopName(dto.getShopName());
        summary.setTotalAmount(dto.getTotalAmount());
        summary.setUserId(dto.getUserId());

        ShoppingDateSummary savedSummary = repository.save(summary);
        sendRecommendationEmail(dto.getUserId());

        return savedSummary;
    }

    public void sendRecommendationEmail(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Find cheapest shop based on totalAmount
        ShoppingDateSummary cheapest = repository.findTopByUserIdOrderByTotalAmountAsc(userId)
                .orElse(null);

        if (cheapest != null) {
            String subject = "Your Best Shop Recommendation";
            String message = String.format(
                    "Hey %s,\n\n" +
                            "Thank you for using our application for your shopping experience!\n" +
                            "Based on your shopping history, we recommend %s as the best shop for you.\n" +
                            "You shopped there on %s with a total of ₹%.2f.\n\n" +
                            "Take advantage of this and happy shopping!",
                    user.getName(),
                    cheapest.getShopName(),
                    cheapest.getShoppingDate(),
                    cheapest.getTotalAmount()
            );

            emailService.sendEmail(user.getEmail(), subject, message);
        }
    }
}
