package net.dongliu.byproxy;

import net.dongliu.byproxy.data.Message;

/**
 * Listener to receive request data. The operation in the call back method must not block
 *
 * @author Liu Dong
 */
@FunctionalInterface
public interface MessageListener {
    void onMessage(Message message);
}
