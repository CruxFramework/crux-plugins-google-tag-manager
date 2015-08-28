# crux-plugins-google-tag-manager
This plugin provides Integration with Google Tag Manager

## Dependency

Add the following maven dependency to your pom.xml file:

```xml
<dependency>
   <groupId>org.cruxframework.plugin</groupId>
   <artifactId>crux-google-tag-manager</artifactId>
   <version>1.0.0-SNAPSHOT</version>
 </dependency>
```
## Configuration
Inherit the plugin module on your GWT module config file (YOUR_MODULE.gwt.xml):

```xml
<inherits name='org.cruxframework.crux.plugin.google.gtm.GTM' />
```
## Usage

You can interact with the plugin programmatically through `org.cruxframework.crux.plugin.google.gtm.client.GoogleTagManager` class.

First, you need to initialize the GTM on your site. To do this, you must call the `initialize` method:

```java
	@Expose
	public void onLoad()
	{
		GoogleTagManager.init("Your GTM code"); // somethign like GTM-XXXXXX
	}
```

Then, you can push variables and events like:

```java
		GoogleTagManager.pushVariable("testVariable", "testValue");
		GoogleTagManager.pushEvent("myEvent");
		GoogleTagManager.pushVariables(new Variable("var1", "value1"), 
		                               new Variable("var2", "value2") //, ...
		                               );
```

