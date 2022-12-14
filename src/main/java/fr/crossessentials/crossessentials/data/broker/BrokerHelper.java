package fr.crossessentials.crossessentials.data.broker;

import fr.crossessentials.crossessentials.CrossEssentials;
import fr.crossessentials.crossessentials.data.broker.impl.RedisBroker;
import fr.crossessentials.crossessentials.data.broker.impl.RedisCredentials;
import fr.crossessentials.crossessentials.exceptions.ConnectionException;

public class BrokerHelper {

    static MessageBroker broker;

    public static boolean init(){
        // TODO: config
        broker = new RedisBroker();

        try {
            broker.connect(new RedisCredentials("localhost",3306,"root","root"));
            return true;
        } catch (ConnectionException e) {
            CrossEssentials.getInstance().getLogger().severe("Error while connecting to the message broker: \n"+e);
            return false;
        }
    }

    public static void shutdown() {
        if(broker != null){
            broker.shutdown();
        }
    }
}