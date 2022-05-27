package com.rest.core.model;

import com.rest.core.util.Auditable;
import lombok.*;

import javax.persistence.*;

@Entity
@Table
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class AccountVerificationCode extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id ;

    private String verification_code ;

    @OneToOne
    private AppUser user ;

}
