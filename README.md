# SearchEngine

![Under Construction](https://cdn.pixabay.com/photo/2017/06/20/08/12/maintenance-2422173_960_720.png)

A generic-purpose, indexing mechanism, based on ElasticSearch v2.4.2.
This project was originally developed for the [BlueBridge H2020 EU Project](http://www.bluebridge-vres.eu/), and is now forked here, having no other dependencies on the project.
You can also find the originating code [here](http://svn.research-infrastructures.eu/public/d4science/gcube/branches/index-management/).

One major differentiation of this fork is that it does not rely on a custom endpoint discovery solution, but on the well-established [Apache Zookeeper](https://zookeeper.apache.org/) to do so.

It consists of 5 components, which are described shortly below:

### elasticsearch-gcube

The core component, loading the elastic search core. It is parametrized by the configuration of the index-service webapp. 

### index-service-commons

Contains shared interfaces and classes between the index-service and the index-service-client-library.

### index-service

The main webapp, intended to store and retrieve data from the elasticsearch embedded node. Each webapp comes with an elasticsearch node.

The rest api allows to store

### index-service-cluster-manager

This is a very simple client library which stores and queries on demand the existence along with the configuration of the active index-service nodes.

### index-service-client-library

A client library to access the functionality provided by the index-service endpoints. 


## ToDo:

* Migrate index-service to spring boot (currently it uses jersey for the http api)
* Add spring boot security (currently it just assumes running within a protected environment. i.e through iptables rules)
* Add a event-based update of the endpoints (having subscribers upon a new node appearance/leaving ), and not the current pull-based fashion.
