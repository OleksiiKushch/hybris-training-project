package com.epam.training.listener;

import com.epam.training.event.SendEmailToCustomerEvent;
import com.epam.training.model.MyCustomerModel;
import de.hybris.bootstrap.annotations.IntegrationTest;
import de.hybris.platform.servicelayer.ServicelayerTest;
import de.hybris.platform.servicelayer.event.EventService;
import de.hybris.platform.servicelayer.impex.ImportService;
import de.hybris.platform.servicelayer.impex.impl.FileBasedImpExResource;
import de.hybris.platform.servicelayer.model.ModelService;
import de.hybris.platform.servicelayer.user.UserService;
import org.apache.commons.lang.CharEncoding;
import org.junit.Before;
import org.junit.Test;

import javax.annotation.Resource;
import java.io.File;

import static java.lang.Thread.sleep;
import static org.junit.Assert.assertTrue;

@IntegrationTest
public class SendEmailToCustomerEventListenerIntegrationTest extends ServicelayerTest {
    private static final String PATH_INIT_FILE = "resources/test/sendEmailToCustomerEventListener/sendEmailToCustomerEventListener_init.impex";
    private static final int TIME_SLEEP_AFTER_PUBLISHED_EVENT = 1000;

    @Resource
    private EventService eventService;
    @Resource
    private UserService userService;
    @Resource
    private ModelService modelService;
    @Resource
    private ImportService importService;

    @Before
    public void setUp() {
        initData();
    }

    private void initData() {
        importService.importData(new FileBasedImpExResource(new File(PATH_INIT_FILE), CharEncoding.UTF_8));
    }

    @Test
    public void testOnEvent() throws InterruptedException {
        MyCustomerModel customerBefore = (MyCustomerModel) userService.getUserForUID("alex@hybhub.com");

        eventService.publishEvent(new SendEmailToCustomerEvent(customerBefore));
        sleep(TIME_SLEEP_AFTER_PUBLISHED_EVENT);

        MyCustomerModel customerAfter = (MyCustomerModel) userService.getUserForUID("alex@hybhub.com");
        modelService.refresh(customerAfter);
        assertTrue(customerAfter.isSendToEmail());
    }
}
