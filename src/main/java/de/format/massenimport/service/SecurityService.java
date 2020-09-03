package de.format.massenimport.service;

public interface SecurityService {

    String findLoggedInUsername();

    void autoLogin(String username, String password);
}
