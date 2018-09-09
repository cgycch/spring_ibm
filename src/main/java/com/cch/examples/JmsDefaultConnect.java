package com.cch.examples;

import java.util.UUID;

import javax.jms.Connection;
import javax.jms.DeliveryMode;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;

import com.ibm.mq.jms.MQConnectionFactory;

public class JmsDefaultConnect {
	
	private static final int SEND_NUMBER = 7;
	 
	public static void main(String[] args) {
 
		MQConnectionFactory connFct = new MQConnectionFactory();
		
		Connection connection = null;
		Session session;
		Destination destination;
		MessageProducer producer;
 
		try {
			connFct.setQueueManager("myibm");
			connFct.setHostName("127.0.0.1");
			connFct.setPort(1414);
			connection = connFct.createConnection();
			connection.start();
			session = connection.createSession(Boolean.TRUE, Session.AUTO_ACKNOWLEDGE);
			destination = session.createQueue("local_queue");
			producer = session.createProducer(destination);
			producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
			sendMessage(session,producer);
			
			session.commit();
			
		} catch (JMSException e) {
			e.printStackTrace();
		}
		
		
	}
 
	private static void sendMessage(Session session, MessageProducer producer) throws JMSException {
		
		for(int i=1;i<=SEND_NUMBER;i++){
			TextMessage message = session.createTextMessage("send Message hello "+UUID.randomUUID()+":"+i);
			System.out.println("send message:message "+i);
			producer.send(message);
		}
		
	}
 


}
