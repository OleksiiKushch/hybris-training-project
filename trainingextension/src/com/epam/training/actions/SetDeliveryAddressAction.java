package com.epam.training.actions;

import com.epam.training.model.AddressBusinessProcessModel;
import de.hybris.platform.core.model.user.AddressModel;
import de.hybris.platform.core.model.user.UserModel;
import de.hybris.platform.processengine.action.AbstractSimpleDecisionAction;
import de.hybris.platform.servicelayer.model.ModelService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SetDeliveryAddressAction extends AbstractSimpleDecisionAction<AddressBusinessProcessModel> {
    private static final Logger LOG = LoggerFactory.getLogger(SetDeliveryAddressAction.class);

    private ModelService modelService;

    public void setModelService(ModelService modelService) {
        this.modelService = modelService;
    }

    @Override
    public Transition executeAction(AddressBusinessProcessModel processCreateAddressModel) {
        AddressModel addressModel = processCreateAddressModel.getAddress();
        UserModel userModel = (UserModel) addressModel.getOwner();
        userModel.setDefaultShipmentAddress(addressModel);
        modelService.save(userModel);
        LOG.info("Set default delivery (shipment) address for user (uid): {}", userModel.getUid());
        return Transition.OK;
    }
}
