/*
 * [y] hybris Platform
 *
 * Copyright (c) 2018 SAP SE or an SAP affiliate company.  All rights reserved.
 *
 * This software is the confidential and proprietary information of SAP
 * ("Confidential Information"). You shall not disclose such Confidential
 * Information and shall use it only in accordance with the terms of the
 * license agreement you entered into with SAP.
 */
package com.myapp.trainingaddonext.constants;

import de.hybris.platform.acceleratorcms.model.components.SearchBoxComponentModel;

import static com.myapp.trainingaddonext.constants.TrainingaddonextConstants.EXTENSIONNAME;

/**
 * Global class for all Trainingaddonext web constants. You can add global constants for your extension into this class.
 */
public final class TrainingaddonextWebConstants // NOSONAR
{
	public static final String SearchBoxComponent = "/view/" + SearchBoxComponentModel._TYPECODE + "Controller";
	public static final String ADDON_PREFIX = "addon:/" + EXTENSIONNAME + "/";

	private TrainingaddonextWebConstants()
	{
		//empty to avoid instantiating this constant class
	}

	// implement here constants used by this extension
}
