package org.example.phase8.publishers;

import org.example.phase8.interfaces.OrderListener;
import org.example.phase8.objects.Order;

import java.util.ArrayList;
import java.util.List;

//Publisher
public class OrderEventManager {

    private List<OrderListener> listeners = new ArrayList<>();

    public void order(OrderListener eventListener) {
        listeners.add(eventListener);
    }

    public void notifyOnOrderComplete(Order order){
        for (OrderListener listener: listeners) {
            listener.onCompletedOrder(order);
        }
    }
}
