package com.myprojects.domain;

import com.myprojects.webapp.TestConfiguration;

public class MyUseCaseImpl implements MyUseCase {

    private TestConfiguration testConfiguration;

    public MyUseCaseImpl(
            TestConfiguration testConfiguration
    ) {
        this.testConfiguration = testConfiguration;
    }

    @Override
    public void operation() {
        System.out.println("My url is: "+testConfiguration.url);
    }
}
