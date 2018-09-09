package com.cch.jms;

import java.util.Properties;
import javax.jms.Destination;
import org.springframework.jms.core.JmsTemplate;
import com.alibaba.fastjson.JSONObject;

public class JMSSender {
	 
		private JmsTemplate jmsTemplate;	 
		private Properties msgheader;	 
		private Destination replyTo;
		
		public void sendMesage(Object object) {
			JSONObject json = new JSONObject();
			json.put("orderData", object);
			jmsTemplate.convertAndSend(replyTo, json.toJSONString());
		}
		
		public JmsTemplate getJmsTemplate() {
			return jmsTemplate;
		}
	 
		public void setJmsTemplate(JmsTemplate jmsTemplate) {
			this.jmsTemplate = jmsTemplate;
		}
	 
		/**
		 * @return Returns the msgheader.
		 */
		public Properties getMsgheader() {
			return msgheader;
		}
	 
		/**
		 * @param msgheader The msgheader to set.
		 */
		public void setMsgheader(Properties msgheader) {
			this.msgheader = msgheader;
		}
	 
		/**
		 * @return Returns the replyTo.
		 */
		public Destination getReplyTo() {
			return replyTo;
		}
	 
		/**
		 * @param replyTo The replyTo to set.
		 */
		public void setReplyTo(Destination replyTo) {
			this.replyTo = replyTo;
		}

}
