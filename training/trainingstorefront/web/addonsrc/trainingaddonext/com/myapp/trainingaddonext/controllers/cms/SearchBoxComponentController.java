package com.myapp.trainingaddonext.controllers.cms;

import de.hybris.platform.acceleratorcms.model.components.SearchBoxComponentModel;
import de.hybris.platform.servicelayer.exceptions.AttributeNotSupportedException;
import de.hybris.platform.servicelayer.model.ModelService;
import de.hybris.training.storefront.controllers.ControllerConstants;
import de.hybris.training.storefront.controllers.cms.AbstractAcceleratorCMSComponentController;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import static com.myapp.trainingaddonext.constants.TrainingaddonextWebConstants.SearchBoxComponent;
import static com.myapp.trainingaddonext.constants.TrainingaddonextWebConstants.ADDON_PREFIX;

@Controller("SearchBoxComponentController")
@RequestMapping(value = SearchBoxComponent)
public class SearchBoxComponentController extends AbstractAcceleratorCMSComponentController<SearchBoxComponentModel> {
    @Resource(name = "modelService")
    private ModelService modelService;

    @Override
    protected void fillModel(HttpServletRequest request, Model model, SearchBoxComponentModel component) {
        for (final String property : getCmsComponentService().getReadableEditorProperties(component)) {
            try {
                final Object value = modelService.getAttributeValue(component, property);
                model.addAttribute(property, value);
            }
            catch (final AttributeNotSupportedException ignore) {
                // ignore
            }
        }
    }

    @Override
    protected String getView(final SearchBoxComponentModel component) {
        return ADDON_PREFIX + ControllerConstants.Views.Cms.ComponentPrefix + StringUtils.lowerCase(getTypeCode(component));
    }
}
