package com.cch.jms;

import javax.jms.JMSException;
import javax.jms.MessageConsumer;
import javax.jms.Session;

import org.springframework.jms.listener.DefaultMessageListenerContainer;

public class SGMessageListenerContainer extends DefaultMessageListenerContainer{
	
	@Override
	protected boolean receiveAndExecute(Object invoker, Session session, MessageConsumer consumer) throws JMSException {
		// TODO Auto-generated method stub
		return super.receiveAndExecute(invoker, session, consumer);
	}
	
	@Override
	protected void handleListenerException(Throwable ex) {
		// TODO Auto-generated method stub
		super.handleListenerException(ex);
	}
	
	
}
