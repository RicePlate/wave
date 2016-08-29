package com.rp;


import javassist.ByteArrayClassPath;
import javassist.CannotCompileException;
import javassist.ClassPool;
import javassist.NotFoundException;
import org.codehaus.groovy.control.BytecodeProcessor;
import org.codehaus.groovy.control.CompilerConfiguration;
import org.codehaus.groovy.tools.shell.Groovysh;
import water.H2OStarter;
import water.MRTask;

public class Wave extends H2OStarter {

  public static void main(String[] args) {
    start(args, true);
    CompilerConfiguration.DEFAULT.setBytecodePostprocessor(new ByteCodePostLoad());
    new Groovysh().run("");
  }

  private static class ByteCodePostLoad implements BytecodeProcessor {
    @Override public byte[] processBytecode(String name, byte[] original) {
      if (!name.startsWith("groovysh_evaluate") && !name.startsWith("script"))
        broadCast(name,original);
      return original;
    }
  }

  private static void broadCast(final String name, final byte[] b) {  // broadcast the bytecode to the cluster for injection on each node
//    ClassPool cp = ClassPool.getDefault();  // TODO: use the pool from Weaver directly
//    cp.insertClassPath(new ByteArrayClassPath(name, b));
    new MRTask() {
      @Override public void setupLocal() {
        System.out.println("Loading class " + name);
        ClassPool cp = ClassPool.getDefault();  // TODO: use the pool from Weaver directly
        cp.insertClassPath(new ByteArrayClassPath(name, b));
        try {
          cp.get(name).toClass();
        } catch (CannotCompileException | NotFoundException e) {
          e.printStackTrace();
        }
      }
    }.doAllNodes();
  }
}
