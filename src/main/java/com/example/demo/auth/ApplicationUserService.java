package com.example.demo.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
//UserDetailsDervice's job is to fetch data from any datasource
public class ApplicationUserService implements UserDetailsService {

    private final ApplicationUserDao applicationUserDao;
    @Autowired
    public ApplicationUserService(@Qualifier("fake") ApplicationUserDao applicationUserDao) {
        //to know what implementation we use
        this.applicationUserDao = applicationUserDao;
    }


    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        return applicationUserDao.selectApplicationUserByUsername(s).orElseThrow(()->new UsernameNotFoundException("User with username"+s+" isn't found."));
    }



}
