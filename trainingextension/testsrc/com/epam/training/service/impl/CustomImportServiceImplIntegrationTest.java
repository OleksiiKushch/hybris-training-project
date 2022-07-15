package com.epam.training.service.impl;

import com.epam.training.service.CustomImportService;
import de.hybris.bootstrap.annotations.IntegrationTest;
import de.hybris.platform.core.model.user.UserModel;
import de.hybris.platform.servicelayer.ServicelayerTransactionalBaseTest;
import de.hybris.platform.servicelayer.user.UserService;
import org.junit.Test;

import javax.annotation.Resource;

import static de.hybris.platform.testframework.Assert.assertEquals;

@IntegrationTest
public class CustomImportServiceImplIntegrationTest extends ServicelayerTransactionalBaseTest {
    @Resource
    private CustomImportService importService;
    @Resource
    private UserService userService;

    @Test(expected = IllegalArgumentException.class)
    public void testImportAllImpexFilesFromFolder_ifArgumentIsNull() {
        importService.importAllImpexFilesFromFolder(null);
    }

    @Test
    public void testImportAllImpexFilesFromFolder_ifSingleFolder() {
        importService.importAllImpexFilesFromFolder("resources/test/customImportService/forValid/forSingleFolder");

        UserModel result1 = userService.getUserForUID("victor@gmail.com");
        UserModel result2 = userService.getUserForUID("riko@epam.com");
        assertEquals("Victor", result1.getDisplayName());
        assertEquals("Riko", result2.getDisplayName());
    }

    @Test
    public void testImportAllImpexFilesFromFolder_ifComplexFolder() {
        importService.importAllImpexFilesFromFolder("resources/test/customImportService/forValid");

        UserModel result1 = userService.getUserForUID("victor@gmail.com");
        UserModel result2 = userService.getUserForUID("riko@epam.com");
        UserModel result3 = userService.getUserForUID("artur@gmail.com");
        assertEquals("Victor", result1.getDisplayName());
        assertEquals("Riko", result2.getDisplayName());
        assertEquals("Artur", result3.getDisplayName());
    }

    @Test
    public void testImportAllImpexFilesFromFolder_ifOneInvalidFile() {
        importService.importAllImpexFilesFromFolder("resources/test/customImportService");

        UserModel result1 = userService.getUserForUID("victor@gmail.com");
        UserModel result2 = userService.getUserForUID("riko@epam.com");
        UserModel result3 = userService.getUserForUID("artur@gmail.com");
        assertEquals("Victor", result1.getDisplayName());
        assertEquals("Riko", result2.getDisplayName());
        assertEquals("Artur", result3.getDisplayName());
    }
}