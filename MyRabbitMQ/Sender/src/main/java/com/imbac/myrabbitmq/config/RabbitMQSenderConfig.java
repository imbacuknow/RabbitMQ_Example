package com.imbac.myrabbitmq.config;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQSenderConfig {

     // set queue, exchange and routingKey
     @Value("${rabbitmq.queue}")
     String queueName;
     @Value("${rabbitmq.exchange}")
     String exchange;
     @Value("${rabbitmq.routingkey}")
     String routingKey;

     // declare queue
     @Bean
     Queue imbacQueue() {
          return new Queue(queueName, false);
     }

     // declare exchange
     @Bean
     DirectExchange exchange() {
          return new DirectExchange(exchange);
     }

     // declare
     @Bean
     Binding imbacBinding(Queue queueName, DirectExchange exchange) {
          return BindingBuilder.bind(queueName).to(exchange).with(routingKey);
     }

     @Bean
     public MessageConverter jsonMessageConverter() {
          return new Jackson2JsonMessageConverter();
     }

     public AmqpTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
          final RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
          rabbitTemplate.setMessageConverter(jsonMessageConverter());
          return rabbitTemplate;
     }
}
