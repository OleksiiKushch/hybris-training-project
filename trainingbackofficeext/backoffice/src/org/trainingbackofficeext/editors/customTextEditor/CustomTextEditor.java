package org.trainingbackofficeext.editors.customTextEditor;

import com.hybris.cockpitng.editors.CockpitEditorRenderer;
import com.hybris.cockpitng.editors.EditorContext;
import com.hybris.cockpitng.editors.EditorListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;

import java.text.MessageFormat;

public class CustomTextEditor implements CockpitEditorRenderer<String> {
    private static final Logger LOG = LoggerFactory.getLogger(CustomTextEditor.class);

    public final static String PARAM_MAX_LENGTH = "max-length";
    private static final String MESSAGE = "Input can't be more than '{0}' symbols.";

    @Override
    public void render(Component component, EditorContext<String> editorContext, EditorListener<String> editorListener) {
        Textbox editorView = new Textbox();
        editorView.setValue(editorContext.getInitialValue());

        Integer maxLength = (Integer) editorContext.getParameter(PARAM_MAX_LENGTH);
        LOG.info("max length: " + maxLength);

        editorView.addEventListener(Events.ON_CHANGE, event -> handleEvent(editorView, event, editorListener, maxLength));
        editorView.setParent(component);
    }

    private void handleEvent(Textbox editorView, Event event, EditorListener<String> editorListener, Integer maxLength) {
        if (Events.ON_CHANGE.equals(event.getName())) {
            String input = (String) editorView.getRawValue();
            if (input.length() > maxLength) {
                String message = MessageFormat.format(MESSAGE, maxLength);
                LOG.debug(message);
                Messagebox.show(message);
            }
        }
    }
}
