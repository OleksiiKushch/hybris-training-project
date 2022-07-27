package com.epam.training.cronJob;

import com.epam.training.model.ImpexUploadCronJobModel;
import de.hybris.bootstrap.annotations.IntegrationTest;
import de.hybris.platform.core.model.user.UserModel;
import de.hybris.platform.cronjob.enums.CronJobResult;
import de.hybris.platform.cronjob.enums.CronJobStatus;
import de.hybris.platform.cronjob.model.CompositeCronJobModel;
import de.hybris.platform.cronjob.model.CronJobModel;
import de.hybris.platform.servicelayer.ServicelayerTransactionalBaseTest;
import de.hybris.platform.servicelayer.cronjob.CronJobService;
import de.hybris.platform.servicelayer.impex.ImportService;
import de.hybris.platform.servicelayer.impex.impl.FileBasedImpExResource;
import de.hybris.platform.servicelayer.model.ModelService;
import de.hybris.platform.servicelayer.user.UserService;
import org.apache.commons.lang.CharEncoding;
import org.junit.Before;
import org.junit.Test;

import javax.annotation.Resource;
import java.io.File;

import static de.hybris.platform.testframework.Assert.assertEquals;

@IntegrationTest
public class CustomCompositeImpexUploadCronJobIntegrationTest extends ServicelayerTransactionalBaseTest {
    private static final String PATH_INIT_FILE = "resources/test/compositeImpexCronJob/compositeImpexCronJob_init.impex";

    @Resource
    private CronJobService cronJobService;
    @Resource
    private ImportService importService;
    @Resource
    private UserService userService;
    @Resource
    private ModelService modelService;

    @Before
    public void setUp() {
        initData();
    }

    private void initData() {
        importService.importData(new FileBasedImpExResource(new File(PATH_INIT_FILE), CharEncoding.UTF_8));
    }

    @Test
    public void testCustomCompositeImpexUploadCronJob() {
        CompositeCronJobModel compositeCronJobModel = (CompositeCronJobModel) cronJobService.getCronJob("impexUploadCompositeCronJob");

        cronJobService.performCronJob(compositeCronJobModel, true);

        CronJobModel cronJob1 = cronJobService.getCronJob("impexUploadServicelayerCronJob1");
        CronJobModel cronJob2 = cronJobService.getCronJob("impexUploadServicelayerCronJob2");
        CronJobModel cronJob3 = cronJobService.getCronJob("impexUploadServicelayerCronJob3");
        UserModel importUser1 = userService.getUserForUID("victor@gmail.com");
        UserModel importUser2 = userService.getUserForUID("riko@epam.com");
        UserModel importUser3 = userService.getUserForUID("artur@gmail.com");
        assertEquals(CronJobStatus.FINISHED, compositeCronJobModel.getStatus());
        assertEquals(CronJobResult.SUCCESS, compositeCronJobModel.getResult());
        assertEquals(CronJobStatus.FINISHED, cronJob1.getStatus());
        assertEquals(CronJobResult.SUCCESS, cronJob1.getResult());
        assertEquals(CronJobStatus.FINISHED, cronJob2.getStatus());
        assertEquals(CronJobResult.SUCCESS, cronJob2.getResult());
        assertEquals(CronJobStatus.FINISHED, cronJob3.getStatus());
        assertEquals(CronJobResult.SUCCESS, cronJob3.getResult());
        assertEquals("Victor", importUser1.getDisplayName());
        assertEquals("Riko", importUser2.getDisplayName());
        assertEquals("Artur", importUser3.getDisplayName());
    }

    @Test
    public void testCustomCompositeImpexUploadCronJob_ifOneOfCronJobsFailure() {
        CompositeCronJobModel compositeCronJobModel = (CompositeCronJobModel) cronJobService.getCronJob("impexUploadCompositeCronJob");
        ImpexUploadCronJobModel cronJob = (ImpexUploadCronJobModel) cronJobService.getCronJob("impexUploadServicelayerCronJob2");
        cronJob.setPathFolder("");
        modelService.save(cronJob);

        cronJobService.performCronJob(compositeCronJobModel, true);

        CronJobModel cronJob1 = cronJobService.getCronJob("impexUploadServicelayerCronJob1");
        CronJobModel cronJob2 = cronJobService.getCronJob("impexUploadServicelayerCronJob2");
        CronJobModel cronJob3 = cronJobService.getCronJob("impexUploadServicelayerCronJob3");
        UserModel importUser1 = userService.getUserForUID("victor@gmail.com");
        UserModel importUser2 = userService.getUserForUID("riko@epam.com");
        assertEquals(CronJobStatus.ABORTED, compositeCronJobModel.getStatus());
        assertEquals(CronJobResult.FAILURE, compositeCronJobModel.getResult());
        assertEquals(CronJobStatus.FINISHED, cronJob1.getStatus());
        assertEquals(CronJobResult.SUCCESS, cronJob1.getResult());
        assertEquals(CronJobStatus.FINISHED, cronJob2.getStatus());
        assertEquals(CronJobResult.FAILURE, cronJob2.getResult());
        assertEquals(CronJobStatus.UNKNOWN, cronJob3.getStatus());
        assertEquals(CronJobResult.UNKNOWN, cronJob3.getResult());
        assertEquals("Victor", importUser1.getDisplayName());
        assertEquals("Riko", importUser2.getDisplayName());
    }

    @Test
    public void testCustomCompositeImpexUploadCronJob_ifOneOfCronJobsThrowException() {
        CompositeCronJobModel compositeCronJobModel = (CompositeCronJobModel) cronJobService.getCronJob("compositeCronJobWithException");

        cronJobService.performCronJob(compositeCronJobModel, true);

        CronJobModel cronJob4 = cronJobService.getCronJob("impexUploadServicelayerCronJob4");
        CronJobModel exceptionCronJob = cronJobService.getCronJob("throwExceptionServicelayerCronJob");
        CronJobModel cronJob5 = cronJobService.getCronJob("impexUploadServicelayerCronJob5");
        UserModel importUser1 = userService.getUserForUID("victor@gmail.com");
        UserModel importUser2 = userService.getUserForUID("riko@epam.com");
        assertEquals(CronJobStatus.ABORTED, compositeCronJobModel.getStatus());
        assertEquals(CronJobResult.FAILURE, compositeCronJobModel.getResult());
        assertEquals(CronJobStatus.FINISHED, cronJob4.getStatus());
        assertEquals(CronJobResult.SUCCESS, cronJob4.getResult());
        assertEquals(CronJobStatus.ABORTED, exceptionCronJob.getStatus());
        assertEquals(CronJobResult.ERROR, exceptionCronJob.getResult());
        assertEquals(CronJobStatus.UNKNOWN, cronJob5.getStatus());
        assertEquals(CronJobResult.UNKNOWN, cronJob5.getResult());
        assertEquals("Victor", importUser1.getDisplayName());
        assertEquals("Riko", importUser2.getDisplayName());
    }
}
