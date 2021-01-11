# SLIMS-API

The SLIMS API allows you to write SLIMSGATE plugins and SLIMS Vaadin plugins. 

## Prerequisites.

As a prerequisite you will need to have an account on AGILENT build infrastructure. 
Please contact your SLIMS project lead to get access. 
They will send you a gradle.properties file looking like this:

```
slimsApiArtifactoryUser=<your user on genohms artifactory>
slimsApiArtifactoryPassword=<your password on genohms artifactory>
slimsApiArtifactoryRepository=<genohms artifact>


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
Build the projects for your favorite IDE (Eclipse or IntelliJ) with:

```
# Eclipse
./gradlew eclipse
# IntelliJ
./gradlew idea
```

Now the plugin projects can be imported in your IDE.
Repeat this command for every new plugin project or dependency change.
Newly defined plugin projects should also be included in [settings.gradle](settings.gradle).

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
