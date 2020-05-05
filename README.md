# OPAL Licenses

OPAL component managing licenses.

## Evaluation and experiments

- To run the evaluation of the **Creative Commons** License Compatibility Chart, clone [cc.licenserdf](https://github.com/projekt-opal/cc.licenserdf) and  
run [CcExperiment](src/main/java/org/dice_research/opal/licenses/cc/CcExperiment.java), [CcExperimentTuples](src/main/java/org/dice_research/opal/licenses/cc/CcExperimentTuples.java) and [CcExperimentTriples](src/main/java/org/dice_research/opal/licenses/cc/CcExperimentTriples.java).
- To run the evaluation of the **European Data Portal** License Compatibility Matrix, run [EdpLcmEvaluationTest](src/test/java/org/dice_research/opal/licenses/EdpLcmEvaluationTest.java).


## Notes

* [Version 0.0.1](https://github.com/projekt-opal/licenses/tree/0.0.1) contains a KnowledgeGraph based on the European Data Portal Licence Compatibility Matrix. It uses the Open Digital Rights Language (ODRL). Additionally, that version contains code to clean licenses, a first LicenseCombinator version, and SPARQL tools for EDP.

## Credits

[Data Science Group (DICE)](https://dice-research.org/) at [Paderborn University](https://www.uni-paderborn.de/)

This work has been supported by the German Federal Ministry of Transport and Digital Infrastructure (BMVI) in the project [Open Data Portal Germany (OPAL)](http://projekt-opal.de/) (funding code 19F2028A).