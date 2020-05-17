package org.example.licenses;

import org.example.licenses.production.AttributeTest;
import org.example.licenses.production.EdpLcmKnowledgeBaseTest;
import org.example.licenses.production.EpdLcmDerivatesTest;
import org.example.licenses.production.OperatorTest;
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