package com.epam.training.listener;

import com.epam.training.event.SendEmailToCustomerEvent;
import com.epam.training.model.MyCustomerModel;
import de.hybris.platform.servicelayer.event.impl.AbstractEventListener;
import de.hybris.platform.servicelayer.model.ModelService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;

public class SendEmailToCustomerEventListener extends AbstractEventListener<SendEmailToCustomerEvent> {
    private static final Logger LOG = LoggerFactory.getLogger(SendEmailToCustomerEventListener.class);

    @Resource
    private ModelService modelService;

    @Override
    protected void onEvent(SendEmailToCustomerEvent sendEmailToCustomerEvent) {
        MyCustomerModel customer = sendEmailToCustomerEvent.getCustomer();
        customer.setSendToEmail(true);
        modelService.save(customer);
        LOG.info("Event email to customer with email: " + customer.getUid());
    }
}
