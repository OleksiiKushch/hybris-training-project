package com.epam.training.facade.populator;

import com.epam.trainingextension.dto.UserData;
import de.hybris.bootstrap.annotations.IntegrationTest;
import de.hybris.platform.core.model.user.UserModel;
import de.hybris.platform.servicelayer.ServicelayerTransactionalBaseTest;
import de.hybris.platform.servicelayer.impex.ImportService;
import de.hybris.platform.servicelayer.impex.impl.FileBasedImpExResource;
import de.hybris.platform.servicelayer.user.UserService;
import org.apache.commons.lang.CharEncoding;
import org.junit.Before;
import org.junit.Test;

import javax.annotation.Resource;
import java.io.File;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@IntegrationTest
public class CustomUserPopulatorIntegrationTest extends ServicelayerTransactionalBaseTest {
    private static final String PATH_INIT_FILE = "resources/test/customUserPopulator/customUserPopulator_init.impex";

    @Resource
    CustomUserPopulator customUserPopulator;
    @Resource
    ImportService importService;
    @Resource
    UserService userService;

    @Before
    public void setUp() {
        importService.importData(new FileBasedImpExResource(new File(PATH_INIT_FILE), CharEncoding.UTF_8));
    }

    @Test
    public void testPopulate() {
        UserModel userModel = userService.getUserForUID("alex@gmail.com");
        UserData userData = new UserData();

        customUserPopulator.populate(userModel, userData);

        assertEquals("alex@gmail.com", userData.getEmail());
        assertEquals("Alex", userData.getFirstName());
        assertEquals("Tue Jul 12 09:21:19 EEST 2022", userData.getCreationTime().toString());
        assertNotNull(userData.getModifiedTime());
    }
}