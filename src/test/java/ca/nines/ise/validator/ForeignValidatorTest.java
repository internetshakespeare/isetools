package ca.nines.ise.validator;

import org.junit.Test;

public class ForeignValidatorTest extends ValidatorTestBase{
  
  ForeignValidator validator;
  
  public ForeignValidatorTest() throws Exception {
    validator = new ForeignValidator();
  }
  
  /**
   * Tests if @lang is a valid 3 letter code
   */
  @Test
  public void testAcceptPrivateUse() throws Exception {
    setUp();
    validator.validate(getDOM("<WORK><FOREIGN lang=\"y-nonsense\">x</FOREIGN></WORK>"));
    checkLog(new String[]{"validator.foreign.invalidCode"});
    
    setUp();
    validator.validate(getDOM("<WORK><FOREIGN lang=\"x-nonsense\">x</FOREIGN></WORK>"));
    checkLog();
  }

  /**
   * Tests if @lang is a valid 3 letter code
   */
  @Test
  public void TestRefinementFlag() throws Exception {
    setUp();
    validator.validate(getDOM("<WORK><FOREIGN lang=\"xyz-US\">x</FOREIGN></WORK>"));
    checkLog(new String[]{"validator.foreign.invalidCode"});
    
    setUp();
    validator.validate(getDOM("<WORK><FOREIGN lang=\"en-US\">x</FOREIGN></WORK>"));
    checkLog();
  }
  
  /**
   * Tests if @lang is a valid 3 letter code
   */
  @Test
  public void testValidLanguage() throws Exception {
    setUp();
    validator.validate(getDOM("<WORK><FOREIGN lang=\"xyz\">x</FOREIGN></WORK>"));
    checkLog(new String[]{"validator.foreign.invalidCode"});
    
    setUp();
    validator.validate(getDOM("<WORK><FOREIGN lang=\"aaa\">x</FOREIGN></WORK>"));
    checkLog();
  }

  /**
   * Tests if @lang is a valid 2 letter code
   */
  @Test
  public void testTwoLetterCode() throws Exception {
    setUp();
    validator.validate(getDOM("<WORK><FOREIGN lang=\"xx\">x</FOREIGN></WORK>"));
    checkLog(new String[]{"validator.foreign.invalidCode"});
    
    setUp();
    validator.validate(getDOM("<WORK><FOREIGN lang=\"aa\">x</FOREIGN></WORK>"));
    checkLog();
  }
  
  /**
   * Tests if @lang attribute exists
   */
  @Test
  public void testLanguageExists() throws Exception {
    setUp();
    validator.validate(getDOM("<WORK><FOREIGN>x</FOREIGN></WORK>"));
    checkLog(new String[]{"validator.foreign.missingCode"});
  }

}
