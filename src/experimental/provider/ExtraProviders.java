package experimental.provider;

import org.osbot.rs07.event.ScriptExecutor;

import java.util.HashMap;
import java.util.Map;

public class ExtraProviders {

    private static final HashMap<Thread, ExtraProvider> contextMap = new HashMap<>();
    private static final HashMap<ScriptExecutor, ExtraProvider> executorMap = new HashMap<>();

    public static ExtraProvider getContext() {
        if (!executorMap.isEmpty()) {
            final HashMap<ScriptExecutor, ExtraProvider> tempMap = new HashMap<>(executorMap);

            for (Map.Entry<ScriptExecutor, ExtraProvider> entry : tempMap.entrySet()) {
                final ScriptExecutor k = entry.getKey();
                final Thread thread = k.getThread();

                if (thread != null) {
                    register(thread, entry.getValue());
                    executorMap.remove(k);
                }

            }

        }

        final ExtraProvider extraProvider = contextMap.get(Thread.currentThread());

        if (extraProvider == null) {
            throw new NullPointerException("Thread isn't registered to a provider");
        }

        return extraProvider;
    }

    public static void register(ExtraProvider extraProvider) {
        register(Thread.currentThread(), extraProvider);
    }

    public static void register(ScriptExecutor scriptExecutor, ExtraProvider extraProvider) {
        executorMap.put(scriptExecutor, extraProvider);
    }

    public static void register(Thread thread, ExtraProvider extraProvider) {
        contextMap.put(thread, extraProvider);
    }

    public static void unregister() {
        unregister(contextMap.get(Thread.currentThread()));
    }

    public static void unregister(Thread thread) {
        contextMap.remove(thread);
    }

    public static void unregister(ExtraProvider extraProvider) {
        if (extraProvider != null) {
            final HashMap<Thread, ExtraProvider> tempContextMap = new HashMap<>(contextMap);
            final HashMap<ScriptExecutor, ExtraProvider> tempExecutorMap = new HashMap<>(executorMap);

            for (Map.Entry<Thread, ExtraProvider> entry : tempContextMap.entrySet()) {
                if (entry.getValue().equals(extraProvider)) {
                    contextMap.remove(entry.getKey());
                }
            }

            for (Map.Entry<ScriptExecutor, ExtraProvider> entry : tempExecutorMap.entrySet()) {
                if (entry.getValue().equals(extraProvider)) {
                    executorMap.remove(entry.getKey());
                }
            }

        }

    }

}
