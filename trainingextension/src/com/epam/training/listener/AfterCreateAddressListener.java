package com.epam.training.listener;

import com.epam.training.model.AddressBusinessProcessModel;
import de.hybris.platform.core.model.user.AddressModel;
import de.hybris.platform.processengine.BusinessProcessService;
import de.hybris.platform.servicelayer.model.ModelService;
import de.hybris.platform.tx.AfterSaveEvent;
import de.hybris.platform.tx.AfterSaveListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Required;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class AfterCreateAddressListener implements AfterSaveListener {
    private static final Logger LOG = LoggerFactory.getLogger(AfterCreateAddressListener.class);

    private static final int ADDRESS_TYPE_CODE = 23;
    private static final String BUSINESS_PROCESS_NAME = "addressBusinessProcess";

    private ModelService modelService;
    private BusinessProcessService businessProcessService;

    @Required
    public void setModelService(ModelService modelService) {
        this.modelService = modelService;
    }

    @Required
    public void setBusinessProcessService(BusinessProcessService businessProcessService) {
        this.businessProcessService = businessProcessService;
    }

    @Override
    public void afterSave(Collection<AfterSaveEvent> collection) {
        List<AfterSaveEvent> createAddressEvents = findOnlyCreatedAddressEvents(collection);
        for (AfterSaveEvent event : createAddressEvents) {
            AddressModel address = modelService.get(event.getPk());
            LOG.info("Found event: AfterSaveEventTypeCode: {}, address pk: {}, pk owner  address: {}", event.getType(), event.getPk().getLongValue(), address.getOwner().getPk());
            AddressBusinessProcessModel process = createAndTurnAddressBusinessProcess(address);
            modelService.save(process);
            businessProcessService.startProcess(process);
            LOG.info("Created and start address business process: {}", process);
        }
    }

    private List<AfterSaveEvent> findOnlyCreatedAddressEvents(Collection<AfterSaveEvent> collection) {
        return collection.stream().filter(event -> event.getType() == AfterSaveEvent.CREATE && event.getPk().getTypeCode() == ADDRESS_TYPE_CODE).collect(Collectors.toList());
    }

    private AddressBusinessProcessModel createAndTurnAddressBusinessProcess(AddressModel address) {
        AddressBusinessProcessModel process = businessProcessService.createProcess(BUSINESS_PROCESS_NAME + System.currentTimeMillis(), BUSINESS_PROCESS_NAME);
        process.setAddress(address);
        return process;
    }

}
