package teaselib.scripts.mine;

import java.io.File;

import org.junit.Test;

import teaselib.core.TeaseLib;
import teaselib.hosts.DummyHost;
import teaselib.hosts.DummyPersistence;

public class MineMaidTest {

    @Test
    public void testActivity() {
        TeaseLib teaseLib = new TeaseLib(new DummyHost(), new DummyPersistence());
        Mine mine = new Mine(teaseLib, new File("../SexScripts/scripts/"));

        mine.play("mine-maid");
    }

}
