package org.dice_research.opal.licenses;

import org.dice_research.opal.licenses.edplcm.EdpOwlKnowledgeBase;
import org.junit.Assert;
import org.junit.Test;

/**
 * Tests {@link Attribute}.
 *
 */
public class EdpOwlKnowledgeBaseTest {

	@Test
	public void test() {
		EdpOwlKnowledgeBase kb = new EdpOwlKnowledgeBase();
		
		System.out.println(kb.toLines());
	}

}