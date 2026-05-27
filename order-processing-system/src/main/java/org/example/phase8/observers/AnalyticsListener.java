package org.example.phase8.observers;

import org.example.phase8.interfaces.OrderListener;
import org.example.phase8.objects.Order;

public class AnalyticsListener implements OrderListener {
    @Override
    public void onCompletedOrder(Order order) {
        System.out.println("Analytics updated for " + order);
    }
}
