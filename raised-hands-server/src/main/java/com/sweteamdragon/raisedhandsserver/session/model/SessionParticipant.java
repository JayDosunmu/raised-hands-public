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
@EqualsAndHashCode(of="sessionParticipantId")
public class SessionParticipant {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long sessionParticipantId;

    @ManyToOne(optional = false, cascade = CascadeType.ALL)
    private Account account;

    @NonNull
    @ManyToOne(optional = false, fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Session session;

    private boolean leader;

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    private Date joinTimestamp;

    public SessionParticipant(Account account, boolean leader) {
        this.account = account;
        this.leader = leader;
    }
}
