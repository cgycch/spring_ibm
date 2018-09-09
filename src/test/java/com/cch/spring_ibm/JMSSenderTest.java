package com.cch.spring_ibm;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.cch.entities.Person;
import com.cch.jms.JMSReceiver;
import com.cch.jms.JMSSender;

public class JMSSenderTest {
	@Test
	public void jmsSender() {
		@SuppressWarnings("resource")
		ApplicationContext app = new ClassPathXmlApplicationContext("beans/appContext-ibmmq.xml");
		JMSSender sender = (JMSSender)app.getBean("jmsSender");
		Person person = new Person();
		person.setName("chen");
		person.setPass("123456");
		sender.sendMesage(person);
	}
	
	@Test
	public void jmsReceiver() {
		@SuppressWarnings("resource")
		ApplicationContext app = new ClassPathXmlApplicationContext("beans/appContext-ibmmq.xml");
		JMSReceiver receiver = (JMSReceiver)app.getBean("jmsReceiver");
		receiver.processMessage();
	}

}
