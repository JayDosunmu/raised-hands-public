package com.sweteamdragon.raisedhandsserver.auth.repository;

import com.sweteamdragon.raisedhandsserver.auth.model.Account;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends CrudRepository<Account, Long> {

    Account findByEmail(String email);
}
