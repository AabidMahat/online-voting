package com.example.voting_system.services;

import com.twilio.Twilio;
import com.twilio.rest.verify.v2.service.Verification;
import com.twilio.rest.verify.v2.service.VerificationCheck;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class PhoneVerificationService {

    @Value("${TWILIO_ACCOUNT_SID}")
    private String twilioAccountSid;

    @Value("${TWILIO_AUTH_TOKEN}")
    private String twilioAuthToken;

    @Value("${TWILIO_SERVICE_ID}")
    private String twilioServiceId;

    @PostConstruct
    public void init() {
        
    }

    public String sendOTP(String phoneNumber) {
        Twilio.init(twilioAccountSid, twilioAuthToken);
        try {
            Verification verification = Verification.creator(
                    twilioServiceId, "+91"+phoneNumber, "sms").create();
            return verification.getStatus();
        } catch (Exception e) {
            System.out.println("\n");
            System.out.println("exception: "+e.getMessage());
            System.out.println("\n");
            return null;
        }
    }

    public boolean verifyOTP(String phoneNumber, String otpCode) {
        Twilio.init(twilioAccountSid, twilioAuthToken);
        try {
            VerificationCheck verificationCheck = VerificationCheck.creator(twilioServiceId)
                    .setTo("+91"+phoneNumber)
                    .setCode(otpCode)
                    .create();
            return "approved".equals(verificationCheck.getStatus());
        } catch (Exception e) {
            System.out.println("\n");
        System.out.println("exception: "+e.getMessage());
        System.out.println("\n");
            return false;
        }
    }
}

