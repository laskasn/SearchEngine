# SearchEngine

<!--
![Under Construction](https://cdn.pixabay.com/photo/2017/06/20/08/12/maintenance-2422173_960_720.png)
-->


A generic-purpose, indexing mechanism, based on ElasticSearch v2.4.2.
This project was originally developed for the [BlueBridge H2020 EU Project](http://www.bluebridge-vres.eu/), and is now forked here, having no other dependencies on the project.
You can also find the originating code [here](http://svn.research-infrastructures.eu/public/d4science/gcube/branches/index-management/).

One major differentiation of this fork is that it does not rely on a custom endpoint discovery solution, but switched to the well-established [Apache Zookeeper](https://zookeeper.apache.org/) to do so.

The framework consists of 5 components, which are described shortly below:

### elasticsearch-gcube

The core component, loading the elastic search core. It is parametrized by the configuration of the index-service webapp. 

### index-service-commons

Contains shared interfaces and classes between the index-service and the index-service-client-library.

### index-service

The main webapp, intended to store and retrieve data from the elasticsearch embedded node. Each webapp comes with an elasticsearch node.



The rest api allows to
* insert records in the index (the schema is inferred by the data of the FIRST record inserted into it).
* delete records from the index
* create/drop indexes
* list "Collections"
* define "CollectionInfo" structures for each "Collection"
* perform a soft reindexing of the collections, taking into consideration the accompanying CollectionInfo structure

**Collection**: semantically is the dataset which resides within an index.
**CollectionInfo**: A structure which holds a soft definition of the indexed document. There is a 1-1 relation between the index and the CollectionInfo. 
It contains, among others: 
* collection field aliases (if set, the index will baptise the output fields by the alias names), 
* the option to define in CollectionInfo.collectionFieldsConfigs.FieldConfig.FacetType, the desired faceting type for the field {None, Normal, Non_Tokenized} which upon a reindexing, changes the response of the index when queried (i.e. returns field facets if asked to).


### index-service-cluster-manager

This is a very simple client library which stores and queries on demand the existence along with the configuration of the active index-service nodes.

### index-service-client-library

A client library to access the functionality provided by the index-service endpoints. 


> **Note:** Two are the end-user components. The index-service and the index-service-client-library. All others are dependencies of those two.


## ToDo:

* Migrate index-service to spring boot (currently it uses jersey for the http api)
* Add spring boot security (currently it just assumes running within a protected environment. i.e through iptables rules)
* Add a event-based update of the endpoints (having subscribers upon a new node appearance/leaving ), and not the current pull-based fashion.
