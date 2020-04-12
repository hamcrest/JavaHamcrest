package org.hamcrest.reflection;

import java.lang.reflect.Member;
import java.lang.reflect.Modifier;
import java.util.Objects;

/**
 * Represents the 4 states of visibility.
 *
 * @author JJ Brown
 */
enum Visibility {
    PUBLIC("public"),
    PROTECTED("protected"),
    PACKAGE_PROTECTED("package-protected (no modifiers)"),
    PRIVATE("private");

    public String getDescription() {
        return description;
    }

    private final String description;

    Visibility(String description) {
        this.description = description;
    }

    static Visibility of(Class<?> clazz) {
        Objects.requireNonNull(clazz, "Cannot determine the visibility of a null-valued reflective Class object");

        if (Modifier.isPublic(clazz.getModifiers())) {
            return Visibility.PUBLIC;
        }
        if (Modifier.isProtected(clazz.getModifiers())) {
            return Visibility.PROTECTED;
        }
        if (Modifier.isPrivate(clazz.getModifiers())) {
            return Visibility.PRIVATE;
        }
        return Visibility.PACKAGE_PROTECTED;
    }

    static Visibility of(Member member) {
        Objects.requireNonNull(member, "Cannot determine the visibility of a null-valued reflective member object");

        if (Modifier.isPublic(member.getModifiers())) {
            return Visibility.PUBLIC;
        }
        if (Modifier.isProtected(member.getModifiers())) {
            return Visibility.PROTECTED;
        }
        if (Modifier.isPrivate(member.getModifiers())) {
            return Visibility.PRIVATE;
        }
        return Visibility.PACKAGE_PROTECTED;
    }
}
