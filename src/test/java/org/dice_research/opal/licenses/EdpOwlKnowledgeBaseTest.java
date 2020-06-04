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

		License l = kb.getLicense("http://europeandataportal.eu/ontologies/od-licenses#OGL2.0");
		//System.out.println(kb.toLines());
		System.out.println(l.getAttributes());
	}

}
