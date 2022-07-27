package com.epam.training.cronJob;

import de.hybris.platform.catalog.job.CompositeJobPerformable;
import de.hybris.platform.cronjob.enums.CronJobResult;
import de.hybris.platform.cronjob.enums.CronJobStatus;
import de.hybris.platform.cronjob.jalo.Job;
import de.hybris.platform.cronjob.jalo.TriggerableJob;
import de.hybris.platform.cronjob.model.CompositeCronJobModel;
import de.hybris.platform.cronjob.model.CompositeEntryModel;
import de.hybris.platform.cronjob.model.CronJobModel;
import de.hybris.platform.servicelayer.cronjob.CronJobFactory;
import de.hybris.platform.servicelayer.cronjob.CronJobService;
import de.hybris.platform.servicelayer.cronjob.PerformResult;
import de.hybris.platform.servicelayer.exceptions.SystemException;
import de.hybris.platform.servicelayer.internal.model.ServicelayerJobModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Required;

public class CustomCompositeJob extends CompositeJobPerformable {
    private static final Logger LOG = LoggerFactory.getLogger(CustomCompositeJob.class);
    private static final int WAITING_STEP_TIME = 1000; // 1 sec.

    private CronJobService cronJobService;

    @Required
    public void setCronJobService(final CronJobService cronJobService) {
        this.cronJobService = cronJobService;
    }

    @Override
    public PerformResult perform(CompositeCronJobModel cronJob) {
        for (CompositeEntryModel entry : cronJob.getCompositeEntries()) {
            try {
                CronJobModel executedCronJob = executeCompositeEntry(entry);
                while (cronJobService.isRunning(getRefreshedCronJobModel(executedCronJob))) {
                    // Wait a little bit and try again
                    try {
                        Thread.sleep(WAITING_STEP_TIME);
                    }
                    catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                }
                if (getRefreshedCronJobModel(executedCronJob).getResult() != CronJobResult.SUCCESS) {
                    return new PerformResult(CronJobResult.FAILURE, CronJobStatus.ABORTED);
                }
            }
            catch (SystemException e) {
                LOG.error("Error while performing the composite job entry {}. Details {}", entry, e);
                return new PerformResult(CronJobResult.ERROR, CronJobStatus.ABORTED);
            }
        }
        return new PerformResult(CronJobResult.SUCCESS, CronJobStatus.FINISHED);
    }

    protected CronJobModel executeCompositeEntry(CompositeEntryModel compositeEntryModel)
    {
        CronJobModel executableCronJob = compositeEntryModel.getExecutableCronJob();
        if (executableCronJob != null) { // got the cronjob -> execute it
            cronJobService.performCronJob(executableCronJob, true);
        }
        else if (compositeEntryModel.getTriggerableJob() != null) { // do we have a triggerable
            Job job = modelService.getSource(compositeEntryModel.getTriggerableJob());
            if (job instanceof TriggerableJob) {  // old way
                executableCronJob = (CronJobModel) modelService.get(((TriggerableJob) job).newExecution());
            }
            else if (compositeEntryModel.getTriggerableJob() instanceof ServicelayerJobModel) {
                ServicelayerJobModel serviceLayerTriggerable = (ServicelayerJobModel) compositeEntryModel.getTriggerableJob();
                CronJobFactory cronJobFactory = cronJobService.getCronJobFactory(serviceLayerTriggerable);
                executableCronJob = cronJobFactory.createCronJob(serviceLayerTriggerable);
            }
            else {
                throw new UnsupportedOperationException("Neither a CronJobModel or TriggerableJob instance was assigned!");
            }
            cronJobService.performCronJob(executableCronJob, true);
        }
        return executableCronJob;
    }
}
