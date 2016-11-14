package water.rp;


import water.AbstractH2OExtension;
import water.H2O;
import water.init.AbstractBuildVersion;

public class WaveExtension extends AbstractH2OExtension {
  public static final AbstractBuildVersion WAVE_ABV;
  static {
    AbstractBuildVersion abv = AbstractBuildVersion.UNKNOWN_VERSION;
    try {
      abv= (AbstractBuildVersion) Class.forName("water.init.WaveBuildVersion").getConstructor().newInstance();
    } catch (Exception ignore) { }
    WAVE_ABV = abv;
  }
  @Override public void init() { H2O.setJetty(new WaveJetty()); }
  @Override public String getExtensionName() { return "Wave"; }
  @Override public AbstractBuildVersion getBuildVersion() { return WAVE_ABV; }
}
