package com.aushev.autoriasearch.service;

import com.aushev.autoriasearch.model.Config;

public interface MailService {

    void setMailingTime(Config config);

    void saveMailingTime(Config config);

    void deactivateMailing(int searchId, int userId);

    void activateMailing(int searchId, int userId);
}
