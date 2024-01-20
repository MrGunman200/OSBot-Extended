package experimental.provider;

import api.invoking.InvokeHelper;
import org.osbot.rs07.script.Script;

public class Helpers {

    public Helpers(Script script) {
        this.script = script;
    }

    private final Script script;
    private InvokeHelper invokeHelper;

    public InvokeHelper getInvokeHelper() {
        return invokeHelper;
    }

    public void setHelpers() {
        invokeHelper = new InvokeHelper(script);
    }

}
