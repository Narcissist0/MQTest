package com.biz;

/**
 * Created by Narsissist on 2017/12/4.
 */
        import com.rabbitmq.client.Channel;
        import com.rabbitmq.client.Connection;
        import com.rabbitmq.client.ConnectionFactory;

        import java.io.BufferedReader;
        import java.io.IOException;
        import java.io.InputStreamReader;
        import java.util.Date;
        import java.util.Scanner;
        import java.util.concurrent.TimeoutException;

public class MqProducer {
    //private  final  static String QUEUE_NAME ="people";
    private final static String EXCHANGE_NAME = "exchange_fanout";

    public static void main(String[] argv) throws java.io.IOException, TimeoutException {

        //创建连接工厂
        ConnectionFactory mqfactory = new ConnectionFactory();
        //设置Rabbitmq 地址
        mqfactory.setHost("120.78.144.230");
        mqfactory.setPort(5672);
        mqfactory.setUsername("wuxin");
        mqfactory.setPassword("wuxin");
//           //创建一个新的连接
        Connection connection = mqfactory.newConnection();
        //创建一个频道
        Channel channel = connection.createChannel();

        channel.exchangeDeclare(EXCHANGE_NAME, "fanout");
        //声明一个队列 在Rabbitmq 中,队列声明是幂等性的（一个幂操作的特点是其任意多次执行所产生的影响均与一次执行的影响相同），也就是说，如果不存在，就创建，如果存在，不会对已经存在的队列产生任何影响。
        //channel.queueDeclare(QUEUE_NAME,false,false,false,null);
        //UserMessage usermessage=new UserMessage();

        while (true) {
            UserM userMessage = new UserM();
            BufferedReader bu = new BufferedReader(new InputStreamReader(System.in));
//            if ("q".equals(bu.readLine())) {
//                return;
//            }
            System.out.println("wecome to rabbitmq chat");
            System.out.println("Type  q to exit");
            System.out.println("you need a name：");
            userMessage.setName(bu.readLine());
            System.out.println("hello" + "  " + userMessage.getName() + "  " + "you can chat now,enjoy it");
            userMessage.setMessage(bu.readLine());
            String msg = userMessage.getName() + "   " + "said" + "   " + userMessage.getMessage();
            channel.basicPublish(EXCHANGE_NAME, "", null, msg.getBytes("UTF-8"));
            System.out.println(msg);
            //关闭连接


        }
    }

}

