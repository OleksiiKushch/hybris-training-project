package com.epam.training.service;

import de.hybris.platform.core.model.user.UserModel;
import de.hybris.platform.servicelayer.user.UserService;

import java.util.List;

public interface CustomUserService extends UserService {
    List<UserModel> getAllUsersWithoutAddress();
}
