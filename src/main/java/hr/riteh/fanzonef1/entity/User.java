package hr.riteh.fanzonef1.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name="User")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private long id;

    @Column(name="username",nullable = false)
    private String username;

    @Column(name="email")
    private String email;

    @Column(name="password", nullable = false)
    private String password;

    @Column(name="dob")
    private String dateOfBirth;

    @Column(name="points")
    private int points;
}
