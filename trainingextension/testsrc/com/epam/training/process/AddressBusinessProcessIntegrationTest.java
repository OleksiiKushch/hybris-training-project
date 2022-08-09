package com.epam.training.process;

import de.hybris.bootstrap.annotations.IntegrationTest;
import de.hybris.platform.core.model.user.UserModel;
import de.hybris.platform.servicelayer.ServicelayerTest;
import de.hybris.platform.servicelayer.impex.ImportService;
import de.hybris.platform.servicelayer.impex.impl.FileBasedImpExResource;
import de.hybris.platform.servicelayer.user.UserService;
import org.apache.commons.lang.CharEncoding;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;
import java.io.File;

@IntegrationTest
public class AddressBusinessProcessIntegrationTest extends ServicelayerTest {
    private static final Logger LOG = LoggerFactory.getLogger(AddressBusinessProcessIntegrationTest.class);

    private static final String PATH_INIT_FILE = "resources/test/processCreateAddress/processCreateAddress_init.impex";
    private static final long TIMEOUT_AFTER_PROCESS_START = 2000;

    @Resource
    private UserService userService;
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
    public void shouldSetDefaultShipmentAddressToUser() throws InterruptedException {
        importTestDataAndSleep("resources/test/processCreateAddress/test-data/createShippingTypeAddress.impex");

        UserModel testUser = userService.getUserForUID("riko@epam.com");

        Assert.assertNotNull(testUser.getDefaultShipmentAddress());
        Assert.assertNull(testUser.getDefaultPaymentAddress());
    }

    @Test
    public void shouldSetDefaultPaymentAddressToUser() throws InterruptedException {
        importTestDataAndSleep("resources/test/processCreateAddress/test-data/createBillingTypeAddress.impex");

        UserModel testUser = userService.getUserForUID("artur@gmail.com");

        Assert.assertNull(testUser.getDefaultShipmentAddress());
        Assert.assertNotNull(testUser.getDefaultPaymentAddress());
    }

    @Test
    public void shouldSetDefaultShipmentAddressAndDefaultPaymentAddressToUser() throws InterruptedException {
        importTestDataAndSleep("resources/test/processCreateAddress/test-data/createShipmentAndBillingTypesAddress.impex");

        UserModel testUser = userService.getUserForUID("bobber@hybhub.com");

        Assert.assertNotNull(testUser.getDefaultShipmentAddress());
        Assert.assertNotNull(testUser.getDefaultPaymentAddress());
    }

    @Test
    public void shouldNotSetAddressToUser_whenAddressIsNotDefaultForDeliveryAndPayment() throws InterruptedException {
        importTestDataAndSleep("resources/test/processCreateAddress/test-data/createWithoutAnyTypeAddress.impex");

        UserModel testUser = userService.getUserForUID("bobber@hybhub.com");

        Assert.assertNull(testUser.getDefaultShipmentAddress());
        Assert.assertNull(testUser.getDefaultPaymentAddress());
    }

    private void importTestDataAndSleep(String pathname) throws InterruptedException {
        importService.importData(new FileBasedImpExResource(new File(pathname), CharEncoding.UTF_8));
        Thread.sleep(TIMEOUT_AFTER_PROCESS_START);
        LOG.info("Sleep on {} millis.", TIMEOUT_AFTER_PROCESS_START);
    }
}
