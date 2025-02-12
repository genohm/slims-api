# Plugins in this section

<details>

<summary>

## [ui-only-actions-as-code](ui-only-actions-as-code)

</summary>

<details>

<summary>

#### Flows

</summary>

* [Create and schedule an order](create-order-and-schedule/src/main/resources/slimsgate.xml#L6)

</details>

<details>

<summary>

#### Slimsgate API Services demonstrated

</summary>

* Using PublicApi service classes
  * [ScheduleOrderService](create-order-and-schedule/src/main/java/com/genohm/slims/custom/beans/CreateOrderAndSchedule.java#L120)
  * [AddRequestService](create-order-and-schedule/src/main/java/com/genohm/slims/custom/beans/CreateOrderAndSchedule.java#L116)
* Using Dao<> classes to add records
  * [Dao\<Order\>](create-order-and-schedule/src/main/java/com/genohm/slims/custom/beans/CreateOrderAndSchedule.java#L91)
  * [Dao\<OrderContent\>](create-order-and-schedule/src/main/java/com/genohm/slims/custom/beans/CreateOrderAndSchedule.java#L101)
* Fetching custom Class objects without custom fields
  * [ContentQueries](create-order-and-schedule/src/main/java/com/genohm/slims/custom/beans/CreateOrderAndSchedule.java#L74)
  * [OrderTypeQueries](create-order-and-schedule/src/main/java/com/genohm/slims/custom/beans/CreateOrderAndSchedule.java#L77)
  * [RequestableQueries](create-order-and-schedule/src/main/java/com/genohm/slims/custom/beans/CreateOrderAndSchedule.java#L104)
* Using Meta classes for default field names 
  * [OrderMeta](create-order-and-schedule/src/main/java/com/genohm/slims/custom/beans/CreateOrderAndSchedule.java#L90) 
  * [OrderContentMeta](create-order-and-schedule/src/main/java/com/genohm/slims/custom/beans/CreateOrderAndSchedule.java#L99)
* Throwing errors for users with [SlimsGateErrorException](create-order-and-schedule/src/main/java/com/genohm/slims/custom/beans/CreateOrderAndSchedule.java#L85)
* Accessing inputs on step forms with [SlimsFlowInitParam](create-order-and-schedule/src/main/java/com/genohm/slims/custom/beans/CreateOrderAndSchedule.java#L69)
* Using *StringUtil* to assist with casting Objects
  * [Casting to Long[]](create-order-and-schedule/src/main/java/com/genohm/slims/custom/beans/CreateOrderAndSchedule.java#L72)
* Accessing checked-off records with *SLIMS_SELECT_SAMPLES*
  * [Slimsgate.xml configuration to require items be checked off](create-order-and-schedule/src/main/resources/slimsgate.xml#L13)
  * [Accessing the pk's of checked-off items in your code](create-order-and-schedule/src/main/java/com/genohm/slims/custom/beans/CreateOrderAndSchedule.java#L70)

</details>
</details>
