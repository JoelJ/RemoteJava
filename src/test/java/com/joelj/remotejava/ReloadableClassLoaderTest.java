package com.joelj.remotejava;

import junit.framework.Assert;
import org.apache.commons.io.FileUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.net.URL;
import java.util.UUID;

/**
 * User: Joel Johnson
 * Date: 10/2/12
 * Time: 9:58 PM
 */
public class ReloadableClassLoaderTest {
	private File testFile;
	private File rootDir;

	private final File root = new File("src/main/resources");
	private final File testFileVer1 = new File(root, "TestJar1.jar");
	private final File testFileVer2 = new File(root, "TestJar2.jar");

	@Before
	public void setUp() {
		rootDir = new File(UUID.randomUUID().toString());
		rootDir.mkdir();
		testFile = new File(root, "TestJar.jar");
	}

	@After
	public void tearDown() {
		testFile.delete();
		rootDir.delete();
	}

	@Test
	public void testReloading() throws Exception {
		switchToVersion1();

		URL[] urls = {testFile.toURI().toURL()};
		ReloadableClassLoader reloadableClassLoader = ReloadableClassLoader.classLoaderFor(urls);
		String version1 = invoke(reloadableClassLoader);
		Assert.assertEquals("version1", version1);

		switchToVersion2();
		reloadableClassLoader.reload();

		String version2 = invoke(reloadableClassLoader);
		Assert.assertEquals("version2", version2);
	}

	private void switchToVersion1() throws IOException {
		if(testFile.exists()) {
			testFile.delete();
		}
		FileUtils.copyFile(testFileVer1, testFile);
	}

	private void switchToVersion2() throws IOException {
		if(testFile.exists()) {
			testFile.delete();
		}
		FileUtils.copyFile(testFileVer2, testFile);
	}

	private String invoke(ReloadableClassLoader reloadableClassLoader) throws ClassNotFoundException, IllegalAccessException, NoSuchFieldException, InstantiationException {
		Class<?> testClass = reloadableClassLoader.loadClass("TestClass");
		Object testInstance = testClass.newInstance();
		Field versionField = testClass.getField("version");
		Object o = versionField.get(testInstance);
		Assert.assertTrue(o instanceof String);
		return (String)o;
	}
}
