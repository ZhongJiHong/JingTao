package com.jt.order.test;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.ConsumerCancelledException;
import com.rabbitmq.client.QueueingConsumer;
import com.rabbitmq.client.ShutdownSignalException;

public class RabbitMQTest_1_simple {

	Connection connection = null;

	// 初始化连接
	@Before
	public void initConnection() throws IOException {

		ConnectionFactory connectionFactory = new ConnectionFactory();

		connectionFactory.setHost("192.168.218.129");
		connectionFactory.setPort(5672);
		connectionFactory.setVirtualHost("/jt");
		connectionFactory.setUsername("jtadmin");
		connectionFactory.setPassword("jtadmin");

		connection = connectionFactory.newConnection();
	}

	// 定义生产者
	@Test
	public void provider() throws IOException {

		// 1. 创建通道,只有通过通道才能连接rabbitMQ
		Channel channel = connection.createChannel();

		// 2. 定义队列的名字
		String queue_name = "simple_1";

		// 3. 声明队列
		/**
		 * durable 是否持久化 一般为false exclusive
		 * 如果为true,当前队列消息,只数据生产者,消费者不能调用,一般为false autoDelete 如果消息队列没有信息则自动删除
		 * 一般为false arguments 额外的扩充参数 没有就为null
		 */
		channel.queueDeclare(queue_name, false, false, false, null);

		// 4. 定义消息
		String msg = "Hello RabbitMQ_1_Simple";

		// 5. 消息队列的发布
		/**
		 * exchange 交换机 没有写"" routingKey routing 路由线路 props 其他的参数 没有为null body
		 * 消息的字节数组形式
		 */
		channel.basicPublish("", queue_name, null, msg.getBytes());
		System.out.println("生产者生产消息成功~~");
	}

	// 定义消费者
	@Test
	public void consumer()
			throws IOException, ShutdownSignalException, ConsumerCancelledException, InterruptedException {

		// 1. 获取通道
		Channel channel = connection.createChannel();

		// 2.
		String queue_name = "simple_1";

		channel.queueDeclare(queue_name, false, false, false, null);

		// 定义消费者
		QueueingConsumer consumer = new QueueingConsumer(channel);

		// 定义消费者消费的回传方式,如果为true
		channel.basicConsume(queue_name, true, consumer);

		while (true) {
			QueueingConsumer.Delivery delivery = consumer.nextDelivery();
			String msg = "消费者获取队列中的消息为:" + new String(delivery.getBody());
			System.out.println(msg);

		}
	}

}
