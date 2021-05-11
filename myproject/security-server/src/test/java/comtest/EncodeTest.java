package comtest;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.io.IOException;


public class EncodeTest extends BaseAppManager {
    private static final Logger log = LoggerFactory.getLogger(EncodeTest.class);
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Test
    public void one() {
        String a = passwordEncoder.encode("admin");
        System.out.println("==========================");
        System.out.println(a);
    }
    @Test
    public void two() {

    }

    @Test
    public void three() throws IOException {

    }
    /**
     * list
     * @throws IOException
     */
    @Test
    public void four() throws IOException {

    }
    /**
     * set
     * @throws IOException
     */
    @Test
    public void five() throws IOException {

    }

    /**
     * hash
     * @throws IOException
     */
    @Test
    public void six() throws IOException {

    }


}
