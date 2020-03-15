package com.sweteamdragon.raisedhandsserver.auth.repositories;

import com.sweteamdragon.raisedhandsserver.auth.models.Account;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends CrudRepository<Account, Long> {

    Account findByEmail(String email);
}
