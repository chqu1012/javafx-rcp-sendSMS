package de.dc.jfx.freesms.app.snippets;
import java.net.URL;
 
public class ResourceResolver {
/**
* Retrieves an absolute resource path from a path relative to the location of the specified class.
* The requested resource must exist otherwise this method will throw an IllegalArgumentException.
* @param clazz a path relative to the location of this class.
* @param path a path relative to the location of this class.
* @return the absolute resource path.
* @throws IllegalArgumentException if a resource at the specified path does not exist.
*/
public static String getResourceFor(Class clazz, String path) {
URL resourceURL = clazz.getResource(path);
if (resourceURL == null) {
throw new IllegalArgumentException("No resource exists at: " + path + " relative to " + clazz.getName());
}
 
return resourceURL.toExternalForm();
}
}