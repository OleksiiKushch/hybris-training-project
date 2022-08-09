package com.epam.training.actions;

import com.epam.training.model.AddressBusinessProcessModel;
import de.hybris.platform.core.model.user.AddressModel;
import de.hybris.platform.processengine.action.AbstractAction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashSet;
import java.util.Set;

public class ValidateCreatedAddressAction extends AbstractAction<AddressBusinessProcessModel> {
    private static final Logger LOG = LoggerFactory.getLogger(ValidateCreatedAddressAction.class);

    protected Transition executeAction(AddressBusinessProcessModel process) {
        AddressModel addressModel = process.getAddress();
        if (addressModel.getBillingAddress() && addressModel.getShippingAddress()) {
            LOG.info("Address: {}, transition: {}", addressModel, Transition.BOTH);
            return Transition.BOTH;
        } else if (addressModel.getBillingAddress()) {
            LOG.info("Address: {}, transition: {}", addressModel, Transition.BILLING);
            return Transition.BILLING;
        } else if (addressModel.getShippingAddress()) {
            LOG.info("Address: {}, transition: {}", addressModel, Transition.DELIVERY);
            return Transition.DELIVERY;
        } else {
            LOG.info("Address: {}, transition: {}", addressModel, Transition.NOTHING);
            return Transition.NOTHING;
        }
    }

    @Override
    public String execute(AddressBusinessProcessModel businessProcessModel) {
        return executeAction(businessProcessModel).toString();
    }

    @Override
    public Set<String> getTransitions() {
        return Transition.getStringValues();
    }

    public enum Transition {
        BILLING,
        DELIVERY,
        BOTH,
        NOTHING;

        public static Set<String> getStringValues() {
            Set<String> result = new HashSet<>();
            for(Transition transition : Transition.values()) {
                result.add(transition.toString());
            }
            return result;
        }
    }
}
