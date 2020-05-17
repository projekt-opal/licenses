package org.dice_research.opal.licenses;

import org.dice_research.opal.licenses.production.AttributeTest;
import org.dice_research.opal.licenses.production.EdpLcmKnowledgeBaseTest;
import org.dice_research.opal.licenses.production.EpdLcmDerivatesTest;
import org.dice_research.opal.licenses.production.OperatorTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({

		AttributeTest.class,

		OperatorTest.class,

		EdpLcmKnowledgeBaseTest.class,

		EpdLcmDerivatesTest.class,

})

public class AllProductionTests {
}