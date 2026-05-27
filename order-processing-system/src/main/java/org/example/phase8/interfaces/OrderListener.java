package org.example.phase8.interfaces;

import org.example.phase8.objects.Order;

//Common observer interface
public interface OrderListener {
    void onCompletedOrder(Order order);
}
