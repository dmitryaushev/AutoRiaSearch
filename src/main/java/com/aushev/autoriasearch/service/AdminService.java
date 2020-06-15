package com.aushev.autoriasearch.service;

import com.aushev.autoriasearch.model.Config;

public interface AdminService {

    void sendEmail(String subject, String text, String... to);
    void mailing();
    void setMailingTime(Config config);
    void saveMailingTime(Config config);
}
