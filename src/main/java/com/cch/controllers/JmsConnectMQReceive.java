package com.cch.controllers;


import javax.jms.Connection;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageConsumer;
import javax.jms.Session;
import javax.jms.TextMessage;
 
import com.ibm.mq.jms.MQConnectionFactory;

public class JmsConnectMQReceive {
	public static void main(String[] args) throws JMSException {
		
		MQConnectionFactory factory = new MQConnectionFactory();
		factory.setHostName("localhost");
		factory.setPort(1414);
		factory.setQueueManager("myibm");
		
		Connection conn = factory.createConnection();
		conn.start();
		Session session = conn.createSession(Boolean.TRUE, Session.AUTO_ACKNOWLEDGE);
		Destination dest = session.createQueue("local_queue");
		MessageConsumer consumer = session.createConsumer(dest);
		int i = 0;
		while(true){
			TextMessage msg = (TextMessage) consumer.receive();
			if(msg == null)
				break;
			else
				System.out.println(msg.getText()+" "+i++);
		}
		session.commit();
		conn.close();
 
	}

}
