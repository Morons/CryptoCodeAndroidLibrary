import java.io.*
import java.util.*

/**
 * This function retrieves a string from a system properties file
 * @param key the Key name to retrieve
 * @param filePath (defaulted to "src/main/resources/")
 * @param fileName (defaulted to "version.properties")
 **/
fun getResourceProperty(
	key: String,
	filePath: String = "src/main/resources/",
	fileName: String = "version.properties"
): String? {
	val properties = Properties()
	val localProperties = File("$filePath$fileName")
	if (localProperties.isFile) {
		InputStreamReader(FileInputStream(localProperties), Charsets.UTF_8).use { reader ->
			properties.load(reader)
		}
	} else {
		properties.setProperty("MAJOR", "0")
		properties.setProperty("MINOR", "0")
		properties.setProperty("VERSION_CODE", "1")
		properties.setProperty("PATCH", "0")
		OutputStreamWriter(FileOutputStream(localProperties), Charsets.UTF_8).use { fileOutputStream ->
			properties.store(fileOutputStream, null)
		}
	}
	return properties.getProperty(key)
}

/**
 * This function retrieves a string from a system properties file
 * @param key the Key name to retrieve
 * @param value the value to be set as a string
 * @param filePath (defaulted to "src/main/resources/")
 * @param fileName (defaulted to "version.properties")
 **/
fun setResourceProperty(
	key: String,
	value: String,
	filePath: String = "src/main/resources/",
	fileName: String = "version.properties"
) {
	val properties = Properties()
	val localProperties = File("$filePath$fileName")
	if (localProperties.isFile) {
		InputStreamReader(FileInputStream(localProperties), Charsets.UTF_8).use { reader ->
			properties.load(reader)
		}
		properties.setProperty(key, value)
		OutputStreamWriter(FileOutputStream(localProperties), Charsets.UTF_8).use { fileOutputStream ->
			properties.store(fileOutputStream, null)
		}
	} else {
		properties.setProperty("MAJOR", "0")
		properties.setProperty("MINOR", "0")
		properties.setProperty("VERSION_CODE", "1")
		properties.setProperty("PATCH", "0")
		properties.setProperty(key, value)
		OutputStreamWriter(FileOutputStream(localProperties), Charsets.UTF_8).use { fileOutputStream ->
			properties.store(fileOutputStream, null)
		}
	}
}