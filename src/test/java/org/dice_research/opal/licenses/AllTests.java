package org.dice_research.opal.licenses;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({

		AttributeTest.class,

		OperatorTest.class,

		CcEvaluationTest.class,

		EpdLcmDerivatesTest.class,

		EdpLcmKnowledgeBaseTest.class, })

public class AllTests {
}