package org.example.phase8.observers;

import org.example.phase8.interfaces.OrderListener;
import org.example.phase8.objects.Order;

//Observer
public class EmailListener implements OrderListener {

    @Override
    public void onCompletedOrder(Order order) {
        System.out.println("Email sent for :" + order);
    }
}
