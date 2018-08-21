package cn.baopz.utils;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RegularTest {
    private static Logger logger = LoggerFactory.getLogger(RegularTest.class);
    @Test
    public void regularTest(){
       String text =  "1\\2/3:4*5?6\"7<8>9|10".replaceAll("[\\ \\\\/:\\\\*\\\\? \"<> |]","-");
        logger.debug("text,{}",text);
    }
}
