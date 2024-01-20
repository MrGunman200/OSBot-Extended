package experimental.provider;

import org.osbot.rs07.event.ScriptExecutor;
import org.osbot.rs07.script.MethodProvider;

import java.util.HashMap;
import java.util.Map;

public class MethodProviders {

    private static final HashMap<Thread, MethodProvider> contextMap = new HashMap<>();
    private static final HashMap<ScriptExecutor, MethodProvider> executorMap = new HashMap<>();

    public static MethodProvider getContext() {
        if (!executorMap.isEmpty()) {
            final HashMap<ScriptExecutor, MethodProvider> tempMap = new HashMap<>(executorMap);

            for (Map.Entry<ScriptExecutor, MethodProvider> entry : tempMap.entrySet()) {
                final ScriptExecutor k = entry.getKey();
                final Thread thread = k.getThread();

                if (thread != null) {
                    register(thread, entry.getValue());
                    executorMap.remove(k);
                }

            }

        }

        final MethodProvider methodProvider = contextMap.get(Thread.currentThread());

        if (methodProvider == null) {
            throw new NullPointerException("Thread isn't registered to a provider");
        }

        return methodProvider;
    }

    public static void register(MethodProvider methodProvider) {
        register(Thread.currentThread(), methodProvider);
    }

    public static void register(ScriptExecutor scriptExecutor, MethodProvider methodProvider) {
        executorMap.put(scriptExecutor, methodProvider);
    }

    public static void register(Thread thread, MethodProvider methodProvider) {
        contextMap.put(thread, methodProvider);
    }

    public static void unregister() {
        unregister(contextMap.get(Thread.currentThread()));
    }

    public static void unregister(Thread thread) {
        contextMap.remove(thread);
    }

    public static void unregister(MethodProvider methodProvider) {
        if (methodProvider != null) {
            final HashMap<Thread, MethodProvider> tempContextMap = new HashMap<>(contextMap);
            final HashMap<ScriptExecutor, MethodProvider> tempExecutorMap = new HashMap<>(executorMap);

            for (Map.Entry<Thread, MethodProvider> entry : tempContextMap.entrySet()) {
                if (entry.getValue().equals(methodProvider)) {
                    contextMap.remove(entry.getKey());
                }
            }

            for (Map.Entry<ScriptExecutor, MethodProvider> entry : tempExecutorMap.entrySet()) {
                if (entry.getValue().equals(methodProvider)) {
                    executorMap.remove(entry.getKey());
                }
            }

        }

    }

}
