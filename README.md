# Spring-Boot and Camel XML QuickStart

This example demonstrates how to configure Camel routes in Spring Boot via a Spring XML configuration file.

The application utilizes the Spring [`@ImportResource`](http://docs.spring.io/spring/docs/current/javadoc-api/org/springframework/context/annotation/ImportResource.html) annotation to load a Camel Context definition via a [camel-context.xml](src/main/resources/spring/camel-context.xml) file on the classpath.

## Building

The example can be built with

    mvn clean install

## Running the example in OpenShift

It is assumed that:

- OpenShift platform is already running, if not you can find details how to [Install OpenShift at your site](https://docs.openshift.com/container-platform/3.9/install_config/index.html).
- Your system is configured for Fabric8 Maven Workflow, if not you can find a [Get Started Guide](https://access.redhat.com/documentation/en-us/red_hat_jboss_fuse/7.0-tp/html-single/fuse_on_openshift_guide/)
- AMQ broker is running and available from the project where the application will be deployed.

To deploy a single basic AMQ broker you could install the images and basic template as: 

    oc replace --force=true -n openshift -f https://raw.githubusercontent.com/jboss-openshift/application-templates/master/amq/amq63-image-stream.json  
    oc replace --force=true -n openshift -f https://raw.githubusercontent.com/jboss-openshift/application-templates/master/amq/amq63-basic.json    

Create the broker:

    oc policy add-role-to-user view system:serviceaccount:$(oc project -q):default
    oc new-app --template=amq63-basic \
      -p APPLICATION_NAME=broker \
      -p MQ_USERNAME=admin \
      -p MQ_PASSWORD=admin \
      -p AMQ_MESH_DISCOVERY_TYPE=kube \
      -p MQ_PROTOCOL=openwire,amqp

You could delete the services not needed as:

    oc delete svc broker-amq-mqtt
    oc delete svc broker-amq-stomp

The example can be built and run on OpenShift using a single goal:

    mvn fabric8:deploy

To list all the running pods:

    oc get pods

Then find the name of the pod that runs this quickstart, and output the logs from the running pods with:

    oc logs <name of pod>

You can also use the OpenShift [web console](https://docs.openshift.com/container-platform/3.9/getting_started/developers_console.html#developers-console-video) to manage the running pods, and view logs and much more.

### Running via an S2I Application Template

Application templates allow you deploy applications to OpenShift by filling out a form in the OpenShift console that allows you to adjust deployment parameters.  This template uses an S2I source build so that it handle building and deploying the application for you.


First, import the Fuse image streams:

    BASEURL=https://raw.githubusercontent.com/jboss-fuse/application-templates/application-templates-2.1.fuse-000064-redhat-4
    oc replace --force=true -n openshift -f ${BASEURL}/fis-image-streams.json

Then create the quickstart template:

    BASEURL=https://raw.githubusercontent.com/jboss-fuse/application-templates/application-templates-2.1.fuse-000064-redhat-4
    oc replace --force=true -n openshift -f ${BASEURL}/quickstarts/spring-boot-camel-xml-template.json

Now when you use "Add to Project" button in the OpenShift console, you should see a template for this quickstart. 

### Integration Testing

The example includes a [fabric8 arquillian](https://github.com/fabric8io/fabric8/tree/v2.2.170.redhat/components/fabric8-arquillian) OpenShift Integration Test. 
Once the container image has been built and deployed in OpenShift, the integration test can be run with:

    mvn test -Dtest=*KT

The test is disabled by default and has to be enabled using `-Dtest`. Open Source Community documentation at [Integration Testing](https://fabric8.io/guide/testing.html) and [Fabric8 Arquillian Extension](https://fabric8.io/guide/arquillian.html) provide more information on writing full fledged black box integration tests for OpenShift. 
