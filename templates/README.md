# Templates

There are some empty template projects listed here. They provide you with an empty project where the minimal required files are present. The following templates are present

 - slimsgate-template
   An empty slimsgate plugin. SLIMSGATE plugins are used to connect to machines and external systems. They also can implement extra business logic required for your lab.

 - slims-vaadin-template 
   An empty slims vaadin plugin. Slims vaadin plugins are used to create custom UIs and external portals for SLIMS.

 - dto-template
   An empty DTO project. Can be used to put DTO objects in to transfer in between SLIMS and SLIMS GATE

 - library-template
   An empty library project. Can be used to put common code into.

## Prerequisites.

As a prerequisite you will need to have an account on Genohm's build infrastructure. They will send you a gradle.properties file looking like this.

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

Once you are setup download any of these projects to try and create a dummy project. We suggest starting with slimsgate-template. 

Navigate to the plugin folder with the command line interface you use. We suggest using GIT Bash for Windows. You can test out if you can build your plugin with

```
./gradlew fatjar
```

You can upload and deploy your plugin with

```
./gradlew uploadToSlims
```
