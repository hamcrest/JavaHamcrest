package org.hamcrest.generator;

public interface SugarConfiguration {
    void addWriter(FactoryWriter factoryWriter);

    void addFactoryMethod(FactoryMethod method);

    void addFactoryMethods(Iterable<FactoryMethod> methods);
}
