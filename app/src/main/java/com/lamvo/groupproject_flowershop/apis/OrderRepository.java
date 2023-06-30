package com.lamvo.groupproject_flowershop.apis;

public class OrderRepository {
    public static OrderService getOrderService() {
        return ApiClient.getClient().create(OrderService.class);
    }
}
