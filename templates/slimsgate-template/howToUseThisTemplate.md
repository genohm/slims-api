# slimsgate-template

This is an empty SLIMS GATE plugin. The minimal required files are present.

## Starting on a new plugin
Do not change this template but copy the entire folder slimsgate-template and give it another plugin name.

## Include your new plugin
Add a line in the file settings.gradle to include your new plugin, example:
		demo			: ['plugin:demo:slimsgate-demo'],

## Editing the copied template.
Change following files:

* resources.spring.camel-context.xml
	- (required) Name of the routebuilder
	- Do not change the name of the class

* recoures.plugin.properties
	- (optional) apiVersion: depending on the SLIMS version you are building the plugin for.
	- (optional) config-class: If you changed the CustomConfiguration.java file, you need to change it here
	- Do not change the type
	
* resources.slimsgate.xml
	- (required) The id of the flow
	- (required) The name of the flow
	- (optional) The name of the step
	- (required) The name of the route from the step
	- Do not change the name of the class

* src.com.genohm.slims.custom.beans.SayHello.java
	- (required) Name of the class
	- (optional) The method sayHi()
	
* src.com.genohm.slims.custom.beans.SayHelloBuilder.java
	- (required) Name of the class, which you changed in the camel-context.xml file
	- (required) The 'from' component has to contain an unique name
	- (required) The '.to' component, with the name of your bean
	- (required) The '.routeId' component with the name of the route from the step

* src.com.genohm.slims.custom.CustomConfiguration.java
	- (optional) Name of the class, also change this in plugin.properties
	- (optional) parameterOne
	- (optional) parameterTwo
	
* test.com.genohm.slims.custom.beans.SayHelloTest
	- (required) Name of the class
	- (required) The injected Mock with the name of your bean
	- (optional) The mocked CustomConfiguration, if you changed this class name
	- (optional) The method to call in the happyPathTest, if you changed this method name
	- (optional) The name of the test method happyPathTest()
	
## Document your plugin
	- Fill in the readme.md file

## Build your plugin
* locally
	- Execute following command in the plugin root folder:
		./gradlew plugin:<customerfolder>:<pluginfolder>:fatjar, example:
		./gradlew plugin:demo:slimsgate-demo:fatjar