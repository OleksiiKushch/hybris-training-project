package org.trainingbackofficeext.widgets;

import com.hybris.cockpitng.annotations.ViewEvent;
import com.hybris.cockpitng.util.DefaultWidgetController;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.Textbox;

import static java.text.MessageFormat.format;
import static org.zkoss.zul.Messagebox.show;

public class LimitedInputController extends DefaultWidgetController {
    private static final String MESSAGE = "Input can't be more than '{0}' symbols.";
    private static final int MAX_LENGTH = 5;

    private Textbox input;

    @ViewEvent(componentID = "submit", eventName = Events.ON_CLICK)
    public void onSubmit() {
        String text = input.getText();
        if (!isValidLength(text.length())) {
            show(format(MESSAGE, MAX_LENGTH));
        }
    }

    private boolean isValidLength(int inputLength) {
        return inputLength <= MAX_LENGTH;
    }

}
