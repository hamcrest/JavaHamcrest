package org.hamcrest.object;

import org.hamcrest.AbstractMatcherTest;
import org.hamcrest.Matcher;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.object.IsCompatibleType.typeCompatibleWith;

public class IsCompatibleTypeTest extends AbstractMatcherTest {
    public static class BaseClass {
    }

    public static class ExtendedClass extends BaseClass {
    }

    public interface BaseInterface {
    }

    public interface ExtendedInterface extends BaseInterface {
    }

    public static class ClassImplementingBaseInterface implements BaseInterface {
    }

    @Override
    protected Matcher<?> createMatcher() {
        return typeCompatibleWith(BaseClass.class);
    }

    public void testMatchesSameClass() {
        assertThat(BaseClass.class, typeCompatibleWith(BaseClass.class));
    }

    public void testMatchesSameInterface() {
        assertThat(BaseInterface.class, typeCompatibleWith(BaseInterface.class));
    }

    public void testMatchesExtendedClass() {
        assertThat(ExtendedClass.class, typeCompatibleWith(BaseClass.class));
    }

    public void testMatchesClassImplementingInterface() {
        assertThat(ClassImplementingBaseInterface.class, typeCompatibleWith(BaseInterface.class));
    }

    public void testMatchesExtendedInterface() {
        assertThat(ExtendedInterface.class, typeCompatibleWith(BaseInterface.class));
    }

//    public void testDoesNotMatchIncompatibleTypes() {
//        assertThat(BaseClass.class, not(compatibleType(ExtendedClass.class)));
//        assertThat(Integer.class, not(compatibleType(String.class)));
//    }

    public void testHasReadableDescription() {
        assertDescription("type < java.lang.Runnable", typeCompatibleWith(Runnable.class));
    }
}
