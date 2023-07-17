package com.lamvo.groupproject_flowershop.apis;

public class OrderRepository {
    public static OrderService getOrderService() {
        return ApiClient2.getClient().create(OrderService.class);
    }
}
