package com.imbac.myrabbitmq.controller;

import com.imbac.myrabbitmq.entity.PersonalCard;
import com.imbac.myrabbitmq.services.RabbitMQSender;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/rabbitmq")
public class RabbitMQController {

     @Autowired
     private RabbitMQSender rabbitMQSender;

     @GetMapping("/sender")
     public String sender(@RequestParam("first_name") String first_name, @RequestParam("last_name") String last_name,
               @RequestParam("personal_id") String personal_id) {

          PersonalCard pc = new PersonalCard();
          pc.setFirst_name(first_name);
          pc.setLast_name(last_name);
          pc.setPersonal_id(personal_id);

          rabbitMQSender.sendMessageDataToQueue(pc);

          return "Message sent to RabbitMQ Successfully";
     }
}
