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
package com.epam.cmstypesaddon.constants;

import com.epam.cmstypesaddon.model.ArticleModel;

import static com.epam.cmstypesaddon.constants.CmstypesaddonConstants.EXTENSIONNAME;

/**
 * Global class for all Cmstypesaddon web constants. You can add global constants for your extension into this class.
 */
public final class CmstypesaddonWebConstants // NOSONAR
{
	public static final String Article = "/view/" + ArticleModel._TYPECODE + "Controller";
	public static final String ADDON_PREFIX = "addon:/" + EXTENSIONNAME + "/";

	private CmstypesaddonWebConstants()
	{
		//empty to avoid instantiating this constant class
	}

	// implement here constants used by this extension
}
