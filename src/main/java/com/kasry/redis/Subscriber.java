package com.kasry.redis;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPubSub;

import java.util.Map;
import java.util.Scanner;


/**
 * The subscriber, subscribers to given channels, and outputs the new messages
 * Created by hamzadev on 26/09/17.
 */

public class Subscriber {

    private static final String SERVER_ADDR = "localhost";

    JedisPubSub jedisPubSub;
    Jedis jedis;

    Subscriber() {
        jedis = new Jedis(SERVER_ADDR);

    }

    public static void main(String[] args) {
        Subscriber subscriber = new Subscriber();
        System.out.println("The subscriber object has been created");
        subscriber.subscribe();
    }

    public void subscribe() {
        this.jedisPubSub = new JedisPubSub() {
            @Override
            public void onMessage(String channel, String message) {
                super.onMessage(channel, message);
                System.out.println("The message:" + message + " has been received");
                String[] parts = message.split(" ");
                Jedis jedis1 = new Jedis("localhost");
                Map<String, String> properties = jedis1.hgetAll( parts[3]);
                System.out.println(properties);
            }

            @Override
            public void onSubscribe(String channel, int subscribedChannels) {
                super.onSubscribe(channel, subscribedChannels);
                System.out.println("Subscribing to the channel : " + channel);
            }

            @Override
            public void onUnsubscribe(String channel, int subscribedChannels) {
                super.onUnsubscribe(channel, subscribedChannels);
                System.out.println("Unsubscribing from the channel : " + channel);
            }

            @Override
            public void unsubscribe() {
                super.unsubscribe();
            }

            @Override
            public void unsubscribe(String... channels) {
                super.unsubscribe(channels);
            }

            @Override
            public void subscribe(String... channels) {
                super.subscribe(channels);
            }
        };
        System.out.println("Constructing the subscriber, which channel do you want to subscribe to? ");
        Scanner scanner = new Scanner(System.in);
        String channel = scanner.nextLine();
        jedis.subscribe(jedisPubSub, channel);
    }
}
