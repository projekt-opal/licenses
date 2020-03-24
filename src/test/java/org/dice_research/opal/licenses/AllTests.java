package org.dice_research.opal.licenses;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({

		AttributeTest.class,

		OperatorTest.class,

		CcDataTest.class,

		CcBackMappingTest.class,

		CcEvaluationSingleTest.class,

		CcEvaluationTest.class,

		EdpLcmKnowledgeBaseTest.class,

		EpdLcmDerivatesTest.class,

})

public class AllTests {
}