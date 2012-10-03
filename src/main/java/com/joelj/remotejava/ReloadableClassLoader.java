package com.joelj.remotejava;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Enumeration;

/**
 * User: Joel Johnson
 * Date: 10/2/12
 * Time: 9:46 PM
 */
public class ReloadableClassLoader {
	private final URL[] urls;
	private URLClassLoader classLoader;

	public static ReloadableClassLoader classLoaderFor(URL[] urls) throws MalformedURLException {
		ReloadableClassLoader reloadableClassLoader = new ReloadableClassLoader(urls);
		reloadableClassLoader.reload();
		return reloadableClassLoader;
	}

	protected ReloadableClassLoader(URL[] urls) throws MalformedURLException {
		this.urls = urls;
	}

	public void reload() {
		classLoader = new URLClassLoader(urls);
	}

	public URL[] getURLs() {
		return classLoader.getURLs();
	}

	public Enumeration<URL> findResources(String name) throws IOException {
		return classLoader.findResources(name);
	}

	public InputStream getResourceAsStream(String name) {
		return classLoader.getResourceAsStream(name);
	}

	public void setDefaultAssertionStatus(boolean enabled) {
		classLoader.setDefaultAssertionStatus(enabled);
	}

	public URL getResource(String name) {
		return classLoader.getResource(name);
	}

	public URL findResource(String name) {
		return classLoader.findResource(name);
	}

	public ClassLoader getParent() {
		return classLoader.getParent();
	}

	public Class<?> loadClass(String name) throws ClassNotFoundException {
		return classLoader.loadClass(name);
	}

	public Enumeration<URL> getResources(String name) throws IOException {
		return classLoader.getResources(name);
	}

	public void setPackageAssertionStatus(String packageName, boolean enabled) {
		classLoader.setPackageAssertionStatus(packageName, enabled);
	}

	public void clearAssertionStatus() {
		classLoader.clearAssertionStatus();
	}

	public void setClassAssertionStatus(String className, boolean enabled) {
		classLoader.setClassAssertionStatus(className, enabled);
	}
}
