package com.kasry;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPubSub;


/**
 * Created by hamzadev on 26/09/17.
 */
public class Subscriber {

    private static final String SERVER_ADDR = "localhost";

    JedisPubSub jedisPubSub;
    Jedis jedis;

    Subscriber() {
        jedis = new Jedis(SERVER_ADDR);

    }
    public void subscribe() {
        this.jedisPubSub = new JedisPubSub() {
            @Override
            public void onMessage(String channel, String message) {
                super.onMessage(channel, message);
                System.out.println("The message:" + message + " has been received");
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
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    System.out.println("Constructing the subscriber");
                    jedis.subscribe(jedisPubSub, "books");
                    wait(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                jedis.quit();
            }
        }).start();
    }

    public static void main(String[] args) {
        Subscriber subscriber = new Subscriber();
        System.out.println("The subscriber object has been created");
        subscriber.subscribe();
    }
}
