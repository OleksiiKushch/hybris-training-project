package com.epam.training.service.impl;

import de.hybris.bootstrap.annotations.IntegrationTest;
import de.hybris.platform.core.model.user.UserModel;
import de.hybris.platform.servicelayer.ServicelayerTransactionalBaseTest;
import de.hybris.platform.servicelayer.impex.ImportService;
import de.hybris.platform.servicelayer.impex.impl.FileBasedImpExResource;
import de.hybris.platform.servicelayer.search.FlexibleSearchService;
import org.apache.commons.lang.CharEncoding;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import javax.annotation.Resource;
import java.io.File;
import java.util.List;

@IntegrationTest
public class CustomUserServiceIntegrationTest extends ServicelayerTransactionalBaseTest {
    private static final String PATH_INIT_FILE = "resources/test/customUserService/customUserService_init.impex";

    @Resource
    private FlexibleSearchService flexibleSearchService;
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
    public void testGetAllUsersWithoutAddress() {
        CustomUserServiceImpl userService = new CustomUserServiceImpl();
        userService.setFlexibleSearchService(flexibleSearchService);

        List<UserModel> result = userService.getAllUsersWithoutAddress();

        Assert.assertEquals(3, result.size()); // 2 (default users) + 1 (from _init.impex file)
        for (UserModel user : result) {
            Assert.assertTrue(user.getAddresses().isEmpty());
            Assert.assertNull(user.getDefaultShipmentAddress());
            Assert.assertNull(user.getDefaultPaymentAddress());
        }
        Assert.assertEquals(1,
                result.stream().filter(user -> "bobber@hybhub.com".equals(user.getUid())).count());
    }
}
