# Spring-Boot and Camel XML QuickStart

This example demonstrates how to configure Camel routes in Spring Boot via a Spring XML configuration file.

The application utilizes the Spring [`@ImportResource`](http://docs.spring.io/spring/docs/current/javadoc-api/org/springframework/context/annotation/ImportResource.html) annotation to load a Camel Context definition via a [camel-context.xml](src/main/resources/spring/camel-context.xml) file on the classpath.

### OpenShift

It is recommendable to install and deploy a local OpenShift instance to deploy the next components:

* AMQ Broker Service
* Spring Boot application with Apache Camel Routes

To have a local OpenShift instance, **minishift** is the best way. Follow the next link to install locally: 

* [Minishit Quickstart](https://docs.openshift.org/latest/minishift/getting-started/quickstart.html)

Create a new project to deploy everything:

    $ oc new-project fis-workshop
    
To deploy the Red Hat images needed:

* Fuse Integration Services images (Apache Camel for Spring Boot)

    $ oc create -n openshift -f https://raw.githubusercontent.com/jboss-fuse/application-templates/master/fis-image-streams.json

* AMQ templates and images:

    $ oc create -n openshift -f https://raw.githubusercontent.com/jboss-openshift/application-templates/master/amq/amq63-basic.json
    $ oc create -n openshift -f https://raw.githubusercontent.com/jboss-openshift/application-templates/master/amq/amq63-image-stream.json 

* To deploy a new AMQ broker

    $ oc new-app --template=amq63-basic \
      -p APPLICATION_NAME=broker \
      -p MQ_USERNAME=admin \
      -p MQ_PASSWORD=admin \
      -p AMQ_MESH_DISCOVERY_TYPE=kube \
      -p MQ_PROTOCOL=openwire,amqp

* Allow *view role* for some Service Accounts needed:

    $ oc policy add-role-to-user view system:serviceaccount:$(oc project -q):default
    $ oc policy add-role-to-user view system:serviceaccount:$(oc project -q):fis-workshop


### Building

The example can be built with

    mvn clean install

### Running the example in OpenShift

It is assumed that:
- OpenShift platform is already running, if not you can find details how to [Install OpenShift at your site](https://docs.openshift.com/container-platform/3.3/install_config/index.html).
- Your system is configured for Fabric8 Maven Workflow, if not you can find a [Get Started Guide](https://access.redhat.com/documentation/en/red-hat-jboss-middleware-for-openshift/3/single/red-hat-jboss-fuse-integration-services-20-for-openshift/)

The example can be built and run on OpenShift using a single goal:

    mvn fabric8:deploy

To list all the running pods:

    oc get pods

Then find the name of the pod that runs this quickstart, and output the logs from the running pods with:

    oc logs <name of pod>

You can also use the OpenShift [web console](https://docs.openshift.com/container-platform/3.3/getting_started/developers_console.html#developers-console-video) to manage the running pods, and view logs and much more.

### Running via an S2I Application Template

Application templates allow you deploy applications to OpenShift by filling out a form in the OpenShift console that allows you to adjust deployment parameters.  This template uses an S2I source build so that it handle building and deploying the application for you.

First, import the Fuse image streams:

    oc create -f https://raw.githubusercontent.com/jboss-fuse/application-templates/GA/fis-image-streams.json

Then create the quickstart template:

    oc create -f https://raw.githubusercontent.com/jboss-fuse/application-templates/GA/quickstarts/spring-boot-camel-xml-template.json

Now when you use "Add to Project" button in the OpenShift console, you should see a template for this quickstart. 


### Integration Testing

The example includes a [fabric8 arquillian](https://github.com/fabric8io/fabric8/tree/v2.2.170.redhat/components/fabric8-arquillian) OpenShift Integration Test. 
Once the container image has been built and deployed in OpenShift, the integration test can be run with:

    mvn test -Dtest=*KT

The test is disabled by default and has to be enabled using `-Dtest`. Open Source Community documentation at [Integration Testing](https://fabric8.io/guide/testing.html) and [Fabric8 Arquillian Extension](https://fabric8.io/guide/arquillian.html) provide more information on writing full fledged black box integration tests for OpenShift. 


### Verify Application

If the deployment process works successfully, the following links will show the Swagger documentation:

* http://fis-workshop-fis-workshop.127.0.0.1.nip.io/fis-workshop/api-docs

This link show the result of test API route:

* http://fis-workshop-fis-workshop.127.0.0.1.nip.io/fis-workshop/api/test
