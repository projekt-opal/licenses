package org.dice_research.opal.licenses;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({

		AttributeTest.class,

		OperatorTest.class,

		ExecutionTest.class,

		CcDataTest.class,

		CcBackMappingTest.class,

		CcEvaluationSingleTest.class,

		CcEvaluationTest.class,

		EdpLcmKnowledgeBaseTest.class,

		EpdLcmDerivatesTest.class,

})

/**
 * Contains alls tests except of @link{EdpLcmEvaluationTest}.
 * 
 * @author Adrian Wilke
 */
public class AllProductionTests {
}