package com.example.voting_system.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.twilio.Twilio;
import com.twilio.rest.verify.v2.service.Verification;
import com.twilio.rest.verify.v2.service.VerificationCheck;

import jakarta.annotation.PostConstruct;

@RestController
@RequestMapping("/api")
public class PhoneVerificationController {
    @Value("${TWILIO_ACCOUNT_SID}")
   private String twilioAccountSid;

   @Value("${TWILIO_AUTH_TOKEN}")
   private String twilioAuthToken;

   @Value("${TWILIO_SERVICE_ID}")
   private String twilioServiceId;

   public PhoneVerificationController(){

   }

   // Initialize Twilio once in the constructor or using @PostConstruct
   @PostConstruct
   public void init(){
   }

    @GetMapping("/generateOTP")
    public ResponseEntity<String> generateOTP(){

        Twilio.init(twilioAccountSid, twilioAuthToken);
        System.out.println("\n");
        System.out.println(twilioServiceId);
        System.out.println("\n");

        Verification verification = Verification.creator(
            twilioServiceId,
            "+919359589355",
            "sms")
            .create();
        
        return new ResponseEntity<>("Your oto has been sent successfully.", HttpStatus.OK);
    }

    @GetMapping("/verifyOTP")
    public ResponseEntity<?> verifyUserOTP() throws Exception{
        Twilio.init(twilioAccountSid, twilioAuthToken);
        VerificationCheck verificationCheck = VerificationCheck.creator(twilioServiceId)
            .setTo("+919359589355")
            .setCode("81293")
            .create();

            if ("approved".equals(verificationCheck.getStatus())) {
                return new ResponseEntity<>("Verification completed successfully.", HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Invalid OTP. Please try again.", HttpStatus.BAD_REQUEST);
            }
    }

    
}
