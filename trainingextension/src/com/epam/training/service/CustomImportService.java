package com.epam.training.service;

import de.hybris.platform.impex.jalo.ImpExException;
import de.hybris.platform.servicelayer.impex.ImportService;

import java.io.IOException;

public interface CustomImportService extends ImportService {
    void importAllImpexFilesFromFolder(String pathFolder);
}
