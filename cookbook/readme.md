# DISCLAIMER !
**Examples provided in this cookbook are still _under construction_, so if you see some sections/folders that are empty or incomplete it is because we are still working on it.
You can find here the list of what is [Completed](#Completed), [In progress](#In-progress) or [Not started](#Not-started).**

## Completed
* [direct-database-actions/basic-crud-actions](plugins/direct-database-actions/basic-crud-actions)

## In progress
* [direct-database-actions](plugins/direct-database-actions)

## Not started
* [common-camel-patterns](plugins/common-camel-patterns)
* [notifications-and-emails](plugins/notifications-and-emails)
* [slimsgate-xml-advanced-patterns](plugins/slimsgate-xml-advanced-patterns)
* [ui-only-actions-as-code](plugins/ui-only-actions-as-code)

# How to use this cookbook

**This section assumes you have already followed the instructions in the [top-level readme](../README.md) to setup your gradle.properties
and run the `./gradlew idea` or `./gradlew eclipse` command successfully**

**For extremely basic examples that can be used as jumping off points for new plugins, please see [templates](../templates)
and the [top-level readme](../README.md) in this repo**

This cookbook is intended to provide you with a series of example plugins and code samples for developing SLIMS plugins 
utilizing the slimsgate api. It is not intended to replace the slims development manual, [Apache's camel documentation](https://camel.apache.org/components/3.14.x/index.html), 
or [Thymeleaf's documentation](https://www.thymeleaf.org/doc/tutorials/3.1/usingthymeleaf.html). Rather, this is a 
supplementary set of salient examples to demonstrate how these tools can join together and interact when creating your own Slimsgate plugins.

## SLIMSGate API Services

In particular, we want to highlight how you can utilize the services provided in the `PublicApi` class. This class can be found
in your External Libraries in a directory like `/slimsservice-api-A.B.C/com/genohm/slims/server/service/PublicApi.class`. 

The `A` `B` and `C` in `slimsservice-api-A.B.C` will be a number like `6.9.0`. You might have more than one of these folders:
one is created for each version of SLIMS you have amongst your plugins' `apiVersion` values. For example, if you run `./gradlew idea` or `./gradlew eclipse`
after creating a plugin that has the following `plugin.properties` file:

```
config-class=com.genohm.slims.custom.CustomConfiguration
type=SLIMSGATE
apiVersion=6.8.0
```

An external Library `slimsservice-api-6.8.0.jar` will be downloaded, and the contained version of `PublicApi.java` will
be the one that the plugin utilizes. If you later change the version to 6.9.1 and run `./gradlew idea`, you will download a new 
external library: `slimsservice-api-6.9.1.jar`.


## What's inside?

Under the [cookbook](cookbook) folder you will find two main sub-folders that will contain entire plugins ([cookbook/plugins](plugins)) or simple code snippets ([cookbook/code-snippets](code-snippets)).

### Plugins
You will find several sub-folders with entire plugins, broken into categories:
<details>

<summary>

#### [common-camel-patterns](plugins/common-camel-patterns)

</summary>

   * Examples to illustrate commonly-utilized camel route designs and camel route endpoints other than "direct" routes that run some beans
   * Concepts explored:
     * [Netty](https://camel.apache.org/components/3.14.x/netty-component.html) (TCP connection plumbing, handled by camel)
     * [Quartz](https://camel.apache.org/components/3.14.x/quartz-component.html) (Timers, schedules)
     * [SMTP](https://camel.apache.org/components/3.14.x/mail-component.html) (Sending emails and monitoring inboxes)
     * [File](https://camel.apache.org/components/3.14.x/file-component.html) (Writing files, watching directories)
     * Using `SlimsGateFlowService` to start other routes
       * Creates a record and logs in the User Interface of SLIMS even if a route is not started by a SLIMS action
     * How `SlimsProxy` and `SlimsFlowInitParam` vary, depending on how the route was started
       * Started from a button being clicked in SLIMS
       * Started from a Rule in SLIMS
       * Started from a SLIMSRest API call
       * Not started from an action in SLIMS, SLIMSRest, or another route using `SlimsGateFlowService` 

</details>

<details>

<summary>

#### [direct-database-actions](plugins/direct-database-actions)

</summary>

   * Examples for both simple and "special" direct interactions with the SLIMS database
   * Concepts explored:
     * CRUD actions - creating, reading, updating, and deleting records
     * Use of `@Transactional`
     * Making URL links to any record
     * "Special" cases:
       * Records with `Quantity` fields
       * Attachments
       * Flags
       * Providing your own `cntn_barcode`
       * Getting meta-information about Fields themselves

</details>

<details>

<summary>

#### [notifications-and-emails](plugins/notifications-and-emails)
 
</summary>

   * Examples of services that can be used to send SLIMS notifications or Emails from SLIMS Email Templates


</details>

<details>

<summary>

#### [slimsgate-xml-advanced-patterns](plugins/slimsgate-xml-advanced-patterns)

</summary>

   * Examples of common ways to use [Thymeleaf](https://www.thymeleaf.org/doc/tutorials/3.1/usingthymeleaf.html) in slimsgate.xml files
   * Creating >1 slimsgate flows based on the plugin's configuration
   * Conditionally adding/removing XML elements based on inputs or configuration
     * For example, conditionally hiding a step, conditionally excluding certain fields

</details>

<details>

<summary>

#### [ui-only-actions-as-code](plugins/ui-only-actions-as-code)

</summary>

   * Examples of how you can utilize services from `PublicApi` to perform actions available in the UI that do more than just a simple CRUD action on the Database
   * Versioning records
   * Enrolling contents in biobanking Studies
   * Blocking positions in locations
   * Updating/Scheduling Orders
   * Creating requests with Requestables
   * Acknowledging Rule evaluations
   * Automating or skipping E-signatures
   * Protocol Run & Workflow actions
     * Starting a run
     * Popping contents from a queue into a run
     * Finishing steps
     * Completing/closing the run
     * Re-queuing contents from a run
   * Label Printing
   * Generating a report from a report template
   * Generating Excels, XML, TXT, and CSV files from Grid templates and thymeleaf
   * Importing a SLIMS-formatted excel import file via a plugin

</details>

### Code snippets

<details>
<summary>

#### [UserService snippets](code-snippets/userService.md)

</summary>
</details>