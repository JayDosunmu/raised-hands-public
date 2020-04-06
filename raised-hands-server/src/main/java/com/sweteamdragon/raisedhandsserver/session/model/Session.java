package com.sweteamdragon.raisedhandsserver.session.model;

import com.sweteamdragon.raisedhandsserver.session.util.SessionUtil;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Setter
public class Session {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long sessionId;

    @NotEmpty
    private String name;

    @NotEmpty
    private String joinId;

    private String passcode;

    @OneToOne(cascade = CascadeType.ALL)
    private SessionParticipant leader;

    @OneToMany(mappedBy = "session", fetch = FetchType.LAZY,  cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<SessionParticipant> participants;

    private boolean active;

    private boolean distractionFree;

    private Date startDate;

    private Date endDate;

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    private Date createTimestamp;

    public Session(String name, boolean distractionFree, Date startDate, Date endDate) {
        this.name = name;
        this.distractionFree = distractionFree;
        this.startDate = startDate;
        this.endDate = endDate;

        this.joinId = SessionUtil.generateSessionId();
        this.passcode = SessionUtil.generateSessionPasscode();

        this.participants = new HashSet<>();

        this.active = startDate == null ? false : new Date().compareTo( startDate) >= 0;
    }

    public void setLeader(SessionParticipant leader) {
        this.leader = leader;
        this.addParticipant(leader);
    }

    public void addParticipant(SessionParticipant participant) {
        this.participants.add(participant);
        participant.setSession(this);
    }
}
