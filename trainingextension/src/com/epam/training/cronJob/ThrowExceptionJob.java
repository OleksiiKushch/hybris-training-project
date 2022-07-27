package com.epam.training.cronJob;

import com.epam.training.model.ThrowExceptionCronJobModel;
import de.hybris.platform.servicelayer.cronjob.AbstractJobPerformable;
import de.hybris.platform.servicelayer.cronjob.PerformResult;

public class ThrowExceptionJob extends AbstractJobPerformable<ThrowExceptionCronJobModel> {
    @Override
    public PerformResult perform(ThrowExceptionCronJobModel throwExceptionCronJobModel) {
        throw new RuntimeException();
    }
}
