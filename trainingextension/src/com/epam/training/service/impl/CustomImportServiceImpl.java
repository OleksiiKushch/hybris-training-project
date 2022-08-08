package com.epam.training.service.impl;

import com.epam.training.service.CustomImportService;
import com.epam.training.util.FileSearcher;
import de.hybris.platform.servicelayer.impex.ImpExResource;
import de.hybris.platform.servicelayer.impex.ImportResult;
import de.hybris.platform.servicelayer.impex.impl.DefaultImportService;
import de.hybris.platform.servicelayer.impex.impl.FileBasedImpExResource;
import de.hybris.platform.servicelayer.util.ServicesUtil;
import de.hybris.platform.tx.Transaction;
import org.apache.commons.lang.CharEncoding;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.List;

public class CustomImportServiceImpl extends DefaultImportService implements CustomImportService {
    private static final Logger LOG = LoggerFactory.getLogger(DefaultImportService.class);

    private static final String MSG_GIVEN_PATH_FOLDER_IS_NULL = "The given pathFolder is null!";
    private static final String EXTENSION_IMPEX = "impex";

    @Override
    public void importAllImpexFilesFromFolder(String pathFolder, boolean isTransactional) {
        ServicesUtil.validateParameterNotNull(pathFolder, MSG_GIVEN_PATH_FOLDER_IS_NULL);
        List<File> files = FileSearcher.getAllFiles(new File(pathFolder), EXTENSION_IMPEX);
        LOG.info("Find files: {}", files.size());
        if (isTransactional) {
            importAllImpexFilesFromFolderTransactExecution(files);
        } else {
            importAllImpexFilesFromFolderDefaultExecution(files);
        }
    }

    private void importAllImpexFilesFromFolderDefaultExecution(List<File> files) {
        for (File file : files) {
            LOG.info("Try to import file: {}", file);
            ImpExResource resource = new FileBasedImpExResource(file, CharEncoding.UTF_8);
            importData(resource);
        }
    }

    private void importAllImpexFilesFromFolderTransactExecution(List<File> files) {
        boolean success = true;
        Transaction tx = Transaction.current();
        tx.begin();
        for (File file : files) {
            LOG.info("Try to import file: {}", file);
            ImpExResource resource = new FileBasedImpExResource(file, CharEncoding.UTF_8);
            ImportResult tempResult = importData(resource);
            if (!tempResult.isSuccessful()) {
                success = false;
                break;
            }
        }
        if (success) {
            tx.commit();
        } else {
            tx.rollback();
        }
    }
}
