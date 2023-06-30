package com.lamvo.groupproject_flowershop.apis;

public class OderDetailRepository {
    public static OrderDetailService getOrderDetailService() {
        return ApiClient.getClient().create(OrderDetailService.class);
    }
}
