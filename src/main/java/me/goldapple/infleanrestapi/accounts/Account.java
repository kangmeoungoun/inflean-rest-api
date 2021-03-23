package me.goldapple.infleanrestapi.accounts;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@EqualsAndHashCode(of = "id")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Account{
    @Id
    @GeneratedValue
    private Integer id;
    @Column(unique = true)
    private String email;
    @JsonIgnore
    private String password;

    @Enumerated(EnumType.STRING)
    @ElementCollection(fetch = FetchType.EAGER)
    private Set<AccountRole> roles = new HashSet<>();


}
