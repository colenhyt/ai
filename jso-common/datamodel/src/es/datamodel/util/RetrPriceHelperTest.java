/*
 * Created on 2006-3-26
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package es.datamodel.util;

import junit.framework.TestCase;

/**
 * Tracy&Allen.EasyShop 0.9
 *
 * @author Allenhuang
 * 
 * created on 2006-3-26
 */
public class RetrPriceHelperTest extends TestCase {

    public void testRetrNumeric() {
        String str="123";
        Double ps=new Double(123.0);
        assertTrue(RetrPriceHelper.retrNumeric(str)==new Double(123.0).floatValue());
        str="39.39";
        assertTrue(RetrPriceHelper.retrNumeric(str)==new Double(39.39).floatValue());
        str="aa111";
        assertTrue(RetrPriceHelper.retrNumeric(str)==new Double(111).floatValue());
        str="11aaa";
        assertTrue(RetrPriceHelper.retrNumeric(str)==new Double(11).floatValue());
        str="  111";
        assertTrue(RetrPriceHelper.retrNumeric(str)==new Double(111).floatValue());
        str="  22  ";
        assertTrue(RetrPriceHelper.retrNumeric(str)==new Double(22).floatValue());
        str="456  ";
        assertTrue(RetrPriceHelper.retrNumeric(str)==new Double(456).floatValue());
        str="11.  33";
        assertTrue(RetrPriceHelper.retrNumeric(str)==new Double(0).floatValue());
        str="11.aaa  33";
        assertTrue(RetrPriceHelper.retrNumeric(str)==new Double(0).floatValue());
        str="ee444rr";
        assertTrue(RetrPriceHelper.retrNumeric(str)==new Double(444).floatValue());
        str="ee444.rr";
        assertTrue(RetrPriceHelper.retrNumeric(str)==new Double(444).floatValue());
        str="12.37die";
        assertTrue(RetrPriceHelper.retrNumeric(str)==new Double(444).floatValue());
    }

}
