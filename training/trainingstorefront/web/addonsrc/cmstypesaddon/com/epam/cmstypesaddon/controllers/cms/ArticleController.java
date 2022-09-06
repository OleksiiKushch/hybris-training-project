package com.epam.cmstypesaddon.controllers.cms;

import com.epam.cmstypesaddon.model.ArticleModel;
import de.hybris.platform.servicelayer.exceptions.AttributeNotSupportedException;
import de.hybris.platform.servicelayer.model.ModelService;
import de.hybris.training.storefront.controllers.ControllerConstants;
import de.hybris.training.storefront.controllers.cms.AbstractAcceleratorCMSComponentController;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import static com.epam.cmstypesaddon.constants.CmstypesaddonWebConstants.ADDON_PREFIX;
import static com.epam.cmstypesaddon.constants.CmstypesaddonWebConstants.Article;

@Controller("ArticleController")
@RequestMapping(value = Article)
public class ArticleController extends AbstractAcceleratorCMSComponentController<ArticleModel> {
    @Resource(name = "modelService")
    private ModelService modelService;

    @Override
    protected void fillModel(HttpServletRequest request, Model model, ArticleModel component) {
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
    protected String getView(final ArticleModel component) {
        return ADDON_PREFIX + ControllerConstants.Views.Cms.ComponentPrefix + StringUtils.lowerCase(getTypeCode(component));
    }
}
