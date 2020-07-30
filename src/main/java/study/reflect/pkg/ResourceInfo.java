package study.reflect.pkg;

import java.net.URL;
import java.util.NoSuchElementException;

/**
 * copy自 Guava
 * Represents a class path resource that can be either a class file or any other resource file
 * loadable from the class path.
 *
 * @since 14.0
 */
public class ResourceInfo {
    private final String resourceName;

    final ClassLoader loader;

    ResourceInfo(String resourceName, ClassLoader loader) {
        this.resourceName = resourceName;
        this.loader = loader;
    }

    /**
     * Returns the url identifying the resource.
     *
     * <p>See {@link ClassLoader#getResource}
     *
     * @throws NoSuchElementException if the resource cannot be loaded through the class loader,
     *                                despite physically existing in the class path.
     */
    public final URL url() {
        URL url = loader.getResource(resourceName);
        if (url == null) {
            throw new NoSuchElementException(resourceName);
        }
        return url;
    }

    /**
     * Returns the fully qualified name of the resource. Such as "com/mycomp/foo/bar.txt".
     */
    public final String getResourceName() {
        return resourceName;
    }

    @Override
    public int hashCode() {
        return resourceName.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof ResourceInfo) {
            ResourceInfo that = (ResourceInfo) obj;
            return resourceName.equals(that.resourceName) && loader == that.loader;
        }
        return false;
    }

    // Do not change this arbitrarily. We rely on it for sorting ResourceInfo.
    @Override
    public String toString() {
        return resourceName;
    }
}
