# checkout-kata
Supermarket checkout exercise

## Overview

Simple model of a supermarket checkout process involving scanning items by SKU, and applying basic pricing and multi-buy special offers to arrive at a price total for a basket of items. 

Full details are not shared here as they are not intended to be publicly available.

## Design

For this simple model I have implemented code from a controller layer down. 
The controller deliberately does not expose a RESTful API - to do so would not fit well with the exercise as it would really require an API that allows patching of a Basket resource, and this would lead to more of the presentation logic in the UI client code, and UI is outside the scope of the exercise.

As this kata specifically avoids any frameworks and is limited to what is available in core Java the 'controller' is not hooked up to any active listening view - 
to do so in core Java would preclude even the Servlet API as that is part of Jakarta EE and so I would be looking at a ServerSocket binding to port 80 and manually parsing the HTTP protocol - fun maybe, but outside the remit I feel (and that is before we consider TLS).  

Instead, the service layer is exercised by an integration test (src/test/groovy/net/gilbert/chris/checkout/EndToEndTest.groovy) that models a typical set of calls from a controller. 
The original kata mentions a main method or function but apart from running through a sample interaction I think a main method would only prove useful if it was to start up some kind of process listening on a port, which as mentioned I think is out of scope, and hence I assume the integration test covers tthe intended role of a main method.

Pricing is retained in units that do not relate to any specific currency until presented to the view.
### Defensive coding
To avoid uncontrolled modification of domain objects I took the following steps:
* Stock items and special offers are immutable, and in this simple implementation the repositories do not allow persisted items to be replaced.
* Baskets ARE mutable, but the 'persisted' instance is never exposed to code outside the repository, and the controller layer never allows direct modification of Baskets that are subsequently persisted.  

## Assumptions
* No basket is expected to contain items where the price exceeds the maximum Integer

## Suggested Improvements (to existing code)

* In this implementation the SpecialOffer is explicitly a multibuy. A more flexible approach would be to allow for different types of offer, which would just require a factory when retrieving offers from the repository. There would be no real need for a 'SpecialOffer' interface or superclass, as the PricingStrategy interface would determine the behaviour regardless of the type of offer. 
* CheckoutService#addItem throws application exception if the basket doesn't exist, but IllegalArgumentException if the stock item doesn't exist. This keeps the code more fluent but is inconsistent. 

## Suggested Additional Functionality
* I have not included any additional properties such as stock item name etc. To do so would involve unnecessary assumptions about these properties (eg is name a simple property, or is it made up of brand, product, size)

## Design Notes

For this exercise I have taken the liberty of applying various simplifications and making certain assumptions:

* I have represented reference data (stock and special offers) as 'persisted' entities accessed via repositories (actually just fronting lists at the moment). 

* I have assigned IDs to the persisted entities separate from the SKUs that are currently used to locate data. This is really to cover a point specifically made in the kata, and is redundant in the current implementation. 
  In reality it is reasonable to assume a unique ID as it is likely that any updates may be soft updates in order to view consistent historical data and hence SKU would not be unique. 
* I have assumed only one type of special offer - multi-buy. The pricing process would allow for other types to be plugged in.
* I have simplified the persistence of reference data by just adding data during bootstrapping rather than allow for any ongoing maintenance of data through updates or deletes.
* In several places I use Lists where Sets would do - this just makes testing a bit easier for this simple kata
* I have passed entities and domain objects right through to the service caller rather than mapping to DTOs. All the available objects are immutable to avoid any unsafe modification but also I would generally use the application domain in the service interface and would expect mapping to and from external views to be done in the controller. 
  This would be important for example if incoming data is bound to objects - exposing entities directly may allow unsafe data to be bound and persisted. 
  I would realistically expect the controller to bind to a view that accepts only identifying data such as SKUs rather than exposing unnecessary data (price for example!) 
  As an example, as a REST purist, we may GET a view of the basket and then PATCH it with an item added - but that item may only include the SKU property  

* The actual price calculations are included in the entities. As a rule I try to avoid anaemic domain model by adding any *intrinsic* behaviour to the domain model - by which I mean *not* behaviour that relates to business rules. In the case of price calculations, the calculation rules are intrinsically tied to the properties of those objects and so in my mind it is valid to apply those calculations there.  
* State of the ongoing checkout processed is maintained in an in-memory basket for simplicity. In reality, the basket would be represented as an entity that is retrieved and persisted as it is updated during checkout, so the flow of a scan might be:
  * Retrieve basket by ID
  * Add Item
  * Store basket
  * Return view of current basket state
    
  in real life, an in-memory basket has two show-stopping drawbacks:
    * in a clustered environment, each update to the basket would see a different view of its current state
    * even in a single server, a temporary outage would mean state is lost and the cashier would have to start again!

## Ignored potential requirements
* The point at which offers are tied to the basket - ideally this would be on entering the supermarket - else on arriving at checkout the offers may not be as advertised when filling the physical basket. In reality I would expect offers to be updated out of shop opening times for this reason. 

## Alternatives

An alternative approach to managing state in the basket might be to maintain a map of item to quantity. If the basket was persisted in a relational database this would be essential (though if it was stored say as JSON in a key value store or distributed in-memory cache like Redis that would not be the case).
An advantage to this approach is that it would be simpler to present a view that includes total cost per type of item. At the moment this information is unavailable; only the total price is available.