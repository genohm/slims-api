# Plugins in this section

<details>

<summary>

## [basic-crud-actions](basic-crud-actions)

</summary>

<details>

<summary>

#### Flows

</summary>

* [Create a content](basic-crud-actions/src/main/resources/slimsgate.xml#L9)
* [Fetch some content](basic-crud-actions/src/main/resources/slimsgate.xml#L26)
* [Delete some content](basic-crud-actions/src/main/resources/slimsgate.xml#L69)
* [Update some content](basic-crud-actions/src/main/resources/slimsgate.xml#L120)

</details>

<details>

<summary>

#### Slimsgate API Services demonstrated

</summary>

* Using Dao\<SomeClass\> to modify records (in this case, Content) 
  * [Creating Content](basic-crud-actions/src/main/java/com/genohm/slims/custom/beans/CreateAContent.java#L63)
  * [Deleting Content](basic-crud-actions/src/main/java/com/genohm/slims/custom/beans/DeleteSomeContent.java#L113)
  * [Updating Content](basic-crud-actions/src/main/java/com/genohm/slims/custom/beans/UpdateSomeContent.java#L71)
* Converting Display Values to PK's in Foreign key fields with [ConvertRecordsService](basic-crud-actions/src/main/java/com/genohm/slims/custom/beans/CreateAContent.java#L60)
* Getting database table names with [DaoConstants](basic-crud-actions/src/main/java/com/genohm/slims/custom/beans/CreateAContent.java#L60)
* Fetching Maps<> that include custom fields
  * [ContentRecordQueries](basic-crud-actions/src/main/java/com/genohm/slims/custom/beans/FetchSomeContent.java#L87) 
  * [ContentTypeRecordQueries](basic-crud-actions/src/main/java/com/genohm/slims/custom/beans/FetchSomeContent.java#L82)
* Fetching custom Class objects without custom fields
  * [ContentQueries](basic-crud-actions/src/main/java/com/genohm/slims/custom/beans/FetchSomeContent.java#L107) 
  * [ContentTypeQueries](basic-crud-actions/src/main/java/com/genohm/slims/custom/beans/FetchSomeContent.java#L103)
* Using Meta classes for default field names 
  * [ContentMeta](basic-crud-actions/src/main/java/com/genohm/slims/custom/beans/FetchSomeContent.java#L88) 
  * [ContentTypeMeta](basic-crud-actions/src/main/java/com/genohm/slims/custom/beans/FetchSomeContent.java#L89)
* Building complex fetches with [FetchRequest](basic-crud-actions/src/main/java/com/genohm/slims/custom/beans/DeleteSomeContent.java#L91)
* Throwing errors for users with [SlimsGateErrorException](basic-crud-actions/src/main/java/com/genohm/slims/custom/beans/DeleteSomeContent.java#L115)
* Accessing inputs on step forms with [SlimsFlowInitParam](basic-crud-actions/src/main/java/com/genohm/slims/custom/beans/DeleteSomeContent.java#L73)
* Using *StringUtil* to assist with casting Objects
  * [Casting to Integer](basic-crud-actions/src/main/java/com/genohm/slims/custom/beans/DeleteSomeContent.java#L77)
  * [Casting to String](basic-crud-actions/src/main/java/com/genohm/slims/custom/beans/DeleteSomeContent.java#L111)
  * [Casting to Long[]](basic-crud-actions/src/main/java/com/genohm/slims/custom/beans/UpdateSomeContent.java#L60)
* Accessing checked-off records with *SLIMS_SELECT_SAMPLES*
  * [Slimsgate.xml configuration to require items be checked off](basic-crud-actions/src/main/resources/slimsgate.xml#L128)
  * [Accessing the pk's of checked-off items in your code](basic-crud-actions/src/main/java/com/genohm/slims/custom/beans/UpdateSomeContent.java#L60)

</details>
</details>