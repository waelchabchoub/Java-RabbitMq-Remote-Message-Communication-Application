/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.rabbitmqapp;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import java.io.IOException;
import java.net.URISyntaxException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.util.concurrent.TimeoutException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author user
 */
public class Producer {
    public static void main(String[] args) throws URISyntaxException, NoSuchAlgorithmException, KeyManagementException {
        ConnectionFactory factory = new ConnectionFactory(); //connect to the rabbitmq provider
        factory.setUri("amqps://gprkrobt:hp-C1KY7n5lNyvMNvXZ04NnjxwqSnvgJ@rat.rmq2.cloudamqp.com/gprkrobt"); //hosted rabbitmq
        try(Connection connection = factory.newConnection()){
            Channel channel = connection.createChannel();
            
            channel.exchangeDeclare("exg-test", "direct", true); //create an exchange (nom: , type: ,durable: )
            
            channel.queueDeclare("test", false, false, false, null); //create queue test (queue: , durable: ,exclusive: , autoDelete: , argument: )
            
            channel.queueBind("test", "exg-test", "test"); //Bind the exchange with the queue

            String msg = "Hello ! at : " + LocalDateTime.now();
            
            channel.basicPublish("", "test", false, null, msg.getBytes());  //publish the msg (exchange: , routingkey: , mandatory: , props: , message: )
        } catch (IOException ex) {
            Logger.getLogger(Producer.class.getName()).log(Level.SEVERE, null, ex);
        } catch (TimeoutException ex) {
            Logger.getLogger(Producer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
