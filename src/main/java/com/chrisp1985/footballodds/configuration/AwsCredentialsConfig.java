package com.chrisp1985.footballodds.configuration;

import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.EnvironmentVariableCredentialsProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AwsCredentialsConfig {

    @Bean
    public AWSCredentialsProvider getAwsCredentials() {
            return new EnvironmentVariableCredentialsProvider();

    }
}
