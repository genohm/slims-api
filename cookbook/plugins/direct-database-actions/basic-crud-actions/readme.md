# plugin-name: "basic-crud-actions"

# General Introduction
This plugin creates several flows intended to serve as examples for basic database interactions utilizing the SLIMSGate api.

You can find more examples and documentation for many of these concepts/tools (Dao<>'s, Queriers, Metas, etc.) in the SLIMS development manual.

### A Note about `ConvertRecordService`
This is a very convenient service which will take a Map<> of a SLIMS record with "human readable" values in its Date, Quantity, Foreign Key, etc. fields 
and convert those values to ones that can be imported successfully into SLIMS for you. Take the following example:

```
// Assume existence of status "Pending" with primary key 1

Map<String, Object> displayValuesMap = new Hashmap<>();
displayValuesMap.put("cntn_fk_status", "Pending")
// displayValuesMap == ["cntn_fk_status": "Pending]
 
Map<String, Object> convertedContent = convertRecordService.convertToInternalFormat(displayValuesMap, DaoConstants.CONTENT);
// convertedContent == ["cntn_fk_status": 1]
```

The alternative would be to perform a database query for the `Pending` status, and then get the pk (`1`) of that status record
to build your map with.

**However, there are some notable things to watch out for when using this service**
1. You must know what the "display value" ought to be for your target record. Because the display value from the target record is 
    configurable on every Foreign Key field, this could change or be set to field whose value is not forced to be unique.
2. If your map includes Foreign Key fields with null values, the service will attempt to fill them throw an error due to the null value.
   - **The takeaway being, do not include null Foreign Key fields in the Map<> you feed to this service -- add them to the output Map<> after the fact if you need them**
3. There are specific rules about formatting for Quantities and Dates as well, which are outlined clearly in the javadoc in the service if you look at the `ConvertRecordService.java` class

## Usage: Content Management for all flows
## Type: slimsgate

# Flows

<details>

<summary>

### <a name="create"></a> Create a Content

</summary>

#### User inputs:
None
#### Process
- Creates a content record with the configured status and content type in the default status and content type fields. 
- Looks up the Content Type and Status using the display values configured in the properties _statusDisplayValue_ and _contentTypeDisplayValue_.
  - "Display value" is the value from the target record (the status or type in this case) that shows in the Content field when that record has been selected
    - **By Default, this will be the "Name" fields from the Content Type and Status. This might have been changed in your 
        instance - you can check the "display field" option in the Content fields cntn_fk_status and cntn_fk_contentType**

</details>


<details>

<summary>

### <a name="fetch"></a> Fetch some content

</summary>

#### User inputs:
None
#### Process
##### Step 1
- Fetches all content records in the database with the content type whose "name" is configured in the property _contentTypeDisplayValue_
  - Depending on the property _fetchRecordsAsMaps_, will either fetch the records as "Content" and "ContentType" objects or Map<String, Object> objects
    - There is no functional difference for this plugin - intended to just show both options
    - **The custom Content and ContenType classes contain convenient methods and less-verbose syntax to work with, but they will not contain custom fields**
    - **Fetching records as Map<> objects requires more boilerplate (casting values to the right classes, etc.), but will contain the records' custom fields as keys**
- Creates an HTML string with an unordered list of with the barcodes of the fetched contents' 
##### Step 2
- Displays the HTML string output from step 1 to the end user

</details>


<details>

<summary>

### <a name="delete"></a> Delete some content

</summary>

#### User inputs:
- The number of contents you want to be deleted
#### Process
##### Step 1
- Take the user-input number of contents to delete
- Fetches that many contents with the content type whose "name" is configured in the property _contentTypeDisplayValue_
  - Sorts the fetch by the configured field from the property _deletionSortField_
    - Sorts ascending if the property _deletionSortDescending_ is `false`, descending if it is `true`
- Throws an error if fewer contents were found than the request number to be deleted
- Deletes the fetched contents from the database
- Creates an HTML string with an unordered list of with the barcodes of the deleted contents
##### Step 2
- Displays the HTML string output from step 1 to the end user

</details>


<details>

<summary>

### <a name="update"></a> Update some content

</summary>

#### User inputs:
- Selected (checked-off) content before executing the flow 
#### Process
- Looks up the content records that were checked off when the flow was started
- Changes the selected contents' default status field to the status whose display value is configured in the property _updateStatusDisplayValue_
  - **Note - the default status is Removed. This means that after execution, the checked-off contents will no longer appear
    unless the "show removed contents" box in the content module is checked**

</details>

# Required Configuration
- At least one Content Type
- All other configuration used can be default items that come with SLIMS

# Parameters description
* (_String_) **contentTypeDisplayValue**: The "Name" of a content type. Might need to be a different fields' value, if the "display value" of `cntn_fk_contentType` has been changed on your instance
* (_boolean_) **fetchRecordsAsMaps**: Whether or not the [Fetch some content](#fetch) flow should fetch Content and ContentType objects, or Map<String, Object> ones
* (_String_) **statusDisplayValue**: The "Name" of a status [records will be created with](#create). Might need to be a different fields' value, if the "display value" of `cntn_fk_status` has been changed on your instance
* (_String_) **deletionSortField**: The field to sort records by when [looking up records to delete](#delete). The first ones returned when sorted will be the ones deleted
* (_boolean_) **deletionSortDescending**: The direction to sort the values in `deletionSortField` by. If true, sort will be descending. If false, ascending.
* (_String_) **updateStatusDisplayValue**: The "Name" of a status [records will be updated to](#update). Might need to be a different fields' value, if the "display value" of `cntn_fk_status` has been changed on your instance