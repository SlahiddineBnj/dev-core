package com.rest.core.model;


import com.rest.core.util.Auditable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Role extends Auditable {
 @Id
 @GeneratedValue(strategy = GenerationType.IDENTITY)
 private Long id ;

 @Column(unique = true)
 private String name ;



}
