package water.rp;

import water.H2O;
import water.api.AbstractRegister;

import java.io.File;

public class Register extends AbstractRegister{
  @Override public void register(String relativeResourcePath) throws ClassNotFoundException {
    H2O.registerResourceRoot(new File("wave"));
  }
}
