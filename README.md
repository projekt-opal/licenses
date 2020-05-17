# Licenses

This repository contains code to generate a list of compatible licenses based on multiple input licenses to be checked.
The following main steps are executed:

- Creation of a [KnowledgeBase](src/main/java/org/example/licenses/KnowledgeBase.java) which contains the licenses and attributes.
- License [Attribute](src/main/java/org/example/licenses/Attribute.java) values are mapped according to their type.
- The [Operator](src/main/java/org/example/licenses/Operator.java) computes composite attributes of all input licenses.
- Finally, the [BackMapping](src/main/java/org/example/licenses/BackMapping.java) creates a list of compatible licenses.
- To add additional Knowledge Bases, the [AttributeFactory](src/main/java/org/example/licenses/AttributeFactory.java) can be utilized and afterwards the [Execution](src/main/java/org/example/licenses/Execution.java) methods help to run an experiment.

## Evaluation and experiments

For the evaluation of the approach, experiments based on two license datasets are provided:
Creative Commons and the European Data Portal (EDP) License Compatibility Matrix.


### Creative Commons experiments

To run the evaluations based on Creative Commons, you first have to download the underlying dataset.
Therefore, download or clone the
[cc.licenserdf](https://github.com/creativecommons/cc.licenserdf)
repository.
Afterwards, the directory of the repository can be set by the system property ``cc.licenserdf``.
Example commands to run the experiments are listed below.


**Creative Commons License Compatibility Chart**

```
java -Dcc.licenserdf=../../cc.licenserdf/cc/licenserdf/licenses/ -jar licenses-jar-with-dependencies.jar cc1
```

This will run the [CcExperiment](src/main/java/org/example/licenses/cc/CcExperiment.java).


**Creative Commons cc.licenserdf with two input licenses**

```
java -Dcc.licenserdf=../../cc.licenserdf/cc/licenserdf/licenses/ -jar licenses-jar-with-dependencies.jar cc2
```

This will run the [CcExperimentTuples](src/main/java/org/example/licenses/cc/CcExperimentTuples.java).


**Creative Commons cc.licenserdf with three input licenses**

```
java -Dcc.licenserdf=../../cc.licenserdf/cc/licenserdf/licenses/ -jar licenses-jar-with-dependencies.jar cc3
```

This will run the [CcExperimentTriples](src/main/java/org/example/licenses/cc/CcExperimentTriples.java).


### European Data Portal experiments

To run the evaluation of the European Data Portal (EDP) License Compatibility Matrix, run the following maven command:

```
mvn clean test -Dtest=EdpLcmEvaluationTest -Drun.edp.lcm.tests=true
```

This will run the [EdpLcmEvaluationTest](src/test/java/org/example/licenses/EdpLcmEvaluationTest.java).


## Credits

ANONYMIZED

This work has been supported by ANONYMIZED.