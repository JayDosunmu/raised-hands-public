package com.sweteamdragon.raisedhandsserver.session.model;

import com.sweteamdragon.raisedhandsserver.auth.model.Account;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.Date;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = { "session" })
@Getter
@Setter
public class SessionParticipant {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long sessionParticipantId;

    @ManyToOne
    private Account account;

    @ManyToOne(fetch = FetchType.LAZY)
    private Session session;

    private boolean leader;

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    private Date joinTimestamp;

    public SessionParticipant(Account account, boolean leader) {
        this.account = account;
        this.leader = leader;
    }

    public SessionParticipant(Account account) {
        this.account = account;
        this.leader = false;
    }
}
