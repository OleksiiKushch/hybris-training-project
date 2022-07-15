package com.epam.training.dao;

import de.hybris.platform.product.daos.ProductDao;

public interface CustomProductDao extends ProductDao {
    /**
     * @param code the product code (article number)
     * @param catalogVersion the product catalog version (examples: Staged, Online, ...)
     * @return the product picture (image) URL
     */
    String getProductPictureUrl(String code, String catalogVersion);
}
