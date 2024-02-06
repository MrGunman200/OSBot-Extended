package api.script.task;

import api.provider.ExtraProvider;

public abstract class Task extends ExtraProvider {

    public abstract int process();

    public abstract boolean canProcess();

    public boolean isComplete() {
        return false;
    }

}
