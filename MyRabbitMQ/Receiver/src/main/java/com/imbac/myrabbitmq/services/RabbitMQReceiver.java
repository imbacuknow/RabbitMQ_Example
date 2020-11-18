package com.imbac.myrabbitmq.services;

import java.util.Properties;

import javax.mail.*;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.InternetAddress;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.imbac.myrabbitmq.model.PersonalCard;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import net.minidev.json.JSONObject;

@Component
public class RabbitMQReceiver {

     @Autowired
     private SendMail sendMail;

     @RabbitListener(queues = "${rabbitmq.queue}")
     public void receiveMessage(PersonalCard personalCard) {
          // JSONObject jo = new JSONObject();
          // jo.put("first_name", personalCard.getFirst_name());
          // jo.put("last_name", personalCard.getLast_name());
          // jo.put("personal_id", personalCard.getPersonal_id());
          // System.out.println("Recieved Message From RabbitMQ: \n" + jo + "\n");

          Gson gson = new GsonBuilder().setPrettyPrinting().create();
          String json = gson.toJson(personalCard);
          System.out.println("Recieved Message From RabbitMQ: \n" + json + "\n");

          sendMail.receiveJsonAndSendMail(json);
     }

}
