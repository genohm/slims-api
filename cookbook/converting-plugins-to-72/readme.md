# Introduction

To help with the conversion of your plugins to the new concepts in 7.2 we provide an automated openrewrite recipe. The openrewrite recipe 

- Automatically converts Location and Instrument code to Content code 
- Automatically converts javax to jakarta API
- Uses some new Java 21 features

The recipe is a tool to help, there is no expectation that no manual changes are needed after the recipe ran. The code will also need to be manually reviewed after the recipe ran.

# How to run the recipe

Keep all your plugins with an apiVersion on 6.9, your code needs to be able to compile on 6.9 for the recipe to run.

In your build.gradle do the following changes
 - Add the openrewrite plugin
 - Add a dependency on rewrite-spring
 - Configure the rewrite task to use our recipe

For example:

```groovy
buildscript {
    //As before
}

plugins {
    id('idea')
    //Add a dependency on the openrewrite plugin
    id("org.openrewrite.rewrite") version("6.26.0") apply(false)
}

subprojects {
    apply plugin: "org.openrewrite.rewrite"
    
    repositories {
        maven {
            credentials {
                username "$slimsApiArtifactoryUser"
                password "$slimsApiArtifactoryPassword"
            }
            url "$slimsApiArtifactoryRepository"
        }
	    mavenCentral()
    }

    if (file('src/main/resources/plugin.properties').exists()) {
        println("Applying slimsplugin to project $it.name")
        apply plugin: 'com.genohm.slimsplugin'
    }

    dependencies {
        //Add a dependency on rewrite-spring
        rewrite 'org.openrewrite.recipe:rewrite-spring:5.22.0'
    }

    //Configure the rewrite task to use our recipe
    rewrite {
        activeRecipe("com.agilent.LocationAsContent")
        exportDatatables = true
        configFile = project.getRootProject().file("cookbook/converting-plugins-to-72/location-as-content.yml")
    }
}
```

Then you should be able to use the :rewriteRun task to run the recipe:

```
./gradlew cookbook:plugins:direct-database-actions:basic-crud-actions:rewriteRun
```

Afterward the changes will be done and you can change your apiVersion to 7.2.0. You can try to recompile

```
./gradlew cookbook:plugins:direct-database-actions:basic-crud-actions:jar
```

And fix any remaining issues manually