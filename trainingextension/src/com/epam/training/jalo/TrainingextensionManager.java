package com.epam.training.jalo;

import com.epam.training.constants.TrainingextensionConstants;
import de.hybris.platform.jalo.JaloSession;
import de.hybris.platform.jalo.extension.ExtensionManager;
import org.apache.log4j.Logger;

@SuppressWarnings("PMD")
public class TrainingextensionManager extends GeneratedTrainingextensionManager
{
	@SuppressWarnings("unused")
	private static final Logger log = Logger.getLogger( TrainingextensionManager.class.getName() );
	
	public static final TrainingextensionManager getInstance()
	{
		ExtensionManager em = JaloSession.getCurrentSession().getExtensionManager();
		return (TrainingextensionManager) em.getExtension(TrainingextensionConstants.EXTENSIONNAME);
	}
}
