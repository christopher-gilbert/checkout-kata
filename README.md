# Checkout Kata
Supermarket checkout exercise

## Overview

Simple model of a supermarket checkout process involving scanning items by SKU, and applying basic pricing and multi-buy special offers to arrive at a total price for a basket of items. 

Full details are not shared here as they are not intended to be publicly available.

## Design

### General
For this simple model I have implemented code from a controller layer down to entities 'persisted' in memory as Lists or Maps of objects. 

### View
As this kata specifically avoids any frameworks and is limited to what is available in core Java the controller is not hooked up to any active listening view - 
to do so in core Java would preclude even the Servlet API as that is part of Jakarta EE and so I would be looking at a ServerSocket binding to port 80 and manually parsing the HTTP protocol - fun maybe, but outside the remit I feel (and that is before we consider TLS).  

Instead, the controller is exercised by an integration test (src/test/groovy/net/gilbert/chris/checkout/EndToEndTest.groovy) that models a typical set of calls from a client accessing some view on the controller. 

The controller is view agnostic. In reality it would probably be exposed as an API accessed by client code on the till system, 
rather than having a view rendered on the server side. 

The controller as implemented does not provide RESTful semantics - to do so would not fit well with the exercise as providing access to the Basket as a resource would lead to more of the presentation logic being pushed to the UI client code, and UI is outside the scope of the exercise.


### Domain 
The domain model is used across layers from the service interface down. 

I have not separated out entity classes from the domain classes, I have simply detached 'persisted' instances to avoid uncontrolled modification of 'stored' representations of the domain. 
This avoids a proliferation of different representations of the domain, and mapping between them.
This would generally also be the case in a more realistic implementation, where either an ORM would map the domain, or it could be serialised/deserialised to a representation suitable for a key/value store.    

Domain objects *are* mapped to DTOs in the controller for safety to avoid uncontrolled binding to properties such as price etc.
However, the use of DTOs is really for illustrative purposes - the controller only takes in simple values and so could safely expose the domain model.

Pricing is retained in units that do not relate to any specific currency until presented to the view.

Price calculations are carried out in the entities. As a rule I try to avoid an anaemic domain model by adding any *intrinsic* behaviour to the domain model. 
For me that means behaviour that purely relates to the properties of those objects and does *not* rely on specific business rules (which should be extracted to separate rule classes). 
In the case of price calculations in stock items and special offers, the calculation rules are intrinsically tied to the properties of those objects.  

### Repositories
The repository layer simply maintains in-memory instances of domain objects. It is not closely aligned with any particular framework, nor does it wholly align with use of either a relational database or key/value store. In particular:
* SpecialOffers are retrieved with their associated StockItems - similar to how you might pull them from a relational database with JPA.
* The Basket is modelled in a way that does not lend itself to an object/relational mapping, because of the way associated stock items are individually listed. This would be more suited to a key value store where the basket may be marshalled into a single representation without associations with other relations.

As I am in the privileged position of not having any real persistence, I have taken the liberty of using an approach that feels intuitive and allows me to fulfil the requirements of the exercise (demonstrating use of streaming API etc) .

I have assigned IDs to the persisted entities separate from other potentially unique identifiers (such as SKUs). 
This makes sense in the case of Baskets, but less so for StockItems and SpecialOffers, at least within the requirements of this exercise. 
It is really to cover a point specifically made in the exercise about ensuring clients refer to SKU rather than item ID, and is redundant in the current implementation.
However, in reality, if these entities were persisted, it is reasonable to assume a unique ID as it is likely that any updates may be soft updates in order to view consistent historical data and hence SKU would not be unique.

## Service

The CheckoutService persists the basket after each item is added. There are two reasons for adding this slight complexity (rather than just maintaining a Basket object in memory):
  * in a clustered environment, in-memory means each update to the basket could see a different view of its current state
  * even in a single server, a temporary outage would mean state is lost and the cashier would have to start again!
An alternative would be to retain the Basket state on the client, but then we would not have persisted Baskets available for future analysis.

The StockPriceManagementService does not provide any functions for maintaining items, nor do the associated repositories expose any such functions, stock items and special offers can only be added, which is sufficient for the exercise.

### Defensive Coding
To avoid uncontrolled modification of 'persisted' domain objects I took the following steps:
* Stock items and special offers are immutable, and in this simple implementation the repositories do not allow persisted items to be replaced.
* Baskets *are* mutable, but the 'persisted' instance is never exposed to code outside the repository. Although the instances that are exposed are not fully deep copies, they are designed to avoid directly exposing any properties that can be modified. 

## Assumptions and Simplifications
* Although the instruction is to keep things simple, the exercise does mention using entities etc, implying that it should be more of a simulation of a real application. 
  I have tried to strike the right balance of simple functions and functionality along with addressing some of the important considerations and design decisions of a real application. 
* No basket is expected to contain items where the total price in pennies exceeds the maximum Integer value.
* The original kata mentions 'the main method' but apart from running through a sample interaction I think a main method would only prove useful if it was to start up some kind of process listening on a port, which as mentioned I think is out of scope, and hence I assume the integration test covers the intended role of a main method.
* Offers are associated with the basket at the time of checkout - ideally this would be on entering the supermarket - else on arriving at checkout the offers may not be as advertised when filling the physical basket. 
  In reality I would expect offers to be updated out of shop opening times for this reason. 
* In several places I use Lists where Sets would do - this just makes testing a bit easier for this simple exercise.
* The exercise mentions the ability to set pricing rules at the start of checkout. This capability is included in the service layer but not used, as the end to end test sets up all the offers at the start of checking out anyway.

## Suggested Improvements (to existing code)
* In this implementation the SpecialOffer is explicitly a multi-buy. 
  A more flexible approach would be to allow for different types of offer, which would just require a factory when retrieving offers from the repository. 
  There would be no real need for a 'SpecialOffer' interface or superclass, as the implementation of the PricingStrategy interface would determine the behaviour regardless of the type of offer. 
* CheckoutService#addItem throws application exception if the basket doesn't exist, but IllegalArgumentException if the stock item doesn't exist. 
  This keeps the code more fluent but is inconsistent - it isn't as trivial as I thought to just override the Kotlin requireNotNull functionality. 
  It would be simple to ensure a consistent result, but slightly more tricky to do it elegantly. 
* Allowing setting of offers in the controller to override stored offers. It would be straightforward to do this, and is supported by the service interface.

## Suggested Additional Functionality
* I have not included any additional (but expected) properties in the domain model such as stock item name etc. To do so would involve unnecessary assumptions about these properties (eg is name a simple property, or is it made up of brand, product, size)
* The view would be more useful showing a price per product type and total price each time an addition is made.
* Ability for cashiers to override specific prices on items (perhaps one was reduced to clear, but that reduced price is not correctly applied in the system)
* Association of baskets to customers (through link to a loyalty card membership) at any point during the checkout process.
