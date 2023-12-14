package za.co.zone.lib

import setResourceProperty
import getResourceProperty
import org.junit.Assert.assertEquals
import org.junit.Test

class ResourcePropertiesUtilsKtTest {
	
	@Test
	fun getResourceProperty() {
		setResourceProperty(
			key = "greeting",
			value = "Hello World",
			filePath = "src/main/resources/",
			fileName = "test.properties"
		)
		val expected = "Hello World"
		val property = getResourceProperty(
			key = "greeting",
			filePath = "src/main/resources/",
			fileName = "test.properties"
		) ?: ""
		assertEquals(expected, property)
	}
}