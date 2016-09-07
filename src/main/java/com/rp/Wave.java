package com.rp;


import org.codehaus.groovy.control.BytecodeProcessor;
import org.codehaus.groovy.control.CompilerConfiguration;
import org.codehaus.groovy.tools.shell.Groovysh;
import water.H2OStarter;
import water.Weaver;

public class Wave extends H2OStarter {
  public static Groovysh SHELL;
  public static void main(String[] args) {
    start(args, true);
    CompilerConfiguration.DEFAULT.setBytecodePostprocessor(new ByteCodePostLoad());
    (SHELL=new Groovysh()).run("");
  }

  public static class ByteCodePostLoad implements BytecodeProcessor {
    @Override public byte[] processBytecode(String name, byte[] original) {
      if (!name.startsWith("groovysh_evaluate") && !name.startsWith("script"))
        Weaver.loadDynamic(name,original);
      return original;
    }
  }
}
