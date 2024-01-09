package experimental.api.script.task;

import experimental.api.script.LoopScript;

import java.util.ArrayList;

public abstract class TaskScript extends LoopScript {

    private final ArrayList<Task> tasks = new ArrayList<>();

    public abstract ArrayList<Task> Tasks();

    public void onStart() throws InterruptedException {
        super.onStart();
        tasks.addAll(Tasks());
        for (Task task : tasks) {
            task.exchangeContext(this.getBot());
        }
    }

    public int onLoop() {
        try {
            return runTasks();
        } catch (Exception | Error e) {
            e.printStackTrace();
            log(e);
        }

        return 600;
    }

    private int runTasks() {
        final ArrayList<Task> taskCopy = new ArrayList<>(tasks);

        for (Task task : taskCopy) {
            if (task.isComplete()) {
                removeTask(task);
                return 0;
            } else if (task.canProcess()) {
                return task.process();
            }
        }

        return 600;
    }

    public synchronized ArrayList<Task> getTasks() {
        return tasks;
    }

    public synchronized void removeTask(Task task) {
        tasks.remove(task);
    }

    public synchronized void addTask(Task task) {
        task.exchangeContext(this.getBot());
        tasks.add(task);
    }

}
