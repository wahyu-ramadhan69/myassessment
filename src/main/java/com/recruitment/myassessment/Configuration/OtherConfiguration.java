package com.recruitment.myassessment.Configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:other.properties")
public class OtherConfiguration {

    private static String flagLoging;
    private static String flagLogTable;

    private static String flagSmtpActive;

    private static String flagPwdTrap;

    public static String getFlagPwdTrap() {
        return flagPwdTrap;
    }

    @Value("${flag.pwd.trap}")
    private void setFlagPwdTrap(String flagPwdTrap) {
        OtherConfiguration.flagPwdTrap = flagPwdTrap;
    }

    public static String getFlagSmtpActive() {
        return flagSmtpActive;
    }

    @Value("${flag.smtp.active}")
    private void setFlagSmtpActive(String flagSmtpActive) {
        OtherConfiguration.flagSmtpActive = flagSmtpActive;
    }

    public static String getFlagLogTable() {
        return flagLogTable;
    }

    @Value("${flag.log.table}")
    private void setFlagLogTable(String flagLogTable) {
        OtherConfiguration.flagLogTable = flagLogTable;
    }

    public static String getFlagLoging() {
        return flagLoging;
    }

    @Value("${flag.loging}")
    private void setFlagLoging(String flagLoging) {
        OtherConfiguration.flagLoging = flagLoging;
    }
}