package com.epam.training.facade.populator;

import com.epam.trainingextension.dto.UserData;
import de.hybris.platform.converters.Populator;
import de.hybris.platform.core.model.user.UserModel;
import de.hybris.platform.servicelayer.dto.converter.ConversionException;
import de.hybris.platform.servicelayer.util.ServicesUtil;

public class CustomUserPopulator implements Populator<UserModel, UserData> {
    @Override
    public void populate(UserModel userModel, UserData userData) throws ConversionException {
        ServicesUtil.validateParameterNotNull(userModel, "The given userModel (source) is null!");
        ServicesUtil.validateParameterNotNull(userData, "The given userData (target) is null!");

        userData.setEmail(userModel.getUid());
        userData.setFirstName(userModel.getName());
        userData.setCreationTime(userModel.getCreationtime());
        userData.setModifiedTime(userModel.getModifiedtime());
    }
}
