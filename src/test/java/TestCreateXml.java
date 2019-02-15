
import org.testng.Assert;
import org.testng.annotations.Test;
import eventhub.producer.CreateXml;

public class TestCreateXml {

    @Test
    public void testXmlCreation(){
      String expectedXml="<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?><mortgage><home type=\"RowHouse\"><contract type=\"annuity\">Fixed interest For 10 Years</contract><contract type=\"30 Years\">Fixed interest throughout</contract></home></mortgage>";
      String actualXml = new CreateXml().createXml();
      Assert.assertEquals(actualXml, expectedXml);
    }
}