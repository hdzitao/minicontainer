package study.reflect.pkg;

/**
 * copy自 Guava
 * Represents a class that can be loaded through {@link #load}.
 *
 * @since 14.0
 */
public final class ClassInfo extends ResourceInfo {
    private static final String CLASS_FILE_NAME_EXTENSION = ".class";
    private final String className;

    ClassInfo(String resourceName, ClassLoader loader) {
        super(resourceName, loader);
        this.className = getClassName(resourceName);
    }

    /**
     * Returns the package name of the class, without attempting to load the class.
     *
     * <p>Behaves identically to {@link Package#getName()} but does not require the class (or
     * package) to be loaded.
     */
    public String getPackageName() {
        int lastDot = className.lastIndexOf('.');
        return (lastDot < 0) ? "" : className.substring(0, lastDot);
    }

    /**
     * Returns the simple name of the underlying class as given in the source code.
     *
     * <p>Behaves identically to {@link Class#getSimpleName()} but does not require the class to be
     * loaded.
     */
    public String getSimpleName() {
        int lastDollarSign = className.lastIndexOf('$');
        if (lastDollarSign != -1) {
            // local and anonymous classes are prefixed with number (1,2,3...), anonymous classes are
            // entirely numeric whereas local classes have the user supplied name as a suffix
            return className.substring(lastDollarSign + 1);
        }
        String packageName = getPackageName();
        if (packageName.isEmpty()) {
            return className;
        }

        // Since this is a top level class, its simple name is always the part after package name.
        return className.substring(packageName.length() + 1);
    }

    /**
     * Returns the fully qualified name of the class.
     *
     * <p>Behaves identically to {@link Class#getName()} but does not require the class to be
     * loaded.
     */
    public String getName() {
        return className;
    }

    /**
     * Loads (but doesn't link or initialize) the class.
     *
     * @throws LinkageError when there were errors in loading classes that this class depends on.
     *                      For example, {@link NoClassDefFoundError}.
     */
    public Class<?> load() {
        try {
            return Class.forName(className, false, loader);
        } catch (Throwable e) {
            return null;
        }
    }

    public static boolean isMatch(String name) {
        return name.endsWith(CLASS_FILE_NAME_EXTENSION);
    }

    private String getClassName(String filename) {
        int classNameEnd = filename.length() - CLASS_FILE_NAME_EXTENSION.length();
        return filename.substring(0, classNameEnd).replace('/', '.');
    }

    @Override
    public String toString() {
        return className;
    }
}
