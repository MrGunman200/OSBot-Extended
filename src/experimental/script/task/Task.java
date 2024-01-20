package experimental.script.task;

import experimental.provider.ExtraProvider;

public abstract class Task extends ExtraProvider {

    public abstract int process();

    public abstract boolean canProcess();

    public boolean isComplete() {
        return false;
    }

}
