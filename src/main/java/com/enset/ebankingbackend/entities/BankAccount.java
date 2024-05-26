package com.enset.ebankingbackend.entities;

import com.enset.ebankingbackend.enums.AccountStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE) //SI JE VEUX UTILISER JOINED OU TABLE_PER_CLASS, je ne doit pas ajouter @DiscriminatorValue("CC") dans les class dérivé et @DiscriminatorColumn(name = "TYPE", length = 4, discriminatorType = DiscriminatorType.STRING) ne doit pas être utilisé dans cette class
@DiscriminatorColumn(name = "TYPE", length = 4, discriminatorType = DiscriminatorType.STRING) //pour @Inheritance(strategy = InheritanceType.SINGLE_TABLE) lorsque c'est SINGLE_TABLE la class ne doit pas être abstraite
@Data @NoArgsConstructor @AllArgsConstructor
public abstract class BankAccount {
    //@TODO 1.pourquoi choisir de prendre l'id des comptes bancaires en string au lieu de prendre les opérations puisqu'ont est sur qu'il y aura plus d'opérations que de comptes
    @Id
    private String id;
    private double balance;
    private Date createdAt;
    @Enumerated(EnumType.STRING)
    private AccountStatus status;
    @ManyToOne
    private Customer customer;
    @OneToMany(mappedBy = "bankAccount", fetch = FetchType.LAZY)
    private List<AccountOperation> accountOperations;
}
