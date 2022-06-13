package transferencias.suite;

import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;
import transferencias.cases.TransferenciasCasosCorrectosTests;
import transferencias.cases.TransferenciasCasosDeErrorTests;

@Suite
@SelectClasses({
        TransferenciasCasosCorrectosTests.class,
        TransferenciasCasosDeErrorTests.class
})
public class TransferenciasTestsSuite {}
    