# slims-vaadin-template

This is an empty SLIMS (Vaadin) plugin. The minimal required files are present.

## Starting on a new plugin
Do not change this template but copy the entire folder slims-vaadin-template and give it another plugin name, for example:
`slims-vaadin-demo`.

## Include your new plugin
Add a line in the file [settings.gradle](../../settings.gradle) to include your new plugin, for example:
```
include 'plugin:demo:slims-vaadin-demo'
```

## Editing the copied template.
Change the following default plugin files:

* [plugin.properties](src/main/resources/plugin.properties)
	- (optional) `apiVersion`: depending on the SLIMS version you are building the plugin for.
	- (optional) `config-class`: If you changed the `CustomConfiguration.java` file, you need to change it here
	- Do not change `type`
* [SayHelloRenderer.java](src/main/java/com/genohm/slims/custom/beans/SayHelloRenderer.java)
	- (required) Name of the class
	- (optional) The method `render()`, for instance pass on parameters to the presenter
* [CustomConfiguration.java](src/main/java/com/genohm/slims/custom/CustomConfiguration.java)
	- (optional) Name of the class, also change this in `plugin.properties`
	- (optional) `parameterOne`
	- (optional) `parameterTwo`
* [SpringConfig.java](src/main/java/com/genohm/slims/custom/SpringConfig.java)
	- (required) Update `SayHelloRenderer.class` to the renamed class
	- (required) Unique `id` in `RendererDefinition.create()`
	- (optional) Alternative `name`, `description`, `icon`, `usage` and `displayRendererCondition` in `RendererDefinition.create()`
	- Do not change the name of the class

Change the following [Model-View-Presenter (MVP)](https://en.wikipedia.org/wiki/Model-view-presenter) pattern plugin files:
* [SayHelloPresenter.java](src/main/java/com/genohm/slims/custom/beans/SayHelloPresenter.java)
	- (required) Name of the class
	- (optional) The method `postConstruct()`, for instance different interactions in between view and model.
	  This can be an interaction driven by a user event in the view or by a state change in the model.
* [SayHelloView.java](src/main/java/com/genohm/slims/custom/beans/SayHelloView.java)
	- (required) Name of the class
	- (optional) The whole class, for instance different view implementation. Here typically Vaadin UI components are used to draw the view.
* [SayHelloModel.java](src/main/java/com/genohm/slims/custom/beans/SayHelloModel.java)
	- (required) Name of the class
	- (optional) The whole class, for instance different model implementation. The model interacts with the data provider and can keep a state of the data.
* [SayHelloDataProvider.java](src/main/java/com/genohm/slims/custom/beans/SayHelloDataProvider.java)
	- (required) Name of the class
	- (optional) The whole class, for instance different data provider implementation. The data provider interacts with the SLIMS datamodel via for instance the `Dao<Content>` and `ContentRecordQueries` API classes.


## Document your plugin
* Fill in the [readme.md](readme.md) file

## Build your plugin
* Execute the fatjar command in the repository root folder, for example:
```
./gradlew plugin:demo:slims-vaadin-demo:fatjar
```