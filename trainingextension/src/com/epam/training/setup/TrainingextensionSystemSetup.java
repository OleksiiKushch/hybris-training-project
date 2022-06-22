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

import static com.epam.training.constants.TrainingextensionConstants.PLATFORM_LOGO_CODE;

import de.hybris.platform.core.initialization.SystemSetup;

import java.io.InputStream;

import com.epam.training.constants.TrainingextensionConstants;
import com.epam.training.service.TrainingextensionService;


@SystemSetup(extension = TrainingextensionConstants.EXTENSIONNAME)
public class TrainingextensionSystemSetup
{
	private final TrainingextensionService trainingextensionService;

	public TrainingextensionSystemSetup(final TrainingextensionService trainingextensionService)
	{
		this.trainingextensionService = trainingextensionService;
	}

	@SystemSetup(process = SystemSetup.Process.INIT, type = SystemSetup.Type.ESSENTIAL)
	public void createEssentialData()
	{
		trainingextensionService.createLogo(PLATFORM_LOGO_CODE);
	}

	private InputStream getImageStream()
	{
		return TrainingextensionSystemSetup.class.getResourceAsStream("/trainingextension/sap-hybris-platform.png");
	}
}
