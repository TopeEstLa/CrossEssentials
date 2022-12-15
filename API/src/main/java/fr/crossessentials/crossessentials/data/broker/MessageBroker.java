package fr.crossessentials.crossessentials.data.broker;

import fr.crossessentials.crossessentials.data.Bundle;
import fr.crossessentials.crossessentials.data.IConnector;

public interface MessageBroker extends IConnector {



    interface IListener {

        void onReceive(Bundle data);
    }
}
