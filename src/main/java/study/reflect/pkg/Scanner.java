package study.reflect.pkg;

import lombok.Getter;
import lombok.SneakyThrows;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.*;
import java.util.jar.Attributes;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.jar.Manifest;

/**
 * copy自 Guava,经过重构
 * Created by taojinhou on 2019/11/13.
 */
public class Scanner {
    // 已扫描的路径
    private final Set<File> scannedUris = new HashSet<>();
    // 要扫描的包名
    private final String topPackagePath;

    private final ClassLoader loader;

    @Getter
    private final Set<ResourceInfo> resources = new HashSet<>();

    public Scanner(String topPackagePath, ClassLoader loader) {
        this.topPackagePath = topPackagePath.isEmpty()
                ? topPackagePath
                : topPackagePath.replace(".", "/") + "/"; // com.my.test 转 com/my/test/
        this.loader = loader;
    }

    /**
     * 启动扫描
     *
     * @return
     * @throws IOException
     */
    @SneakyThrows
    public final Set<ResourceInfo> scan() {
        for (Map.Entry<File, ClassLoader> entry : getPackagePaths(this.loader).entrySet()) {
            scanFile(entry.getKey(), entry.getValue());
        }
        return this.resources;
    }

    /**
     * 加载classloader下的包路径
     *
     * @param classloader
     * @return
     */
    private Map<File, ClassLoader> getPackagePaths(ClassLoader classloader) {
        Map<File, ClassLoader> entries = new LinkedHashMap<>();

        // 添加父类类加载器的包路径
        ClassLoader parent = classloader.getParent();
        if (parent != null) {
            entries.putAll(getPackagePaths(parent));
        }

        // 添加类加载器包路径
        Set<URL> packageUrls = new HashSet<>();
        if (classloader instanceof URLClassLoader) {
            packageUrls.addAll(Arrays.asList(((URLClassLoader) classloader).getURLs()));
        } else if (classloader.equals(ClassLoader.getSystemClassLoader())) {
            // 系统classloader就扫描 java.class.path
            String javaClassPath = System.getProperty("java.class.path");
            String pathSeparator = System.getProperty("path.separator");
            for (String entry : javaClassPath.split(pathSeparator)) {
                try {
                    try {
                        packageUrls.add(new File(entry).toURI().toURL());
                    } catch (SecurityException e) { // File.toURI checks to see if the file is a directory
                        packageUrls.add(new URL("file", null, new File(entry).getAbsolutePath()));
                    }
                } catch (MalformedURLException ignored) {
                }
            }
        }
        // 加载url对应的file
        for (URL url : packageUrls) {
            if ("file".equals(url.getProtocol())) {
                entries.put(url2File(url), classloader);
            }
        }

        return entries;
    }

    /**
     * 扫描具体一个文件
     *
     * @param file
     * @param classloader
     * @throws IOException
     */
    private void scanFile(File file, ClassLoader classloader) throws IOException {
        if (scannedUris.add(file.getCanonicalFile())) { // 添加成功,没有扫过
            // 文件不存在返回
            try {
                if (!file.exists()) {
                    return;
                }
            } catch (SecurityException ignore) {
                return;
            }
            // 根据文件类型扫文件
            if (file.isDirectory()) {
                scanDirectory(classloader, file);
            } else {
                scanJar(file, classloader);
            }
        }
    }

    /**
     * 扫描文件夹
     *
     * @param classloader
     * @param directory
     * @throws IOException
     */
    private void scanDirectory(ClassLoader classloader, File directory) throws IOException {
        Set<File> currentPath = new HashSet<>();
        currentPath.add(directory.getCanonicalFile());
        doScanDirectory(directory, classloader, "", currentPath);
    }

    /**
     * 真正开始扫描文件夹
     *
     * @param directory     the root of the directory to scan
     * @param classloader   the classloader that includes resources found in {@code directory}
     * @param packagePrefix resource path prefix inside {@code classloader} for any files found
     *                      under {@code directory}
     * @param currentPath   canonical files already visited in the current directory tree path, for
     *                      cycle elimination
     */
    private void doScanDirectory(File directory, ClassLoader classloader, String packagePrefix, Set<File> currentPath)
            throws IOException {
        if (this.topPackagePath.startsWith(packagePrefix) // 从父包一层一层往下扫
                || packagePrefix.startsWith(this.topPackagePath)) { // 扫描包的子包

            File[] files = directory.listFiles();
            if (files != null) {
                for (File f : files) {
                    String name = f.getName();
                    if (f.isDirectory()) {
                        File deref = f.getCanonicalFile();
                        if (currentPath.add(deref)) {
                            doScanDirectory(deref, classloader, packagePrefix + name + "/", currentPath);
                            currentPath.remove(deref);
                        }
                    } else {
                        String resourceName = packagePrefix + name;
                        if (!resourceName.equals(JarFile.MANIFEST_NAME)) {
                            this.resources.add(ResourceFactory.getResource(resourceName, classloader));
                        }
                    }
                }
            }
        }
    }

    /**
     * 扫描jar包
     *
     * @param file
     * @param classloader
     * @throws IOException
     */
    private void scanJar(File file, ClassLoader classloader) throws IOException {
        try (JarFile jarFile = new JarFile(file)) {
            // 获取Manifest下的包路径
            Manifest manifest = jarFile.getManifest();
            if (manifest != null) {
                String classpathAttribute =
                        manifest.getMainAttributes().getValue(Attributes.Name.CLASS_PATH.toString());
                if (classpathAttribute != null) {
                    String pathSeparator = "\\s+";
                    for (String path : classpathAttribute.split(pathSeparator)) {
                        URL url;
                        try {
                            url = new URL(file.toURI().toURL(), path);
                        } catch (MalformedURLException e) {
                            continue;
                        }
                        if ("file".equals(url.getProtocol())) {
                            scanFile(url2File(url), classloader);
                        }
                    }
                }
            }
            // 扫描jar包
            Enumeration<JarEntry> entries = jarFile.entries();
            while (entries.hasMoreElements()) {
                JarEntry entry = entries.nextElement();
                if (entry.isDirectory() || entry.getName().equals(JarFile.MANIFEST_NAME)) {
                    continue;
                }
                if (entry.getName().startsWith(this.topPackagePath)) {
                    this.resources.add(ResourceFactory.getResource(entry.getName(), classloader));
                }
            }

        }
    }

    private File url2File(URL url) {
        try {
            return new File(url.toURI()); // Accepts escaped characters like %20.
        } catch (URISyntaxException e) { // URL.toURI() doesn't escape chars.
            return new File(url.getPath()); // Accepts non-escaped chars like space.
        }
    }
}
