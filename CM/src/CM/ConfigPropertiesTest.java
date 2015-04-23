import static org.junit.Assert.*;

import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

public class ConfigPropertiesTest {

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testGetValue() throws IOException {
		ConfigProperties cfp = new ConfigProperties("src/CM/config.properties");
		assertEquals("123", cfp.getValue("name"));

	}

	@Test
	public void testSetValue() throws IOException {
		ConfigProperties cfp = new ConfigProperties("src/CM/config.properties");
		Assert.assertEquals(cfp.setValue("age", "18"), true);
	}

	@Test
	public void testGetAllValue() throws IOException {
		ConfigProperties cfp = new ConfigProperties("src/CM/config.properties");

		Map<String, String> map1 = new HashMap<String, String>();
		Map<String, String> map2 = cfp.getAllValue();
		map1.put("age", "18");
		map1.put("name", "123");
		Assert.assertTrue(isMapsEqual(map1, map2));

	}

	public boolean isMapsEqual(Map map1, Map map2) {
		if (map1.equals(map2))
			return true;
		else
			return false;
	}
}
