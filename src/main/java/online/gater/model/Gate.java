package online.gater.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Gate extends BaseEntity {
    @NotNull
    private int number;
    @NotNull
    private int name;
    @NotNull
    private int password;
    @NotNull
    private boolean authorizedOnly;
    @NotNull
    private int relay;
    @ManyToMany
    private List<User> users  = new ArrayList<>();
    @OneToMany(mappedBy = "gate", cascade= CascadeType.ALL)
    private List<GateUser> gateUsers = new ArrayList<>();
}
