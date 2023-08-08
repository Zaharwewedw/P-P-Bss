package com.bank.authorization.audit;

import com.bank.authorization.dto.UserDTO;
import com.bank.authorization.model.Users;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;
import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "audit", schema = "auth")
@Getter
@Setter
public class AuditUser {

    @Column(name = "entity_type")
    private String entityType;
    @Column(name = "operation_type")
    private String operationType;
    @Column(name = "created_by")
    private String createdBy;
    @Column(name = "modified_by")
    private String modifiedBy;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "created_at")
    private Timestamp createdAtImage;
    @Column(name = "modified_at")
    private Timestamp modifiedAtImage;
    public void createdAt () {
        Date date = new Date();
        createdAtImage = new Timestamp(date.getTime());
    }

    public void modifiedAt () {
        Date date = new Date();
        modifiedAtImage = new Timestamp(date.getTime());
    }
    @Column(name = "new_entity_json")
    private String newEntityJsonImage;
    @Column(name = "entity_json")
    private String entityJsonImage;
    public void newEntityJson (UserDTO us) {
        newEntityJsonImage = "{" +
                "  \"role\":" + us.getRole() +
                "  \"profileId\":" + us.getProfileId()+
                "  \"password\":" + us.getPassword() +
                "}";
    }

    public void entityJson (Users us) {
        entityJsonImage = "{" +
                "  \"role\":" + us.getRole() +
                "  \"profileId\":" + us.getProfileId()+
                "  \"password\":" + us.getPassword() +
                "}";
    }

    @Override
    public String toString() {
        return  this.operationType + " " + this.createdAtImage + " " + this.modifiedAtImage
                + " " + this.entityType  + " " + this.modifiedBy + " " +
                this.createdBy;
    }
}
