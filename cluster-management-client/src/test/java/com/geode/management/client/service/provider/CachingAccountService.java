package com.geode.management.client.service.provider;


import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.geode.management.client.dao.AccountDao;
import com.geode.management.client.service.AccountService;
import com.geode.management.domain.Account;

/**
 * The CachingAccountsService class is an implementation of the AccountService interface implementing business functions
 * and rules for processing VOYA Accounts.  This implementation supports caching Account information as well as the
 * capability to fetch Account details from an external data source(s).
 *
 * @author jb
 * @see org.springframework.stereotype.Service
 * @see com.geode.management.domain.Account
 * @see com.geode.management.client.dao.AccountDao
 * @see com.geode.management.client.service.AccountService
 */
@Service("accountService")
public class CachingAccountService implements AccountService {

//  protected final Logger log = Logger.getLogger(getClass().getName());
  protected final Logger log = LoggerFactory.getLogger(getClass().getName());

  @Autowired
  private AccountDao accountDao;

  @PostConstruct
  public void init() {
    getAccountDao();
    log.info(String.format("%1$s initialized!", getClass().getSimpleName()));
  }

  protected AccountDao getAccountDao() {
    Assert.state(accountDao != null, "A reference to the AccountDao was not properly configured and initialized!");
    return accountDao;
  }

  @Cacheable(value = "Account")
  public Account getAccount(final Long accountId) {
    log.info(String.format("%1$s.get(%2$s): getting Account with ID (%2$s) from external data source!",
      getClass().getSimpleName(), accountId));
    return getAccountDao().load(accountId);
  }

}
