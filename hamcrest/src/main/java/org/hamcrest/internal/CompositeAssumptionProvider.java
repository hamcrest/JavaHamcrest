package org.hamcrest.internal;

import org.hamcrest.Matcher;

import java.util.List;

class CompositeAssumptionProvider extends AssumptionProvider {

    final List<AssumptionProvider> providers;

    CompositeAssumptionProvider(List<AssumptionProvider> providers) {
        this.providers = providers;
    }

    @Override
    public <T> void assumeThat(String message, T actual, Matcher<T> matcher) {
        for (AssumptionProvider provider : providers) {
            provider.assumeThat(message, actual, matcher);
        }
    }
}
