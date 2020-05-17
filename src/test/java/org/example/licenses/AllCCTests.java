package org.example.licenses;

import org.example.licenses.cc.CcBackMappingTest;
import org.example.licenses.cc.CcDataTest;
import org.example.licenses.cc.CcEvaluationSingleTest;
import org.example.licenses.cc.CcEvaluationTest;
import org.example.licenses.cc.ExecutionTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({

		CcDataTest.class,

		CcBackMappingTest.class,

		CcEvaluationSingleTest.class,

		CcEvaluationTest.class,

		ExecutionTest.class,

})

public class AllCCTests {
}