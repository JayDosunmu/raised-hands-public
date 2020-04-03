package com.sweteamdragon.raisedhandsserver.session.model;

import com.sweteamdragon.raisedhandsserver.auth.model.Account;
import lombok.*;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SessionParticipant {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long sessionParticipantId;

    @ManyToOne
    @JoinColumn(name = "account_id")
    private Account account;

    @NonNull
    @ManyToOne(optional = false)
    private Session session;

    private boolean leader;

    @Temporal(TemporalType.TIMESTAMP)
    private Date joinTimestamp;
}
