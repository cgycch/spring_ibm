package com.cch.jms;

import javax.jms.Message;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsOperations;
import org.springframework.jms.listener.adapter.MessageListenerAdapter;
import org.springframework.stereotype.Component;

//消息消费者的类上必须加上@Component，或者是@Service，这样的话，
//消息消费者类就会被委派给Listener类，原理类似于使用SessionAwareMessageListener
//以及MessageListenerAdapter来实现消息驱动POJO
@Component
public class ReceiveMessage extends MessageListenerAdapter {
	@Autowired
	JmsOperations jmsOperations;

	@Override
	@JmsListener(destination = "local_queue")
	public void onMessage(Message message) {
		String messageBody = new String(message.toString());
		System.out.println("成功监听local_queue消息队列，传来的值为:" + messageBody);
	}
}
