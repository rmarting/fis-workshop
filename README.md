# Spring-Boot and Camel XML QuickStart

This example demonstrates how to configure Camel routes in Spring Boot via a Spring XML configuration file.

The application utilizes the Spring [`@ImportResource`](http://docs.spring.io/spring/docs/current/javadoc-api/org/springframework/context/annotation/ImportResource.html) annotation to load a Camel Context definition via a [camel-context.xml](src/main/resources/spring/camel-context.xml) file on the classpath.

## Building

The example can be built with

```bash
mvn clean install
```

## Running the example in your local environment

```bash
mvn spring-boot:run
```

## Running the example in OpenShift

It is assumed that:

- OpenShift platform is already running, if not you can find details how to [Install OpenShift at your site](https://docs.openshift.com/container-platform/3.9/install_config/index.html).
- Your system is configured for Fabric8 Maven Workflow, if not you can find a [Get Started Guide](https://access.redhat.com/documentation/en-us/red_hat_jboss_fuse/7.0-tp/html-single/fuse_on_openshift_guide/)
- AMQ broker is running and available from the project where the application will be deployed.

To deploy a single basic AMQ broker you could install the images and basic template as: 

```bash
oc replace --force  -f \
  https://raw.githubusercontent.com/jboss-container-images/jboss-amq-7-broker-openshift-image/74-7.4.0.GA/amq-broker-7-image-streams.yaml
```

```bash
oc replace --force -f \
  https://raw.githubusercontent.com/jboss-container-images/jboss-amq-7-broker-openshift-image/74-7.4.0.GA/templates/amq-broker-74-basic.yaml
```

Create the broker:

```bash
oc policy add-role-to-user view system:serviceaccount:$(oc project -q):default
oc new-app --template=amq-broker-74-basic \
    -p APPLICATION_NAME=amqp-broker \
    -p AMQ_NAME=amqp-broker \
    -p AMQ_PROTOCOL=amqp \
    -p AMQ_USER=admin \
    -p AMQ_PASSWORD=admin \
    -p AMQ_ROLE=admin \
    -p AMQ_REQUIRE_LOGIN=true
```

You could delete the services not needed as:

```bash
oc delete svc amqp-broker-amq-mqtt amqp-broker-amq-stomp amqp-broker-amq-tcp
```

The example can be built and run on OpenShift using a single goal:

```bash
mvn fabric8:deploy
```

To list all the running pods:

```bash
oc get pods
```

Then find the name of the pod that runs this quickstart, and output the logs from the running pods with:

```bash
oc logs <name of pod>
```

You can also use the OpenShift [web console](https://docs.openshift.com/container-platform/3.11/getting_started/developers_console.html#developers-console-video) to manage the running pods, and view logs and much more.

### Running via an S2I Application Template

Application templates allow you deploy applications to OpenShift by filling out a form in the OpenShift console that allows you to adjust deployment parameters.  This template uses an S2I source build so that it handle building and deploying the application for you.

First, import the Fuse image streams:

```bash
    BASEURL=https://raw.githubusercontent.com/jboss-fuse/application-templates/application-templates-2.1.fuse-000064-redhat-4
    oc replace --force=true -n openshift -f ${BASEURL}/fis-image-streams.json
```

Then create the quickstart template:

```bash
    BASEURL=https://raw.githubusercontent.com/jboss-fuse/application-templates/application-templates-2.1.fuse-000064-redhat-4
    oc replace --force=true -n openshift -f ${BASEURL}/quickstarts/spring-boot-camel-xml-template.json
```

Now when you use "Add to Project" button in the OpenShift console, you should see a template for this quickstart. 

### Integration Testing

The example includes a [fabric8 arquillian](https://github.com/fabric8io/fabric8/tree/v2.2.170.redhat/components/fabric8-arquillian) OpenShift Integration Test. 
Once the container image has been built and deployed in OpenShift, the integration test can be run with:

```bash
mvn test -Dtest=*KT
```

The test is disabled by default and has to be enabled using `-Dtest`. Open Source Community documentation at [Integration Testing](https://fabric8.io/guide/testing.html) and [Fabric8 Arquillian Extension](https://fabric8.io/guide/arquillian.html) provide more information on writing full fledged black box integration tests for OpenShift. 

## Application Resources

* [API Docs](http://fuse-workshop-spike-apache-camel.apps.santan.emea-1.rht-labs.com/fuse-workshop/api-docs)
