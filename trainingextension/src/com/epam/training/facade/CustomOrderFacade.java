package com.epam.training.facade;

import de.hybris.platform.commercefacades.order.OrderFacade;
import de.hybris.platform.commercefacades.order.data.OrderData;

import java.util.List;

public interface CustomOrderFacade extends OrderFacade {
    List<OrderData> getOrders();
}
