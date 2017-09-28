package com.kasry.redis;

import com.kasry.redis.Publisher;
import com.kasry.redis.Subscriber;
import redis.clients.jedis.Jedis;

/**
 * Created by hamzadev on 26/09/17.
 */
public class RedisConnect {

    public static void main(String[] args) {
        Jedis jedis = new Jedis("localhost");
        System.out.println(jedis.get("name"));
        System.out.println("Server is running : " + jedis.ping());
        //Using publisher to publish to the channel specified in the first parameter
        Publisher publisher = new Publisher();
        Subscriber subscriber = new Subscriber();
        publisher.publish("books", "msg1");
    }
}
