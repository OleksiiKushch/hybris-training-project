package com.epam.training.cronJob;

import com.epam.training.model.ImpexUploadCronJobModel;
import de.hybris.bootstrap.annotations.IntegrationTest;
import de.hybris.platform.core.model.user.UserModel;
import de.hybris.platform.cronjob.enums.CronJobResult;
import de.hybris.platform.cronjob.enums.CronJobStatus;
import de.hybris.platform.servicelayer.ServicelayerTransactionalBaseTest;
import de.hybris.platform.servicelayer.cronjob.CronJobService;
import de.hybris.platform.servicelayer.internal.model.ServicelayerJobModel;
import de.hybris.platform.servicelayer.model.ModelService;
import de.hybris.platform.servicelayer.user.UserService;
import org.junit.Before;
import org.junit.Test;

import javax.annotation.Resource;

import static de.hybris.platform.testframework.Assert.assertEquals;

@IntegrationTest
public class ImpexUploadJobIntegrationTest extends ServicelayerTransactionalBaseTest {
    @Resource
    private CronJobService cronJobService;
    @Resource
    private ModelService modelService;
    @Resource
    private UserService userService;

    private ServicelayerJobModel servicelayerJobModel = null;
    private ImpexUploadCronJobModel impexUploadCronJobModel = null;

    @Before
    public void setUp() {
        servicelayerJobModel = modelService.create(ServicelayerJobModel.class);
        servicelayerJobModel.setSpringId("impexUploadJob");
        servicelayerJobModel.setCode("impexUploadJob");
        modelService.save(servicelayerJobModel);
    }

    @Test
    public void testPerform() throws InterruptedException {
        impexUploadCronJobModel = modelService.create(ImpexUploadCronJobModel.class);
        impexUploadCronJobModel.setActive(Boolean.TRUE);
        impexUploadCronJobModel.setJob(servicelayerJobModel);
        impexUploadCronJobModel.setPathFolder("resources/test/customImportService/forValid");
        modelService.save(impexUploadCronJobModel);

        cronJobService.performCronJob(impexUploadCronJobModel, true);

        assertEquals(CronJobStatus.FINISHED, impexUploadCronJobModel.getStatus());
        assertEquals(CronJobResult.SUCCESS, impexUploadCronJobModel.getResult());
        UserModel result1 = userService.getUserForUID("victor@gmail.com");
        UserModel result2 = userService.getUserForUID("riko@epam.com");
        UserModel result3 = userService.getUserForUID("artur@gmail.com");
        assertEquals("Victor", result1.getDisplayName());
        assertEquals("Riko", result2.getDisplayName());
        assertEquals("Artur", result3.getDisplayName());
    }

    @Test
    public void testPerform_ifPathFolderIsEmpty() throws InterruptedException {
        impexUploadCronJobModel = modelService.create(ImpexUploadCronJobModel.class);
        impexUploadCronJobModel.setActive(Boolean.TRUE);
        impexUploadCronJobModel.setJob(servicelayerJobModel);
        impexUploadCronJobModel.setPathFolder("");
        modelService.save(impexUploadCronJobModel);

        cronJobService.performCronJob(impexUploadCronJobModel, true);

        assertEquals(CronJobStatus.FINISHED, impexUploadCronJobModel.getStatus());
        assertEquals(CronJobResult.FAILURE, impexUploadCronJobModel.getResult());
    }
}