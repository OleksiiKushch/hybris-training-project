package com.epam.training.facade.impl;

import com.epam.training.facade.CustomOrderFacade;
import de.hybris.platform.commercefacades.order.data.OrderData;
import de.hybris.platform.commercefacades.order.impl.DefaultOrderFacade;
import de.hybris.platform.converters.Converters;
import de.hybris.platform.core.enums.OrderStatus;
import de.hybris.platform.core.model.order.OrderModel;
import de.hybris.platform.core.model.user.CustomerModel;
import de.hybris.platform.store.BaseStoreModel;

import java.util.List;

public class CustomOrderFacadeImpl extends DefaultOrderFacade implements CustomOrderFacade {
    private static final OrderStatus[] ENY_ORDER_STATUS = new OrderStatus[0];

    @Override
    public List<OrderData> getOrders() {
        CustomerModel currentCustomer = (CustomerModel) getUserService().getCurrentUser();
        BaseStoreModel currentBaseStore = getBaseStoreService().getCurrentBaseStore();
        List<OrderModel> orderList = getCustomerAccountService().getOrderList(currentCustomer, currentBaseStore, ENY_ORDER_STATUS);
        return Converters.convertAll(orderList, getOrderConverter());
    }
}