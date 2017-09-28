package com.kasry;

import java.util.ArrayList;
import java.util.concurrent.CountDownLatch;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPubSub;

public class JedisTest {

    private static final String JEDIS_SERVER = "127.0.0.1";

    private ArrayList<String> messageContainer = new ArrayList<String>();

    private CountDownLatch messageReceivedLatch = new CountDownLatch(1);
    private CountDownLatch publishLatch = new CountDownLatch(1);

    public static void main(String[] args) throws InterruptedException {
        new JedisTest().run();
    }

    private void run() throws InterruptedException {
        setupPublisher();
        JedisPubSub jedisPubSub = setupSubscriber();

        // publish away!
        publishLatch.countDown();

        messageReceivedLatch.await();
        log("Got message: %s", messageContainer.iterator().next());

        jedisPubSub.unsubscribe();
    }

    private void setupPublisher() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    log("Connecting");
                    Jedis jedis = new Jedis(JEDIS_SERVER);
                    log("Waiting to publish");
                    publishLatch.await();
                    log("Ready to publish, waiting one sec");
                    Thread.sleep(1000);
                    log("publishing");
                    jedis.publish("test", "This is a message");
                    log("published, closing publishing connection");
                    jedis.quit();
                    log("publishing connection closed");
                } catch (Exception e) {
                    log(">>> Exception when setting up publisher, " + e.getMessage());
                    // e.printStackTrace();
                }
            }
        }, "publisherThread").start();
    }

    private JedisPubSub setupSubscriber() {
        final JedisPubSub jedisPubSub = new JedisPubSub() {
            @Override
            public void onUnsubscribe(String channel, int subscribedChannels) {
                log("onUnsubscribe");
            }

            @Override
            public void onSubscribe(String channel, int subscribedChannels) {
                log("onSubscribe");
            }

            @Override
            public void onPMessage(String pattern, String channel, String message) {
            }

            @Override
            public void onMessage(String channel, String message) {
                messageContainer.add(message);
                log("Message received");
                messageReceivedLatch.countDown();
            }
        };
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    log("Connecting");
                    Jedis jedis = new Jedis(JEDIS_SERVER);
                    log("subscribing");
                    jedis.subscribe(jedisPubSub, "test");
                    log("subscribe returned, closing down");
                    jedis.quit();
                } catch (Exception e) {
                    log(">>> Exception returned - " + e.getMessage());
                    // e.printStackTrace();
                }
            }
        }, "subscriberThread").start();
        return jedisPubSub;
    }

    static final long startMillis = System.currentTimeMillis();

    private static void log(String string, Object... args) {
        long millisSinceStart = System.currentTimeMillis() - startMillis;
        System.out.printf("%20s %6d %s\n", Thread.currentThread().getName(), millisSinceStart,
                String.format(string, args));
    }
}