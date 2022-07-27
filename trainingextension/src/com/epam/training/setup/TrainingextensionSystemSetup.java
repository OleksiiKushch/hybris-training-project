/*
 * [y] hybris Platform
 *
 * Copyright (c) 2018 SAP SE or an SAP affiliate company. All rights reserved.
 *
 * This software is the confidential and proprietary information of SAP
 * ("Confidential Information"). You shall not disclose such Confidential
 * Information and shall use it only in accordance with the terms of the
 * license agreement you entered into with SAP.
 */
package com.epam.training.setup;

import com.epam.training.constants.TrainingextensionConstants;
import com.epam.training.service.TrainingextensionService;
import de.hybris.platform.core.initialization.SystemSetup;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStream;

import static com.epam.training.constants.TrainingextensionConstants.PLATFORM_LOGO_CODE;


@SystemSetup(extension = TrainingextensionConstants.EXTENSIONNAME)
public class TrainingextensionSystemSetup {
	private static final Logger LOG = LoggerFactory.getLogger(TrainingextensionSystemSetup.class);
	private final TrainingextensionService trainingextensionService;

	public TrainingextensionSystemSetup(final TrainingextensionService trainingextensionService) {
		this.trainingextensionService = trainingextensionService;
	}

	@SystemSetup(process = SystemSetup.Process.INIT, type = SystemSetup.Type.ESSENTIAL)
	public void createEssentialData() {
		trainingextensionService.createLogo(PLATFORM_LOGO_CODE);
	}

	private InputStream getImageStream()  {
		return TrainingextensionSystemSetup.class.getResourceAsStream("/trainingextension/sap-hybris-platform.png");
	}
}
