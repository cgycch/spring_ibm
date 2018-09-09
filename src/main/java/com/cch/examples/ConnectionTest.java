package com.cch.examples;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Properties;

import javax.jms.BytesMessage;
import javax.jms.Connection;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TextMessage;

import com.ibm.msg.client.jms.JmsConnectionFactory;
import com.ibm.msg.client.jms.JmsConstants;
import com.ibm.msg.client.jms.JmsFactoryFactory;
import com.ibm.msg.client.wmq.WMQConstants;

public class ConnectionTest {
	
	public static final String SYSTEM_CHARCTER = "UTF-8";
	private Connection jmsConnection = null;
	private Session jmsSession = null;
	private Queue jmsQueue = null;
	private MessageProducer jmsProducer = null;
	private MessageConsumer messageConsumer = null;
 
	/**
	 * 初始化连接参数
	 * 
	 * @throws JMSException
	 */
	public void initial() throws JMSException {
		Properties pro = new Properties();
 
		try {
			File file = new File("src/main/resources/configs/app.properties");
			pro.load(new FileInputStream(file));
			JmsFactoryFactory jmsff = JmsFactoryFactory.getInstance(JmsConstants.WMQ_PROVIDER);
			JmsConnectionFactory jmscf = jmsff.createConnectionFactory();
 
			/**
			 * 队列管理器
			 */
			jmscf.setStringProperty("XMSC_WMQ_QUEUE_MANAGER",
					pro.getProperty("jms.mq.queuemanagername"));
 
			/**
			 * 运行队列管理器的主机地址
			 */
			jmscf.setStringProperty("XMSC_WMQ_HOST_NAME",
					pro.getProperty("jms.mq.hostname"));
			/*
			 * 队列管理器监听的端口
			 */
			jmscf.setIntProperty("XMSC_WMQ_PORT",
					Integer.parseInt((String) pro.get("jms.mq.port")));
 
			/*
			 * 服务器连接通道名称
			 */
			jmscf.setStringProperty("XMSC_WMQ_CHANNEL",
					pro.getProperty("jms.mq.channel"));
 
			/*
			 * 编码规则
			 */
			jmscf.setIntProperty("CCSID", 1381);
 
			/*
			 * 以客户端连接的方式连接队列管理器
			 */
			jmscf.setIntProperty("XMSC_WMQ_CONNECTION_MODE",
					WMQConstants.WMQ_CM_CLIENT);
 
			
			/*
			 * 创建连接需要制定用户名和密码:服务器端的设置需要注意：对应的用户是否有访问队列管理器和队列的权限
			 */
			this.jmsConnection = jmscf.createConnection("cch_ibm", "123456");
			this.jmsSession = this.jmsConnection.createSession(true, 0);
			this.jmsQueue = this.jmsSession.createQueue(pro.getProperty("jms.mbfe.queuename"));
			this.jmsProducer = this.jmsSession.createProducer(this.jmsQueue);
			messageConsumer = this.jmsSession.createConsumer(this.jmsQueue);
		} catch (IOException e) {
			e.printStackTrace();
		}
 
	}
 
	/**
	 * 发送消息
	 * 
	 * @param textMessage
	 * @throws ECDSServiceException
	 */
	public void send(String textMessage)
 
	{
		try {
			BytesMessage bm = this.jmsSession.createBytesMessage();
			bm.writeBytes(textMessage.getBytes(SYSTEM_CHARCTER));
			this.jmsConnection.start();
			this.jmsProducer.send(bm);
			this.jmsSession.commit();
			this.close();
			System.out.println("send: "+textMessage);
		} catch (JMSException e) {
			e.getLinkedException().printStackTrace();
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}
 
	/**
	 * 接收消息
	 * 
	 * @param textMessage
	 * @throws UnsupportedEncodingException
	 * @throws ECDSServiceException
	 */
	public void get() throws UnsupportedEncodingException
	{
		try {
			this.jmsConnection.start();
 
			Message message = this.messageConsumer.receive();
			System.out.println("mag receive:"+((TextMessage) message).getText());
			if (message instanceof BytesMessage) {
				BytesMessage bm = (BytesMessage) message;
				byte[] buf = new byte[1024];
				int len = 0;
				ByteArrayOutputStream content = new ByteArrayOutputStream();
				while ((len = bm.readBytes(buf)) > 0) {
					content.write(buf, 0, len);
				}
				String strMessage = new String(content.toByteArray(),
						SYSTEM_CHARCTER);
				System.out.println(strMessage);
			}
 
			this.jmsSession.commit();
			this.close();
		} catch (JMSException e) {
			e.getLinkedException().printStackTrace();
			e.printStackTrace();
		}
	}
 
	public static void main(String[] args) throws JMSException, UnsupportedEncodingException {
 
		// 创建连接
		ConnectionTest test = new ConnectionTest();
		// 发送消息
		test.initial();
		test.send("hello queue!");
		test.get();
	}
 
	public void close() {
		if (this.jmsProducer != null) {
			try {
				this.jmsProducer.close();
				this.jmsProducer = null;
			} catch (JMSException e) {
				e.printStackTrace();
			}
		}
	}
 


}
