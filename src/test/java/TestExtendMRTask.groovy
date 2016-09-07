import org.junit.BeforeClass
import org.junit.Test

class TestExtendMRTask extends TestUtil {

    @BeforeClass public static void init() { launchShell(new String[0]); }

    @Test public void testNewTask() {
        TEST_SHELL.execute("import water.MRTask");
        TEST_SHELL.execute("import water.fvec.Vec");
        TEST_SHELL.execute("import water.fvec.Chunk");
        TEST_SHELL.execute("v = Vec.makeZero(1000)");
        TEST_SHELL.execute("class A extends MRTask{\n" +
                "  double s=0\n" +
                "  void map(Chunk c) {\n" +
                "    for(int i=0; i<c._len; ++i)\n" +
                "      s += 1\n" +
                "  }\n" +
                "  void reduce(MRTask t) {\n" +
                "    s += t.s\n" +
                "  \n}" +
                "}\n");
        TEST_SHELL.execute("println 1000 == new A().doAll(v).s")
        TEST_SHELL.execute("v.remove()")
    }


//    class A extends MRTask {
//        double s=0
//        void map(Chunk c) {
//            for(int i=0;i<c._len;++i) {
//                s += 2
//            }
//        }
//        void reduce(MRTask t) {
//            println "hola from node: "
//            println H2O.SELF.index()
//            s += t.s
//        }
//    }

}
