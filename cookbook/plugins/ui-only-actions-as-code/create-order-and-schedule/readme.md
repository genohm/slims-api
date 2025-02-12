---
plugin-name: "create-order-and-schedule"
---

# General Introduction
This plugin creates a new order (using the order type from the plugin configuration), links the selected content to the 
order, applies a request to each of the selected content (using the requestable type in the plugin configuration), then 
schedules the new order.

## Usage: _Content Management_
## Type: *slimsgate*

# Step 1:
## User inputs:
none
## Process
An order is created, the content is linked to the order (by creating OrderContent), requestables are added to each
content record, and then order is scheduled. A feedback map outlining the content and the order is output to the next
step.


# Step 2:
## User inputs:
none
## Process
This step is just for displaying the feedback map.

# Required Configuration
- A valid requestable
- A valid order type

# Parameters description
* (String) **requestableUid**: The UID for the requestable that will be used to create requests for the selected content.
* (String) **orderTypeUid**: The UID for the desired order type for the new order.

# Troubleshooting
The only expected errors include being unable to find the requestable or order type.

