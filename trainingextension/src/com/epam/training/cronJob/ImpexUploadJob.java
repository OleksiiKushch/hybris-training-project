package com.epam.training.cronJob;

import com.epam.training.model.ImpexUploadCronJobModel;
import com.epam.training.service.CustomImportService;
import de.hybris.platform.cronjob.enums.CronJobResult;
import de.hybris.platform.cronjob.enums.CronJobStatus;
import de.hybris.platform.servicelayer.cronjob.AbstractJobPerformable;
import de.hybris.platform.servicelayer.cronjob.PerformResult;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Required;

public class ImpexUploadJob extends AbstractJobPerformable<ImpexUploadCronJobModel> {
    private static final Logger LOG = LoggerFactory.getLogger(ImpexUploadJob.class);

    private CustomImportService customImportService;

    @Required
    public void setCustomImportService(CustomImportService customImportService) {
        this.customImportService = customImportService;
    }

    @Override
    public PerformResult perform(ImpexUploadCronJobModel cronJobModel)
    {
        LOG.info("Start impex cron job!");
        String pathFolder = cronJobModel.getPathFolder();
        if (StringUtils.isEmpty(pathFolder)) {
            return new PerformResult(CronJobResult.FAILURE, CronJobStatus.FINISHED);
        }
        try {
            customImportService.importAllImpexFilesFromFolder(pathFolder, false);
        } catch (IllegalArgumentException exception) {
            LOG.warn(exception.getMessage());
            return new PerformResult(CronJobResult.FAILURE, CronJobStatus.FINISHED);
        }
        LOG.info("Import data from directory: {} was successfully", pathFolder);
        return new PerformResult(CronJobResult.SUCCESS, CronJobStatus.FINISHED);
    }
}
