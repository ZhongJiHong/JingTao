package com.jt.order.test;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.QueueingConsumer;

//特点 队列消息只能被一个消费者消费
public class RabbitMQTest_2_work {
	
	private Connection connection = null;
	//定义队列名称
	private String queue_name = "Qwork";
	
	@Before
	public void initConnection() throws IOException{
		//1.定义ConnectionFactory对象
		ConnectionFactory connectionFactory = new ConnectionFactory();
		connectionFactory.setHost("192.168.218.129");
		connectionFactory.setPort(5672);
		connectionFactory.setVirtualHost("/jt");
		connectionFactory.setUsername("jtadmin");
		connectionFactory.setPassword("jtadmin");	
		//获取连接
		connection = connectionFactory.newConnection();	
	}
	
	//定义生产者
	@Test
	public void proverder() throws Exception{
		//获取通道
		Channel channel = connection.createChannel();
		
		//获取队列
		channel.queueDeclare(queue_name, false, false, false, null);
		
		for (int i = 0; i<100; i++) {
			String msg = "京淘项目"+i;
			System.out.println(msg);
			channel.basicPublish("", queue_name,null,msg.getBytes());				
		}
		//关闭连接
		channel.close();
		connection.close();
	}
	
	//定义一个消费者
	@Test
	public void consumer1() throws Exception{
		//获取通道
		Channel channel = connection.createChannel();
		
		//获取队列
		channel.queueDeclare(queue_name, false, false, false, null);
		
		//定义消息执行的数量  当消息执行后 回复ack确认之后,才能执行下一消息   
		//消息队列中  最多允许3次信息没有返回ack信息 否则将不再发送消息
		channel.basicQos(1);
		
		//定义消费者
		QueueingConsumer consumer = new QueueingConsumer(channel);
		
		//获取消息队列信息
		channel.basicConsume(queue_name,false,consumer);
		while(true){
			QueueingConsumer.Delivery delivery = consumer.nextDelivery();
			String msg = new String(delivery.getBody());
			Thread.sleep(1000);
			System.out.println("消费端1:"+msg);
			
			//回复信息 
			//回复的状态码     false表示自己手动传回
			channel.basicAck(delivery.getEnvelope().getDeliveryTag(), false);
		}
	}
	
	
	@Test
	public void consumer2() throws Exception{
		//获取通道
		Channel channel = connection.createChannel();
		
		//获取队列
		channel.queueDeclare(queue_name, false, false, false, null);
		
		//定义消息执行的数量  当消息执行后 回复ack确认之后,才能执行下一消息   
		//消息队列中  最多允许3次信息没有返回ack信息 否则将不再发送消息
		channel.basicQos(1);
		
		//定义消费者
		QueueingConsumer consumer = new QueueingConsumer(channel);
		
		//获取消息队列信息
		channel.basicConsume(queue_name,false,consumer);
		while(true){
			QueueingConsumer.Delivery delivery = consumer.nextDelivery();
			String msg = new String(delivery.getBody());
			Thread.sleep(500);
			System.out.println("消费端2:"+msg);
			
			//回复信息 
			//回复的状态码     false表示自己手动传回
			channel.basicAck(delivery.getEnvelope().getDeliveryTag(), false);
		}
	}
	
}
