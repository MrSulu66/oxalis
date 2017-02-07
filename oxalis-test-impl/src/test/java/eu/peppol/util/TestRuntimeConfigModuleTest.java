/*
 * Copyright 2010-2017 Norwegian Agency for Public Management and eGovernment (Difi)
 *
 * Licensed under the EUPL, Version 1.1 or – as soon they
 * will be approved by the European Commission - subsequent
 * versions of the EUPL (the "Licence");
 *
 * You may not use this work except in compliance with the Licence.
 *
 * You may obtain a copy of the Licence at:
 *
 * https://joinup.ec.europa.eu/community/eupl/og_page/eupl
 *
 * Unless required by applicable law or agreed to in
 * writing, software distributed under the Licence is
 * distributed on an "AS IS" basis,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either
 * express or implied.
 * See the Licence for the specific language governing
 * permissions and limitations under the Licence.
 */

package eu.peppol.util;

import com.google.inject.*;
import no.difi.oxalis.api.config.GlobalConfiguration;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;

/**
 * Verifies that we can obtain an instance of {@link GlobalConfiguration} implemented by
 * {@link UnitTestGlobalConfigurationImpl} from a Guice module created on the fly.
 *
 * @author steinar
 *         Date: 12.12.2015
 *         Time: 00.53
 */
public class TestRuntimeConfigModuleTest {

    @Test
    public void verifySingleton() {
        Injector injector = Guice.createInjector(new TestModule());
        TestClass testClass = injector.getInstance(TestClass.class);

        assertNotNull(testClass);
        assertNotNull(testClass.getConfiguration1());
        assertNotNull(testClass.getConfiguration2());
        assertEquals(testClass.getConfiguration1(), testClass.getConfiguration2(), "They should be equal");

        TestClass instance2 = injector.getInstance(TestClass.class);

        assertEquals(instance2.getConfiguration1(), testClass.getConfiguration1(), "Singleton is not working");
    }

    public static class TestModule extends AbstractModule {
        @Override
        protected void configure() {
            bind(TestClass.class);
        }

        @Provides
        @Singleton
        GlobalConfiguration provideGlobalConfiguration() {
            GlobalConfiguration configuration = UnitTestGlobalConfigurationImpl.createInstance();
            return configuration;
        }
    }


    public static class TestClass {
        @Inject
        GlobalConfiguration configuration1;

        @Inject
        GlobalConfiguration configuration2;

        public GlobalConfiguration getConfiguration1() {
            return configuration1;
        }

        public GlobalConfiguration getConfiguration2() {
            return configuration2;
        }
    }
}
