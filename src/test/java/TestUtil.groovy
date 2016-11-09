import com.rp.Wave
import org.codehaus.groovy.control.CompilerConfiguration
import org.codehaus.groovy.tools.shell.Groovysh

import static water.H2OStarter.start

public class TestUtil {

    static Groovysh TEST_SHELL;
    public static void launchShell(String[] args) {
        start(args, true);
        CompilerConfiguration.DEFAULT.setBytecodePostprocessor(new Wave.ByteCodePostLoad());
        TEST_SHELL=new Groovysh()
    }
    public static void launchShell() { launchShell(new String[0]); }
}