/*
 * Copyright (c) 2010 - 2015 Norwegian Agency for Pupblic Government and eGovernment (Difi)
 *
 * This file is part of Oxalis.
 *
 * Licensed under the EUPL, Version 1.1 or – as soon they will be approved by the European Commission
 * - subsequent versions of the EUPL (the "Licence"); You may not use this work except in compliance with the Licence.
 *
 * You may obtain a copy of the Licence at:
 *
 * https://joinup.ec.europa.eu/software/page/eupl5
 *
 *  Unless required by applicable law or agreed to in writing, software distributed under the Licence
 *  is distributed on an "AS IS" basis,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the Licence for the specific language governing permissions and limitations under the Licence.
 *
 */

package eu.peppol.util;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Scope;
import com.google.inject.Singleton;
import eu.peppol.persistence.MessageRepository;
import eu.peppol.persistence.MessageRepositoryFactory;
import eu.peppol.security.KeystoreManager;
import eu.peppol.security.KeystoreManagerImpl;
import eu.peppol.service.LookupService;
import eu.peppol.smp.*;
import eu.peppol.statistics.RawStatisticsRepository;
import eu.peppol.statistics.RawStatisticsRepositoryFactory;
import eu.peppol.statistics.RawStatisticsRepositoryFactoryProvider;

/**
 * @author steinar
 *         Date: 09.12.2015
 *         Time: 15.02
 */
public class RuntimeConfigurationModule extends AbstractModule {

    @Override
    protected void configure() {

        bind(GlobalConfiguration.class).to(GlobalConfigurationImpl.class).in(Singleton.class);

        bind(KeystoreManager.class).to(KeystoreManagerImpl.class).in(Singleton.class);

        // SMP module
        new SmpModule().configure(binder());
    }

    @Provides
    @Singleton
    MessageRepository provideMessageRepository(MessageRepositoryFactory messageRepositoryFactory) {

        MessageRepository instance = messageRepositoryFactory.getInstanceWithDefault();
        return instance;
    }

    @Provides @Singleton
    RawStatisticsRepository provideStatisticsRepository() {
        RawStatisticsRepositoryFactory instance = RawStatisticsRepositoryFactoryProvider.getInstance();

        return instance.getInstanceForRawStatistics();
    }

}