package com.jt.order.test;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.QueueingConsumer;

//定义路由模式
/**
 * 可以向指定的消息队列中发送消息
 * @author LYJ
 *
 */
public class RabbitMQTest_4_route {
	
	private Connection connection = null;
	
	
	@Before
	public void initConnection() throws IOException{
		//1.定义ConnectionFactory对象
		ConnectionFactory connectionFactory = new ConnectionFactory();
		connectionFactory.setHost("192.168.154.137");
		connectionFactory.setPort(5672);
		connectionFactory.setVirtualHost("/jt");
		connectionFactory.setUsername("jtadmin");
		connectionFactory.setPassword("jtadmin");	
		//获取连接
		connection = connectionFactory.newConnection();	
	}
	
	
	@Test
	public void proverder() throws Exception{
		Channel channel=connection.createChannel();
		String EXCHANGER_NAME="E-T";
		//声明交换机
		//①参：交换机的名字
		//②参：定义类型，fanout-发布订阅模式  direct-路由模式  topic-主题模式
		channel.exchangeDeclare(EXCHANGER_NAME,"direct");
		for(int i=0;i<100;i++){
			String Routing_Key= null;
			if(i%2==0){
				Routing_Key = "1707A";
			}else{
				Routing_Key = "1707B";
			}
			String message=i+"";
			
			//指定路由key
			channel.basicPublish(EXCHANGER_NAME,Routing_Key,null,message.getBytes());
		}
		/*//获取Channel
		Channel channel = connection.createChannel();
		//定义交换机
		String exchange_name = "E-T";
		//生命交换机模式    type 定义类型 fanout 发布订阅模式   direct-路由模式    topic-主题模式
		channel.exchangeDeclare(exchange_name, "direct");
		
		//发布消息
		for (int i = 0; i < 100; i++) {
			String routekey = null;
			if(i%2==0){
				routekey = "1707A";
			}else{
				routekey = "1707B";
			}
			String msg = "路由消息"+i;
			channel.basicPublish("exchange_name", routekey, null, msg.getBytes());
		}*/
	}
	
	//定义消费者1
	@Test
	public void consumer1() throws Exception{
		//定义通道
		Channel channel = connection.createChannel();
		//定义交换机名称
		String exchange_name = "E-T";		
		//定义队列名称
		String queue_name = "ret1";
		//声明交换机
		channel.exchangeDeclare(exchange_name, "direct");
		//声明队列
		channel.queueDeclare(queue_name, false, false, false, null);
		//绑定路由器和交换机
		//参数1 队列名称    参数2 交换机名称   参数3 路由key
		String routeKey = "1707A";
		channel.queueBind(queue_name, exchange_name, routeKey);
		//定义执行个数
		channel.basicQos(1);
		//定义消费者
		QueueingConsumer consumer = new QueueingConsumer(channel);
		
		//将消费者添加入通道中
		channel.basicConsume(queue_name, false, consumer);
		
		while(true){
			//获取队列消息
			QueueingConsumer.Delivery delivery = consumer.nextDelivery();
			String msg = new String(delivery.getBody());
			System.out.println("消费者1707A:"+msg);
			
			//返回信息
			channel.basicAck(delivery.getEnvelope().getDeliveryTag(), false);
		}
		
	}
	
	
	@Test
	public void consumer2() throws Exception{
		//定义通道
		Channel channel = connection.createChannel();
		
		//定义交换机名称
		String exchange_name = "E-T";
		
		//定义队列名称
		String queue_name = "ret2";
		
		//声明交换机
		channel.exchangeDeclare(exchange_name, "direct");
		
		//声明队列
		channel.queueDeclare(queue_name, false, false, false, null);
		
		//绑定路由器和交换机
		//参数1 队列名称    参数2 交换机名称   参数3 路由key
		String routeKey = "1707B";
		channel.queueBind(queue_name, exchange_name, routeKey);
		
		//定义执行个数
		channel.basicQos(1);
		
		//定义消费者
		QueueingConsumer consumer = new QueueingConsumer(channel);
		
		//将消费者添加入通道中
		channel.basicConsume(queue_name, false, consumer);
		
		while(true){
			//获取队列消息
			QueueingConsumer.Delivery delivery = consumer.nextDelivery();
			String msg = new String(delivery.getBody());
			System.out.println("消费者1707A:"+msg);
			
			//返回信息
			channel.basicAck(delivery.getEnvelope().getDeliveryTag(), false);
		}
		
	}
	
	
	
	
	
	
	
	
	
	
}
