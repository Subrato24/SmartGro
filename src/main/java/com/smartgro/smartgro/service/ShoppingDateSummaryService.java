package com.smartgro.smartgro.service;

import com.smartgro.smartgro.dto.ShoppingDateSummaryDto;
import com.smartgro.smartgro.entity.ShoppingDateSummary;
import com.smartgro.smartgro.entity.User;
import com.smartgro.smartgro.repository.ShoppingDateSummaryRepository;
import com.smartgro.smartgro.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;

@Service
public class ShoppingDateSummaryService {
    private final ShoppingDateSummaryRepository repository;
    private final EmailService emailService;
    private final UserRepository userRepository;
    private static final Logger logger = LoggerFactory.getLogger(ShoppingDateSummaryService.class);

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
        try {
            User user = userRepository.findById(userId)
                    .orElseThrow(() -> new RuntimeException("User not found"));

            ShoppingDateSummary cheapest = repository.findTopByUserIdOrderByTotalAmountAsc(userId)
                    .orElse(null);

            if (cheapest != null) {
                String subject = "Your Best Shop Recommendation";
                String message = String.format(
                        "Hey %s,\n\nThank you for using our application!\n" +
                                "We recommend %s as the best shop for you.\n" +
                                "You shopped there on %s with a total of ₹%.2f.\n\nHappy shopping!",
                        user.getName(),
                        cheapest.getShopName(),
                        cheapest.getShoppingDate(),
                        cheapest.getTotalAmount()
                );

                emailService.sendEmail(user.getEmail(), subject, message);
                logger.info("Recommendation email sent to {}", user.getEmail());
            } else {
                logger.info("No shopping summary found for userId {}", userId);
            }
        } catch (Exception e) {
            logger.error("Error in sending recommendation email for userId {}: {}", userId, e.getMessage(), e);
        }
    }

}
