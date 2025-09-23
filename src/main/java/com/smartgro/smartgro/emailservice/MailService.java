package com.smartgro.smartgro.emailservice;

import com.smartgro.smartgro.dto.SuggestionDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MailService {
    private final JavaMailSender mailSender;

    public MailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void sendSuggestionEmail(String to, List<SuggestionDTO> suggestions) {
        StringBuilder body = new StringBuilder();
        body.append("Hi,\n\nHere are your smart grocery tips based on the last 3 months:\n\n");
        body.append(String.format("%-25s %-12s %-20s %-12s %-12s%n",
                "Item", "BestPrice", "BestShop", "YourAvg", "Save/mo"));

        for (var s : suggestions) {
            body.append(String.format("%-25s %-12s %-20s %-12s %-12s%n",
                    s.getItemName(),
                    s.getBestAvgPrice(),
                    s.getShopName(),
                    s.getUserAvgPrice(),
                    s.getEstimatedMonthlySaving()));
        }
        body.append("\n— SmartGrocery\n");

        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setTo(to);
        msg.setSubject("Smart Grocery: Best deals for you");
        msg.setText(body.toString());
        mailSender.send(msg);
    }
}
