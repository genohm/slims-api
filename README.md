# SLIMS-API

![build_test workflow](https://github.com/genohm/slims-api/actions/workflows/main.yml/badge.svg?branch=master)

The SLIMS API allows you to write SLIMSGATE plugins and SLIMS Vaadin plugins. 

## Prerequisites.

- Java 11

- You will need to have an account on AGILENT's build infrastructure. 
Please contact your SLIMS project lead to get access. 
They will send you a gradle.properties file looking like this:

    ```
    slimsApiArtifactoryUser=<your user on SLIMS API artifactory>
    slimsApiArtifactoryPassword=<your password on SLIMS API artifactory>
    slimsApiArtifactoryRepository=<Artifcatory URL>
    
    
    slimsRestApiUser=<a user on your installed slims>
    slimsRestApiPassword=<the password for that user>
    slimsRestApiEndpoint=<the url to the installed slimsrest>
    ```
    
    You will need to place this file in 
    
    ```
    ~/.gradle/gradle.properties
    ```

## Using gradle

### Setup

Once your account is setup, download this repository and navigate to the base folder of the repository with the command line interface you use. 
We suggest using GIT Bash for Windows. 
Make sure your JAVA_HOME is set to java 11.
Build the projects for your favorite IDE (Eclipse or IntelliJ) with:

```
# Eclipse
./gradlew eclipse
# IntelliJ
./gradlew idea
```

Now the plugin projects can be imported in your IDE.
Repeat this command for every new plugin project or dependency change.
Newly defined plugin projects should also be included in [settings.gradle](settings.gradle):

```
// Included by default
include 'templates:slimsgate-template'
include 'templates:slims-vaadin-template'
include 'templates:library-template'
include 'templates:dto-template'

// An example of adding a plugin of your own, stored in slims-api/personal-plugins/plugin-one
include 'personal-plugins:plugin-one'
```

This will tell gradle to build them into modules and download their dependencies when you run `./gradlew idea` or `./gradlew eclipse`

### Usage

Next try to create a dummy project. 
We suggest starting with slimsgate-template.
You can test out if you can build your individual plugin with:

```
./gradlew templates:slimsgate-template:fatjar
```

Alternatively you can also build all your plugins at once with:

```
./gradlew fatjar
```

You can upload and deploy your plugin with:

```
./gradlew templates:slimsgate-template:uploadToSlims
```

## Example templates

Visit [templates](templates/) to look for empty example plugins.

Visit [cookbook](cookbook/) to find code examples and example plugins for working with SLIMS
