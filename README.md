# OPAL Licenses

OPAL component managing DCAT licenses.

## Supported licenses

The following knowlede graph lists the supported licenses including their permissions, prohibitions, and requirements.

```
@prefix cc:    <https://creativecommons.org/ns> .
@prefix opal:  <http://projekt-opal.de/licenses/> .
@prefix odrl:  <http://www.w3.org/ns/odrl/2/> .

<https://www.govdata.de/dl-de/by-2-0>
        a                 odrl:policy ;
        odrl:permission   [ odrl:action  odrl:derive , odrl:distribute , odrl:reproduce ] ;
        odrl:prohibition  []  ;
        odrl:requirement  [ odrl:action  cc:Attribution , cc:Notice ] .

<https://creativecommons.org/licenses/by/3.0/at/legalcode>
        a                 odrl:policy ;
        odrl:permission   [ odrl:action  odrl:derive , odrl:distribute , odrl:reproduce ] ;
        odrl:prohibition  []  ;
        odrl:requirement  [ odrl:action  cc:Attribution , cc:Notice ] .

<https://www.govdata.de/dl-de/by-1-0>
        a                 odrl:policy ;
        odrl:permission   [ odrl:action  odrl:derive , odrl:distribute , odrl:reproduce ] ;
        odrl:prohibition  []  ;
        odrl:requirement  [ odrl:action  cc:Attribution ] .

<https://www.govdata.de/dl-de/by-nc-1-0>
        a                 odrl:policy ;
        odrl:permission   [ odrl:action  odrl:derive , odrl:distribute , odrl:reproduce ] ;
        odrl:prohibition  [ odrl:action  cc:CommercialUse ] ;
        odrl:requirement  [ odrl:action  cc:Attribution ] .

<http://www.ordnancesurvey.co.uk/business-and-government/public-sector/mapping-agreements/inspire-licence.html>
        a                 odrl:policy ;
        odrl:permission   [ odrl:action  odrl:reproduce ] ;
        odrl:prohibition  [ odrl:action  cc:CommercialUse ] ;
        odrl:requirement  [ odrl:action  cc:Attribution , cc:Notice ] .

<https://www.govdata.de/dl-de/zero-2-0>
        a                 odrl:policy ;
        odrl:permission   [ odrl:action  odrl:derive , odrl:distribute , odrl:reproduce ] ;
        odrl:prohibition  []  ;
        odrl:requirement  []  .

<http://creativecommons.org/licenses/by-nc-nd/4.0/legalcode>
        a                 odrl:policy ;
        odrl:permission   [ odrl:action  odrl:distribute , odrl:reproduce ] ;
        odrl:prohibition  [ odrl:action  cc:CommercialUse ] ;
        odrl:requirement  [ odrl:action  cc:Attribution , cc:Notice ] .

<http://www.formez.it/iodl/>
        a                 odrl:policy ;
        odrl:permission   [ odrl:action  odrl:derive , odrl:distribute , odrl:reproduce ] ;
        odrl:prohibition  []  ;
        odrl:requirement  [ odrl:action  cc:Copyleft , cc:ShareAlike , cc:Attribution , cc:Notice ] .

<https://gnu.org/licenses/fdl-1.3.en.html>
        a                 odrl:policy ;
        odrl:permission   [ odrl:action  odrl:derive , odrl:distribute , odrl:reproduce ] ;
        odrl:prohibition  []  ;
        odrl:requirement  [ odrl:action  cc:Copyleft , cc:ShareAlike , cc:Attribution , cc:Notice ] .

<http://opendatacommons.org/licenses/by/1.0/>
        a                 odrl:policy ;
        odrl:permission   [ odrl:action  odrl:derive , odrl:distribute , odrl:reproduce ] ;
        odrl:prohibition  []  ;
        odrl:requirement  [ odrl:action  cc:Copyleft , cc:ShareAlike , cc:Attribution , cc:Notice ] .

<http://creativecommons.org/licenses/by-nd/4.0/legalcode>
        a                 odrl:policy ;
        odrl:permission   [ odrl:action  odrl:distribute , odrl:reproduce ] ;
        odrl:prohibition  []  ;
        odrl:requirement  [ odrl:action  cc:Attribution , cc:Notice ] .

<http://data.norge.no/nlod/en/1.0>
        a                 odrl:policy ;
        odrl:permission   [ odrl:action  odrl:derive , odrl:distribute , odrl:reproduce ] ;
        odrl:prohibition  []  ;
        odrl:requirement  [ odrl:action  cc:Copyleft , cc:ShareAlike , cc:Attribution , cc:Notice ] .

<http://www.nationalarchives.gov.uk/doc/open-government-licence/version/2/>
        a                 odrl:policy ;
        odrl:permission   [ odrl:action  odrl:derive , odrl:distribute , odrl:reproduce ] ;
        odrl:prohibition  []  ;
        odrl:requirement  [ odrl:action  cc:Attribution , cc:Notice ] .

<https://creativecommons.org/publicdomain/zero/1.0/legalcode>
        a                 odrl:policy ;
        odrl:permission   [ odrl:action  odrl:derive , odrl:distribute , odrl:reproduce ] ;
        odrl:prohibition  []  ;
        odrl:requirement  []  .

<https://gnu.org/licenses/old-licenses/fdl-1.2>
        a                 odrl:policy ;
        odrl:permission   [ odrl:action  odrl:derive , odrl:distribute , odrl:reproduce ] ;
        odrl:prohibition  []  ;
        odrl:requirement  [ odrl:action  cc:Copyleft , cc:ShareAlike , cc:Attribution , cc:Notice ] .

<http://creativecommons.org/licenses/by/4.0/legalcode>
        a                 odrl:policy ;
        odrl:permission   [ odrl:action  odrl:derive , odrl:distribute , odrl:reproduce ] ;
        odrl:prohibition  []  ;
        odrl:requirement  [ odrl:action  cc:Attribution , cc:Notice ] .

<http://creativecommons.org/licenses/by-nc/4.0/legalcode>
        a                 odrl:policy ;
        odrl:permission   [ odrl:action  odrl:derive , odrl:distribute , odrl:reproduce ] ;
        odrl:prohibition  [ odrl:action  cc:CommercialUse ] ;
        odrl:requirement  [ odrl:action  cc:Attribution , cc:Notice ] .

<http://data.gov.ro/base/images/logoinst/OGL-ROU-1.0.pdf>
        a                 odrl:policy ;
        odrl:permission   [ odrl:action  odrl:derive , odrl:distribute , odrl:reproduce ] ;
        odrl:prohibition  []  ;
        odrl:requirement  [ odrl:action  cc:Attribution ] .

<http://www.nationalarchives.gov.uk/doc/non-commercial-government-licence/non-commercial-government-licence.htm>
        a                 odrl:policy ;
        odrl:permission   [ odrl:action  odrl:derive , odrl:distribute , odrl:reproduce ] ;
        odrl:prohibition  [ odrl:action  cc:CommercialUse ] ;
        odrl:requirement  [ odrl:action  cc:Attribution , cc:Notice ] .

<https://gnu.org/licenses/old-licenses/fdl-1.1>
        a                 odrl:policy ;
        odrl:permission   [ odrl:action  odrl:derive , odrl:distribute , odrl:reproduce ] ;
        odrl:prohibition  []  ;
        odrl:requirement  [ odrl:action  cc:Copyleft , cc:ShareAlike , cc:Attribution , cc:Notice ] .

<http://creativecommons.org/licenses/by/3.0/nl/legalcode>
        a                 odrl:policy ;
        odrl:permission   [ odrl:action  odrl:derive , odrl:distribute , odrl:reproduce ] ;
        odrl:prohibition  []  ;
        odrl:requirement  [ odrl:action  cc:Attribution , cc:Notice ] .

<http://www.dati.gov.it/iodl/2.0/>
        a                 odrl:policy ;
        odrl:permission   [ odrl:action  odrl:derive , odrl:distribute , odrl:reproduce ] ;
        odrl:prohibition  []  ;
        odrl:requirement  [ odrl:action  cc:Attribution , cc:Notice ] .

<https://www.etalab.gouv.fr/licence-ouverte-open-licence>
        a                 odrl:policy ;
        odrl:permission   [ odrl:action  odrl:derive , odrl:distribute , odrl:reproduce ] ;
        odrl:prohibition  []  ;
        odrl:requirement  [ odrl:action  cc:Attribution ] .

<http://opendatacommons.org/licenses/odbl/1.0/>
        a                 odrl:policy ;
        odrl:permission   [ odrl:action  odrl:derive , odrl:distribute , odrl:reproduce ] ;
        odrl:prohibition  []  ;
        odrl:requirement  [ odrl:action  cc:Copyleft , cc:ShareAlike , cc:Attribution , cc:Notice ] .

<http://creativecommons.org/licenses/by-sa/3.0/nl/legalcode>
        a                 odrl:policy ;
        odrl:permission   [ odrl:action  odrl:derive , odrl:distribute , odrl:reproduce ] ;
        odrl:prohibition  []  ;
        odrl:requirement  [ odrl:action  cc:LesserCopyleft , cc:ShareAlike , cc:Attribution , cc:Notice ] .

<http://www.nationalarchives.gov.uk/doc/open-government-licence/version/1/>
        a                 odrl:policy ;
        odrl:permission   [ odrl:action  odrl:derive , odrl:distribute , odrl:reproduce ] ;
        odrl:prohibition  []  ;
        odrl:requirement  [ odrl:action  cc:Attribution , cc:Notice ] .

<http://creativecommons.org/licenses/by-nc-sa/4.0/legalcode>
        a                 odrl:policy ;
        odrl:permission   [ odrl:action  odrl:derive , odrl:distribute , odrl:reproduce ] ;
        odrl:prohibition  [ odrl:action  cc:CommercialUse ] ;
        odrl:requirement  [ odrl:action  cc:Copyleft , cc:ShareAlike , cc:Attribution , cc:Notice ] .

<https://joinup.ec.europa.eu/software/page/eupl/licence-eupl>
        a                 odrl:policy ;
        odrl:permission   [ odrl:action  odrl:derive , odrl:distribute , odrl:reproduce ] ;
        odrl:prohibition  []  ;
        odrl:requirement  [ odrl:action  cc:Copyleft , cc:ShareAlike , cc:Attribution , cc:Notice ] .

<http://creativecommons.org/licenses/by-sa/4.0/legalcode>
        a                 odrl:policy ;
        odrl:permission   [ odrl:action  odrl:derive , odrl:distribute , odrl:reproduce ] ;
        odrl:prohibition  []  ;
        odrl:requirement  [ odrl:action  cc:Copyleft , cc:ShareAlike , cc:Attribution , cc:Notice ] .

<http://www.nationalarchives.gov.uk/doc/open-government-licence/version/3/>
        a                 odrl:policy ;
        odrl:permission   [ odrl:action  odrl:derive , odrl:distribute , odrl:reproduce ] ;
        odrl:prohibition  []  ;
        odrl:requirement  [ odrl:action  cc:Attribution , cc:Notice ] .

<http://creativecommons.org/publicdomain/mark/1.0/>
        a                 odrl:policy ;
        odrl:permission   [ odrl:action  odrl:derive , odrl:distribute , odrl:reproduce ] ;
        odrl:prohibition  []  ;
        odrl:requirement  []  .

<http://opendatacommons.org/licenses/pddl/1-0/>
        a                 odrl:policy ;
        odrl:permission   [ odrl:action  odrl:derive , odrl:distribute , odrl:reproduce ] ;
        odrl:prohibition  []  ;
        odrl:requirement  []  .
@prefix cc:    <https://creativecommons.org/ns> .
@prefix opal:  <http://projekt-opal.de/licenses/> .
@prefix odrl:  <http://www.w3.org/ns/odrl/2/> .

<https://www.govdata.de/dl-de/by-2-0>
        a                 odrl:policy ;
        odrl:permission   [ odrl:action  odrl:derive , odrl:distribute , odrl:reproduce ] ;
        odrl:prohibition  []  ;
        odrl:requirement  [ odrl:action  cc:Attribution , cc:Notice ] .

<https://creativecommons.org/licenses/by/3.0/at/legalcode>
        a                 odrl:policy ;
        odrl:permission   [ odrl:action  odrl:derive , odrl:distribute , odrl:reproduce ] ;
        odrl:prohibition  []  ;
        odrl:requirement  [ odrl:action  cc:Attribution , cc:Notice ] .

<https://www.govdata.de/dl-de/by-1-0>
        a                 odrl:policy ;
        odrl:permission   [ odrl:action  odrl:derive , odrl:distribute , odrl:reproduce ] ;
        odrl:prohibition  []  ;
        odrl:requirement  [ odrl:action  cc:Attribution ] .

<https://www.govdata.de/dl-de/by-nc-1-0>
        a                 odrl:policy ;
        odrl:permission   [ odrl:action  odrl:derive , odrl:distribute , odrl:reproduce ] ;
        odrl:prohibition  [ odrl:action  cc:CommercialUse ] ;
        odrl:requirement  [ odrl:action  cc:Attribution ] .

<http://www.ordnancesurvey.co.uk/business-and-government/public-sector/mapping-agreements/inspire-licence.html>
        a                 odrl:policy ;
        odrl:permission   [ odrl:action  odrl:reproduce ] ;
        odrl:prohibition  [ odrl:action  cc:CommercialUse ] ;
        odrl:requirement  [ odrl:action  cc:Attribution , cc:Notice ] .

<https://www.govdata.de/dl-de/zero-2-0>
        a                 odrl:policy ;
        odrl:permission   [ odrl:action  odrl:derive , odrl:distribute , odrl:reproduce ] ;
        odrl:prohibition  []  ;
        odrl:requirement  []  .

<http://creativecommons.org/licenses/by-nc-nd/4.0/legalcode>
        a                 odrl:policy ;
        odrl:permission   [ odrl:action  odrl:distribute , odrl:reproduce ] ;
        odrl:prohibition  [ odrl:action  cc:CommercialUse ] ;
        odrl:requirement  [ odrl:action  cc:Attribution , cc:Notice ] .

<http://www.formez.it/iodl/>
        a                 odrl:policy ;
        odrl:permission   [ odrl:action  odrl:derive , odrl:distribute , odrl:reproduce ] ;
        odrl:prohibition  []  ;
        odrl:requirement  [ odrl:action  cc:Copyleft , cc:ShareAlike , cc:Attribution , cc:Notice ] .

<https://gnu.org/licenses/fdl-1.3.en.html>
        a                 odrl:policy ;
        odrl:permission   [ odrl:action  odrl:derive , odrl:distribute , odrl:reproduce ] ;
        odrl:prohibition  []  ;
        odrl:requirement  [ odrl:action  cc:Copyleft , cc:ShareAlike , cc:Attribution , cc:Notice ] .

<http://opendatacommons.org/licenses/by/1.0/>
        a                 odrl:policy ;
        odrl:permission   [ odrl:action  odrl:derive , odrl:distribute , odrl:reproduce ] ;
        odrl:prohibition  []  ;
        odrl:requirement  [ odrl:action  cc:Copyleft , cc:ShareAlike , cc:Attribution , cc:Notice ] .

<http://creativecommons.org/licenses/by-nd/4.0/legalcode>
        a                 odrl:policy ;
        odrl:permission   [ odrl:action  odrl:distribute , odrl:reproduce ] ;
        odrl:prohibition  []  ;
        odrl:requirement  [ odrl:action  cc:Attribution , cc:Notice ] .

<http://data.norge.no/nlod/en/1.0>
        a                 odrl:policy ;
        odrl:permission   [ odrl:action  odrl:derive , odrl:distribute , odrl:reproduce ] ;
        odrl:prohibition  []  ;
        odrl:requirement  [ odrl:action  cc:Copyleft , cc:ShareAlike , cc:Attribution , cc:Notice ] .

<http://www.nationalarchives.gov.uk/doc/open-government-licence/version/2/>
        a                 odrl:policy ;
        odrl:permission   [ odrl:action  odrl:derive , odrl:distribute , odrl:reproduce ] ;
        odrl:prohibition  []  ;
        odrl:requirement  [ odrl:action  cc:Attribution , cc:Notice ] .

<https://creativecommons.org/publicdomain/zero/1.0/legalcode>
        a                 odrl:policy ;
        odrl:permission   [ odrl:action  odrl:derive , odrl:distribute , odrl:reproduce ] ;
        odrl:prohibition  []  ;
        odrl:requirement  []  .

<https://gnu.org/licenses/old-licenses/fdl-1.2>
        a                 odrl:policy ;
        odrl:permission   [ odrl:action  odrl:derive , odrl:distribute , odrl:reproduce ] ;
        odrl:prohibition  []  ;
        odrl:requirement  [ odrl:action  cc:Copyleft , cc:ShareAlike , cc:Attribution , cc:Notice ] .

<http://creativecommons.org/licenses/by/4.0/legalcode>
        a                 odrl:policy ;
        odrl:permission   [ odrl:action  odrl:derive , odrl:distribute , odrl:reproduce ] ;
        odrl:prohibition  []  ;
        odrl:requirement  [ odrl:action  cc:Attribution , cc:Notice ] .

<http://creativecommons.org/licenses/by-nc/4.0/legalcode>
        a                 odrl:policy ;
        odrl:permission   [ odrl:action  odrl:derive , odrl:distribute , odrl:reproduce ] ;
        odrl:prohibition  [ odrl:action  cc:CommercialUse ] ;
        odrl:requirement  [ odrl:action  cc:Attribution , cc:Notice ] .

<http://data.gov.ro/base/images/logoinst/OGL-ROU-1.0.pdf>
        a                 odrl:policy ;
        odrl:permission   [ odrl:action  odrl:derive , odrl:distribute , odrl:reproduce ] ;
        odrl:prohibition  []  ;
        odrl:requirement  [ odrl:action  cc:Attribution ] .

<http://www.nationalarchives.gov.uk/doc/non-commercial-government-licence/non-commercial-government-licence.htm>
        a                 odrl:policy ;
        odrl:permission   [ odrl:action  odrl:derive , odrl:distribute , odrl:reproduce ] ;
        odrl:prohibition  [ odrl:action  cc:CommercialUse ] ;
        odrl:requirement  [ odrl:action  cc:Attribution , cc:Notice ] .

<https://gnu.org/licenses/old-licenses/fdl-1.1>
        a                 odrl:policy ;
        odrl:permission   [ odrl:action  odrl:derive , odrl:distribute , odrl:reproduce ] ;
        odrl:prohibition  []  ;
        odrl:requirement  [ odrl:action  cc:Copyleft , cc:ShareAlike , cc:Attribution , cc:Notice ] .

<http://creativecommons.org/licenses/by/3.0/nl/legalcode>
        a                 odrl:policy ;
        odrl:permission   [ odrl:action  odrl:derive , odrl:distribute , odrl:reproduce ] ;
        odrl:prohibition  []  ;
        odrl:requirement  [ odrl:action  cc:Attribution , cc:Notice ] .

<http://www.dati.gov.it/iodl/2.0/>
        a                 odrl:policy ;
        odrl:permission   [ odrl:action  odrl:derive , odrl:distribute , odrl:reproduce ] ;
        odrl:prohibition  []  ;
        odrl:requirement  [ odrl:action  cc:Attribution , cc:Notice ] .

<https://www.etalab.gouv.fr/licence-ouverte-open-licence>
        a                 odrl:policy ;
        odrl:permission   [ odrl:action  odrl:derive , odrl:distribute , odrl:reproduce ] ;
        odrl:prohibition  []  ;
        odrl:requirement  [ odrl:action  cc:Attribution ] .

<http://opendatacommons.org/licenses/odbl/1.0/>
        a                 odrl:policy ;
        odrl:permission   [ odrl:action  odrl:derive , odrl:distribute , odrl:reproduce ] ;
        odrl:prohibition  []  ;
        odrl:requirement  [ odrl:action  cc:Copyleft , cc:ShareAlike , cc:Attribution , cc:Notice ] .

<http://creativecommons.org/licenses/by-sa/3.0/nl/legalcode>
        a                 odrl:policy ;
        odrl:permission   [ odrl:action  odrl:derive , odrl:distribute , odrl:reproduce ] ;
        odrl:prohibition  []  ;
        odrl:requirement  [ odrl:action  cc:LesserCopyleft , cc:ShareAlike , cc:Attribution , cc:Notice ] .

<http://www.nationalarchives.gov.uk/doc/open-government-licence/version/1/>
        a                 odrl:policy ;
        odrl:permission   [ odrl:action  odrl:derive , odrl:distribute , odrl:reproduce ] ;
        odrl:prohibition  []  ;
        odrl:requirement  [ odrl:action  cc:Attribution , cc:Notice ] .

<http://creativecommons.org/licenses/by-nc-sa/4.0/legalcode>
        a                 odrl:policy ;
        odrl:permission   [ odrl:action  odrl:derive , odrl:distribute , odrl:reproduce ] ;
        odrl:prohibition  [ odrl:action  cc:CommercialUse ] ;
        odrl:requirement  [ odrl:action  cc:Copyleft , cc:ShareAlike , cc:Attribution , cc:Notice ] .

<https://joinup.ec.europa.eu/software/page/eupl/licence-eupl>
        a                 odrl:policy ;
        odrl:permission   [ odrl:action  odrl:derive , odrl:distribute , odrl:reproduce ] ;
        odrl:prohibition  []  ;
        odrl:requirement  [ odrl:action  cc:Copyleft , cc:ShareAlike , cc:Attribution , cc:Notice ] .

<http://creativecommons.org/licenses/by-sa/4.0/legalcode>
        a                 odrl:policy ;
        odrl:permission   [ odrl:action  odrl:derive , odrl:distribute , odrl:reproduce ] ;
        odrl:prohibition  []  ;
        odrl:requirement  [ odrl:action  cc:Copyleft , cc:ShareAlike , cc:Attribution , cc:Notice ] .

<http://www.nationalarchives.gov.uk/doc/open-government-licence/version/3/>
        a                 odrl:policy ;
        odrl:permission   [ odrl:action  odrl:derive , odrl:distribute , odrl:reproduce ] ;
        odrl:prohibition  []  ;
        odrl:requirement  [ odrl:action  cc:Attribution , cc:Notice ] .

<http://creativecommons.org/publicdomain/mark/1.0/>
        a                 odrl:policy ;
        odrl:permission   [ odrl:action  odrl:derive , odrl:distribute , odrl:reproduce ] ;
        odrl:prohibition  []  ;
        odrl:requirement  []  .

<http://opendatacommons.org/licenses/pddl/1-0/>
        a                 odrl:policy ;
        odrl:permission   [ odrl:action  odrl:derive , odrl:distribute , odrl:reproduce ] ;
        odrl:prohibition  []  ;
        odrl:requirement  []  .
```


## Notes

* The data is based on the [European Data Portal Licence Compatibility Matrix](https://www.europeandataportal.eu/en/content/licence-assistant-european-data-portal-licence-compatibility-matrix)
* The used vocabulary is the [Open Digital Rights Language (ODRL)](https://www.w3.org/TR/odrl-model/)

## Credits

[Data Science Group (DICE)](https://dice-research.org/) at [Paderborn University](https://www.uni-paderborn.de/)

This work has been supported by the German Federal Ministry of Transport and Digital Infrastructure (BMVI) in the project [Open Data Portal Germany (OPAL)](http://projekt-opal.de/) (funding code 19F2028A).