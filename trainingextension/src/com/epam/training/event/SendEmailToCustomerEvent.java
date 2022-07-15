package com.epam.training.event;

import com.epam.training.model.MyCustomerModel;
import de.hybris.platform.servicelayer.event.ClusterAwareEvent;
import de.hybris.platform.servicelayer.event.events.AbstractEvent;

public class SendEmailToCustomerEvent extends AbstractEvent implements ClusterAwareEvent {
    private static final long serialVersionUID = 2669293150219020249L;

    private final MyCustomerModel customer;

    public SendEmailToCustomerEvent(MyCustomerModel customer) {
        super(customer);
        this.customer = customer;
    }

    public MyCustomerModel getCustomer() {
        return customer;
    }

    @Override
    public boolean publish(int sourceId, int targetId) {
        return true;
    }
}
