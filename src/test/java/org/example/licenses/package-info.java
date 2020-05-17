/**
 * The tests are sub-divided in 3 groups:
 * 
 * <p>
 * (1) Production test: Default tests.
 * </p>
 * 
 * <p>
 * (2) CC tests: These tests require data of an additional repository. The
 * directory to use can be set by the system property 'cc.licenserdf' (VM
 * arguments). For instance:
 * <code>-Dcc.licenserdf=../cc.licenserdf/cc/licenserdf/licenses/</code>
 * </p>
 * 
 * <p>
 * (3) EDP evaluation tests: As the experiments do not create a perfect
 * F1-Score, these tests are disabled by default. To enable the tests, set the
 * system property 'run.edp.lcm.tests' to true (VM arguments). For instance:
 * <code>-Drun.edp.lcm.tests=true</code>
 * </p>
 * 
 * <p>
 * See also the main README.md file.
 * </p>
 * 
 * @see org.example.utils.Cfg
 * 
 * @author Adrian Wilke
 */
package org.example.licenses;