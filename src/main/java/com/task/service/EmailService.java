package com.task.service;

import com.sendgrid.*;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;
import com.task.controller.EmployeeController;
import com.task.dto.VendorDTO;
import com.task.entity.Sms;
import com.task.entity.Vendor;
import com.task.repo.SmsRepository;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class EmailService {

    @Autowired
    SmsRepository smsRepository;

    public void sendEmailToVendors(Vendor vendor, Logger log) throws IOException {
        String message = String.format("Sending payments to vendor %s at UPI %s", vendor.getName(), vendor.getUpi());
        sendEmailToVendor(vendor, message, log);

    }

    private void sendEmailToVendor(Vendor vendor, String message, Logger log) throws IOException {
        log.info("Entering in {} and sendEmailToVendor", EmailService.class.getName());
        Email from = new Email("example@gmail.com");
        String subject = "Payment Notification";
        Email to = new Email(vendor.getEmail());
        Content content = new Content("text/plain", message);
        Mail mail = new Mail(from, subject, to, content);

        SendGrid sg = new SendGrid("API-KEY");
        Request request = new Request();
        try {
            request.setMethod(Method.POST);
            request.setEndpoint("mail/send");
            request.setBody(mail.build());
            Response response = sg.api(request);
            Sms sms=new Sms();
            sms.setVendorEmail(vendor.getEmail());
            sms.setVendorMesg(message);
            sms.setVendorName(vendor.getName());
            if(response.getStatusCode()==202){
                sms.setMesgStatus("SUCCESS");
            }else{
                sms.setMesgStatus("FAILED");
            }
            smsRepository.save(sms);
            log.info("Exiting from {} and sendEmailToVendor", EmailService.class.getName());
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}



