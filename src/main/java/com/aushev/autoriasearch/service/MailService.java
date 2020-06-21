package com.aushev.autoriasearch.service;

import com.aushev.autoriasearch.model.Config;

public interface MailService {

    void setMailingTime(Config config);

    void saveMailingTime(Config config);
}
