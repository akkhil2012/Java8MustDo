package org.example;

public interface ContextAware {
    String getName();

    String getContext();

    void setContext(String context);

    default void addToContext(String context, String name) {
        String contextWithSuffix = context.isEmpty() ? context : context + " / ";
        setContext(contextWithSuffix + name);
    }
}
