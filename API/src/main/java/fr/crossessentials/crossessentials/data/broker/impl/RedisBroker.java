package fr.crossessentials.crossessentials.data.broker.impl;

import fr.crossessentials.crossessentials.data.Bundle;
import fr.crossessentials.crossessentials.data.ConnectorCredentials;
import fr.crossessentials.crossessentials.data.broker.MessageBroker;
import fr.crossessentials.crossessentials.data.exceptions.BundleDeserializeException;
import fr.crossessentials.crossessentials.data.exceptions.ConnectionException;
import io.lettuce.core.ClientOptions;
import io.lettuce.core.RedisClient;
import io.lettuce.core.RedisURI;
import io.lettuce.core.pubsub.RedisPubSubAdapter;
import io.lettuce.core.pubsub.StatefulRedisPubSubConnection;
import io.lettuce.core.pubsub.api.async.RedisPubSubAsyncCommands;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class RedisBroker implements MessageBroker {

    protected final CompletableFuture<Void> ready = new CompletableFuture<>();
    RedisClient redisClient;
    StatefulRedisPubSubConnection<String, String> pubConnection;
    StatefulRedisPubSubConnection<String, String> subConnection;
    RedisPubSubAsyncCommands<String, String> subCommands; // subscribe
    RedisPubSubAsyncCommands<String, String> pubCommands; // publish
    Map<String, List<IListener>> listeners = new HashMap<>();

    @Override
    public void connect(ConnectorCredentials connectorCredentials) throws ConnectionException {
        if (!(connectorCredentials instanceof RedisCredentials credentials)) {
            throw new ConnectionException("Invalid credentials");
        }

        try{

            RedisURI.Builder builder = RedisURI.Builder.redis(credentials.host(), credentials.port());

            if (credentials.username() != null && !credentials.username().isEmpty()) {
                builder.withAuthentication(credentials.username(), credentials.password());
            } else if(credentials.password() != null && !credentials.password().isEmpty()){
                builder.withPassword(credentials.password().toCharArray());
            }

            this.redisClient = RedisClient.create(builder.build());
            this.redisClient.connect();

            redisClient.setOptions(ClientOptions.builder().autoReconnect(true).build());
            pubConnection = redisClient.connectPubSub();
            subConnection = redisClient.connectPubSub();

            subConnection.addListener(new RedisPubSubAdapter<>() {
                @Override
                public void message(String channel, String message) {
                    List<IListener> mqListeners = listeners.get(channel);
                    if (mqListeners != null) {
                        Bundle bundle;
                        try {
                            //System.out.println is temporary for debugging
                            System.out.println("Receive message from Redis: " + message);
                            bundle = Bundle.deserialize(message);
                        } catch (BundleDeserializeException e) {
                            System.out.println("Cannot deserialize received bundle: " + e);
                            return;
                        }
                        for (IListener mqListener : mqListeners) {
                            try {
                                mqListener.onReceive(bundle);
                            } catch (Exception e) {
                                System.out.println( "Error while calling listener: " + e);
                            }
                        }
                    }
                }
            });

            subCommands = subConnection.async();
            pubCommands = pubConnection.async();

            ready.complete(null);
        }catch (Exception e){
            throw new ConnectionException(e.getMessage());
        }
    }

    @Override
    public void shutdown() {
        pubConnection.close();
        subConnection.close();
        redisClient.shutdown();
    }

    @Override
    public void waitForConnection() {
        try {
            ready.get();
        } catch (InterruptedException | ExecutionException error) {
            System.out.println("An unexpected error occurred while waiting for connection: " + error);
        }
    }
}
