package study.reflect;

import study.reflect.pkg.ClassInfo;
import study.reflect.pkg.ResourceInfo;
import study.reflect.pkg.Scanner;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Predicate;

/**
 * Created by taojinhou on 2020/7/29.
 */
public final class PackageResolver {
    private PackageResolver() {
    }


    public static Set<Class<?>> scanClass(String pkgName, ClassLoader loader, Predicate<Class<?>> filter) {
        Set<ResourceInfo> resourceInfos = new Scanner(pkgName, loader).scan();
        Set<Class<?>> classes = new HashSet<>();
        for (ResourceInfo resource : resourceInfos) {
            if (resource instanceof ClassInfo) {
                Class<?> clazz = ((ClassInfo) resource).load();
                if (filter.test(clazz)) {
                    classes.add(clazz);
                }
            }
        }

        return classes;
    }
}
