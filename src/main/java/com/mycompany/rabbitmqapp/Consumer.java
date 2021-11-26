/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.rabbitmqapp;

import com.rabbitmq.client.CancelCallback;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;
import com.rabbitmq.client.Delivery;
import java.io.IOException;
import java.net.URISyntaxException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.TimeoutException;


/**
 *
 * @author user
 */
public class Consumer {
    
     public static void main(String[] args) throws IOException, TimeoutException, URISyntaxException, NoSuchAlgorithmException, KeyManagementException {
       ConnectionFactory factory = new ConnectionFactory(); //connect to the rabbitmq provider
        factory.setUri("amqps://gprkrobt:hp-C1KY7n5lNyvMNvXZ04NnjxwqSnvgJ@rat.rmq2.cloudamqp.com/gprkrobt");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();
        channel.queueDeclare("test", false, false, false, null); //create queue test (queue: , durable: ,exclusive: , autoDelete: , argument: )
        channel.basicConsume("test",true,new DeliverCallback() {       //get the message (queue: , autoack: )

            @Override
            public void handle(String string, Delivery dlvr) throws IOException {
               String msg = new String(dlvr.getBody(),"UTF-8"); //convert bytes to string and get the messsage
               System.out.println("received : " + msg);
            }
        }, new CancelCallback() {
            @Override
            public void handle(String string) throws IOException {           //write method for if the consumer is canceled by the server
            
            }});
}}
