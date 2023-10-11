# Plugins in this section

## [basic-crud-actions](basic-crud-actions)

#### Slimsgate API Services demonstrated
* Using Dao\<SomeClass\> to modify records (in this case, Content) 
  * [Creating Content](basic-crud-actions/src/main/java/com/genohm/slims/custom/beans/CreateAContent.java#L63)
  * [Deleting Content](basic-crud-actions/src/main/java/com/genohm/slims/custom/beans/DeleteSomeContent.java#L126)
* [ConvertRecordsService](basic-crud-actions/src/main/java/com/genohm/slims/custom/beans/CreateAContent.java#L60)
* [DaoConstants](basic-crud-actions/src/main/java/com/genohm/slims/custom/beans/CreateAContent.java#L60)
* [ContentRecordQueries](basic-crud-actions/src/main/java/com/genohm/slims/custom/beans/FetchSomeContent.java#L87) & [ContentTypeRecordQueries](basic-crud-actions/src/main/java/com/genohm/slims/custom/beans/FetchSomeContent.java#L82)
* [ContentQueries](basic-crud-actions/src/main/java/com/genohm/slims/custom/beans/FetchSomeContent.java#L107) & [ContentTypeQueries](basic-crud-actions/src/main/java/com/genohm/slims/custom/beans/FetchSomeContent.java#L103)
* [ContentMeta](basic-crud-actions/src/main/java/com/genohm/slims/custom/beans/FetchSomeContent.java#L88) & [ContentTypeMeta](basic-crud-actions/src/main/java/com/genohm/slims/custom/beans/FetchSomeContent.java#L89)
* Building complex fetches with [FetchRequest](basic-crud-actions/src/main/java/com/genohm/slims/custom/beans/DeleteSomeContent.java#L99)
* Throwing errors for users with [SlimsGateErrorException](basic-crud-actions/src/main/java/com/genohm/slims/custom/beans/DeleteSomeContent.java#L111)
* Getting user Flow inputs from [SlimsFlowInitParam](basic-crud-actions/src/main/java/com/genohm/slims/custom/beans/DeleteSomeContent.java#L80)
* Using StringUtil to assist with casting Objects
  * [Casting Integers](basic-crud-actions/src/main/java/com/genohm/slims/custom/beans/DeleteSomeContent.java#L77)
  * [Casting Strings](basic-crud-actions/src/main/java/com/genohm/slims/custom/beans/DeleteSomeContent.java#L124)

#### Flows
* [Create a content](basic-crud-actions/src/main/resources/slimsgate.xml#L9)
* [Fetch some content](basic-crud-actions/src/main/resources/slimsgate.xml#L26)
* [Delete a content](basic-crud-actions/src/main/resources/slimsgate.xml#L69)
* [Change some content]()