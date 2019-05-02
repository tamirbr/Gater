package online.gater.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class GateUser extends BaseEntity {
    private int sn;
    private String phoneNumber;
    private String name;
    @ManyToOne
    private Gate gate;
}
