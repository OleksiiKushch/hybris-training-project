package com.epam.training.service;

import de.hybris.platform.servicelayer.impex.ImportService;

public interface CustomImportService extends ImportService {
    void importAllImpexFilesFromFolder(String pathFolder, boolean isTransactional);
}
