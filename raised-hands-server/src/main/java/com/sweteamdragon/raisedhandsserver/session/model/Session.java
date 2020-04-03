package com.sweteamdragon.raisedhandsserver.session.model;

import com.sweteamdragon.raisedhandsserver.session.util.SessionUtil;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Session {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long sessionId;

    @NotEmpty
    private String joinId;

    @NotEmpty
    private String name;

    private String passcode;

    @OneToOne(optional = false)
    private SessionParticipant leader;

    @OneToMany(mappedBy = "session")
    private Set<SessionParticipant> participants;

    private boolean active;

    private boolean distractionFree;

    private Date startDate;

    private Date endDate;

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
}
